package org.accela.testlocalnet;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Common
{
	private static InetAddress preIP = null;

	static
	{
		try
		{
			preIP = InetAddress.getByAddress(new byte[] { 10, 0, 0, 10 });
		}
		catch (UnknownHostException ex)
		{
			ex.printStackTrace();
		}
	}

	public static final InetAddress IP = preIP;

	public static final int DATA_LEN = 3 * 1024 * 1024;
	public static final byte[] DATA = new byte[DATA_LEN];

	static
	{
		try
		{
			DataInputStream in = new DataInputStream(new FileInputStream(
					"CommonData.dat"));
			in.readFully(DATA);
			in.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public static final int TOKEN_LEN = 1024*50; //55535;
	public static final byte[] TOKEN = new byte[TOKEN_LEN];

	static
	{
		try
		{
			DataInputStream in = new DataInputStream(new FileInputStream(
					"CommonData.dat"));
			in.readFully(TOKEN);
			in.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

}
