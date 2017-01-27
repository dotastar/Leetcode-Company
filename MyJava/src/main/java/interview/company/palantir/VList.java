package interview.company.palantir;

import interview.AutoTestUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Implement VList, it is a list of array, the length of the arrays are 1, 2, 4, 8, …
 * Only create the new array when you need one. It should support two methods:
 * void append(T element)
 * T getByIndex(int index)
 * <p>
 * Created by yazhoucao on 10/29/15.
 */
public class VList<T> {

    /**
     * Hint: Map between total element size to array index
     * Array size: 1, 2, 4, 8, 16, 32, ...
     * Total size: 1, 3, 7, 15, 31, 63, ...
     * Array indx: 0, 1, 2, 3, 4, 5, ...
     * Bit representation: 1, 10, 100, 1000, 10000, ...
     * <p>
     * Bit range of
     * elem index -> Array index
     * [0, 10) -> 0
     * [10, 100) -> 1
     * [100, 1000) -> 2
     * ...
     * <p>
     * Corner case:
     * Array size = 0 --> Array index = 0
     * <p>
     * Optimization: pre-computation of mapping
     */

    private int elemSize;
    private List<T[]> arrays = new ArrayList<>();

    public VList() {
        elemSize = 0;
        arrays = new ArrayList<>();
    }

    /**
     * Assume max arrays.size() is 32 since arrays[31].size will be 2^31 which is very very big
     */
    private static int getArrayIdx(int elemIdx) {
        if (elemIdx == 0) {
            return 0;
        }
        for (int i = 31; i >= 0; i--) {
            int highestBit = 1 << i;
            if ((elemIdx & highestBit) != 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("Element Size: " + elemIdx);
    }

    private int[] getArrayAndElemIdx(int elemIdx) {
        int arrayIdx = getArrayIdx(elemIdx);
        int arraySize = (int) Math.pow(2, arrayIdx);
        if (arrays.size() <= arrayIdx) {
            arrays.add((T[]) new Object[arraySize]);
        }
        int eleSubIdx = elemIdx % arraySize;
        return new int[]{arrayIdx, eleSubIdx};
    }

    public void append(T element) {
        int[] indices = getArrayAndElemIdx(elemSize);
        arrays.get(indices[0])[indices[1]] = element;
        elemSize++;
    }

    public T getByIndex(int index) {
        if (index < 0 || index > elemSize) {
            return null;
        }
        int[] indices = getArrayAndElemIdx(index);
        return arrays.get(indices[0])[indices[1]];
    }


    public static void main(String[] args) {
        AutoTestUtils.runTestClass(VList.class);
    }

    @Test
    public void test1() {
        VList<Integer> list = new VList<>();
        for (int i = 0; i < 1000; i++) {
            list.append(i);
            assertEquals(new Integer(i), list.getByIndex(i));
        }
    }
}
