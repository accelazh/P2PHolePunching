package org.accela.testlocalnet.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.accela.testlocalnet.Common;

public class Client
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		Socket server = new Socket(Common.IP, 1234);
		
		BufferedReader in=new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter out=new PrintWriter(new OutputStreamWriter(server.getOutputStream()));
		
		while(true)
		{
			out.println("Client said ... "+"["+new Date()+"]");
			out.println("Client said ... "+"["+new Date()+"]");
			out.println("Client said ... "+"["+new Date()+"]");
			out.flush();
			System.out.println("From Server: "+in.readLine());
		}
		
		//server.close();
	}
}
