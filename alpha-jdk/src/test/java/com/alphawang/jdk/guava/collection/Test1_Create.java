package com.alphawang.jdk.guava.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alpha on Mar/13/15.
 */
public class Test1_Create {

    @Test
    public void createList() {
        // Before
        List<String> list1 = new ArrayList<String>(2);
        list1.add("A");
        list1.add("B");

        // After
        List<String> list2 = Lists.newArrayList("A", "B");

        // or
        // Note for Java 7 and later: this method is now unnecessary and should be treated as deprecated.
//        List<String> list2 = Lists.newArrayListWithCapacity(2);
//        list2.add("A");
//        list2.add("B");

        /*
        Lists.newArrayListWithCapacity() : Creates an ArrayList instance backed by an array with the specified initial size; simply delegates to ArrayList.ArrayList(int).

        //This method will soon be deprecated
        Lists.newArrayListWithExpectedSize(): Creates an ArrayList instance to hold estimatedSize elements, plus an unspecified amount of padding; you almost certainly mean to call new ArrayListWithCapacity(int)
        */

        Assert.assertTrue(Arrays.equals(list1.toArray(), list2.toArray()));
    }

    @Test
    public void createMap() {
        // Before
        Map<String, String> map1 = new HashMap<String, String>();

        // After
        Map<String, String> map2 = Maps.newHashMapWithExpectedSize(2);  // Maps.newHashMap();

        Assert.assertTrue(map2.size() == 0);
    }






    @Test
    public void createImmutableList() {
        // Before
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        List<String> list1 = Collections.unmodifiableList(list);

        // After
        List<String> list2 = ImmutableList.of("A", "B");

        Assert.assertTrue(Arrays.equals(list1.toArray(), list2.toArray()));
    }

    @Test
    public void createImmutableMap() {
        // Before
        Map<String, String> map = new HashMap<String, String>();
        map.put("A", "1");
        map.put("B", "2");
        Map<String, String> map1 = Collections.unmodifiableMap(map);

        // After
        Map<String, String> map2 = ImmutableMap.of("A", "1", "B", "2");

        Assert.assertTrue(map1.size() == map2.size());
    }


}
