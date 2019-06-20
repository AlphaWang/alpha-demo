package com.alphawang.algorithm.search;

import java.util.Arrays;

/**
 * Created by Alpha on 12/11/17.
 *
 * 数字全排列算法。深度优先策略。
 */
public class DFS {

    private static int totalCount = 3;
    private static int[] book = new int[10];
    private static int[] a = new int[10];

    private static void dfs(int step) {

        System.out.println("step " + step);

        if (step == totalCount + 1) {
            System.out.print("============ Result: ");
            for (int i = 1; i <= totalCount; i++) {
                System.out.print(a[i]);
            }
            System.out.println();

            return;
        }

        for (int num = 1; num <= totalCount; num++) {
            if (book[num] == 0) {

                System.out.println("++ step = " + step + ". num = " + num);

                a[step] = num;
                book[num] = 1;

                System.out.println("结果 " + Arrays.toString(a));
                System.out.println("标记 " + Arrays.toString(book));
                dfs(step + 1);

                book[num] = 0;
                System.out.println("-- step = " + step + ". num = " + num);
                System.out.println("取标 " + Arrays.toString(book));
            }
        }
    }

    public static void main(String[] args) {
        dfs(1);

/*  output:
 step 1
++ step = 1. num = 1
结果 [0, 1, 0, 0, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 0, 0, 0, 0, 0, 0, 0, 0]
step 2
++ step = 2. num = 2
结果 [0, 1, 2, 0, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 0, 0, 0, 0, 0, 0, 0]
step 3
++ step = 3. num = 3
结果 [0, 1, 2, 3, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 1, 0, 0, 0, 0, 0, 0]
step 4
============ Result: 123
-- step = 3. num = 3
取标 [0, 1, 1, 0, 0, 0, 0, 0, 0, 0]
-- step = 2. num = 2
取标 [0, 1, 0, 0, 0, 0, 0, 0, 0, 0]
++ step = 2. num = 3
结果 [0, 1, 3, 3, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 0, 1, 0, 0, 0, 0, 0, 0]
step 3
++ step = 3. num = 2
结果 [0, 1, 3, 2, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 1, 0, 0, 0, 0, 0, 0]
step 4
============ Result: 132
-- step = 3. num = 2
取标 [0, 1, 0, 1, 0, 0, 0, 0, 0, 0]
-- step = 2. num = 3
取标 [0, 1, 0, 0, 0, 0, 0, 0, 0, 0]
-- step = 1. num = 1
取标 [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
++ step = 1. num = 2
结果 [0, 2, 3, 2, 0, 0, 0, 0, 0, 0]
标记 [0, 0, 1, 0, 0, 0, 0, 0, 0, 0]
step 2
++ step = 2. num = 1
结果 [0, 2, 1, 2, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 0, 0, 0, 0, 0, 0, 0]
step 3
++ step = 3. num = 3
结果 [0, 2, 1, 3, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 1, 0, 0, 0, 0, 0, 0]
step 4
============ Result: 213
-- step = 3. num = 3
取标 [0, 1, 1, 0, 0, 0, 0, 0, 0, 0]
-- step = 2. num = 1
取标 [0, 0, 1, 0, 0, 0, 0, 0, 0, 0]
++ step = 2. num = 3
结果 [0, 2, 3, 3, 0, 0, 0, 0, 0, 0]
标记 [0, 0, 1, 1, 0, 0, 0, 0, 0, 0]
step 3
++ step = 3. num = 1
结果 [0, 2, 3, 1, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 1, 0, 0, 0, 0, 0, 0]
step 4
============ Result: 231
-- step = 3. num = 1
取标 [0, 0, 1, 1, 0, 0, 0, 0, 0, 0]
-- step = 2. num = 3
取标 [0, 0, 1, 0, 0, 0, 0, 0, 0, 0]
-- step = 1. num = 2
取标 [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
++ step = 1. num = 3
结果 [0, 3, 3, 1, 0, 0, 0, 0, 0, 0]
标记 [0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
step 2
++ step = 2. num = 1
结果 [0, 3, 1, 1, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 0, 1, 0, 0, 0, 0, 0, 0]
step 3
++ step = 3. num = 2
结果 [0, 3, 1, 2, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 1, 0, 0, 0, 0, 0, 0]
step 4
============ Result: 312
-- step = 3. num = 2
取标 [0, 1, 0, 1, 0, 0, 0, 0, 0, 0]
-- step = 2. num = 1
取标 [0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
++ step = 2. num = 2
结果 [0, 3, 2, 2, 0, 0, 0, 0, 0, 0]
标记 [0, 0, 1, 1, 0, 0, 0, 0, 0, 0]
step 3
++ step = 3. num = 1
结果 [0, 3, 2, 1, 0, 0, 0, 0, 0, 0]
标记 [0, 1, 1, 1, 0, 0, 0, 0, 0, 0]
step 4
============ Result: 321
-- step = 3. num = 1
取标 [0, 0, 1, 1, 0, 0, 0, 0, 0, 0]
-- step = 2. num = 2
取标 [0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
-- step = 1. num = 3
取标 [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
 */
    }
}
