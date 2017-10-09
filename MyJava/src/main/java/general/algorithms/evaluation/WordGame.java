package general.algorithms.evaluation;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import interview.AutoTestUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiPredicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME;


/**
 * Given two words - initial and target word, and a dictionary contains all the words,
 * find the minimum number of steps that transform the initial word to the target.
 * (Optional, make it easier: The two words are of the same length.)
 * The transformation from wordA->wordB is legal iff wordA and wordB exist in a dictionary and wordB and wordA differ by exactly one character.
 *
 * e.g. LEAD->GOLD  : *LEAD* ; LOAD , GOAD , *GOLD* ; 3 steps
 * e.g. APE -> MAN  : APE -> APT -> OPT -> OAT -> MAT -> MAN
 *
 * CA2
 *
 * Audience: mid,senior
 */
@Slf4j
public class WordGame {

  public static void main(String[] args) {
    Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
    root.setLevel(Level.DEBUG);
    AutoTestUtils.runTestClassAndPrint(WordGame.class);
  }

  /**
   * Time: Building graph O(|V^2|) + MinHeap keeps polling and updating neighbors O(|V| * log(|V|) + |E|)
   */
  public static class SolutionDijkstras implements Solution {
    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(of = "word")
    @ToString(exclude = "neighbors")
    class Vertex {
      private String word;
      private int cost;
      private Set<Vertex> neighbors = new HashSet<>();

      Vertex(String word, int cost) {
        this.word = word;
        this.cost = cost;
      }
    }

    public List<String> transform(String begin, String end, Set<String> dict) {
      if (begin.equals(end)) {
        return ImmutableList.of(begin);
      }

      PriorityQueue<Vertex> unvisitedQ = new PriorityQueue<>(dict.size(), Comparator.comparing(Vertex::getCost));
      dict.forEach(word -> unvisitedQ.add(new Vertex(word, word.equals(begin) ? 0 : Integer.MAX_VALUE)));
      buildNeighborGraph(unvisitedQ);
      Map<String, String> predecessorMap = new HashMap<>(dict.size());

      while (!unvisitedQ.isEmpty()) {
        Vertex currWord = unvisitedQ.poll();
        log.debug("Current word {}", currWord);
        if (currWord.getWord().equals(end)) {
          break;
        }
        for (Vertex neighbor : currWord.getNeighbors()) {
          int currCost = currWord.getCost() + 1; // In a normal weighted graph, 1 should be distance(curr, neighbor)
          if (neighbor.getCost() > currCost) {
            neighbor.setCost(currCost);
            // re-order this element
            unvisitedQ.remove(neighbor);
            unvisitedQ.add(neighbor);
            predecessorMap.put(neighbor.getWord(), currWord.getWord()); // update predecessor
          }
        }
      }

      List<String> path = buildPath(begin, end, predecessorMap);
      log.debug("=============== Final path: {} =============== ", path);
      return path;
    }

    /**
     * How to model and build the graph?
     *
     * 1.Model it as a neighboring matrix
     * 2.Model it as a adjacency list
     * * a.Build it inside Vertex class: couple neighbors with the Vertex class with fixed type (HashSet in this case)
     * * b.Build it outside, use a Map: maintain a separate reference, can be a little hassle
     *
     * Assuming editDistance() function has O(1) runtime,
     * then Time is O(|V|^2)
     */
    private void buildNeighborGraph(Collection<Vertex> nodes) {
      Preconditions.checkState(noDups(nodes)); // set ensures that there is no dups
      List<Vertex> nodeList = new ArrayList<>(nodes);
      for (int i = 0; i < nodeList.size(); i++) {
        Vertex vi = nodeList.get(i);
        for (int j = i + 1; j < nodeList.size(); j++) {
          Vertex vj = nodeList.get(j);
          if (editDistance(vi.getWord(), vj.getWord()) == 1) {
            vi.neighbors.add(vj);
            vj.neighbors.add(vi);
          }
        }
      }
    }
  }

  /**
   * Time: Building graph O(|V^2|) + BFS O(|V|+|E|)
   */
  public static class SolutionBFS implements Solution {

    public List<String> transform(String begin, String end, Set<String> dict) {
      if (begin.equals(end)) {
        return ImmutableList.of(begin);
      }

      Map<String, List<String>> adjacencyList = buildAdjacencyList(dict, (a, b) -> editDistance(a, b) == 1);
      Map<String, String> predecessorMap = new HashMap<>(dict.size());

      Queue<String> q = new LinkedList<>();
      q.add(begin);
      while (!q.isEmpty()) {
        String curr = q.poll();
        if (curr.equals(end)) {
          break;
        }
        List<String> neighbors = adjacencyList.get(curr);
        if (neighbors != null) {
          neighbors.stream()
              .filter(neighbor -> !predecessorMap.containsKey(neighbor)) // this step filters out visited as well
              .peek(q::add)
              .forEach(neighbor -> predecessorMap.put(neighbor, curr));
        }
      }

      List<String> path = buildPath(begin, end, predecessorMap);
      log.debug("=============== Final path: {} ===============", path);
      return path;
    }

    /**
     * Assuming editDistance() function has O(1) runtime,
     * then Time is O(|V|^2)
     */
    private static <T> Map<T, List<T>> buildAdjacencyList(Collection<T> dict, BiPredicate<T, T> isAdjacent) {
      Map<T, List<T>> adjacencyList = new HashMap<>(dict.size());
      // Note: if compute editDistance() is expensive, use for loop like above buildNeighborGraph()
      for (T ti : dict) {
        List<T> neighborsOfI = adjacencyList.computeIfAbsent(ti, k -> new ArrayList<>());
        for (T tj : dict) {
          if (ti.equals(tj) || !isAdjacent.test(ti, tj)) {
            continue;
          }
          neighborsOfI.add(tj);
        }
      }
      log.debug("AdjacencyList building complete: {}", adjacencyList);
      return adjacencyList;
    }
  }

  public static class SolutionDFS implements Solution {

    @Override
    public List<String> transform(String begin, String end, Set<String> dict) {
      return null;
    }
  }

  private static <T> List<T> buildPath(@NonNull T begin, @NonNull T end, Map<T, T> predecessorMap) {
    log.debug("Predecessor map: {}", predecessorMap);
    // Assume guarantee there is a path exists
    List<T> path = new ArrayList<>(predecessorMap.size());
    if (!predecessorMap.containsKey(end)) {
      return path; // No predecessor means it's not reachable from begin node
    }
    path.add(end);
    T curr = end;
    while (curr != null && !curr.equals(begin)) {
      curr = predecessorMap.get(curr);
      path.add(curr);
    }
    return Lists.reverse(path);
  }

  // Assume they have the same length
  private static int editDistance(String a, String b) {
    Preconditions.checkState(a.length() == b.length());
    int count = 0;
    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) != b.charAt(i)) {
        count++;
      }
    }
    Verify.verify(count != 0);
    return count;
  }

  private static <T> boolean noDups(Collection<T> collect) {
    Set<T> set = new HashSet<>();
    return collect.stream().allMatch(set::add);
  }

  interface Solution {
    List<String> transform(@NonNull String begin, @NonNull String end, Set<String> dict);
  }

  /**
   * Follow-up Questions:
   *
   * 1.What changes to make if this has to become an online service? How will you minimize latency? (Doublet solver)
   * * Pre-compute pairs and store the paths. Use all-pairs shortest path alg with modifications to store the paths
   * * Handle updates to dictionaries. How? Recompute and invalidate current solutions? Or perform a partial re-computation?
   *
   * 2.Will cycles be output? (If represented as a graph)
   * * Essentially, will shortest path computed ever contain a cycle? (smile) No. Why?
   */

  /**
   * Evaluation
   *
   * A great candidate:
   * Will be able to reduce this problem into one of the two forms described above fairly quickly (5 min).
   * If the graph form is chosen
   * Will spend some time trying to find an efficient way to form the graph representation, will comment on complexity of that process.
   * Will implement one of the two solutions completely.
   * Will correctly comment on complexity (realizes use of heap vs array while maintaining min dist, realizes when to stop evaluation (chosen node is target) )
   * Will be able to get to the followup questions and provide answers
   * If the edit-distance based solution is chosen
   * TODO
   *
   * A good candidate:
   * Will be able to reduce this problem into one of the two forms described above (10 min). May need a little hint. (e.g. Two words are friends iff they differ by one char.. or some such)
   * If the graph-based solution is chosen
   * Will find a naive way of loading word list into graph or focus mostly on the transformation aspect.
   * Will provide a semi-working solution .
   * If Graph method chosen, will not be able to correctly implement Dijkstra/A* .
   * Will not be able to comment on complexity correctly.
   * If the edit-distance based solution is chosen
   * TODO
   *
   * A poor candidate:
   * Will not be able to map this problem into either of these forms.
   * Will look to store the words in a hash and attempt a brute force solution (some form of N^2 lookups per character , followed by some exponential eval of all possibilities)
   * Will not be able to correctly estimate complexity of brute force solution .
   * Will not be able to pick up on hints about alternate representations (e.g. graph)
   */



  private Solution solutionToTest;

  @Before
  public void setup() {
    solutionToTest = new SolutionBFS();
  }

  @Test
  public void test_sanity_check() {
    Set<String> dict = ImmutableSet.of("AAA");
    String begin = "AAA";
    String end = "AAA";

    List<String> actualPath = solutionToTest.transform(begin, end, dict);

    assertResults(actualPath, begin, end, dict);
  }

  @Test
  public void test_negative_case() {
    Set<String> dict = ImmutableSet.of("AAA","ABC");
    String begin = "AAA";
    String end = "ABC";

    List<String> actualPath = solutionToTest.transform(begin, end, dict);

    Assert.assertTrue(actualPath.isEmpty());
  }

  @Test
  public void test_simplest_case() {
    /**
     * AAA - ABA - ABC
     *     \     /
     *       AAC
     */
    Set<String> dict = ImmutableSet.of("AAC", "ABA", "ABC", "AAA");
    String begin = "ABC";
    String end = "AAA";

    List<String> actualPath = solutionToTest.transform(begin, end, dict);

    assertResults(actualPath, begin, end, dict);
  }

    @Test
  public void test_no_redundant_elems() {
    Set<String> dict = ImmutableSet.of("OAT", "MAN", "APT", "APE", "OPT", "MAT");
    String begin = "APE";
    String end = "MAN";

    List<String> actualPath = solutionToTest.transform(begin, end, dict);

    assertResults(actualPath, begin, end, dict);
  }

  @Test
  public void test_redundant() {
    Set<String> dict = ImmutableSet.of("OAT", "MAN", "APT", "ATT", "BPT", "CPT", "APE", "OPT", "MAT");
    String begin = "APE";
    String end = "MAN";

    List<String> actualPath = solutionToTest.transform(begin, end, dict);

    assertResults(actualPath, begin, end, dict);
  }

  private void assertResults(List<String> actualPath, String begin, String end, Set<String> dict) {
    Assert.assertNotNull(actualPath);
    Assert.assertEquals(begin, actualPath.get(0));
    Assert.assertEquals(end, actualPath.get(actualPath.size() - 1));
    Assert.assertTrue("Wrong path: " + actualPath, validatePath(actualPath, dict));
  }

  private boolean validatePath(List<String> path, Set<String> dict) {
    for (int i = 0; i < path.size() - 1; i++) {
      String curr = path.get(i);
      String next = path.get(i + 1);
      int distance = editDistance(curr, next);
      if (distance != 1 || !dict.contains(curr) || !dict.contains(next)) {
        log.error("{} and {} have distance of {}", curr, next, distance);
        return false;
      }
    }
    return true;
  }
}
