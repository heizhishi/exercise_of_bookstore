package com.example.test;

public class ThreadLocalTest implements Runnable {
	ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	int i = 0;
	String name = null;

	@Override
	public void run() {
		for (; i < 5; i++) {
			name = Thread.currentThread().getName();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			threadLocal.set(Thread.currentThread().getName());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ":"
					+ threadLocal.get());
		}
	}

	public static void main(String[] args) {
		ThreadLocalTest threadLocalTest = new ThreadLocalTest();
		new Thread(threadLocalTest, "AAA").start();
		new Thread(threadLocalTest, "BBB").start();
	}
}
