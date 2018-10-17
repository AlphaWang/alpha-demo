package com.alphawang.jdk.guava.optional;

import com.alphawang.jdk.guava.service.CityService;
import org.junit.Test;

/**
 * Created by Alpha on Mar/23/15.
 */
public class Test5_Preconditions {

    private CityService cityService = new CityService();

    @Test(expected = IllegalArgumentException.class)
    public void testCheckArgument() {
        cityService.save(null);
    }
}
