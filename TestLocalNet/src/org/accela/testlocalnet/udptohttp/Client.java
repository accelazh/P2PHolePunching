package org.accela.testlocalnet.udptohttp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.accela.testlocalnet.Common;

public class Client
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		DatagramSocket socket = new DatagramSocket(44444, Common.IP);

		StringBuffer sb = new StringBuffer();
		sb.append("GET /index.jsp HTTP/1.1\r\n");
		sb.append("Accept-Language: zh-cn\r\n");
		sb.append("Connection: Keep-Alive\r\n");
		sb.append("Host: 10.0.0.10\r\n");
		sb.append("Content-Length: 1270\r\n");
		sb.append("\r\n");
		sb.append("userName=new_andy&password=new_andy\r\n");
		sb.append("\r\n");

		byte[] buf = sb.toString().getBytes();
		DatagramPacket packet =
				new DatagramPacket(buf,
						buf.length,
						Common.IP,
						80);

		socket.send(packet);
		buf = new byte[1024 * 1024];
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		System.out.println(new String(buf, 0, packet.getLength()));

	}

}
