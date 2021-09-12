package com.javabasics5.Assignment2;

import java.util.List;

public class Assignment2_5 {

    public static void main(String[] args) {
        int[] intArray = {4,5,2,2,2,3,7,9};
        boolean returnVal = groupSumClump(0,intArray,20);

        if (returnVal)
            System.out.println("TRUE");
        else
            System.out.println("FALSE");

    }

    public static boolean groupSumClump(int x, int[] array, int sum){
        int consecutiveCount = 1;
//        int currentSum = 0;

        if(x+1 < array.length && array[x] == array[x+1]) {
            consecutiveCount++;
            x++;
            while(x+1<array.length){
                if(array[x] == array[x+1]) {
                    consecutiveCount++;
                    x++;
                }else
                    break;

            }
        }

        int clumpSum = consecutiveCount*array[x]; // current number or sum of all current consecutive number(if more than 1)
        if(sum-clumpSum == 0)   // current value
        {
            return true;
        }
//        if(x == array.length)
        if(x < array.length-1) {
            // current sum added
            if (groupSumClump(x+1, array, sum - clumpSum))
                return true;
            // current sum not added
            if (groupSumClump(x+1, array, sum))
                return true;
        }
        return false;




        /*
        for (int i=x; i<array.length; i++){
            if (array[i] == array[i+1]){ // if next value is identical to current
                consecutiveCount++;
            }else{
                int consecutiveSum = consecutiveCount * array[i]; // current number or sum of all consecutive numbers (if more than 1)
                if ((currentSum + consecutiveSum) == sum){
                    return true;
                }else{

                    /*
                    if ((currentSum + (consecutiveSum)) < sum) {
                        currentSum += consecutiveSum;
                    }else{
                        groupSumClump();
                    }
                     /
                    consecutiveCount = 1;
                }
            }

        }
         */

    }
}
