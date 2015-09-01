package general.misc;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Encodings {
	public static void main(String[] args) throws UnsupportedEncodingException {
		long id = 1235;
		String encoded = base64Encoding(id); // "AAAAAAAABNM=";
		System.out.println(encoded);

		// encode to binary as one number
		System.out.println(Integer.toBinaryString(5678));		

		//encode to binary as several digits
		System.out.println(Integer.toBinaryString(5)+Integer.toBinaryString(6)+Integer.toBinaryString(7)+Integer.toBinaryString(8));
		
		//encode to String of UTF-8 encoding
		System.out.println(encoded);
		System.out.println(Integer.toBinaryString(5678).getBytes("UTF-8").length);
		for (Byte b : Integer.toBinaryString(5678).getBytes("UTF-8")) {
			System.out.print(Integer.toBinaryString(b) + "");
		}
	}
	
	public static String base64Encoding(long data) {
		byte[] bytes = ByteBuffer.allocate(Long.BYTES / Byte.BYTES).putLong(data).array();
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static long base64Decoding(String data) {
		long res = 0;
		
		return res;
	}

	private static Map<Character, Integer> getBase64Map() {
		Map<Character, Integer> map = new HashMap<>(64);
		int value = 0;
		for (char key = 'A'; key <= 'Z'; key++)
			map.put(key, value++);
		for (char key = 'a'; key <= 'z'; key++)
			map.put(key, value++);
		map.put('+', value++);
		map.put('/', value++);
		return map;
	}
}
