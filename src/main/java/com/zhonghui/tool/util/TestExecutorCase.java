package com.zhonghui.tool.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TestExecutorCase {
	private static Executor executor=Executors.newFixedThreadPool(10);
	public static void main(String[] args) {
		for(int i=0;i<20;i++){
			executor.execute(new Task());
		}
	}
	static class Task implements Runnable{
		
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}

}
