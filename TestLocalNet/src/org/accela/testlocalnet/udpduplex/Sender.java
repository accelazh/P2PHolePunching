package org.accela.testlocalnet.udpduplex;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender implements Runnable
{
	private DatagramSocket socket = null;

	private InetAddress ip = null;

	private int port = 0;

	private DataInput data = null;

	private byte[] buf = new byte[Util.BLOCK_LEN];

	public Sender(DatagramSocket socket,
			InetAddress ip,
			int port,
			DataInput data)
	{
		this.socket = socket;
		this.ip = ip;
		this.port = port;
		this.data = data;
	}

	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				data.readFully(buf);
				DatagramPacket packet =
						new DatagramPacket(buf, buf.length, ip, port);
				socket.send(packet);

				try
				{
					Thread.sleep(Util.DURATION);
				} catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		} catch (EOFException ex)
		{
			// do nothing
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

}
