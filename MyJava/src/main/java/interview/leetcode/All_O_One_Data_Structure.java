package interview.leetcode;

import interview.AutoTestUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * https://leetcode.com/problems/all-oone-data-structure/
 *
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */
public class All_O_One_Data_Structure {

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(All_O_One_Data_Structure.class);
  }

  private static class Node {
    // Stores all keys associated with this count
    final Set<String> keys = new HashSet<>();
    int count;
    Node prev;
    Node next;
  }

  private void linkNodes(Node prev, Node next) {
    prev.next = next;
    next.prev = prev;
  }

  // Append target node to source node
  private void appendNodeToList(Node source, Node target) {
    linkNodes(target, source.next);
    linkNodes(source, target);
  }

  // Append target node to source node
  private void removeNodeFromList(Node target) {
    linkNodes(target.prev, target.next);
    target.next = null;
    target.prev = null;
  }

  /**
   * null -> head -> Node... -> tail -> null
   * 1. head stores min;
   * 2. tail stores max;
   */
  private final Node head = new Node();
  private final Node tail = new Node();

  /**
   * countToNodes only keeps nodes that has a non-zero count.
   * Nodes with zero count must be removed in order to keep inc(), dec() O(1) time.
   */
  private final Map<Integer, Node> countToNodes = new HashMap<>();
  private final Map<String, Integer> keyToCounts = new HashMap<>();

  /** Initialize your data structure here. **/
  public All_O_One_Data_Structure() {
    linkNodes(head, tail);
    head.count = Integer.MIN_VALUE;
    tail.count = Integer.MAX_VALUE;
  }

  /**
   * Inserts a new key <Key> with value 1. Or increments an existing key by 1.
   *
   * Core idea:
   * Use two maps + an ordered linkedin list to solve this problem.
   *
   * Maintain the head and tail of the the linkedin list, so getMaxKey() nad getMinKey() are satisfied.
   * Be noted that a Node should not have empty keys, otherwise getMaxKey() nad getMinKey() won't be O(1) time.
   *
   * Create two hashmaps, one stores key to count, the other stores count to the linkedin node.
   * Since inc() and dec() requires O(1) key lookup, we need a hashmap keyed by the input parameter "key".
   * Since inc() and dec() requires O(1) look up of the neighbor elements (count +/- 1), we need a second map stores count to Node.
   * Since multiple keys may have the same count, each node must store a set of keys so that inc() is guaranteed O(1).
   *
   * We split inc() into two cases:
   * 1. The key exist
   * 2. The key doesn't exist
   *
   * There are a few cases:
   * 1. inc() a new key, move it to a new bucket
   *    Example:
   *    Given null -> head -> tail -> null
   *    Do inc("foo")
   *    Expect null -> head -> Node(["foo"], 1) -> tail -> null
   *
   * 2. inc() an existing key, move it to a new bucket
   *    Example:
   *    Given null -> head -> Node(["foo"], 1) -> tail -> null
   *    Do inc("foo")
   *    Expect null -> head -> Node(["foo"], 2) -> tail -> null
   *
   * 3. inc() a new key, move it to an existing bucket
   *    Example:
   *    Given null -> head -> Node(["foo"], 1) -> tail -> null
   *    Do inc("boo")
   *    Expect null -> head -> Node(["foo", "bar"], 1) -> tail -> null
   *
   * 4. inc() an existing key, drop the current bucket, move it to an existing bucket
   *    Example:
   *    Given null -> head -> Node(["foo"], 1) -> Node(["bar"], 2) -> tail -> null
   *    Do inc("foo")
   *    Expect null -> head -> Node(["foo", "bar"], 2) -> tail -> null
   *
   * 5. inc() an existing key, keep the current bucket, move it to an existing bucket
   *    Example:
   *    Given null -> head -> Node(["foo", "baz"], 1) -> Node(["bar"], 2) -> tail -> null
   *    Do inc("foo")
   *    Expect null -> head  -> Node(["baz"], 1) -> Node(["foo", "bar"], 2) -> tail -> null
   *
   *    Three states to maintain: List, count map, Node map.
   *
   *    Four operations:
   *    1. Insert (a node for List, a string for the keys for NodeMap, a count for CountMap)
   *    2. Remove (List, NodeMap, CountMap)
   *    3. Merge (List, NodeMap)
   *    4. Update count (CountMap)
   *
   *
   */
  public void inc(String key) {
    if (keyToCounts.containsKey(key)) {
      changeKeyByOffset(key, 1);
    } else {
      keyToCounts.put(key, 1);
      Node desiredNode = setupDesiredNode(head, 1);
      desiredNode.keys.add(key);
    }
  }

  public void dec(String key) {
    if (!keyToCounts.containsKey(key)) {
      return;
    }
    Integer currCnt = keyToCounts.get(key);
    if (currCnt > 1) {
      changeKeyByOffset(key, -1);
    } else {  // currCnt == 1
      removeKeyAndEmptyNode(currCnt, key);                                  // clean up the old node on condition
      keyToCounts.remove(key);
    }
  }

  private void changeKeyByOffset(String key, int offset) {
    Integer cnt = keyToCounts.get(key);
    assert cnt != null;
    Integer desiredCnt = cnt + offset;
    Node currNode = countToNodes.get(cnt);                                // get the existing bucket
    Node desiredNode = setupDesiredNode(currNode, desiredCnt); // upsert the desired new bucket
    assert countToNodes.get(desiredCnt) == desiredNode;
    keyToCounts.put(key, desiredCnt);
    assert desiredNode.count == keyToCounts.get(key);
    desiredNode.keys.add(key);                                            // move the key to the desired bucket
    removeKeyAndEmptyNode(cnt, key);                                      // clean up the old node on condition
  }

  private Node setupDesiredNode( Node currNode, Integer desiredCnt) {
    Node desiredNode = countToNodes.get(desiredCnt);
    if (desiredNode == null) {
      desiredNode = createAndRegisterBucket(desiredCnt);
      appendNodeToList(currNode, desiredNode);
    }
    return desiredNode;
  }

  private void removeKeyAndEmptyNode(Integer cnt, String key) {
    Node currNode = countToNodes.get(cnt);
    if (currNode == null) {
      return;
    }
    currNode.keys.remove(key);
    if (currNode.keys.isEmpty()) {
      removeNodeFromList(currNode);
      countToNodes.remove(cnt);
    }
  }

  private Node createAndRegisterBucket(Integer count) {
    assert !countToNodes.containsKey(count);
    // create a bucket
    Node node = new Node();
    node.count = count;
    countToNodes.put(count, node);
    return node;
  }

  /** Returns one of the keys with maximal value. */
  public String getMaxKey() {
    Node maxNode = tail.prev;
    if (maxNode == head) {
      return "";
    } else if (maxNode.keys.isEmpty()) {
      return "";
    } else {
      return maxNode.keys.iterator().next();
    }
  }

  /** Returns one of the keys with Minimal value. */
  public String getMinKey() {
    Node minNode = head.next;
    if (minNode == tail) {
      return "";
    } else if (minNode.keys.isEmpty()) {
      return "";
    } else {
      return minNode.keys.iterator().next();
    }
  }

  /**
   * Tests
   */
  @Test
  public void test_inc() {
    All_O_One_Data_Structure obj = new All_O_One_Data_Structure();
    assertThat(obj.getMaxKey()).isEqualTo("");
    assertThat(obj.getMinKey()).isEqualTo("");
    obj.inc("foo");
    obj.inc("foo");
    assertThat(obj.getMaxKey()).isEqualTo("foo");
    assertThat(obj.getMinKey()).isEqualTo("foo");
    obj.inc("bar");
    obj.inc("bar");
    obj.inc("bar");
    assertThat(obj.getMaxKey()).isEqualTo("bar");
    assertThat(obj.getMinKey()).isEqualTo("foo");
  }

  @Test
  public void test_dec() {
    All_O_One_Data_Structure obj = new All_O_One_Data_Structure();
    obj.inc("foo");
    obj.inc("bar");
    obj.inc("bar");
    assertThat(obj.getMaxKey()).isEqualTo("bar");
    assertThat(obj.getMinKey()).isEqualTo("foo");
    obj.dec("bar");
    obj.dec("bar");
    assertThat(obj.getMaxKey()).isEqualTo("foo");
    assertThat(obj.getMinKey()).isEqualTo("foo");
    obj.dec("foo");
    assertThat(obj.getMaxKey()).isEqualTo("");
    assertThat(obj.getMinKey()).isEqualTo("");
  }

  @Test
  public void test_dec_hello() {
    All_O_One_Data_Structure obj = new All_O_One_Data_Structure();
    obj.dec("hello");
    assertThat(obj.getMaxKey()).isEqualTo("");
    assertThat(obj.getMinKey()).isEqualTo("");
  }
}
