package test1;

import java.util.LinkedList;
import java.util.List;

public class TestSubList
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		List<String> list=new LinkedList<String>();
		list.add("0");
		System.out.println(list.subList(1, list.size()));
		System.out.println(list.subList(1, list.size()).toArray(new String[0]).length);
	}

}
