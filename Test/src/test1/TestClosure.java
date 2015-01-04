package test1;

public class TestClosure
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int a=10;
		Runnable task=new Runnable(){

			@Override
			public void run()
			{
				System.out.println(a);
				"123".trim();
			}
			
		};
	}

}
