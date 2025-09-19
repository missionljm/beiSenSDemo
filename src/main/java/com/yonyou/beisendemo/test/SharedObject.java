package com.yonyou.beisendemo.test;

import cn.hutool.json.JSONUtil;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

class SharedObject {
    private String message;
    private boolean hasMessage = false;

    public synchronized void writeMessage(String message) {
        while (hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        hasMessage = true;
        notifyAll();
    }

    public synchronized String readMessage() {
        while (!hasMessage) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasMessage = false;
        notifyAll();
        return message;

    }

    public static Map<String, List<Integer>> map = new HashMap<>();
    public static void main(String[] args) {
//        SharedObject sharedObject = new SharedObject();
//        Thread writer = new Thread(() -> {
//            sharedObject.writeMessage("Hello from Writer!");
//        });
//        Thread reader = new Thread(() -> {
//            String message = sharedObject.readMessage();
//            System.out.println("Reader received: " + message);
//        });
//        writer.start();
//        reader.start();
        SharedObject sharedObject = new SharedObject();
        sharedObject.show();
        sharedObject.show();
        sharedObject.show();
        sharedObject.show();
    }

    static {
        List list = new ArrayList<>();
        list.add(10);
        map.put("key" , list);
    }



    public void show() {
        List<Integer> list = get("key");
        list.add(10);
        System.out.println(JSONUtil.toJsonStr(map));
    }

    public List<Integer> get(String key) {
        return map.computeIfAbsent(key, k -> new ArrayList<>());
    }

}
