package com.alphawang.algorithm.recursion;

import com.google.common.collect.Maps;
import java.util.Map;

public class StairSteps {

    /**
     * 有 N 个台阶，每次可以跨1个或2个台阶，走完N个台阶有多少种走法？
     * 
     * 递推公式：
     * f(n) = f(n-1) + f(n-2)
     * 
     * > 第一步走 1 个，剩余n-1个台阶的走法：f(n-1)
     * > 第一步走 2 个，剩余n-2个台阶的走法：f(n-2)
     * 
     * 终止条件：
     * f(1) = 1, f(2) = 2
     */
    public static void main(String[] args) {
        System.out.println(calc(4));
        System.out.println(calcWithCache(4));
        System.out.println(calcWithLoop(4));
        
        System.out.println(calc(7));
        System.out.println(calcWithCache(7));
        System.out.println(calcWithLoop(7));
    }
    
    private static int calc(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        
        return calc(n-1) + calc(n-2);
    }

    /**
     *  递归要警惕重复计算
     */
    private static int calcWithCache(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        
        if (results.containsKey(n)) {
            return results.get(n);
        }

        int result = calc(n-1) + calc(n-2);
        results.put(n, result);
        
        return result;
    }

    /**
     * Recursion can be transform to Loop
     * TODO ？
     */
    private static int calcWithLoop(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        
        int result = 0;
        
        int pre = 2;
        int prepre = 1;
        for (int i = 3; i <= n; i++) {
            result = pre + prepre;
            prepre = pre;
            pre = result;
        }
        
        return result;
    }
    
    private static Map<Integer, Integer> results = Maps.newHashMap();

}
