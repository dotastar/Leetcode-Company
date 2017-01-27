package interview.epi.chapter16_recursion;

import interview.epi.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Q14_Compute_The_Diameter_Of_A_Tree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static class TreeNode {
		List<Pair<TreeNode, Double>> edges = new ArrayList<>();
	}

	public double computeDiameter(TreeNode T) {
		return T != null ? computeHeightAndDiameter(T).getSecond() : 0.0;
	}

	// Returns (height, diameter) pair.
	private Pair<Double, Double> computeHeightAndDiameter(TreeNode r) {
		double diameter = Double.MIN_VALUE;
		double[] height = { 0.0, 0.0 }; // Stores the max two heights.
		for (Pair<TreeNode, Double> e : r.edges) {
			Pair<Double, Double> heightDiameter = computeHeightAndDiameter(e.getFirst());
			if (heightDiameter.getFirst() + e.getSecond() > height[0]) {
				height[1] = height[0];
				height[0] = heightDiameter.getFirst() + e.getSecond();
			} else if (heightDiameter.getFirst() + e.getSecond() > height[1]) {
				height[1] = heightDiameter.getFirst() + e.getSecond();
			}
			diameter = Math.max(diameter, heightDiameter.getSecond());
		}
		return new Pair<>(height[0], Math.max(diameter, height[0] + height[1]));
	}
}
