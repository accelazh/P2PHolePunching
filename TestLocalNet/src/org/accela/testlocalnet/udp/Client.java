package org.accela.testlocalnet.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.accela.testlocalnet.Common;

public class Client
{

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException
	{
		DatagramSocket server = new DatagramSocket(2345, Common.IP);

		byte[] buf = new byte[Common.TOKEN_LEN];
		while (true)
		{
			System.arraycopy(Common.TOKEN, 0, buf, 0, Common.TOKEN_LEN);

			DatagramPacket packet = new DatagramPacket(buf, Common.TOKEN_LEN,
					Common.IP, 1235);

			server.send(packet);

			Thread.sleep(1000);

		}

	}

}
