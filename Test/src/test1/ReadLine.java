package test1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadLine
{

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader in =
				new BufferedReader(new FileReader("testReadLine.txt"));
		System.out.println(in.readLine());
		System.out.println(in.readLine());
		System.out.println(in.readLine());
	}

}
