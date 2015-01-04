package org.accela.udppunchholetest.bombard.test;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.accela.udppunchholetest.bombard.Penetrator;
import org.junit.Test;

public class TestPenetrator
{
	@Test
	public void test() throws SocketException, UnknownHostException,
			InterruptedException
	{
		Penetrator p = new Penetrator(22224,
				InetAddress.getByAddress(new byte[] { 123, 45, 67, 89 }));

		p.start();

		Thread.sleep(120 * 1000);
	}
}
