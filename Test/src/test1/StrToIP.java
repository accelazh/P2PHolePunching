package test1;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class StrToIP
{
	public static void main(String[] args) throws UnknownHostException
	{
		String ipStr="192.43.67.89";
		System.out.println(InetAddress.getByName(ipStr));
		System.out.println(Arrays.toString(InetAddress.getByName(ipStr).getAddress()));
		
	}

}
