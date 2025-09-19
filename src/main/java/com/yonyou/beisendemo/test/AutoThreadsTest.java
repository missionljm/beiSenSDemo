package com.yonyou.beisendemo.test;

public class AutoThreadsTest implements Runnable{

    @Override
    public void run() {
        System.out.println("这是一个线程");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new AutoThreadsTest());
        thread.start();
    }
}
