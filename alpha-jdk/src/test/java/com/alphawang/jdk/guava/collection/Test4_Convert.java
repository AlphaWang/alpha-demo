package com.alphawang.jdk.guava.collection;

import com.alphawang.jdk.guava.model.City;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Alpha on Mar/20/15.
 */
public class Test4_Convert {

    private List<City> cities;

    private Function<City, Long> cityToIdFunction;

    @Before
    public void setup() {
        cities = Lists.newArrayList(
            new City(1L, "Shanghai", 1360L),
            new City(2L, "Beijing", 1020L),
            new City(3L, "Guangzhou", 680L),
            new City(4L, "Chengdu", 450L));

        cityToIdFunction = new Function<City, Long>() {
            @Override
            public Long apply(City input) {
                return input.getId();
            }
        };
    }

    @Test
    public void listToMapAsValue() {
        Map<Long, City> idCityMap = Maps.uniqueIndex(cities, cityToIdFunction);

        System.out.println("\nlistToMapAsValue: \n" + idCityMap);
        Assert.assertTrue(idCityMap.size() == cities.size());

        /*
        List<ItemUnitInfo> results = itemRepository.findUnitInfoByIds(itemIds, locale);

        return Maps.uniqueIndex(results, new Function<ItemUnitInfo, Long>() {
            @Nullable
            @Override
            public Long apply(@Nullable ItemUnitInfo input) {
                return input.getItemId();
            }
        });
        */
    }

    @Test
    public void listToMapAsKey() {
        Map<City, Long> cityIdMap = Maps.toMap(cities, cityToIdFunction);
        // or
        cityIdMap = FluentIterable.from(cities).toMap(cityToIdFunction);

        System.out.println("\nlistToMapAsKey: \n" + cityIdMap);
        Assert.assertTrue(cityIdMap.size() == cities.size());
    }

    @Test
    public void mapToList() {
        Map<City, String> cityCommentMap = ImmutableMap.of(
            new City(1L, "Shanghai", 1360L), "big",
            new City(2L, "Beijing", 1020L), "dirty"
        );

        List<String> cityComments = FluentIterable.from(cityCommentMap.entrySet())
            .transform(new Function<Map.Entry<City, String>, String>() {
                @Override
                public String apply(Map.Entry<City, String> input) {
                    return input.getKey().getName() + " IS " + input.getValue();
                }
            }).toList();
        System.out.println("\nmapToList:\n" + cityComments);
    }

}
