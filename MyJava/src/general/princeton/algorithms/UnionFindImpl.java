package general.princeton.algorithms;

import java.util.Arrays;

/**
 * Union-find applications
 * ￼・Percolation.
 * ・Games (Go, Hex).
 * ・Dynamic connectivity (below example).
 * ・Least common ancestor.
 * ・Equivalence of finite state automata.
 * ・Hoshen-Kopelman algorithm in physics.
 * ・Hinley-Milner polymorphic type inference.
 * ・Kruskal's minimum spanning tree algorithm.
 * ・Compiling equivalence statements in Fortran.
 * ・Morphological attribute openings and closings.
 * ・Matlab's bwlabel() function in image processing.
 * 
 * @author yazhoucao
 * 
 */
public class UnionFindImpl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = StdIn.readInt();
		UnionFind uf = new WeightedQuickUnionUF(N);
		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			if (!uf.connected(p, q)) {
				uf.union(p, q);
				StdOut.println(p + " " + q);
			}
			StdOut.println((uf.toString()));
		}
	}

	public static interface UnionFind{
		public boolean connected(int p, int q);
		
		public void union(int p, int q);
		
		public String toString();
	}
	
	/**
	 * Quick Find Union-Find
	 * Given a set of N objects.
	 * ・Union command: connect two objects.
	 * ・Find/connected query: is there a path connecting the two objects?
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class QuickFindUF implements UnionFind{
		int[] id;

		public QuickFindUF(int N) {
			id = new int[N];
			for (int i = 0; i < N; i++)
				id[i] = i;
		}

		/**
		 * Time: O(n)
		 */
		public void union(int p, int q) {
			if (!isValid(p, q))
				return;
			int pid = id[p];
			int qid = id[q];
			for (int i = 0; i < id.length; i++) {
				if (id[i] == pid)
					id[i] = qid;
			}
		}

		/**
		 * Time: O(1)
		 */
		public boolean connected(int p, int q) {
			if (!isValid(p, q))
				return false;
			return id[p] == id[q];
		}

		private boolean isValid(int p, int q) {
			int len = id.length;
			return (p >= 0 && q >= 0 && p < len && q < len);
		}

		int count() {
			return id.length;
		}
		
		public String toString(){ return Arrays.toString(id); }
	}

	/**
	 * Think it as a forest, d[i] is parent of i.
	 * Root of i is id[id[id[...id[i]...]]].
	 * 
	 * Find. Check if p and q have the same root.
	 * Union. To merge components containing p and q, set the id of p's root to
	 * the id of q's root.
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class QuickUnionUF  implements UnionFind{
		int[] id;

		public QuickUnionUF(int N) {
			id = new int[N];
			for (int i = 0; i < N; i++) {
				id[i] = i;
			}
		}

		public int count() {
			return id.length;
		}

		/**
		 * Thought: if p and q have the same root, then they are connected.
		 * Time: O(n), worst case
		 */
		public boolean connected(int p, int q) {
			if (!isValid(p, q))
				return false;
			return root(p) == root(q);
		}

		/**
		 * Merge the forest of p and q
		 * 
		 * Time: O(n), worst case
		 */
		public void union(int p, int q) {
			if (!isValid(p, q))
				return;
			id[root(p)] = root(q);
		}

		/**
		 * Return the root of p
		 * Time: O(n), worst case
		 */
		private int root(int p) {
			if (p >= id.length || p < 0)
				return Integer.MIN_VALUE;
			while (id[p] != p) {
				p = id[p];
			}
			return p;
		}

		private boolean isValid(int p, int q) {
			int len = id.length;
			return (p >= 0 && q >= 0 && p < len && q < len);
		}
		
		public String toString(){ return Arrays.toString(id); }
	}

	/**
	 * Weighted quick-union.
	 * ・Modify quick-union to avoid tall trees.
	 * ・Keep track of size of each tree (number of objects).
	 * ・Balance by linking root of smaller tree to root of larger tree.
	 * 
	 * Same as quick-union, but maintain extra array sz[i] to count number of
	 * objects in the tree rooted at i.
	 * 
	 * @author yazhoucao
	 * 
	 */
	public static class WeightedQuickUnionUF  implements UnionFind{
		int[] id;
		int[] sz;

		public WeightedQuickUnionUF(int N) {
			id = new int[N];
			sz = new int[N];
			for (int i = 0; i < N; i++) {
				id[i] = i;
				sz[i] = 1;
			}
		}

		/**
		 * Time: O(lg(n))
		 */
		public boolean connected(int p, int q) {
			if (!isValid(p, q))
				return false;
			return root(p) == root(q);
		}

		/**
		 * Use sz[] to make the merged tree balanced.
		 * Small tree will be merged to bigger tree.
		 * 
		 * Time: O(lg(n))
		 */
		public void union(int p, int q) {
			if (!isValid(p, q))
				return;
			int prid = root(p);
			int qrid = root(q);
			if (prid == qrid)
				return;
			if (sz[prid] >= sz[qrid]) {
				id[qrid] = prid; // merge q to p
				sz[prid] += sz[qrid];
			} else {
				id[prid] = qrid; // merge p to q
				sz[qrid] += sz[prid];
			}
		}

		/**
		 * Return the root of p
		 * Time: O(lg(n)), guaranteed
		 * 
		 * Proof see the Graduate/PrincetonAlgorithms_Coursera/UnionFind.pdf
		 */
		public int root(int p) {
			if (p >= id.length || p < 0)
				return Integer.MIN_VALUE;
			while (id[p] != p) {
				p = id[p];
			}
			return p;
		}

		/**
		 * Path compression, just add one line of code compare to root(), but
		 * improves a lot. It flattened the tree dramatically by pointing its
		 * father to its grandfather.
		 * 
		 */
		public int root_improved(int p) {
			if (p >= id.length || p < 0)
				return Integer.MIN_VALUE;
			while (id[p] != p) {
				id[p] = id[id[p]]; //
				p = id[p];
			}
			return p;
		}

		private boolean isValid(int p, int q) {
			int len = id.length;
			return (p >= 0 && q >= 0 && p < len && q < len);
		}
		
		public String toString(){ return Arrays.toString(id); }
	}
}
