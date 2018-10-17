package com.alphawang.jdk.guava.optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Alpha on Mar/23/15.
 */
public class Test1_FailFast {

    private List<String> list;

    @Before
    public void setUp() {
        list = Lists.newArrayList();
        list.add("AA");
        list.add(null);
        list.add("CC");
    }

    /**
        If you really need null values, and you're having problems with a null-hostile collection implementations,
        use a different implementation.
        For example, use Collections.unmodifiableList(Lists.newArrayList()) instead of ImmutableList.
     */
    @Test(expected = NullPointerException.class)
    public void testImmutableList() {
        List<String> copy = ImmutableList.copyOf(list);
        System.out.println(copy);
    }


    @Test(expected = NullPointerException.class)
    public void testLists() {
        list = Lists.newArrayList("1", null, "3");
        Assert.assertTrue(list.size() == 3);

        Iterator source = null;
        list = Lists.newArrayList(source);
    }

}
