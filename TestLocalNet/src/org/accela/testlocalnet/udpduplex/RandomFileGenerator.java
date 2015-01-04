package org.accela.testlocalnet.udpduplex;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class RandomFileGenerator
{
	public static void main(String[] args) throws IOException
	{
//		DataInputStream in=new DataInputStream(new FileInputStream("RandData2.dat"));
//		for(int i=0;i<100;i++)
//		{
//			System.out.print(in.readByte()+" ");
//		}
				
		DataOutputStream out=new DataOutputStream(new FileOutputStream("RandData3.dat"));
		byte[] buf=new byte[1024*1024];
		Random rand=new Random();
		
		for(int i=0;i<1024;i++)
		{
			rand.nextBytes(buf);
			out.write(buf);
			System.out.println("i: "+i);
		}
	}

}
