package com.javabasics4;

//singleton class
public class Assignment1 {

    private static Assignment1 instance = null;
    private Assignment1(){    }

    public static Assignment1 getInstance(){
        if(instance == null){
            synchronized (instance) { // only locks if new instance is needed.
                if(instance == null) {  // double checks to make sure no new instance was created if/while waiting
                    try {
                        instance = new Assignment1();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

}
