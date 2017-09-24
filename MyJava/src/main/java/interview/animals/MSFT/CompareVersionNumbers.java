package interview.animals.MSFT;

import com.google.common.base.Strings;
import interview.AutoTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Version number consists of one, two, three or four parts, separated with dots:
 * <SKU number>.<major>.<minor>.<build>
 * Any trailing part that consists of 0 can be omitted, write a method to compare two version numbers.
 * v1 > v2 return 1,
 * v1 < v2 return -1,
 * v1 = v2 return 0.
 *
 *
 */
public class CompareVersionNumbers {

  private static int compareVersionNumber(String vNum1, String vNum2) {
    // sanity check…
    if (Strings.isNullOrEmpty(vNum1) || Strings.isNullOrEmpty(vNum2)) {
      throw new IllegalArgumentException("Illegal arguments: " + vNum1 + " ," + vNum2);
    }

    String[] partsNum1 = vNum1.split("\\.");
    String[] partsNum2 = vNum2.split("\\.");

    // handle trailing zeroes
    int num1RealLength = partsNum1.length - countTrailingZeroParts(partsNum1);
    int num2RealLength = partsNum2.length - countTrailingZeroParts(partsNum2);

    int commonLength = Math.min(num1RealLength, num2RealLength);
    // check the first commonLength parts
    for (int i = 0; i < commonLength; i++) {
      int partNum1 = Integer.valueOf(partsNum1[i]);
      int partNum2 = Integer.valueOf(partsNum2[i]);
      if (partNum1 != partNum2) {
        return partNum1 > partNum2 ? 1 : -1;
      }
    }
    // commonLength parts are equal after the check
    return num1RealLength - num2RealLength;
  }

  private static int countTrailingZeroParts(String[] parts) {
    int count = 0;
    for (int i = parts.length - 1; i >= 0; i--) {
      if (Integer.valueOf(parts[i]) != 0) {
        break;
      }
      count++;
    }
    return count;
  }

  public static void main(String[] args) {
    AutoTestUtils.runTestClassAndPrint(CompareVersionNumbers.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void test_no_digits() {
    String vNum1 = "";
    String vNum2 = "";

    compareVersionNumber(vNum1, vNum2);
  }

  @Test
  public void test_no_effective_digits() {
    String vNum1 = "0";
    String vNum2 = "0";

    int result = compareVersionNumber(vNum1, vNum2);
    assertEquals(0, result);
  }


  @Test
  public void test_no_trailing_zero_part() {
    String vNum1 = "101.0.0.2";
    String vNum2 = "101.0.1";

    int result = compareVersionNumber(vNum1, vNum2);
    assertEquals(-1, result);
  }

  @Test
  public void test_one_trailing_zero_part() {
    String vNum1 = "101.1.0.2";
    String vNum2 = "101.1.1.0";

    int result = compareVersionNumber(vNum1, vNum2);
    assertEquals(-1, result);
  }

  @Test
  public void test_one_trailing_zero_part_different_length() {
    String vNum1 = "101.1.1.0";
    String vNum2 = "101.1";

    int result = compareVersionNumber(vNum1, vNum2);
    assertEquals(1, result);
  }

  @Test
  public void test_two_trailing_zero_parts() {
    String vNum1 = "101.1.0.0";
    String vNum2 = "101.1.0.0";

    int result = compareVersionNumber(vNum1, vNum2);
    assertEquals(0, result);
  }

  @Test
  public void test_all_zero_parts() {
    String vNum1 = "0.0.0.0";
    String vNum2 = "0.0.0.0";

    int result = compareVersionNumber(vNum1, vNum2);
    assertEquals(0, result);
  }
}
