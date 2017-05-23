package io.ginkgo.test.interview;

public class Test1 {
	public static void main(String[] args) {
		String s1 = "123";
		String s2 = new String("123");
		System.out.println(s1.equals(s2));
		System.out.println(s1 == s2);
		int i1 = 123;
		int i2 = new Integer(123);
		System.out.println(i1 == i2);
		Integer I1 = 123;
		Integer I2 = new Integer(123);
		System.out.println(I1.equals(I2));
		System.out.println(I1 == I2);
	}
}