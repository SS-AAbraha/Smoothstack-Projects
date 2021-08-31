package com.javabasics1;

public class Assignment1 {
    public static void main(String[] args){

        System.out.println("   *   ");
        System.out.println("  ***  ");
        System.out.println(" ***** ");
        System.out.println("*******");
        System.out.println(".........");
        System.out.println(".........");
        System.out.println("*******");
        System.out.println(" ***** ");
        System.out.println("  ***  ");
        System.out.println("   *   ");

        // triangle
        for(int i=0;i<4;i++){
            for(int j=0;j<=i;j++){
                System.out.printf("*");
            }
            System.out.println();
        }
        System.out.println(".........");

        // upside-down triangle
        System.out.println("..........");
        for(int i=4;i>0;i--){
            for(int j=0;j<i;j++){
                System.out.printf("*");
            }
            System.out.println();
        }
        System.out.println();

        // pyramid
        for(int i=0;i<7;i=i+2){
            System.out.printf(" ");
            int spaces = (7-i)/2;
            for(int k=0; k<spaces; k++)
                System.out.printf(" ");

            for(int j=0;j<=i;j++){
                System.out.printf("*");
            }

            for(int k=0; k<spaces; k++)
                System.out.printf(" ");
            System.out.printf(" ");
            System.out.println();
        }
        System.out.println("...........");

        // upside-down pyramid
        System.out.println("............");
        for(int i=7;i>0;i=i-2){
            System.out.printf(" ");
            int spaces = (7-i)/2;
            for(int k=0; k<spaces; k++)
                System.out.printf(" ");

            for(int j=0;j<i;j++){
                System.out.printf("*");
            }

            for(int k=0; k<spaces; k++)
                System.out.printf(" ");
            System.out.printf(" ");
            System.out.println();
        }


    }
}
