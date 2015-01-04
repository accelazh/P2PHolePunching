package org.accela.udppunchholetest.bombard.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.accela.udppunchholetest.bombard.BatchPenetrator;

public class TestPunchHole_Penetrator
{
	public static void main(String[] args) throws FileNotFoundException,
			UnknownHostException,
			InterruptedException,
			SocketException
	{
		Thread.sleep(1 * 1000);
		System.out.println("Penetrator Launched....");

		System.setOut(new PrintStream(new FileOutputStream("punchHoleLog_penetrator.txt")));
		BatchPenetrator p =
				new BatchPenetrator(InetAddress.getByAddress(new byte[] {
						(byte) 218, 94, (byte) 159, 98 }));

		p.start();

		Thread.sleep(300 * 1000);
	}

}
