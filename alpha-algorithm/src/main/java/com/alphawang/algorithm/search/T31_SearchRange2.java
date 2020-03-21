package com.alphawang.algorithm.search;

import java.util.Arrays;

public class T31_SearchRange2 {

    public static int[] search(int[] nums, int val) {
        int left = searchLeft(nums, val, 0, nums.length - 1);
        if (left < 0) {
            return new int[]{-1, -1};
        }

        return new int[]{searchLeft(nums, val, 0, nums.length - 1), searchRight(nums, val, 0, nums.length - 1)};
    }

    public static int searchLeft(int[] nums, int val, int start, int end) {
        if (start > end) {
            return -1;
        }

        int mid = start + (end - start) / 2;

        if (nums[mid] == val) {
            int newLeft = searchLeft(nums, val, start, mid - 1);
            System.out.println("found left:" + newLeft);
            if (newLeft >= 0) {
                return newLeft;
            }
            return mid;
        } else if (nums[mid] < val) {
            return searchLeft(nums, val, mid + 1, end);
        } else {
            return searchLeft(nums, val, start, mid - 1);
        }
    }

    public static int searchRight(int[] nums, int val, int start, int end) {
        if (start > end) {
            return -1;
        }
        //System.out.println("right range:" + start + "-->" + end);
        int mid = start + (end - start) / 2;
        if (nums[mid] == val) {
            int newRight = searchRight(nums, val, mid + 1, end);
            if (newRight >= 0) {
                return newRight;
            }
            return mid;
        } else if (nums[mid] < val) {
            return searchRight(nums, val, mid + 1, end);
        } else {
            return searchRight(nums, val, start, mid - 1);
        }
    }


    public static void main(String[] args) {
        printSearch(new int[]{1, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 9}, 7, new int[]{1, 10});
//        printSearch(new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4});

    }

    private static void printSearch(int[] nums, int target, int[] expect) {
        int[] result = search(nums, target);

        System.out.print(String.format("%s in %s : %s. ", target, Arrays.toString(nums), Arrays.toString(result)));
        System.out.println(Arrays.equals(result, expect) ? "PASS" : "FAILED");
    }


}
