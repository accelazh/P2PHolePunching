package org.accela.testlocalnet.bighttp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.accela.testlocalnet.Common;

public class Server
{
	public static void main(String[] args) throws IOException
	{
		URL url=new URL("http://www.google.com");
		HttpURLConnection conn=(HttpURLConnection)url.openConnection();

		BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line=null;
		while((line=in.readLine())!=null)
		{
			System.out.println(line);
		}
		
		conn.setDoOutput(true);
		DataOutputStream out=new DataOutputStream(conn.getOutputStream());
		out.write(Common.DATA);
		out.flush();
		
		
		in.close();
		out.close();
	}

}
