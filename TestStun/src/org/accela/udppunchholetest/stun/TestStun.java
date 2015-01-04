package org.accela.udppunchholetest.stun;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Date;

import de.javawi.jstun.attribute.ChangeRequest;
import de.javawi.jstun.attribute.ChangedAddress;
import de.javawi.jstun.attribute.ErrorCode;
import de.javawi.jstun.attribute.MappedAddress;
import de.javawi.jstun.attribute.MessageAttribute;
import de.javawi.jstun.attribute.MessageAttributeParsingException;
import de.javawi.jstun.header.MessageHeader;
import de.javawi.jstun.header.MessageHeaderInterface.MessageHeaderType;
import de.javawi.jstun.header.MessageHeaderParsingException;
import de.javawi.jstun.util.UtilityException;

public class TestStun
{
	private static PrintWriter record = null;;

	public static void main(String[] args) throws UtilityException,
			IOException,
			MessageHeaderParsingException,
			MessageAttributeParsingException
	{
		record = new PrintWriter(new FileWriter("mapRecord.txt", true));

		DatagramSocket socket =
				new DatagramSocket(0, InetAddress.getLocalHost());
		socket.setSoTimeout(9 * 1000);
		System.out.println("Local socket set up: address = " + socket
				.getLocalSocketAddress());

		record.println();

		testoriginal(socket, "jstun.javawi.de", 3478);
		testoriginal(socket, "stun.ekiga.net", 3478);
		testoriginal(socket, "stun.sipgate.net", 10000);

		testoriginal(socket, "stun.xten.net", 3478);
		testoriginal(socket, "stun.softjoys.com", 3478);
		testoriginal(socket, "stun.voipstunt.com", 3478);
	}

	private static void testoriginal(DatagramSocket socket,
			final String stunServer,
			final int stunPort) throws UtilityException,
			UnknownHostException,
			IOException,
			MessageHeaderParsingException,
			MessageAttributeParsingException
	{
		System.out.println();
		System.out.println("************************************************");
		System.out.println("Stun Server: " + stunServer);
		System.out.println("Stun Port: " + stunPort);
		System.out.println("************************************************");
		System.out.println();

		// 准备工作
		StunParameter stun = new StunParameter();
		stun.socket = socket;
		stun.orignAddr =
				new InetSocketAddress(InetAddress.getByName(stunServer),
						stunPort);
		stun.dontPrint = true;
		for (int i = 0; i < 3 && null == stun.changedAddr; i++)
		{
			stun.changedAddr = testBindRequest(stun);
		}
		if (null == stun.changedAddr)
		{
			return;
		}
		stun.dontPrint = false;

		/*
		 * 为什么要把下列测试走两遍？因为在第二遍： 如果nat是Port restricted cone
		 * nat，下面的测试可以测出nat能否在一个session中同时允许original port 和changed
		 * port都通过。如果nat是restricted cone nat，下面的测试可以测出nat能否在一个session中
		 * 同时允许original IP和changed IP都通过。
		 */
		for (int i = 0; i < 2; i++)
		{
			System.out.println("=================Round "+(i+1)+"==================\n");
			
			// 发送给原IP/port，从原IP/port发回
			stun.clearChanges();
			testBindRequest(stun);

			// 发送给原IP/port，从改变的IP/port发回
			stun.clearChanges();
			stun.changeRecvPort = true;
			testBindRequest(stun);

			stun.clearChanges();
			stun.changeRecvIP = true;
			testBindRequest(stun);

			stun.clearChanges();
			stun.changeRecvIP = true;
			stun.changeRecvPort = true;
			testBindRequest(stun);

			// 发送给改变的IP/port，从原的IP/port发回
			stun.clearChanges();
			stun.changeSendPort = true;
			testBindRequest(stun);

			stun.clearChanges();
			stun.changeSendIP = true;
			testBindRequest(stun);

			stun.clearChanges();
			stun.changeSendIP = true;
			stun.changeSendPort = true;
			testBindRequest(stun);
		}

		record.println();
		record.flush();
	}

	private static class StunParameter
	{
		public DatagramSocket socket = null;

		public InetSocketAddress orignAddr = null;
		public InetSocketAddress changedAddr = null;

		public boolean changeSendIP = false;
		public boolean changeSendPort = false;

		public boolean changeRecvIP = false;
		public boolean changeRecvPort = false;

		public boolean dontPrint = false;

		public void clearChanges()
		{
			changeSendIP = false;
			changeSendPort = false;

			changeRecvIP = false;
			changeRecvPort = false;
		}
	}

	private static InetSocketAddress testBindRequest(StunParameter stun) throws MessageHeaderParsingException,
			MessageAttributeParsingException,
			UtilityException,
			IOException
	{
		assert ((!stun.changeSendIP && !stun.changeSendPort) || (!stun.changeRecvIP && !stun.changeRecvPort));

		boolean send = !stun.changeRecvIP && !stun.changeRecvPort;
		boolean changedIP = send ? stun.changeSendIP : stun.changeRecvIP;
		boolean changedPort = send ? stun.changeSendPort : stun.changeRecvPort;

		if (!stun.dontPrint)
		{
			System.out.println("====Testing with " + (send ? "sending to"
					: "receiving from")
					+ " "
					+ (changedIP ? "changed" : "original")
					+ " IP and "
					+ (changedPort ? "changed" : "original")
					+ " port====");
		}

		InetAddress sendIP = null;
		int sendPort = 0;

		if (!send)
		{
			sendIP = stun.orignAddr.getAddress();
			sendPort = stun.orignAddr.getPort();
		} else
		{
			if (stun.changeSendIP)
			{
				sendIP = stun.changedAddr.getAddress();
			} else
			{
				sendIP = stun.orignAddr.getAddress();
			}

			if (stun.changeSendPort)
			{
				sendPort = stun.changedAddr.getPort();
			} else
			{
				sendPort = stun.orignAddr.getPort();
			}
		}

		MessageAttribute[] attr =
				bindRequest(stun.socket,
						sendIP,
						sendPort,
						stun.changeRecvIP,
						stun.changeRecvPort,
						stun.dontPrint);

		if (!stun.dontPrint)
		{
			System.out.println();
		}

		if (attr != null)
		{
			ChangedAddress retChangedAddr = (ChangedAddress) attr[1];
			return new InetSocketAddress(InetAddress.getByName(retChangedAddr
					.getAddress().toString()), retChangedAddr.getPort());
		} else
		{
			return null;
		}
	}

	private static MessageAttribute[] bindRequest(DatagramSocket socket,
			InetAddress stunServer,
			int stunPort,
			boolean changeRecvIP,
			boolean changeRecvPort,
			boolean dontPrint) throws UtilityException,
			IOException,
			MessageHeaderParsingException,
			MessageAttributeParsingException
	{
		MessageHeader header =
				new MessageHeader(MessageHeaderType.BindingRequest);
		header.generateTransactionID();

		ChangeRequest changeReq = new ChangeRequest();
		if (changeRecvIP)
		{
			changeReq.setChangeIP();
		}
		if (changeRecvPort)
		{
			changeReq.setChangePort();
		}
		header.addMessageAttribute(changeReq);

		byte[] data = header.getBytes();
		DatagramPacket packet =
				new DatagramPacket(data, data.length, stunServer, stunPort);

		SocketAddress sendToAddr = packet.getSocketAddress();
		if (!dontPrint)
		{
			System.out.println("Request sent to " + packet.getSocketAddress());
		}
		socket.send(packet);

		MessageHeader recvHeader = null;
		DatagramPacket recvPacket = null;
		do
		{
			recvPacket = new DatagramPacket(new byte[1024], 1024);
			try
			{
				socket.receive(recvPacket);
			} catch (SocketTimeoutException ex)
			{
				if (!dontPrint)
				{
					System.out
							.println("Can't receive the reply: socket time out.");
				}
				return null;
			}

			recvHeader = MessageHeader.parseHeader(recvPacket.getData());
			recvHeader.parseAttributes(recvPacket.getData());

		} while (!(recvHeader.equalTransactionID(header)));

		if (!dontPrint)
		{
			System.out.println("Reply received from " + recvPacket
					.getSocketAddress());
		}

		MappedAddress mappedAddr =
				(MappedAddress) recvHeader
						.getMessageAttribute(MessageAttribute.MessageAttributeType.MappedAddress);
		ChangedAddress changedAddr =
				(ChangedAddress) recvHeader
						.getMessageAttribute(MessageAttribute.MessageAttributeType.ChangedAddress);
		ErrorCode ec =
				(ErrorCode) recvHeader
						.getMessageAttribute(MessageAttribute.MessageAttributeType.ErrorCode);
		if (ec != null)
		{
			System.err
					.println("Message header contains an Errorcode message attribute.");
			System.err.println(ec.getResponseCode() + "\n" + ec.getReason());

			return null;
		}

		if (!dontPrint)
		{
			System.out.println("Mapped Address: " + mappedAddr);
			System.out.println("Changed Address: " + changedAddr);
		}

		if (!dontPrint)
		{
			record.print(new Date() + "\t\t");
			record.print(addressToString(socket.getLocalSocketAddress()) + "\t\t");
			record.print(addressToString(sendToAddr) + "\t\t");
			record.print(ipToString(mappedAddr.getAddress().getInetAddress()) + ":"
					+ mappedAddr.getPort()
					+ "\t\t");
			record.println();
		}

		return new MessageAttribute[] { mappedAddr, changedAddr };
	}

	private static String ipToString(InetAddress ipAddr)
	{
		byte[] ip = ipAddr.getAddress();
		String ret = "";
		for (byte b : ip)
		{
			ret += (b & 0xff) + ".";
		}
		ret = ret.substring(0, ret.length() - 1);

		return ret;
	}

	private static String addressToString(SocketAddress addr)
	{
		InetSocketAddress inAddr = (InetSocketAddress) addr;
		String ret = ipToString(inAddr.getAddress());
		ret += ":" + inAddr.getPort();

		return ret;
	}
}
