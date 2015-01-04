package org.accela.testlocalnet.udpduplex;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Receiver implements Runnable
{
	private DatagramSocket socket = null;

	private DataInput data = null;

	private byte[] recvBuf = new byte[Util.BLOCK_LEN];

	private byte[] readBuf = new byte[Util.BLOCK_LEN];

	public Receiver(DatagramSocket socket, DataInput data)
	{
		this.socket = socket;
		this.data = data;
	}

	@Override
	public void run()
	{
		try
		{
			int count = 0;
			while (true)
			{
				data.readFully(readBuf);
				DatagramPacket packet =
						new DatagramPacket(recvBuf, recvBuf.length);
				socket.receive(packet);
				
				if (!Arrays.equals(readBuf, recvBuf))
				{
					System.out.println("[" + Receiver.this
							+ "] "
							+ "Broken data received: count="
							+ count);
				}
				else
				{
//					System.out.println("[" + Receiver.this
//							+ "] "
//							+ "Good data received: count="
//							+ count);
				}

				count++;
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
