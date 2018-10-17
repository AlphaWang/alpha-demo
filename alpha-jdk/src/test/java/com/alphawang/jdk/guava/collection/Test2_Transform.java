package com.alphawang.jdk.guava.collection;

import com.alphawang.jdk.guava.model.City;
import com.alphawang.jdk.guava.model.VitaminLocale;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Alpha on Mar/16/15.
 */
public class Test2_Transform {
    private List<City> cities;
    private Map<City, VitaminLocale> cityLocaleMap;

    private Function<City, String> cityNameFunction;
    private Function<String, Integer> stringLengthFunction;

    @Before
    public void setup() {
        cities = Lists.newArrayList(
            new City(1L,"Shanghai", 1360L),
            new City(2L, "Beijing", 1020L),
            new City(3L, "Guangzhou", 680L),
            new City(4L, "Chengdu", 450L)
        );

        cityLocaleMap = ImmutableMap.of(
            new City(1L, "Shanghai", 1360L), VitaminLocale.en_US,
            new City(2L, "Beijing", 1020L), VitaminLocale.zh_CN
        );

        cityNameFunction = new Function<City, String>() {
            @Override public String apply(City input) {
                return input.getName();
            }
        };

        stringLengthFunction = new Function<String, Integer>() {
            @Override public Integer apply(String input) {
                return input.length();
            }
        };
    }

    /**
     * Lists.transform
     */
    @Test
    public void transformList() {
        // before:
        List<String> cityNamesBefore = new ArrayList<>();
        for (City city : cities) {
            System.out.println("-- normal transform: " + city);
            cityNamesBefore.add(city.getName());
        }

        // or Iterables.transform()
        List<String> cityNames = Lists.transform(cities, new Function<City, String>() {
            @Override
            public String apply(City input) {
                System.out.println("-- guava transform: " + input);
                return input.getName();
            }
        });

        Assert.assertTrue(cityNames.size() == 4);
        System.out.println(cityNames);   // lazy
    }

    /**
     * FluentIterable.transform
     */
    @Test
    public void transformFluent() {
        List<Integer> cityNameLength = FluentIterable.from(cities)
            .transform(cityNameFunction)
            .transform(stringLengthFunction)
            .toList();

        System.out.println(cityNameLength);
        Assert.assertTrue(cityNameLength.size() == 4);
    }

    /**
     * Functions.compose
     */
    @Test
    public void functions() {
        List<Integer> cityNameLength = Lists.transform(cities, Functions.compose(stringLengthFunction, cityNameFunction));

        System.out.println(cityNameLength);
        Assert.assertTrue(cityNameLength.size() == 4);
    }








    @Test
    public void transformMapEntry() {
        Map<City, String> map = Maps.transformEntries(cityLocaleMap, new Maps.EntryTransformer<City, VitaminLocale, String>() {
            @Override
            public String transformEntry(City key, VitaminLocale value) {
                return key.getPopulation() + "|" + value.getKoreaTitle();
            }
        });

        System.out.print(map);
        Assert.assertTrue(map.size() == cityLocaleMap.size());
    }

    @Test
    public void transformMapValue() {
        Map<City, String> map = Maps.transformValues(cityLocaleMap, new Function<VitaminLocale, String>() {
            @Override
            public String apply(VitaminLocale input) {
                return input.getEnglishTitle();
            }
        });

        System.out.print(map);
        Assert.assertTrue(map.size() == cityLocaleMap.size());
    }
}
