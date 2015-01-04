package org.accela.testlocalnet.mimichttp;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
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

		StringBuffer sb = new StringBuffer();
		sb.append("GET /index.jsp HTTP/1.1\r\n");
		sb.append("Accept-Language: zh-cn\r\n");
		sb.append("Connection: Keep-Alive\r\n");
		sb.append("Host: 192.168.0.106\r\n");
		sb.append("Content-Length: 37000\r\n");
		sb.append("\r\n");
		sb.append("userName=new_andy&password=new_andy\r\n");
		for(int i=0;i<1000;i++)
		{
			rand.nextBytes(buf);
			sb.append(new Date().toString()+"\r\n");
		}
		sb.append("\r\n");

		while (true)
		{
			out.println(sb.toString());
			out.flush();
			in.readFully(buf);
		}
	}

}
