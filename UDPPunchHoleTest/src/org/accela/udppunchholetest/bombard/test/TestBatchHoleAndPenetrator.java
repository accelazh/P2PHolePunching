package org.accela.udppunchholetest.bombard.test;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.accela.udppunchholetest.bombard.BatchHole;
import org.accela.udppunchholetest.bombard.Penetrator;
import org.junit.Test;

public class TestBatchHoleAndPenetrator
{
	@Test
	public void test() throws UnknownHostException, SocketException,
			InterruptedException
	{
		BatchHole bh = new BatchHole(InetAddress.getLocalHost());
		Penetrator p = new Penetrator(22224,
				InetAddress.getLocalHost());

		bh.start();
		p.start();

		Thread.sleep(120 * 1000);
	}
}
