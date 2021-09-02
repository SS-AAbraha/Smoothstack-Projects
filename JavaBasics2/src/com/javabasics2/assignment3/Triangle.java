package com.javabasics2.assignment3;

import java.util.concurrent.TransferQueue;

public class Triangle implements Shape{
    private int base;
    private int height;

    Triangle(){
        base = 5;
        height = 5;
    }
    Triangle(int base,int height){
        this.base = base;
        this.height = height;
    }
    public void setBase(int base) {
        this.base = base;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getBase() {
        return base;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public double CalculateArea(){
        return (base*height)/2;
    }

    @Override
    public void display(){
        System.out.println("Area: " + CalculateArea());
    }
}
