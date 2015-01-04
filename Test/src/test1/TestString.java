package test1;

public class TestString
{
	public static void main(String[] args)
	{
		String str="man";
		
		long startTime=System.currentTimeMillis();
		for(int i=0;i<50000;i++)
		{
			str+=i;
		}
		long endTime=System.currentTimeMillis();
		System.out.println("elapse: "+(endTime-startTime));
	}

}
