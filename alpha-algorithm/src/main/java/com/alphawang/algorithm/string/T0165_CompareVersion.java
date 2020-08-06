package com.alphawang.algorithm.string;

public class T0165_CompareVersion {

    public static int compareVersion(String version1, String version2) {
        String[] verSpl1 = version1.split("\\.");
        String[] verSpl2 = version2.split("\\.");
        int max = verSpl1.length > verSpl2.length ? verSpl1.length : verSpl2.length;
        for (int i = 0; i < max; i++) {
            Integer ver1 = verSpl1.length <= i ? 0 : Integer.valueOf(verSpl1[i]);
            Integer ver2 = verSpl2.length <= i ? 0 : Integer.valueOf(verSpl2[i]);
            
            int result = ver1.compareTo(ver2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        test("0.1", "1.1", -1);
        test("1.0.1", "1", 1);
        test("7.5.2.4", "7.5.3", -1);
        test("1.01", "1.001", 0);
        test("1.0", "1.0.0", 0);
    }

    private static void test(String version1, String version2, int expected) {
        int result = compareVersion(version1, version2);
        System.out.println(String.format("%s vs. %s = %s, expected = %s",
                                         version1, version2, result, expected));
    }


}
