package com.alphawang.algorithm;

import java.util.Arrays;

public class Utils {

    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static void print(int[] array) {
        System.out.println(Arrays.toString(array));
    }
}
