package test1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TestRandomAccessFile
{
	public static void main(String[] args) throws IOException
	{
		RandomAccessFile raf=new RandomAccessFile("raf.dat", "rw");
		for(int i=0;i<254;i++)
		{
			raf.writeByte(i);
		}
		
		raf.seek(0);
		for(int i=0;i<254;i++)
		{
			System.out.print(raf.readByte()+" ");
		}
		System.out.println();
		
		raf.seek(100);
		raf.write(123);
		
		raf.seek(0);
		for(int i=0;i<254;i++)
		{
			System.out.print(raf.readByte()+" ");
		}
		System.out.println();
		
	}
}
