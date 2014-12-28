package interview.epi.chapter9_stack_queue;

/**
 * Problem 9.7
 * Write recursive and iterative routines that take a postings list, and
 * computes the jump-first order. Assume each node has an node has an order
 * field, which is an integer that is initialized to -1 for each node.
 * 
 * @author yazhoucao
 * 
 */
public class Q7_Search_A_Postings_List {

	public static void main(String[] args) {

	}

	public static void searchPostingsList(PostingListNode L) {
		helper(L, 0);
	}

	private static int helper(PostingListNode L, int order) {
		if (L != null && L.order == -1)
			L.order = order++;
		order = helper(L.jump, order);
		order = helper(L.next, order);
		return order;
	}

	public static class PostingListNode {
		private int order;
		private PostingListNode next, jump;

		public PostingListNode(int order, PostingListNode next,
				PostingListNode jump) {
			this.order = order;
			this.next = next;
			this.jump = jump;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public PostingListNode getNext() {
			return next;
		}

		public void setNext(PostingListNode next) {
			this.next = next;
		}

		public PostingListNode getJump() {
			return jump;
		}

		public void setJump(PostingListNode jump) {
			this.jump = jump;
		}
	}
}
