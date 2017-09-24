package general.algorithms.evaluation;

import com.google.common.base.Verify;
import interview.AutoTestUtils;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * Provide an implementation of the following interface
 */
public class MaxStackPeekAndPop {

  /**
   * Note: This is not "Implement a stack that also provides the maximum value". Solutions using two stacks will not work reasonably, do not accept them.
   *
   * Interviewer tips: This is very similar to another, much simpler interview question (which provides peekMax() but not popMax()),
   * and you should be prepared to redirect the candidate if they seem to be ignoring popMax or that the 'middle' of the stack may be modified.
   * Good solutions tend to be along the lines of a List and a Tree with some method of keeping them in sync.
   */
  public interface MaxStack<T extends Comparable<T>> {

    // The standard three Stack methods - push adds an element to the stack
    void push(T toPush);

    // Peek returns the top value on the stack
    T peek();

    // Pop removes and returns the top value on the stack
    T pop();

    // Two special methods, so this isn't just 'implement a stack'
    // PeekMax() returns the highest value in the stack
    //      (remember that T must implement Comparable)
    T peekMax();

    // popMax() removes and returns the highest value in the stack
    T popMax();
  }

  /**
   * Potential approaches and pitfalls:
   * 1.Arrays are generally bad news, because deleting from the middle is an O(n) operation
   * 2.LinkedList and Heap
   * This can work well, but remove() from both the LinkedList and the Heap are O(n) operations because of the difficulty of finding an element in the middle. Each element will need a pointer to its partner element, and will need to know how to delete itself.
   * 3.LinkedList and BST
   * This is perhaps easier, since a 'standard' BST can be used. Each BST element can point to its element in the list, and deleting a known node from a LinkedList is easy. They will have to write their own List, though, to get access to the nodes themselves. They will also need to keep track of the maximum element in the BST through whatever means to support O(1) peekMax.
   * 4.Custom objects
   * These are objects that function as both list elements and tree elements, and thus will typically have at least 5 pointers on them (next, prev, parent, left, right). Some may have 7 (next, prev, larger, smaller, parent, left, right) to support the three types of lookups that are needed (binary search into sorted list, find max, find top). While this approach generally indicates that the candidate has understood what needs to be done well, actual implementation tends to be complex and spend a lot of time on 'peripheral' functions like the binary search. If the candidate suggests this, feel free to tell them to assume the existence of the helper functions that they need (such as the tree traversal and balancing code), rather than spending time writing those. Keep the interview focused on keeping the structures in sync, not implementing a BST.
   * 5.Another approach is to build an object with a 'deleted' flag and a reference to it's partner object, and just flipping that on removal. This means that peek() and pop() need to dig to find the first 'real' element, but avoids modifying the middle of the stack/heap at the expense of some memory. It's also a good solution for synchronization issues, since it makes the areas that need to be held under lock simpler.
   *
   * There are also tradeoffs to (potentially) be made around the speed of different methods. The suggested solution gives O(log('n)) for push, pop, and popMax, but...
   * A pair of linked lists can give O('n) push, O(1) pop and popMax. One list stores elements in insertion order, the other stores in sorted order (and does a linear scan on insertion), but removing the top of one and linking that to the element in the other is constant time, good for situations where quick removal is more important.
   * One linked list can provide O(1) push and pop, O('n) popMax by scanning for the largest element. Generally discouraged, but some candidates may mention that it is worthwhile if popMax is expected to be rare
   */

  /**
   * Followup questions:
   * What are the performance characteristics (space required, speed of the operations compared to O(1) for normal stacks)?
   * How do you handle duplicate elements? If the stack is {5,3,5} and popMax is called, what should the new state of the stack be?
   * (Hard) What needs to be done to make this structure thread-safe?
   */

  /**
   * Scoring:
   * A great candidate will
   * Implement the stack cleanly and correctly, with clear and easy-to-follow logic. Peeks should be O(1), and the pops should be no worse than O(log('n)).
   * Ask about the relative importance of popMax and pop, to make the correct performance tradeoff
   * Be able to at least discuss all three followup questions
   * Complete the question in less than 30 minutes
   *
   * A good candidate will
   * Implement the stack correctly and with the right runtimes, but potentially not in the cleanest fashion. In particular, their synchronization code may be messy.
   * May not consider performance or space tradeoffs while implementing, but can discuss them if they are brought up.
   * May not understand the difficulties in synchronizing this stack compared to another stack, but can discuss the other two followups
   * May take 30-40 minutes to complete the question
   *
   * A bad candidate may
   * Have coherence issues between their data structures
   * May treat their data structure as a stack despite having control of the implementation
   * Only implement the version without popMax() and have difficulty implementing popMax after it is pointed out
   * May not be able to discuss performance tradeoffs or duplicate elements
   * Take more than 40 minutes
   *
   * A terrible candidate may
   * Not be able to complete the stack, or have serious correctness issues with their implementation
   * Not be able to identify the data structures that they need.
   * Need to be cut off to avoid the interview running over
   */
  @Data
  @ToString(exclude = {"next", "prev"})
  @NoArgsConstructor
  private static class LinkedNode<T> {
    private LinkedNode<T> next;
    private LinkedNode<T> prev;
    private T data;

    LinkedNode(T data) {
      this.data = data;
    }
  }

  public static class MaxStackImplBST_naive<T extends Comparable<T>> implements MaxStack<T> {
    private LinkedNode<T> listHead;
    private LinkedNode<T> listTail;
    private TreeMap<T, List<LinkedNode<T>>> maxTree = new TreeMap<>(Comparator.naturalOrder());

    @Override
    public void push(T toPush) {
      LinkedNode<T> newTail = pushToStack(toPush);
      pushToMaxTree(newTail);
    }

    @Override
    public T peek() {
      return listTail == null ? null : listTail.getData();
    }

    @Override
    public T pop() {
      if (listTail == null) {
        return null;
      }

      LinkedNode<T> popFromStack = popFromStack();
      LinkedNode<T> popFromMaxTree = popFromMaxTree(popFromStack.getData());
      Verify.verify(popFromStack.equals(popFromMaxTree));
      return popFromStack.getData();
    }

    @Override
    public T peekMax() {
      return maxTree.isEmpty() ? null : maxTree.lastKey();
    }

    @Override
    public T popMax() {
      if (listTail == null) {
        return null;
      }

      // Delete from tree
      Map.Entry<T, List<LinkedNode<T>>> maxTreeEntry = maxTree.lastEntry();
      LinkedNode<T> removed = popFromMaxTree(maxTreeEntry.getKey());

      // Delete from stack
      LinkedNode<T> prev = removed.getPrev();
      LinkedNode<T> next = removed.getNext();
      if (prev != null) {
        prev.setNext(next);
      } else { // popping head
        Verify.verify(removed.equals(listHead));
        listHead = next;
      }
      if (next != null) {
        next.setPrev(prev);
      } else { // popping tail
        Verify.verify(removed.equals(listTail));
        listTail = prev;
      }
      return removed.getData();
    }

    private LinkedNode<T> pushToStack(T toPush) {
      LinkedNode<T> newTail = new LinkedNode<>(toPush);
      // TODO: create dummy node for both head and tail can the save check for null
      if (listHead == null) {
        listHead = newTail;
        listTail = newTail;
      } else {
        listTail.next = newTail;
        newTail.prev = listTail;
        listTail = newTail;
      }
      return newTail;
    }

    private LinkedNode<T> popFromStack() {
      LinkedNode<T> oldTail = listTail;
      listTail = listTail.prev;
      if (listTail == null) {
        listHead = null;
      }
      return oldTail;
    }

    private void pushToMaxTree(LinkedNode<T> toPush) {
      List<LinkedNode<T>> dups = maxTree.computeIfAbsent(toPush.getData(), mapKey -> new LinkedList<>());
      dups.add(toPush);
    }

    private LinkedNode<T> popFromMaxTree(T elem) {
      List<LinkedNode<T>> dups = maxTree.get(elem);
      Verify.verifyNotNull(dups);
      Verify.verify(!dups.isEmpty());
      LinkedNode<T> removed = dups.remove(dups.size() - 1);
      if (dups.isEmpty()) {
        maxTree.remove(elem);
      }
      return removed;
    }
  }

  public static class MaxStackImplBST_withDummyNodes<T extends Comparable<T>> implements MaxStack<T> {
    private LinkedNode<T> listHead;
    private LinkedNode<T> listTail;
    private TreeMap<T, List<LinkedNode<T>>> maxTree = new TreeMap<>(Comparator.naturalOrder());

    public MaxStackImplBST_withDummyNodes() {
      listHead = new LinkedNode<>();
      listTail = new LinkedNode<>();
      listHead.setNext(listTail);
      listTail.setPrev(listHead);
    }

    @Override
    public void push(T toPush) {
      LinkedNode<T> newTail = pushToStack(toPush);
      pushToMaxTree(newTail);
    }

    @Override
    public T peek() {
      return maxTree.isEmpty() ? null : listTail.getPrev().getData();
    }

    @Override
    public T pop() {
      if (maxTree.isEmpty()) {
        return null;
      }

      LinkedNode<T> popFromStack = popFromStack();
      LinkedNode<T> popFromMaxTree = popFromMaxTree(Verify.verifyNotNull(popFromStack.getData()));
      Verify.verify(popFromStack.equals(popFromMaxTree));
      return popFromStack.getData();
    }

    @Override
    public T peekMax() {
      return maxTree.isEmpty() ? null : maxTree.lastKey();
    }

    @Override
    public T popMax() {
      if (maxTree.isEmpty()) {
        return null;
      }

      // Delete from tree
      Map.Entry<T, List<LinkedNode<T>>> maxTreeEntry = maxTree.lastEntry();
      LinkedNode<T> removed = popFromMaxTree(maxTreeEntry.getKey());
      // Delete from list
      removeFromList(removed);
      return removed.getData();
    }

    private void addToList(LinkedNode<T> newNode) {
      listTail.getPrev().setNext(newNode);
      newNode.setPrev(listTail.getPrev());
      newNode.setNext(listTail);
      listTail.setPrev(newNode);
    }

    private void removeFromList(LinkedNode<T> node) {
      // Delete from stack
      LinkedNode<T> prev = node.getPrev();
      LinkedNode<T> next = node.getNext();
      prev.setNext(next);
      next.setPrev(prev);
    }

    private LinkedNode<T> pushToStack(T toPush) {
      LinkedNode<T> newNode = new LinkedNode<>(toPush);
      addToList(newNode);
      return newNode;
    }

    private LinkedNode<T> popFromStack() {
      if (listHead.getNext().equals(listTail)) {
        return null;
      }
      LinkedNode<T> toPopNode = listTail.getPrev();
      removeFromList(toPopNode);
      return toPopNode;
    }

    private void pushToMaxTree(LinkedNode<T> toPush) {
      List<LinkedNode<T>> dups = maxTree.computeIfAbsent(toPush.getData(), mapKey -> new LinkedList<>());
      dups.add(toPush);
    }

    private LinkedNode<T> popFromMaxTree(T elem) {
      List<LinkedNode<T>> dups = maxTree.get(elem);
      Verify.verifyNotNull(dups);
      Verify.verify(!dups.isEmpty());
      LinkedNode<T> removed = dups.remove(dups.size() - 1);
      if (dups.isEmpty()) {
        maxTree.remove(elem);
      }
      return removed;
    }
  }

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(MaxStackPeekAndPop.class);
  }

  private MaxStack<Integer> stack;

  @Before
  public void setup() {
    stack = new MaxStackImplBST_naive<>();
//    stack = new MaxStackImplBST_withDummyNodes<>();
  }

  @Test
  public void testPushAndPeek() {
    stack.push(1);
    stack.push(2);
    assertEquals(2, stack.peek().intValue());
    assertEquals(2, stack.peek().intValue());
  }

  @Test
  public void testPopEmptyStack() {
    assertNull(stack.pop());
  }

  @Test
  public void testPeekEmptyStack() {
    assertNull(stack.peek());
  }

  @Test
  public void testPushAndPop() {
    stack.push(1);
    stack.push(2);
    stack.push(3);
    assertEquals(3, stack.pop().intValue());
    assertEquals(2, stack.pop().intValue());
    assertEquals(1, stack.pop().intValue());
    assertNull(stack.pop());
  }

  @Test
  public void testPushAndPopAndPeek() {
    stack.push(1);
    stack.push(2);
    stack.push(3);
    assertEquals(3, stack.peek().intValue());
    assertEquals(3, stack.pop().intValue());
    assertEquals(2, stack.peek().intValue());
    assertEquals(2, stack.pop().intValue());
    assertEquals(1, stack.peek().intValue());
    assertEquals(1, stack.pop().intValue());
    assertNull(stack.pop());
  }

  @Test
  public void testPushAndPeekMax_ascending() {
    stack.push(1);
    assertEquals(1, stack.peekMax().intValue());
    stack.push(2);
    assertEquals(2, stack.peekMax().intValue());
    stack.push(3);
    assertEquals(3, stack.peekMax().intValue());
  }

  @Test
  public void testPushAndPeekMax_descending() {
    stack.push(3);
    assertEquals(3, stack.peekMax().intValue());
    stack.push(2);
    assertEquals(3, stack.peekMax().intValue());
    stack.push(1);
    assertEquals(3, stack.peekMax().intValue());
  }

  @Test
  public void testPushAndPopMax_ascending() {
    stack.push(1);
    stack.push(2);
    stack.push(3);
    assertEquals(3, stack.popMax().intValue());
    assertEquals(2, stack.popMax().intValue());
    assertEquals(1, stack.popMax().intValue());
  }

  @Test
  public void testPushAndPopMax_descending() {
    stack.push(3);
    stack.push(2);
    stack.push(1);
    assertEquals(3, stack.popMax().intValue());
    assertEquals(2, stack.popMax().intValue());
    assertEquals(1, stack.popMax().intValue());
  }

  @Test
  public void testPushAndPeekAndPopMax() {
    stack.push(3);
    stack.push(2);
    stack.push(1);

    assertEquals(1, stack.peek().intValue());
    assertEquals(3, stack.peekMax().intValue());
    assertEquals(3, stack.popMax().intValue());

    assertEquals(1, stack.peek().intValue());
    assertEquals(2, stack.peekMax().intValue());
    assertEquals(2, stack.popMax().intValue());

    assertEquals(1, stack.peek().intValue());
    assertEquals(1, stack.peekMax().intValue());
    assertEquals(1, stack.popMax().intValue());
  }

  @Test
  public void testPushAndPeekAndPopMax_non_monotonic_sequence_number() {
    stack.push(3);
    stack.push(2);
    stack.push(1);
    stack.push(4);

    assertEquals(4, stack.peek().intValue());
    assertEquals(4, stack.peekMax().intValue());
    assertEquals(4, stack.popMax().intValue());

    assertEquals(1, stack.peek().intValue());
    assertEquals(3, stack.peekMax().intValue());
    assertEquals(3, stack.popMax().intValue());

    assertEquals(1, stack.peek().intValue());
    assertEquals(2, stack.peekMax().intValue());
    assertEquals(2, stack.popMax().intValue());

    assertEquals(1, stack.peek().intValue());
    assertEquals(1, stack.peekMax().intValue());
    assertEquals(1, stack.popMax().intValue());
  }

  @Test
  public void testPushAndPeekAndPopMax_contain_dups() {
    stack.push(3);
    stack.push(2);
    stack.push(3);
    stack.push(2);

    assertEquals(2, stack.peek().intValue());
    assertEquals(3, stack.peekMax().intValue());
    assertEquals(3, stack.popMax().intValue());

    assertEquals(2, stack.peek().intValue());
    assertEquals(3, stack.peekMax().intValue());
    assertEquals(3, stack.popMax().intValue());

    assertEquals(2, stack.peek().intValue());
    assertEquals(2, stack.peekMax().intValue());
    assertEquals(2, stack.popMax().intValue());

    assertEquals(2, stack.peek().intValue());
    assertEquals(2, stack.peekMax().intValue());
    assertEquals(2, stack.popMax().intValue());
  }

  @Test
  public void testPushAndPeekAndPopMax_two_elements() {
    stack.push(3);
    stack.push(2);
    assertEquals(2, stack.peek().intValue());
    assertEquals(3, stack.peekMax().intValue());
    assertEquals(3, stack.popMax().intValue());

    assertEquals(2, stack.peek().intValue());
    assertEquals(2, stack.peekMax().intValue());
    assertEquals(2, stack.popMax().intValue());
  }

  @Test
  public void testPushAndPeekAndPopMax_one_element() {
    stack.push(3);
    assertEquals(3, stack.peek().intValue());
    assertEquals(3, stack.peekMax().intValue());
    assertEquals(3, stack.popMax().intValue());

    assertNull(stack.peek());
    assertNull(stack.peekMax());
    assertNull(stack.pop());
  }
}
