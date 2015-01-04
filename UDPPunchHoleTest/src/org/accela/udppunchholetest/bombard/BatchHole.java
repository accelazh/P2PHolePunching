package org.accela.udppunchholetest.bombard;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

public class BatchHole
{
	private InetAddress targetIP = null;

	private static final int COUNT = 256;

	private static final long INTERVAL = 10;

	private static final long SLEEP_TIME = 30 * 1000;

	private Thread luncher = null;

	private PortGenerator portGen = new PortGenerator();

	private List<Hole> holes = new LinkedList<Hole>();

	public BatchHole(InetAddress targetIP)
	{
		this.targetIP = targetIP;

		this.luncher = new Thread(new Luncher(), "BatchHole.Launcher");
	}

	public void start()
	{
		luncher.start();
	}

	private class Luncher implements Runnable
	{

		@Override
		public void run()
		{
			while (true)
			{
				launchHoles();

				try
				{
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}

		private void launchHoles()
		{
			for (int i = 0; i < COUNT; i++)
			{
				Hole h = null;
				try
				{
					h = new Hole(portGen.nextPort(), targetIP);
				}
				catch (IOException ex)
				{
					System.err.println("Failed to create a hole");
					ex.printStackTrace();
					continue;
				}

				holes.add(h);
				h.start();

				try
				{
					Thread.sleep(INTERVAL);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}

	}
}
