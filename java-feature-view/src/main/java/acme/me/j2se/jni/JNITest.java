package acme.me.j2se.jni;

import acme.me.j2se.serialize.Student;

public class JNITest {
	public native void plus(int i, int j);

	public native void minus(int i, int j);

	public native void print(String str);

	public native Student get(Student s);

	public native void exit();

	public static void main(String[] args) {
		System.loadLibrary("JNITest");
		JNITest tNative = new JNITest();
		tNative.plus(22, 20);
		
		Student s = tNative.get(new Student());
		System.out.println(s);
		
		tNative.minus(1, 23);
		
		tNative.exit();
	}
}
