package interview.epi.chaper6_array;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Problem 6.19
 * Design an algorithm that reads packets and continuously maintains a uniform
 * random subset of size k of the packets after n >= k-th packets are read.
 * 
 * @author yazhoucao
 * 
 */
public class Q19_Sample_Online_Data {

	static String methodName = "reservoirSampling";
	static Class<?> c = Q19_Sample_Online_Data.class;

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		Random gen = new Random();
		int n = gen.nextInt(1000), k = 10;
		System.out.println("n:" + n + " k: " + k);

		List<Integer> A = new ArrayList<>(n);
		for (int i = 0; i < n; ++i) {
			A.add(i);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);

		for (Integer i : A) {
			oos.writeObject(i);
			ByteArrayInputStream sin = new ByteArrayInputStream(
					baos.toByteArray());
			int[] ans = reservoirSampling(sin, k);
			System.out.println(Arrays.toString(ans));
			assert ans.length == k;
			sin.reset();
		}

		close(baos);
		close(oos);
	}

	/**
	 * Idea: before k elements has been read, just store all of them,
	 * after k elements, for each element that just read, decide if add it to
	 * the sample by generating a random number tar within [0,n), if tar < k
	 * then kick out sample[tar] and replace it with the new read element.
	 * 
	 * Time: O(n), Space: O(k).
	 */
	public static int[] reservoirSampling(InputStream sin, int k)
			throws IOException, ClassNotFoundException {
		int[] sample = new int[k];
		try (ObjectInputStream in = new ObjectInputStream(sin)) {
			// store the first k elements
			Integer x = (Integer) readObjectSilently(in);
			for (int i = 0; i < k && x != null; i++) {
				sample[i] = x;
				x = (Integer) readObjectSilently(in);
			}

			// after the first k elements
			Random ran = new Random();
			int elementNum = k + 1;
			x = (Integer) readObjectSilently(in);
			while (x != null) {
				int tar = ran.nextInt(++elementNum);
				// select one to kick out from the sample,
				// and replace it with the new one if tar<k
				if (tar < k)
					sample[tar] = x;
				x = (Integer) readObjectSilently(in);
			}
		}
		return sample;
	}

	private static Object readObjectSilently(ObjectInputStream osin)
			throws ClassNotFoundException, IOException {
		Object object = null;
		try {
			object = osin.readObject();
		} catch (EOFException e) {
			// we don't want to force the calling code to catch an EOFException
			// only to realize there is nothing more to read.
		}
		return object;
	}

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException e) {
			// We want to close "closeable" silently
		}
	}
}
