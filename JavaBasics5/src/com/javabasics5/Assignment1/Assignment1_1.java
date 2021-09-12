package com.javabasics5.Assignment1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Assignment1_1 {

    public static void main(String[] args){
        String[] strArray = {"John","Eve","Bill","Smith", "Steve"};
        List<String> names = Arrays.asList(strArray);


        // SORT by:
        // length
        Collections.sort(names, (l0,l1) -> (l0.length() - l1.length()));
        System.out.println("Sort by length (shortest to longest): ");
        names.forEach(l-> System.out.println("\t" + l));

        // reverse length
        Collections.sort(names, (l0,l1) -> (l1.length() - l0.length()));
        System.out.println("Sort by length (longest to shortest): ");
        names.forEach(l -> System.out.println("\t" + l));

        // alphabetical order (first char only)
        Collections.sort(names, (l0,l1) -> ( l0.toLowerCase().charAt(0) - l1.toLowerCase().charAt(0)));
        System.out.println("Sort by alphabetical order:");
        names.forEach(l -> System.out.println("\t" + l));

        // string that contain "e" first
        Collections.sort(names, (l0,l1)-> {
            for (int i=0; i<l0.length(); i++){
                if(l0.toLowerCase().charAt(i) == 'e'){
                    return -1;
                }
            }
            return 1;
        });
        System.out.println("Sort: words with e first");
        names.forEach(l -> System.out.println("\t" + l));

        // by static method
        Collections.sort(names, (l0,l1)-> sortByE(l0,l1));
        System.out.println("Sort: words with e first (method)");
        names.forEach(l -> System.out.println("\t" + l));

    }

    public static int sortByE (String s1, String s2){
        for (int i=0; i<s1.length(); i++){
            if(s1.toLowerCase().charAt(i) == 'e'){
                return -1;
            }
        }
        return 0;
    }
}
