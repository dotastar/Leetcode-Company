package general.network;

import java.net.UnknownHostException;
import org.jboss.netty.handler.ipfilter.CIDR;


public class UnderstandingCIDR {


  public static void main(String[] args) {
    String cidrStr = "2403:0:506::/48";
    try {
      CIDR cidr = CIDR.newCIDR(cidrStr);
      System.out.println(cidr.toString());
      System.out.println(cidr.getClass().toString());
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
  }
}
