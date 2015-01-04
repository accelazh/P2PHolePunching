package org.accela.testlocalnet.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import org.accela.testlocalnet.Common;

public class Server
{
	public static void main(String[] args) throws IOException
	{
		DatagramSocket server = new DatagramSocket(1235, Common.IP);
		System.out.println(server.getLocalPort());
		System.out.println(server.getLocalAddress());

		byte[] buf = new byte[Common.TOKEN_LEN];
		while (true)
		{
			Arrays.fill(buf, (byte) 0);
			DatagramPacket packet = new DatagramPacket(buf, Common.TOKEN_LEN);
			server.receive(packet);
			System.out.println("data received, check "
					+ (Arrays.equals(packet.getData(), Common.TOKEN) ? "succ"
							: "fail")
					+ ", packet="
					+ packet.getAddress()
					+ ":"
					+ packet.getPort());
		}
	}

}
