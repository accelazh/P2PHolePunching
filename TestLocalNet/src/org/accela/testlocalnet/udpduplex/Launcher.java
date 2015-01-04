package org.accela.testlocalnet.udpduplex;

import java.io.FileNotFoundException;
import java.net.SocketException;

public class Launcher
{
	public static void main(String[] args) throws SocketException,
			FileNotFoundException
	{
		final int portA = 2222;
		final int portB = 3333;

		Messager messagerA =
				new Messager(portA, portB, "RandDataA.dat", "RandDataB.dat");
		Messager messagerB =
				new Messager(portB, portA, "RandDataB.dat", "RandDataA.dat");
		
		messagerA.startToReceive();
		messagerB.startToReceive();
		
		messagerA.startToSend();
		messagerB.startToSend();
	}

}
