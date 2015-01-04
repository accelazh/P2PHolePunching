package org.accela.testlocalnet.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.accela.testlocalnet.Common;

public class Server
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket server = new ServerSocket(1234, 50, Common.IP);

		Socket client = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(
				client.getOutputStream()));

		while (true)
		{
			System.out.println("From Client: " + in.readLine());
			System.out.println("From Client: " + in.readLine());
			System.out.println("From Client: " + in.readLine());
			out.println("Server said ... " + "[" + new Date() + "]");
			out.flush();
		}

		// client.close();

	}
}
