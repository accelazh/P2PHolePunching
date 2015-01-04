package org.accela.testlocalnet.tokentcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.accela.testlocalnet.Common;

public class Client
{
	public static void main(String[] args) throws IOException
	{
		Socket server = new Socket(Common.IP, 1234);

		DataInputStream in = new DataInputStream(server.getInputStream());
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
		

		while (true)
		{
			out.write(Common.TOKEN);
			out.flush();
		}
	}

}
