package test1;

public class TestByteToIntv
{
	public static void main(String[] args)
	{
		byte bytes = -42;

		int result = bytes & 0xff;
		System.out.println("无符号数: \t" + result);
		System.out.println("2进制bit位: \t" + Integer.toBinaryString(result));
	}
}
