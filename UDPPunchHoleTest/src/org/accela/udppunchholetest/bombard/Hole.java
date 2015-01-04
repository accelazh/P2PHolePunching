package org.accela.udppunchholetest.bombard;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.accela.udppunchholetest.Common;

public class Hole
{
	private DatagramSocket socket = null;

	private InetAddress targetIP = null;

	private Thread sender = null;

	private Thread receiver = null;

	private Thread terminator = null;

	private static final PortGenerator portGen = new PortGenerator();

	private static final long SEND_INTERVAL = 20;

	public static final long LIFE_SPAN = 30 * 1000;

	public boolean alive = true;

	public Hole(int localPort, InetAddress targetIP) throws SocketException,
			UnknownHostException
	{
		socket = new DatagramSocket(localPort, InetAddress.getLocalHost());
		this.targetIP = targetIP;
		// System.out.println("Hole UDP socket set up. address=" + socket
		// .getLocalSocketAddress() + ", targetIP=" + this.targetIP);

		this.sender = new Thread(new Sender(), "Hole.Sender");
		this.receiver = new Thread(new Receiver(), "Hole.Receiver");
		this.terminator = new Thread(new Terminator(), "Hole.Terminator");
	}

	public void start()
	{
		this.receiver.start();
		this.sender.start();
		this.terminator.start();
	}

	private class Sender implements Runnable
	{
		@Override
		public void run()
		{
			while (alive)
			{
				byte[] buf = new String("H").getBytes();
				DatagramPacket packet =
						new DatagramPacket(buf,
								buf.length,
								targetIP,
								portGen.nextPort());
				try
				{
					socket.send(packet);
				} catch (IOException ex)
				{
					if (alive)
					{
						System.err.println("Failed to send udp packet.");
						ex.printStackTrace();
					}
					continue;
				}

				try
				{
					Thread.sleep(SEND_INTERVAL);
				} catch (InterruptedException ex)
				{
					if (alive)
					{
						ex.printStackTrace();
					}
				}
			}
		}

	}

	private class Receiver implements Runnable
	{
		byte[] buf = new byte[Common.MAX_MESSAGE_LEN];

		@Override
		public void run()
		{
			while (alive)
			{
				Arrays.fill(buf, (byte) 0);
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				try
				{
					socket.receive(packet);
				} catch (IOException ex)
				{
					if (alive)
					{
						System.err.println("Failed to receive udp packet.");
						ex.printStackTrace();
					}
					continue;
				}
				System.out.println("UDP packet received from " + packet
						.getSocketAddress()
						+ " with message: \n\""
						+ new String(packet.getData(),
								packet.getOffset(),
								packet.getLength())
						+ "\""
						+ "("
						+ packet.getLength()
						+ " bytes)");
			}
		}
	}

	private class Terminator implements Runnable
	{

		@Override
		public void run()
		{
			try
			{
				Thread.sleep(LIFE_SPAN);
			} catch (InterruptedException ex)
			{
				ex.printStackTrace();
			}

			alive = false;
			sender.interrupt();
			receiver.interrupt();
			socket.close();
		}

	}
}
