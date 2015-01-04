package org.accela.udppunchholetest.bombard.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.accela.udppunchholetest.bombard.BatchHole;
import org.junit.Test;

public class TestBatchHole
{

	@Test
	public void test() throws UnknownHostException, InterruptedException
	{
		BatchHole bh = new BatchHole(InetAddress.getByAddress(new byte[] {
				123,
				45,
				67,
				89 }));
		
		bh.start();
		
		Thread.sleep(120*1000);
	}

}
