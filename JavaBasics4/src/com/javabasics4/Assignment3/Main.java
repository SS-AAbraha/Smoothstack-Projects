package com.javabasics4.Assignment3;

public class Main {

    public static void main(String[] args){
        int intArray[] = new int[10];

        for(int i=0; i<intArray.length; i++){
            intArray[i] = -1;
        }

        Runnable myProducer = new Producer(intArray);
        Thread producerThread = new Thread(myProducer);

        Runnable myConsumer = new Consumer(intArray);
        Thread consumerThread = new Thread(myConsumer);

        producerThread.start();
        consumerThread.start();

    }
}
