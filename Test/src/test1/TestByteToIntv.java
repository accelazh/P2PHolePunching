package test1;

public class TestByteToIntv
{
	public static void main(String[] args)
	{
		byte bytes = -42;

		int result = bytes & 0xff;
		System.out.println("�޷�����: \t" + result);
		System.out.println("2����bitλ: \t" + Integer.toBinaryString(result));
	}
}
