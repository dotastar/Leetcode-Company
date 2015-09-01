package general.misc;

import java.io.UnsupportedEncodingException;

public class Encodings {
	public static void main(String[] args) throws UnsupportedEncodingException{
		//encode to binary as one number
		System.out.println(Integer.toBinaryString(5678));
		

		//encode to binary as several digits
		System.out.println(Integer.toBinaryString(5)+Integer.toBinaryString(6)+Integer.toBinaryString(7)+Integer.toBinaryString(8));
		
		//encode to String of UTF-8 encoding
		System.out.println(Integer.toBinaryString(5678).getBytes("UTF-8").length);
		for(Byte b : Integer.toBinaryString(5678).getBytes("UTF-8")){
			System.out.print(Integer.toBinaryString(b)+"");
		}
	}
}
