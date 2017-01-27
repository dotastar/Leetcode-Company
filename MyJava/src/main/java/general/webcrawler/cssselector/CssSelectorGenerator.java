package general.webcrawler.cssselector;

import general.webcrawler.parser.FieldMatchCondition;
import general.webcrawler.parser.ListItemMatchCondition;
import general.webcrawler.parser.ListIterationCssSelector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.util.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;

/**
 * CSS Selector auto generator
 * The goal is to give profile parser a CSS path auto-generate and a self-healing capability, make them maintenance-free
 * and scalable in creating new parsers
 *
 * How:
 * 1.Create a designed-for-parsing profile for each parser, make each text field that we want to parse unique and final
 * 2.The CssSelectorGenerator will generate CssSelector expression based on each profile page
 * 3.The profile parser will use the generated CSS selector expressions to parse profiles
 *
 * Current problems:
 * 1.a.Some fields can't be created easily, e.g. contributed project of Github
 * --> Create an environment for it, e.g. create a few accounts, and let the test account make contribution to their
 * projects
 * 1.b.Some fields can't be final, still need to be updated periodically, e.g.Contributions in the last year
 * --> Workaround: try to find some unique text around that field which is unique and final
 *
 * 2.Sometimes we don't know how many elements there are, usually just get a list of elements, and iterate through them
 * --> Solution:
 * a.find the list element, generate a css selector
 * b.find one list item node and generate a css selector from list to the list item (root node is the list node we find
 * in step a)
 * c.so now we know how to iterate through every list item in that list
 * d.then generate css selector for every field we need within the list item from the list item node
 *
 * 3.How to search for an html element that doesn't have a text, e.g. the link(src/href) of an image in a profile
 * --> One solution to this is to define a relative relation to a known element, like a sibling/common ancestor, use the
 * relation to navigate
 *
 */
@Data
@Slf4j
public class CssSelectorGenerator {

  // Below is just a rough categorization of attributes from https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes
  private static final Set<String> IDENTIFIABLE_ATTRIBUTES = ImmutableSet.of("alt", "autocomplete", "autofocus", "autoplay", "autosave", "bgcolor", "class",
      "color", "for", "form", "formaction", "headers", "id", "itemid", "itemprop", "keytype", "kind", "label", "language", "name", "placeholder", "required",
      "scope", "style", "summary", "type", "target", "usemap", "value");
  private static final Set<String> FLAKY_ATTRIBUTES = ImmutableSet.of("accept", "accept-charset", "accesskey", "action", "align", "async", "border", "buffered",
      "challenge", "charset", "checked", "cite", "code", "codebase", "cols", "colspan", "content",
      "contenteditable", "contextmenu", "controls", "coords", "data", "datetime", "default", "defer", "dir",
      "dirname", "disabled", "download", "draggable", "dropzone", "enctype", "height", "hidden", "high", "href",
      "hreflang", "http-equiv", "icon", "ismap", "lang", "list", "loop", "low", "manifest", "max", "maxlength",
      "media", "method", "min", "multiple", "muted", "novalidate", "open", "optimum", "pattern", "ping", "poster",
      "preload", "radiogroup", "readonly", "rel", "reversed", "rows", "rowspan", "sandbox", "scoped", "seamless",
      "selected", "shape", "size", "sizes", "span", "spellcheck", "src", "srcdoc", "srclang", "srcset", "start",
      "step", "tabindex", "title", "width", "wrap");

  private Multiset<TagIdentifier> tagIdCounts = HashMultiset.create();
  private Map<Element, List<TagIdentifier>> tag2Id = new HashMap<>();

  public static String extractField(Element element, FieldMatchCondition condition) {
    String value;
    switch (condition.getFieldType()) {
    case ID:
      value = element.id();
      break;
    case CLASS:
      value = element.className();
      break;
    case ATTRIBUTE:
      Preconditions.checkNotNull(condition.getAttributeName());
      value = element.attr(condition.getAttributeName());
      break;
    case OWN_TEXT:
      value = element.ownText();
      break;
    case TEXT:
      value = element.text();
      break;
    default:
      throw new IllegalArgumentException("Unknown TargetType " + condition.getFieldType());
    }
    return value;
  }

  public static boolean isMatch(Element element, FieldMatchCondition condition) {
    String fieldValue = extractField(element, condition);
    Preconditions.checkState(fieldValue != null);
    return isMatch(fieldValue, condition);
  }

  public static boolean isMatch(String fieldValue, FieldMatchCondition condition) {
    Preconditions.checkState(fieldValue != null);

    switch (condition.getMatchType()) {
    case EQUALS:
      return fieldValue.equals(condition.getMatchValue());
    case CONTAINS:
      return fieldValue.contains(condition.getMatchValue());
    default:
      throw new IllegalArgumentException("Unknown EqualType " + condition.getMatchValue());
    }
  }

  /**
   * Link selectors together
   *
   * @param selectors: selectors have to be in reverse order
   */
  private static String link(List<String> selectors) {
    Preconditions.checkArgument(!selectors.isEmpty());

    StringBuilder sb = new StringBuilder();
    for (int i = selectors.size() - 1; i > 0; i--) {
      sb.append(selectors.get(i)).append(" > ");
    }
    sb.append(selectors.get(0));

    Preconditions.checkState(sb.length() > 0);
    return sb.toString();
  }

  // Generate a plain CSS Selector that could link ancestor and node
  // Note: the selector could generate multiple nodes
  private static String link(Element ancestor, Element node) {
    List<String> selectors = new ArrayList<>();
    while (node != ancestor) {
      selectors.add(node.tagName());
      node = node.parent();
    }
    String selector = link(selectors);
    log.debug("Generate selector {} links {} to {}", selector, node2String(ancestor), node2String(node));
    return selector;
  }

  private static List<Attribute> flat(Attribute attribute) {
    String[] classes = attribute.getValue().split(" ");
    return Arrays.stream(classes)
        .filter(name -> name.length() > 0)
        .map(name -> new Attribute(attribute.getKey(), name))
        .collect(Collectors.toList());
  }

  // Lowest common ancestor
  private static Element lcs(Element... nodes) {
    Preconditions.checkArgument(nodes.length > 1);
    Element ancestor = lcs(nodes[0], nodes[1]);
    for (int i = 2; i < nodes.length; i++) {
      ancestor = lcs(ancestor, nodes[i]);
    }
    return ancestor;
  }

  private static Element lcs(Element node1, Element node2) {
    Preconditions.checkNotNull(node1);
    Preconditions.checkNotNull(node2);
    return lcs(node1, depth(node1), node2, depth(node2));
  }

  private static Element lcs(Element n1, int d1, Element n2, int d2) {
    if (d1 < d2) {
      return lcs(n2, d2, n1, d1);
    }
    int diff = d1 - d2;
    Preconditions.checkState(diff >= 0);
    while (diff-- != 0) {
      n1 = n1.parent();
    }
    while (n1 != n2) {
      n1 = n1.parent();
      n2 = n2.parent();
    }
    return n1;
  }

  private static int depth(Element node) {
    int depth = 0;
    while (node != null) {
      depth++;
      node = node.parent();
    }
    return depth;
  }

  private static String node2String(Element node) {
    return node.tagName() + node.attributes();
  }

  public void initialize(Element root) {
    if (root == null) {
      return;
    }

    Tag tag = root.tag();
    Attributes attributes = root.attributes();
    List<Attribute> flatAttributes = new ArrayList<>();
    attributes.forEach(attribute -> {
      if (attribute.getKey().equalsIgnoreCase("class") || attribute.getKey().equalsIgnoreCase("style")) {
        flatAttributes.addAll(flat(attribute));
      } else {
        flatAttributes.add(attribute);
      }
    });

    final List<TagIdentifier> tagIds = new ArrayList<>();
    flatAttributes.forEach(attribute -> {
      TagIdentifier attr = new TagIdentifier(attribute);
      TagIdentifier tagAttr = new TagIdentifier(tag, attribute);
      tagIdCounts.add(attr);
      tagIdCounts.add(tagAttr);
      tagIds.add(attr);
      tagIds.add(tagAttr);
    });
    tag2Id.put(root, tagIds);

    root.children().forEach(this::initialize);
  }

  /**
   * TODO: the result should include indication of if it is identifiable(no other Elements)
   */
  public String autoGenerateSingleItem(Element root, FieldMatchCondition condition) {
    Element node = search(root, condition);
    return generateCssSelector(node);
  }

  public ListIterationCssSelector autoGenerateListItems(Element root, ListItemMatchCondition listItemMatchCondition) {
    Pair<FieldMatchCondition, FieldMatchCondition> sameFields = listItemMatchCondition.getSameFieldBetweenSiblingItems();
    FieldMatchCondition[] listItemFields = listItemMatchCondition.getFieldsWithinListItem();
    Element listRoot = searchLCS(root, sameFields.getKey(), sameFields.getValue());
    if (listRoot == null) {
      log.error("Couldn't find LCS in conditions {}, {}", sameFields.getKey(), sameFields.getValue());
      return null;
    }
    // Generate list selector
    log.debug("Found node {} as root of listItems", node2String(listRoot));
    String listSelector = generateCssSelector(listRoot);
    log.debug("Generated list root selector: {} for FieldMatchCondition {}", listSelector, sameFields);
    ListIterationCssSelector result = new ListIterationCssSelector(listSelector);
    // Generate list-to-item selector
    Element itemRoot = searchLCS(listRoot, listItemFields);
    CssSelectorGenerator listItemGenerator = new CssSelectorGenerator();
    listItemGenerator.initialize(itemRoot);
    String list2ItemSelector = link(listRoot, itemRoot);
    log.debug("Generated list to item selector: {}", list2ItemSelector);
    result.setList2ItemSelector(list2ItemSelector);
    // Generate item to each field selector
    for (FieldMatchCondition fieldMatch : listItemFields) {
      String fieldSelector = listItemGenerator.autoGenerateSingleItem(itemRoot, fieldMatch);
      log.debug("Generated field of list item selector: {} for FieldMatchCondition {}", fieldSelector, fieldMatch);
      result.getFieldsSelector().add(fieldSelector);
    }
    return result;
  }

  public Element search(Element root, FieldMatchCondition condition) {
    log.debug("Searching for node... \tFieldMatchCondition: {}, under root({})", condition, node2String(root));
    Element result = searchRecursively(root, condition);
    if (result == null) {
      log.error("Couldn't find node in condition {}", condition);
    } else {
      log.debug("Found node {}", node2String(result));
    }
    return result;
  }

  private Element searchRecursively(Element root, FieldMatchCondition condition) {
    if (root == null || isMatch(root, condition)) {
      return root;
    }
    for (Element child : root.children()) {
      Element target = searchRecursively(child, condition);
      if (target != null) {
        return target;
      }
    }
    return null;
  }

  private Element searchLCS(Element root, FieldMatchCondition... fieldMatchConditions) {
    Preconditions.checkArgument(fieldMatchConditions.length > 1);
    log.debug("Searching for LCS under node({}) for FieldMatchConditions: {}", node2String(root), Arrays.toString(fieldMatchConditions));
    Element[] targets = new Element[fieldMatchConditions.length];
    for (int i = 0; i < fieldMatchConditions.length; i++) {
      targets[i] = search(root, fieldMatchConditions[i]);
    }
    Element result = lcs(targets);
    if (result == null) {
      log.error("Couldn't find LCS for FieldMatchConditions: {}", Arrays.toString(fieldMatchConditions));
    } else {
      log.debug("Found LCS {}", node2String(result));
    }
    return result;
  }

  // Bottom-up generate css selector: From leaf to root
  // Another way to do it is top-down, could reduce the tree size by deleting parents if current node is unique,
  // once the tree size is small, it will have a lot more unique tagId, how to choose a robust one is a question
  // it will also need to re-initialize tagIdCount and tag2Id which is more complex
  public String generateCssSelector(Element node) {
    if (node == null) {
      return null;
    }
    Elements parents = node.parents();
    Element root = parents.get(parents.size() - 1);
    parents.add(0, node);
    List<String> selectorChain = new ArrayList<>();
    for (Element parent : parents) {
      if (generateByNode(parent, selectorChain)) {
        break;
      }
      // TODO: if keep escalating will resolve the same number of results, should use the lowest node(trim path)
      String partialSelector = link(selectorChain);
      if (isUnique(partialSelector, root)) {
        break;
      }
    }

    String finalSelector = link(selectorChain);
    return finalSelector;
  }

  private boolean generateByNode(Element node, List<String> chain) {
    List<TagIdentifier> tagIdentifiers = tag2Id.get(node);
    TagIdentifier bestTagId = tagIdentifiers.stream()
        .filter(this::isNotGenerated)
        .max(((o1, o2) -> priority(o1) - priority(o2)))
        .orElse(null);

    // Generate CssSelector String for the current node
    boolean stopNext = false;
    StringBuilder selectorBuilder = new StringBuilder();
    if (bestTagId != null && !FLAKY_ATTRIBUTES.contains(bestTagId.getAttribute().getKey())) {
      selectorBuilder.append(bestTagId.toCssSelector());
      stopNext = isUnique(bestTagId);
    } else {
      selectorBuilder.append(node.tagName());
    }

    if (!stopNext && !node.siblingElements().isEmpty()) {
      // TODO: consider Top-down approach, it can avoid unnecessarily appending :nth-child.
      // Append index to see if it makes it unique
      selectorBuilder.append(":nth-child(").append(1 + node.elementSiblingIndex()).append(")");
    }

    chain.add(selectorBuilder.toString());

    log.debug("Generate selector:{} for node({})", selectorBuilder, node2String(node));
    return stopNext;
  }

  private boolean isUnique(String selector, Element root) {
    Elements selected = Selector.select(selector, root);
    Preconditions.checkState(selected.size() > 0);
    if (selected.size() == 0) {
      log.error("Wrong css selector: {}, no Element found!", selector);
    }
    return selected.size() == 1;
  }

  private boolean isUnique(TagIdentifier identifier) {
    if (identifier == null) {
      return false;
    }
    String attrKey = identifier.getAttribute().getKey();
    if (attrKey.equalsIgnoreCase("id")) {
      return true;
    } else {
      return tagIdCounts.count(identifier) == 1;
    }
  }

  // TODO: Need more cases
  private boolean isNotGenerated(TagIdentifier identifier) {
    String key = identifier.getAttribute().getKey();
    String value = identifier.getAttribute().getValue();
    try {
      boolean isGenerated = StringUtils.isNumeric(value) || LocalDate.parse(value) != null || key.contains("data-");
      if (isGenerated) {
        log.debug("Classify {} as generated TagIdentifier", identifier);
      }
      return !isGenerated;
    } catch (IllegalArgumentException e) {
      return true;
    }
  }

  // TODO: Need better algorithm for setting priority
  private int priority(TagIdentifier identifier) {
    if (FLAKY_ATTRIBUTES.contains(identifier.getAttribute().getKey())) {
      return -100000;
    }
    // Another strategy is isUnique > IDENTIFIABLE_ATTRIBUTES,
    // this will eliminate more cases of tag + index,
    // but will reduce robustness (unrecognizable flaky generated tagId)
    int score = identifier.hasTag() ? 0 : 1;
    if (IDENTIFIABLE_ATTRIBUTES.contains(identifier.attribute.getKey())) {
      score += 1000;
    }
    if (isUnique(identifier)) {
      score += 100;
    } else {
      int occurrence = tagIdCounts.count(identifier);
      score += -occurrence;
    }
    return score;
  }

  @Data
  @AllArgsConstructor
  @EqualsAndHashCode(of = { "tag", "attribute" })
  private static class TagIdentifier {
    private Tag tag;
    @NonNull private Attribute attribute;

    public TagIdentifier(Attribute attribute) {
      this.attribute = attribute;
    }

    private boolean hasTag() {
      return tag != null;
    }

    public String toCssSelector() {
      StringBuilder sb = new StringBuilder();
      if (hasTag()) {
        sb.append(tag);
      }
      if (attribute.getKey().equalsIgnoreCase("class")) {
        sb.append('.').append(attribute.getValue());
      } else if (attribute.getKey().equalsIgnoreCase("id")) {
        sb.append('#').append(attribute.getValue());
      } else {
        /**
         * [attr] has attribute
         * [attr=val] has attribute with value val
         * [attr!=val] does not have attribute with value val
         * [attr^=val] attribute begins with val
         * [attr$=val] attribute ends with val
         * [attr*=val] attribute includes val
         * [attr~=val] attribute includes val as a word
         * [attr|=val] attribute begins with val and optional hyphen
         */
        sb.append('[').append(attribute.getKey()).append("=").append(attribute.getValue()).append("]");
      }
      return sb.toString();
    }

    @Override
    public String toString() {
      if (tag == null) {
        return attribute.toString();
      }
      return tag.getName() + ":" + attribute.toString();
    }
  }
}
