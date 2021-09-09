package com.javabasics4.Assignment3;

public class Producer implements Runnable{

    public int array[];

    Producer(int[] array){
        this.array = array;
    }

    @Override
    public void run() {
        int sleepTimer = 100;
        while(true) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] < 0) {
                    array[i] = i; // produces values on first empty slot
                    System.out.println("produced " + i);
                    break;
                }
                if(i == array.length-1){ // producer doesn't produce when all slots are full
                    System.out.println("Array full. Producer cannot produce!");
//                    sleepTimer = 350; // slows down producer rate (once full).
                }
            }
            try {
                Thread.sleep(sleepTimer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
