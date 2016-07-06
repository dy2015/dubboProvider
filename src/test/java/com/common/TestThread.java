package com.common;

class MyThread implements Runnable {
	boolean stop = false; // 引入一个布尔型的共享变量stop
	private static MyThread myThread;

	public static MyThread getThread() {
		if (myThread == null) {
			synchronized (MyThread.class) {
				System.out.println("执行了！");
				myThread = new MyThread();
			}
		}
		return myThread;
	}

	public void run() {
		while (!stop) // 通过判断stop变量的值来确定是否继续执行线程体
		{
			System.out.println(Thread.currentThread().getName() + "is running");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Thread is exiting...");
	}
}

public class TestThread {
	public static void main(String[] args) throws InterruptedException {
		MyThread mm = MyThread.getThread();
		Thread m1 = new Thread(MyThread.getThread());
		Thread m2 = new Thread(MyThread.getThread());
		System.out.println("Starting thread...");
		m1.start();
		m2.start();
		Thread.sleep(3000);
		System.out.println("Interrupt thread...");
		mm.stop = true; // 修改共享变量
		Thread.sleep(3000); // 主线程休眠以观察线程中断后的情况
		System.out.println("Stopping application...");
	}
}