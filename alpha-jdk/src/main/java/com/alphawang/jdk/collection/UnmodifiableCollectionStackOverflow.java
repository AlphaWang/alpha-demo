package com.alphawang.jdk.collection;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UnmodifiableCollectionStackOverflow {

    /**
     *  Run with `-Xss160k`
     */
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("A", "B");
        Collection<String> unmodifiableCollection = Collections.unmodifiableCollection(list);

        for (int i = 0; i < 2048; i++) {
            unmodifiableCollection = Collections.unmodifiableCollection(unmodifiableCollection);
        }

        System.out.println(unmodifiableCollection);
        System.out.println(unmodifiableCollection.isEmpty());  //StackOverflowError
    }
}
