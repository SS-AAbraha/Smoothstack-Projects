package com.javabasics2.assignment3;

import org.w3c.dom.css.Rect;

import javax.print.attribute.standard.PresentationDirection;

public class Rectangle implements Shape{
    private int length;
    private int width;

    Rectangle(){
        length = 5;
        width = 5;
    }
    Rectangle(int length, int width){
        this.length = length;
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }

    @Override
    public double CalculateArea(){
        return width*length;
    }

    @Override
    public void display(){
        System.out.println("Area: " + CalculateArea());
    }
}
