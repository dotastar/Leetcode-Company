package general.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import sun.net.util.IPAddressUtil;


@Slf4j
public class HelloWorldInetAddress {

  public static void main(String[] args) throws UnknownHostException {
    compareStringToBytesFn();

    System.out.println();
    isValidInetAddress(stringToBytes("\u0001\u00F0\u000F\u00ff"));

    System.out.println();
    stringToBytes("10"); // "10" is interpreted as '1' -> 49 and '0' -> 48 by stringToBytes()

    printByteToCharMap();

    String ip = "173.215.218.247";

    Arrays.stream(ip.split(".")).map(Byte::valueOf).map(b -> ((char) b.byteValue()));
  }

  private static void printByteToCharMap() {
    byte b = 127;
    do {
      b = (byte) (b + 1);
      log.info("Byte:\t{}\tChar:\t{}", b, (char)b);
    } while (b != 127);
  }

  private static void compareStringToBytesFn() {
    String [] ipArr = { "1.2.3.4", "1.2.3.255", "76.169.224.74", "128.129.130.131", "255.254.253.252" };
    for (String ip : ipArr) {
      isValidInetAddress(myStringToBytes(ip));
      isValidInetAddress(myIpV4StringToBytes(ip));
      isValidInetAddress(stripOutDotsStringToBytes(ip));
      isValidInetAddress(stringToBytes(ip));
      System.out.println();
    }
  }

  private static boolean isValidInetAddress(byte[] ipBytes) {
    try {
      log.info("Valid InetAddress:\t{}", InetAddress.getByAddress(ipBytes));
      log.info("======================================================");
    } catch (UnknownHostException e) {
      log.error("Not a valid InetAddress:\t{}", e.getMessage());
      log.info("======================================================");
      return false;
    }
    return true;
  }

  @SneakyThrows(UnknownHostException.class)
  private static byte[] myStringToBytes(String ip) {
    log.info("myStringToBytes:\t{}", ip);
    InetAddress byName = InetAddress.getByName(ip);
    byte[] ipBytes = byName.getAddress();
    displayByteAndIntArray(ipBytes);
    return ipBytes;
  }

  private static byte[] myIpV4StringToBytes(String ip) {
    log.info("IPAddressUtil:\t{}", ip);
    byte[] ipBytes = IPAddressUtil.textToNumericFormatV4(ip);
    displayByteAndIntArray(ipBytes);
    return ipBytes;
  }

  /**
   * Get bytes from string following Avro convention.
   *
   * This method extracts the least significant 8-bits of each character in the string
   * (following Avro convention.) The returned byte array is the same length as the
   * string, i.e. if there are 8 characters in the string, the byte array will have 8 bytes.
   *
   * Validation is optional. If validation is enabled, then the input is valid if
   * the most significant 8-bits of all characters is always 0.
   *
   * @param input string to get bytes from.
   * @param validate indicates whether validation is enabled, validation is enabled if true.
   * @return extracted bytes if the string is valid or validation is not enabled, else return null.
   */
  private static byte[] stringToBytes(String input, boolean validate) {
    char orChar = 0;
    int length = input.length();
    byte[] bytes = new byte[length];
    for (int i = 0; i < length; ++i) {
      char c = input.charAt(i);
      orChar |= c;
      bytes[i] = (byte) (c & 0x00ff);
    }
    if (validate && (orChar & 0xff00) != 0) {
      return null;
    }
    displayByteAndIntArray(bytes);
    return bytes;
  }

  private static byte[] stringToBytes(String input) {
    log.info("stringToBytes:\t{}", input);
    return stringToBytes(input, true);
  }

  private static byte[] stripOutDotsStringToBytes(String input) {
    log.info("stripOutDotsStringToBytes:\t{}", input);
    return stringToBytes(input.replace(".", ""), true);
  }

  private static void displayByteAndIntArray(byte[] bytes) {
    log.info("IP bytes:\t{}", Arrays.toString(bytes));
    int[] copy = new int[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      copy[i] = bytes[i] & 0xff;
    }
    log.info("IP ints:\t\t{}", Arrays.toString(copy));
  }
}
