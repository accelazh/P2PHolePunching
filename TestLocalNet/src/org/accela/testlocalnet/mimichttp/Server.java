package org.accela.testlocalnet.mimichttp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.accela.testlocalnet.Common;

public class Server
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket server= new ServerSocket(1234, 50, Common.IP);
		
		Socket client=server.accept();
		DataInputStream in=new DataInputStream(client.getInputStream());
		DataOutputStream out=new DataOutputStream(client.getOutputStream());
		
		byte[] buf=new byte[Common.DATA_LEN];
		
		while(true)
		{
			Arrays.fill(buf, (byte)0);
			
			int len=in.read(buf);
			System.out.println("read len: "+len);
			out.write(buf);
		}
		
	}

}
