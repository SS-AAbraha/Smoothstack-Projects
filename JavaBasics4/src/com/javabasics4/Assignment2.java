package com.javabasics4;

import com.sun.source.tree.SynchronizedTree;

public class Assignment2 {

    public static void main (String[] args){

        Integer A1 = 0;
        Integer A2 = 10;

        System.out.println("START.");

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try{
                    synchronized (A1){
                            Thread.sleep(100);
                        synchronized (A2){
                            System.out.println("Thread 1 is done!");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Runnable myRunnable2 = new Runnable() {
            @Override
            public void run() {
                try{
                    synchronized (A2){
                        Thread.sleep(100);
                        synchronized (A1){
                            System.out.println("Thread 2 is done!");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        new Thread(myRunnable).start();
        new Thread(myRunnable2).start();

        System.out.println("END.");
    }
}
