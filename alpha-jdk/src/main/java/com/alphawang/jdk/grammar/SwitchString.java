package com.alphawang.jdk.grammar;

public class SwitchString {

    public static void main(String[] args) {
        // cause NPE.
        String flag = null;
        
        switch (flag) {
            case "A":
                System.out.println("got A");
                break;
            case "B":
                System.out.println("got B");
                break;
            default:
                System.out.println("got default");
        }
    }

}
