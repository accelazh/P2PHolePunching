package org.accela.udppunchholetest.bombard.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.accela.udppunchholetest.bombard.PortGenerator;
import org.junit.Test;

public class TestPortGenerator
{
	@Test
	public void test() throws FileNotFoundException
	{
		PortGenerator pg = new PortGenerator();
		System.setOut(new PrintStream(new FileOutputStream("sysOut.txt")));
		for (int i = 0; i < 1000000; i++)
		{
			int port = pg.nextPort();
			System.out.println(port);
		}
	}
}
