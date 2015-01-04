package org.accela.udppunchholetest.bombard;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;

public class BatchPenetrator
{

	private InetAddress targetIP = null;

	private static final int COUNT = 256;

	private static final long INTERVAL = 10;

	private static final long SLEEP_TIME = 30 * 1000;

	private Thread luncher = null;

	private PortGenerator portGen = new PortGenerator();

	private List<Penetrator> penetrators = new LinkedList<Penetrator>();

	public BatchPenetrator(InetAddress targetIP)
	{
		this.targetIP = targetIP;

		this.luncher = new Thread(new Luncher(), "BatchPenetrator.Launcher");
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
				launchPenetrators();

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

		private void launchPenetrators()
		{
			for (int i = 0; i < COUNT; i++)
			{
				Penetrator p = null;
				try
				{
					p = new Penetrator(portGen.nextPort(), targetIP);
				}
				catch (IOException ex)
				{
					System.err.println("Failed to create a penetrator");
					ex.printStackTrace();
					continue;
				}

				penetrators.add(p);
				p.start();

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
