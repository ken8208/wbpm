/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.wbpm.test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:ken@wolaidai.com">ken</a>
 */
public class LambdaDemo
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		String[] array = {"a", "b", "c"};
		for (Integer i : Lists.newArrayList(1, 2, 3))
		{
			Stream.of(array).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
		}
		List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
		IntStream stream =
				nums.stream().filter(num -> num != null).distinct().mapToInt(num -> num * 2).peek(System.out::println);
		stream.forEach(num -> System.out.println("num = "+num));
//		System.out.println("sum is:" + stream.skip(2).limit(4).sum());
		List<Integer> ints = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
		System.out.println("ints sum is:" + ints.stream().reduce((sum, item) -> sum + item).get());


	}

}
