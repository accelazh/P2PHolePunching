package test1;

public class SubstringTest
{
	public static void main(String[] args)
	{
		String command = " \" nh hao";
		String message =
				command.substring(command.indexOf('\"') + 1,
						command.lastIndexOf('\"'));
		System.out.println("|" + message + "|");
		
		StringIndexOutOfBoundsException ex;
	}

}
