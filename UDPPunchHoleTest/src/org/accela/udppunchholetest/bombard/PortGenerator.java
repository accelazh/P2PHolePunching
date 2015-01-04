package org.accela.udppunchholetest.bombard;


public class PortGenerator
{
	private static final int MIN = 1024;

	private static final int MAX = 65535;

	private static final int GAP = 256;

	private int offset = 0;

	private int count = 0;

	public PortGenerator()
	{
		// do nothing
	}

	public synchronized int nextPort()
	{
		int port = calPort();
		incrPort();

		assert (port >= MIN);
		assert (port <= MAX);
		return port;
	}

	private int calPort()
	{
		int port = MIN + GAP * count + offset;
		return port;
	}

	private void incrPort()
	{
		count++;
		if (calPort() > MAX)
		{
			count = 0;
			offset++;
			if (offset >= GAP)
			{
				offset = 0;
			}
		}
	}
	
	

}
