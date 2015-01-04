package org.accela.testlocalnet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CommonDataFiller
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		File file=new File("CommonData.dat");
		PrintWriter out=new PrintWriter(new FileWriter(file));
		while(file.length()<Common.DATA_LEN)
		{
			out.println("Hello, This is a test file!");
			out.flush();
			
			System.out.println("file length: "+file.length());
		}
		
		out.close();
	}

}
