package com.alphawang.algorithm.string.design;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

class Result {

    /*
     * Complete the 'compare' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING version1
     *  2. STRING version2
     */

    public static int compare(String version1, String version2) {
        return UserAgent.VERSION_COMPARATOR.compare(version1, version2);
    }

}


public class UserAgent {
    public static final VersionComparator VERSION_COMPARATOR = new VersionComparator();
    private String version;
    private String os;
    private String model;
    //...
    // 8.0.2
    // 8.0.2.4

    public UserAgent(String version, String os, String model){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(version));
        this.version = version;
        this.os = os;
        this.model = model;
    }

    public boolean isVersionLessThan(String version){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(version));
        return VERSION_COMPARATOR.compare(this.version, version) < 0;
    }

    public boolean isVersionLessThanOrEqual(String version){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(version));
        return VERSION_COMPARATOR.compare(this.version, version) <= 0;
    }

    public boolean isVersionGreaterThan(String version){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(version));
        return !isVersionLessThanOrEqual(version);
    }

    public boolean isVersionGreaterThanOrEqual(String version){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(version));
        return !isVersionLessThan(version);
    }

    static class VersionComparator implements Comparator<String> {
        @Override
        public int compare(String v1, String v2) {
            String[] version1 = v1.split("\\.");
            String[] version2 = v2.split("\\.");
            int count = Math.max(version1.length, version2.length);
            for (int i = 0; i < count; i++) {
                int number1 = getNumberOfVersion(version1, i);
                int number2 = getNumberOfVersion(version2, i);
                if (number1 != number2) {
                    return number1 - number2;
                }
            }
            return 0;
        }
    }

    private static int getNumberOfVersion(String[] version, int index) {
        if (index < version.length) {
            return Integer.valueOf(version[index]);
        }
        return 0;
    }

    public static void main(String[] args) {

        UserAgent agent = new UserAgent("0.1",null,null);
        // output:false
        System.out.println(agent.isVersionGreaterThan("1.1"));

        UserAgent agent2 = new UserAgent("1.0.1",null,null);
        // output:true
        System.out.println(agent2.isVersionGreaterThan("1"));

        UserAgent agent3 = new UserAgent("7.5.2.4",null,null);
        // output:false
        System.out.println(agent3.isVersionGreaterThanOrEqual("7.5.3"));

        UserAgent agent4 = new UserAgent("1.01",null,null);
        // output:false
        System.out.println(agent4.isVersionGreaterThan("1.001"));


        UserAgent agent5 = new UserAgent("1.0",null,null);
        // output:false
        System.out.println(agent5.isVersionGreaterThan("1.0.0"));

    }



//    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        String version1 = bufferedReader.readLine();
//
//        String version2 = bufferedReader.readLine();
//
//        int result = Result.compare(version1, version2);
//
//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedReader.close();
//        bufferedWriter.close();
//    }
}
