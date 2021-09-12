package com.javabasics5.Assignment2;

@FunctionalInterface
interface PerformOperation{
    boolean check(int a);
}


//Lambdas
public class Assignment2_1 {

    public static void main(String[] args){
        int numCases = 0;


        if(args.length <= 0) {
            System.out.println("No input detected!");
        }else {
            numCases = Integer.parseInt(args[0]);

            if (numCases > 0) {
                int[] testType = new int[numCases];
                int[] testNumber = new int[numCases];
                int count = 0;

                // get the rest of the input
                try {
                    for (int i = 1; i <= numCases * 2; i++) {
                        testType[count] = Integer.parseInt(args[i]);
                        testNumber[count] = Integer.parseInt(args[++i]);
                        count++;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                // method to check if a number is odd or even
                PerformOperation isOdd = number -> {
                    if(number%2 == 0)   // if even
                        return false;
                    else
                        return true;
                };

                // method for checking if a number is prime
                PerformOperation isPrime = number -> {
                    boolean divisibleByOther = false;

                    if(number < 0){ // negative number
                        return false;
                    }
                    if(number < 4){ // less than 4 and positive
                        return true;
                    }
                    // number is greater than 3
                    for(int i=2; i<number; i++){
                        if(number%i == 0){
                            return false;
                        }
                    }
                    //not divisible by any number between 2 and number-1.
                    return true;
                };

                //method to check if a number is a palindrome
                PerformOperation isPalindrome = number -> {
                    int nReversed = 0;
                    int length = 1;

                    int temp = number;
                    while(true){
                        if(temp > 9){
                            length++;
                            temp = temp/10;
                        }else
                            break;
                    }

                    temp = number;
                    for(int i=0; i<length; i++){
                        nReversed = (nReversed*10) + temp%10;
                        temp = temp/10;
                    }

                    if(nReversed == number)
                        return true;
                    else
                        return false;
                };



                for (int i = 0; i < numCases; i++) {
                    if(testType[i] == 1)    //  check for even or odd.

                        if(isOdd.check(testNumber[i]))
                            System.out.println("ODD");
                        else
                            System.out.println("EVEN");

                    if(testType[i] == 2)    // check for prime numbers.
                        if(isPrime.check(testNumber[i]))
                            System.out.println("PRIME");
                        else
                            System.out.println("COMPOSITE");

                    if(testType[i] == 3)    // check for palindrome numbers.
                        if(isPalindrome.check(testNumber[i]))
                            System.out.println("PALINDROME");
                        else
                            System.out.println("NON-PALINDROME");
                }

            }
        }
    }

}
