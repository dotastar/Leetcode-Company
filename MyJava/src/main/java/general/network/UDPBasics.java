package general.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * The key differences from TCP is using DatagramSocket which is not reliable.
 * @author yazhoucao
 *
 */
public class UDPBasics implements Runnable {

	public static void main(String[] args) throws SocketException {
		Thread t1 = new Thread(new UDPBasics());
		t1.start();

		Thread t2 = new Thread(new UDPBasics());
		t2.start();
	}

	private static boolean receiverUp = false;

	public void run() {
		if (!receiverUp) {
			receiverUp = true;
			receiver();
		} else
			sender();
	}

	/**
	 * Sender: UDP Socket programming
	 */
	public void sender() {
		// Create UDP Socket for sending datagram
		try (DatagramSocket socket = new DatagramSocket()) {
			// Prepare datagram for sending
			byte[] data = "Hello World".getBytes();
			int p = 8888; // port
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			DatagramPacket packet = new DatagramPacket(data, data.length, addr,
					p);

			// Send
			socket.send(packet);
			System.out.println("Data has sent to " + addr.toString() + ":" + p);

			// Receive,
			// Prepare new datagram first for holding the incoming packet. The
			// array of byte must be clear in advance.
			// socket.receive(packet);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Receiver: UDP Socket programming
	 */
	public void receiver() {
		// Start listening for datagram on a certain socket
		try (DatagramSocket socket = new DatagramSocket(8888)) {
			// Prepare datagram for receiving
			byte[] data = new byte[512];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			// Start listening for a datagram
			socket.receive(packet);

			// Obtaining the data and address and port of sender
			int port = packet.getPort();
			InetAddress addr = packet.getAddress();
			String content = new String(packet.getData(), 0, packet.getLength());
			System.out.println(String.format(
					"Packet received from %s:%d, content: %s",
					addr.getHostAddress(), port, content));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
