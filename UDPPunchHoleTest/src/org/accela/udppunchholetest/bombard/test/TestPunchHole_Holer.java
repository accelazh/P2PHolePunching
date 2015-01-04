package org.accela.udppunchholetest.bombard.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.accela.udppunchholetest.bombard.BatchHole;

public class TestPunchHole_Holer
{
	public static void main(String[] args) throws FileNotFoundException,
			UnknownHostException,
			InterruptedException
	{
		Thread.sleep(1 * 1000);
		System.out.println("Holer Launched....");

		System.setOut(new PrintStream(new FileOutputStream("punchHoleLog_holer.txt")));
		BatchHole bh =
				new BatchHole(InetAddress.getByAddress(new byte[] { (byte) 219,
						(byte) 219, 127, 2 }));

		bh.start();

		Thread.sleep(300 * 1000);
	}

}
