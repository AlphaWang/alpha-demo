package com.alphawang.jdk.collection;

import com.alphawang.util.Printer;

public class HashMapBitTest {
    public static void main(String[] args) {
        Printer.print(5 >>> 16);
        Printer.print(16 >>> 16);
        Printer.print(32 >>> 16);
        Printer.print(128 >>> 16);
        Printer.print(1 << 30);
    }

    /**
     * HashMap: put()会调用此方法计算hash
     * return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
     * 
     * 数组index = (length - 1) & hash
     * 即 （2的n次方 - 1） & hash
     * 
     * n = tab.length
     * tab[i = (n - 1) & hash]
     * 
     * Computes key.hashCode() and spreads (XORs) higher bits of hash
     * to lower.  Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;
}
