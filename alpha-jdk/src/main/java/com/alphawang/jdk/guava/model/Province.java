package com.alphawang.jdk.guava.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Created by Alpha on Mar/13/15.
 */
@Data
public class Province {

    private String name;
    private List<City> cities = Lists.newArrayListWithCapacity(20);

    public  List<City> getCities() {
        return ImmutableList.copyOf(cities); //Collections.unmodifiableList(cities);
    }

    public void addCity(City city) {
        cities.add(city);
    }


}
