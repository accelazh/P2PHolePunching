package test1;

public class TestThread
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Thread t = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException ex)
					{
						ex.printStackTrace();
					}
				}
			}

		});
		t.start();
	}

}
