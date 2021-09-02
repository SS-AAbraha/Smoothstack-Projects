package com.javabasics2.assignment3;

public class Circle implements Shape {
    static double pi = 3.1415;
    private int radius;

    Circle(){
        radius = 5;
    }
    Circle(int radius){
        this.radius = radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getRadius() {
        return radius;
    }

    @Override
    public double CalculateArea(){
        return pi*(radius*radius);
    }

    @Override
    public void display(){
        System.out.println("Area: " + CalculateArea());
    }
}
