package org.accela.testlocalnet.bigtcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.accela.testlocalnet.Common;

public class Client
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		Socket server = new Socket(Common.IP, 1234);
		DataInputStream in=new DataInputStream(server.getInputStream());
		DataOutputStream out=new DataOutputStream(server.getOutputStream());
		
		byte[] buf=new byte[Common.DATA_LEN];
		
		while(true)
		{
			Arrays.fill(buf, (byte)0);
			
			out.write(Common.DATA);
			out.flush();
			in.readFully(buf);
			System.out.println("From Server: "+(Arrays.equals(buf, Common.DATA)?"data ok":"data wrong"));
		}
		
		//server.close();
	}
}
