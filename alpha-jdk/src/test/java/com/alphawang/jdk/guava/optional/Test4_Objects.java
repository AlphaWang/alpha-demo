package com.alphawang.jdk.guava.optional;

import com.alphawang.jdk.guava.model.City;
import com.alphawang.jdk.guava.service.CityService;
import com.google.common.base.MoreObjects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Alpha on Mar/23/15.
 */
public class Test4_Objects {

    private CityService cityService;

    private City defaultCity = new City(0L, "", 0L);

    @Before
    public void setUp() {
        cityService = new CityService();
    }

    @Test
    public void testFirstNonNull() {
        City city = MoreObjects.firstNonNull(cityService.getNullableCity(1L), defaultCity);   //Objects.firstNonNull is deprecated
        Assert.assertNotNull(city);
    }

}
