package org.accela.udppunchholetest.bombard;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Penetrator
{
	private DatagramSocket socket = null;

	private InetAddress targetIP = null;

	private static final PortGenerator portGen = new PortGenerator();

	private static final long SEND_INTERVAL = 20;

	private Thread sender = null;

	public Penetrator(int localPort, InetAddress targetIP) throws SocketException,
			UnknownHostException
	{
		socket = new DatagramSocket(localPort, InetAddress.getLocalHost());
		this.targetIP = targetIP;
		// System.out.println("Penetrator UDP socket set up. address="
		// + socket.getLocalSocketAddress()
		// + ", targetIP="
		// + this.targetIP);

		this.sender = new Thread(new Sender(), "Penetrator.Sender");
	}

	public void start()
	{
		this.sender.start();
	}

	private class Sender implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				byte[] buf = new String("N").getBytes();
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
					System.err.println("Failed to send udp packet.");
					ex.printStackTrace();
					continue;
				}

				try
				{
					Thread.sleep(SEND_INTERVAL);
				} catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}

	}
}
