package com.javabasics4.Assignment3;

public class Consumer implements Runnable{

    public int array[];

    Consumer(int[] array){
        this.array = array;
    }

    @Override
    public void run() {
        int sleepTimer = 150;
        while (true) {
            try {
                Thread.sleep(sleepTimer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = array.length - 1; i >= 0; i--) {
                if (array[i] >= 0) {
                    System.out.println("Consumed " + array[i]);
                    array[i] = -1;
                    break;
                }
                if(i == 0){ // if array is empty consumer doesn't consume
                    System.out.println("Array empty. Nothing to consume! ");
                }
            }

        }
    }
}
