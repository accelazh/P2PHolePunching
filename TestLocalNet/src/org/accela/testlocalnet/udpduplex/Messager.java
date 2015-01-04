package org.accela.testlocalnet.udpduplex;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.accela.testlocalnet.Common;

public class Messager
{
	private Thread senderThread = null;
	private Thread receiverThread = null;

	public Messager(int localPort,
			int remotePort,
			String dataFileToSend,
			String dataFileToReceive) throws SocketException,
			FileNotFoundException
	{
		DatagramSocket socket = new DatagramSocket(localPort, Common.IP);

		Sender sender =
				new Sender(socket,
						Common.IP,
						remotePort,
						new DataInputStream(new FileInputStream(dataFileToSend)));
		Receiver receiver =
				new Receiver(socket,
						new DataInputStream(new FileInputStream(dataFileToReceive)));

		this.senderThread = new Thread(sender);
		this.receiverThread = new Thread(receiver);
	}

	public void startToReceive()
	{
		this.receiverThread.start();
	}

	public void startToSend()
	{
		this.senderThread.start();
	}
}
