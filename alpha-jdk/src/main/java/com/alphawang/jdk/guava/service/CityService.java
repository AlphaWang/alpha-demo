package com.alphawang.jdk.guava.service;

import com.alphawang.jdk.guava.model.City;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alpha on Mar/23/15.
 */
public class CityService {

    private CityRepository cityRepository = new CityRepository();

    public City getNullableCity(Long id) {
        return cityRepository.getCity(id);
    }

    public Optional<City> getOptionalCity(Long id) {
        // return Optional instead of null
        City city = this.getNullableCity(id);
        return Optional.fromNullable(city);
    }

    public List<City> getNonNullCities() {
        // return a default value if it's null
        return Optional.fromNullable(cityRepository.getCities()).or(Collections.EMPTY_LIST); // Lists.<City>newArrayList()
    }

    public City save(City city) {
        Preconditions.checkArgument(city != null, "IllegalArgumentException: city is null");
        // or
        city = Preconditions.checkNotNull(city, "NullPointerException: city is null");

        return cityRepository.save(city);
    }
}
