package com.alphawang.jdk.guava.collection;

import com.alphawang.jdk.guava.model.City;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Alpha on Mar/16/15.
 */
public class Test3_Filter {

    private List<City> cities;
    private Predicate<City> populationPredicate;
    private Predicate<City> namePredicate;

    @Before
    public void setup() {
        cities = Lists.newArrayList(
            new City(1L, "Shanghai", 1360L),
            new City(2L, "Beijing", 1020L),
            new City(3L, "Guangzhou", 680L),
            new City(4L, "Chengdu", 450L)
        );

        populationPredicate = new Predicate<City>() {
            @Override
            public boolean apply(City input) {
                return input.getPopulation() > 600L;
            }
        };

        namePredicate = new Predicate<City>() {
            @Override
            public boolean apply(City input) {
                return input.getName().startsWith("S");
            }
        };
    }

    /**
     *  Iterables.filter()
     */
    @Test
    public void iterablesFilter() {
        Iterable<City> largeCities = Iterables.filter(cities, new Predicate<City>() {
            @Override public boolean apply(City input) {
                return input.getPopulation() > 1000L;
            }
        });

        System.out.println(largeCities);
        Assert.assertTrue(Iterables.size(largeCities) == 2);
    }

    /**
     * FluentIterable.filter()
     */
    @Test
    public void fluentIterableFilter() {

        List<City> result = FluentIterable.from(cities)
            .filter(populationPredicate)
            .filter(namePredicate)
            .toList();

        Assert.assertTrue(result != null);
    }

    /**
     * Predicates.and()
     */
    @Test
    public void predicates() {
        Iterable<City> result = Iterables.filter(cities, Predicates.and(populationPredicate, namePredicate));

        Assert.assertTrue(!Iterables.isEmpty(result));
    }




    @Test
    public void filterMap() {
        Map<String, City> nameCityMap = Maps.uniqueIndex(cities, new Function<City, String>() {
            @Override
            public String apply(City input) {
                return input.getName();
            }
        });
        System.out.println(nameCityMap);


        Map<String, City> filteredMap = Maps.filterEntries(nameCityMap, new Predicate<Map.Entry<String, City>>() {
            @Override
            public boolean apply(Map.Entry<String, City> input) {
                return input.getKey().startsWith("S")
                    && input.getValue().getPopulation() > 1000L;
            }
        });

        Assert.assertTrue(filteredMap.size() == 1);
    }
}
