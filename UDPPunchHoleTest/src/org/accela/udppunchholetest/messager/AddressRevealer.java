package org.accela.udppunchholetest.messager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

import org.accela.udppunchholetest.Common;


public class AddressRevealer
{
	public static void main(String[] args)
	{
		// initialize
		final DatagramSocket socket;
		try
		{
			socket = new DatagramSocket();
		}
		catch (SocketException ex)
		{
			System.out.println("Failed to create UDP socket.");
			ex.printStackTrace();
			return;
		}
		System.out.println("Local UDP socket set up. address="
				+ socket.getLocalSocketAddress());

		BufferedReader sysIn = new BufferedReader(new InputStreamReader(
				System.in));

		// launch receiver thread
		Thread receiver = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				byte[] buf = new byte[Common.MAX_MESSAGE_LEN];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				while (true)
				{
					// receive datagram packet
					Arrays.fill(buf, (byte) 0);
					try
					{
						socket.receive(packet);
					}
					catch (IOException ex)
					{
						System.out
								.println("Failed to receive udp packet reply.");
						ex.printStackTrace();
						continue;
					}
					System.out.println("UDP packet received from "
							+ packet.getSocketAddress()
							+ " with message: \n\""
							+ new String(packet.getData(), packet.getOffset(),
									packet.getLength()) + "\"");

					// extract ip and port
					InetAddress ip = packet.getAddress();
					int port = packet.getPort();
					String message = "Your address is " + ip + ":" + port + ".";

					// construct datagram packet and send
					byte[] data = message.getBytes();
					if (data.length > Common.MAX_MESSAGE_LEN)
					{
						assert (false);
						continue;
					}
					DatagramPacket packetToSend = new DatagramPacket(data,
							data.length, ip, port);
					try
					{
						socket.send(packetToSend);
					}
					catch (IOException ex)
					{
						System.out.println("Failed to send answer UDP packet.");
						ex.printStackTrace();
						continue;
					}

					// echo answer packet sent
					System.out.println("Answer UDP packet sent to "
							+ packet.getSocketAddress()
							+ " with message: \n\""
							+ message
							+ "\"");
				}
			}

		});
		receiver.setDaemon(true);
		receiver.start();

		// command line handling
		while (true)
		{
			// print command prompt
			System.out.print("Command>");

			// read user command and execute
			String command = null;
			try
			{
				command = sysIn.readLine();
			}
			catch (IOException ex)
			{
				System.out
						.println("IOException occured when reading commands.");
				ex.printStackTrace();
				continue;
			}
			command = command.trim();

			if (command.length() <= 0)
			{
				continue;
			}
			if (command.startsWith("exit"))
			{
				break;
			}
			else
			{
				System.out.println("Error: Unknown command.");
			}
		}// end of while

		// shutdown
		socket.close();
	}
}
