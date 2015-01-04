package org.accela.udppunchholetest.messager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.accela.udppunchholetest.Common;


public class Messager
{
	public static void main(String[] args) throws IOException
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
									packet.getLength())
							+ "\""
							+ "("
							+ packet.getLength()
							+ " bytes)");
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
			else if (command.startsWith("send"))
			{
				final String ERROR_MESSAGE = "Error: illegal command format!\n"
						+ "Usage: send <IP>:<PORT> \"<MESSAGE>\"\n"
						+ "MESSAGE shoule be no longer than "
						+ Common.MAX_MESSAGE_LEN
						+ " bytes.";

				StringTokenizer tokens = new StringTokenizer(command);
				if (tokens.countTokens() < 3)
				{
					System.out.println(ERROR_MESSAGE);
					continue;
				}
				tokens.nextToken();

				// extract ip and port
				StringTokenizer addrToken = new StringTokenizer(
						tokens.nextToken(), ":");
				if (addrToken.countTokens() != 2)
				{
					System.out.println(ERROR_MESSAGE);
					continue;
				}
				InetAddress ip = null;
				try
				{
					ip = InetAddress.getByName(addrToken.nextToken());
				}
				catch (UnknownHostException ex)
				{
					System.out.println(ERROR_MESSAGE);
					ex.printStackTrace();
					continue;
				}
				int port = -1;
				try
				{
					port = Integer.parseInt(addrToken.nextToken());
				}
				catch (NumberFormatException ex)
				{
					System.out.println(ERROR_MESSAGE);
					ex.printStackTrace();
					continue;
				}

				// extract message
				if (!tokens.nextToken().startsWith("\"")
						|| !command.trim().endsWith("\""))
				{
					System.out.println(ERROR_MESSAGE);
					continue;
				}

				String message = null;
				try
				{
					message = command.substring(command.indexOf('\"') + 1,
							command.lastIndexOf('\"'));
				}
				catch (StringIndexOutOfBoundsException ex)
				{
					System.out.println(ERROR_MESSAGE);
					ex.printStackTrace();
					continue;
				}

				// construct datagram packet and send
				byte[] data = message.getBytes();
				if (data.length > Common.MAX_MESSAGE_LEN)
				{
					System.out.println(ERROR_MESSAGE);
					continue;
				}
				DatagramPacket packet = new DatagramPacket(data, data.length,
						ip, port);
				try
				{
					socket.send(packet);
				}
				catch (IOException ex)
				{
					System.out.println("Failed to send udp packet.");
					ex.printStackTrace();
					continue;
				}

				// echo command
				System.out.println("UDP packet sent to "
						+ packet.getSocketAddress()
						+ " with message: \n\""
						+ message
						+ "\"");
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
