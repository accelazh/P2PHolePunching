package org.accela.testlocalnet.tcppush;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

import org.accela.testlocalnet.Common;

public class Client
{
	public static void main(String[] args) throws IOException
	{
		Socket server = new Socket(Common.IP, 1234);

		DataInputStream in = new DataInputStream(server.getInputStream());
		PrintWriter out = new PrintWriter(server.getOutputStream());

		byte[] buf = new byte[64];
		Random rand = new Random();

		while (true)
		{
			for (int i = 0; i < 100; i++)
			{
				rand.nextBytes(buf);
				out.println(Arrays.toString(buf));
			}
			out.flush();
			in.readFully(buf);
		}
	}

}
