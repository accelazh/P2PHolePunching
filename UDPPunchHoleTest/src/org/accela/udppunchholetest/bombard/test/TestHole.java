package org.accela.udppunchholetest.bombard.test;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.accela.udppunchholetest.bombard.Hole;
import org.junit.Test;

public class TestHole
{
	@Test
	public void test() throws SocketException, UnknownHostException,
			InterruptedException
	{
		Hole hole = new Hole(22223, InetAddress.getByAddress(new byte[] {
				123,
				45,
				67,
				89 }));
		hole.start();

		Thread.sleep(120 * 1000);
	}
}
