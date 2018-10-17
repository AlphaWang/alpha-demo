package com.alphawang.jdk.guava.optional;

import com.alphawang.jdk.guava.model.City;
import com.alphawang.jdk.guava.service.CityService;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Alpha on Mar/23/15.
 */
public class Test2_Optional {

    private CityService cityService;

    private City defaultCity = new City(0L, "", 0L);

    @Before
    public void setUp() {
        cityService = new CityService();
    }

    @Test
    public void testOptionalApi() {
        Optional<City> optional = cityService.getOptionalCity(1L);
        if (optional.isPresent()) {
            System.out.println("API return: " + optional.get());
            // do business logic
        } else {
            System.err.println("API return null");
        }
    }

    @Test
    public void testNonNullApi() {
        List<City> cities = cityService.getNonNullCities();
        Assert.assertNotNull(cities);
        Assert.assertTrue(cities.isEmpty());
    }

    @Test
    public void testDefaultValue() {
        City nullableCity = cityService.getNullableCity(200L);
        Assert.assertNull(nullableCity);

        City city = Optional.fromNullable(nullableCity).or(defaultCity);
        Assert.assertNotNull(city);

        System.out.println("NonNull Object: " + city);
        // do business logic
    }

    @Test
    public void testDefaultValue2() {
        Long population = Optional.fromNullable(cityService.getNullableCity(1L)).transform(new Function<City, Long>() {
            @Override
            public Long apply(City input) {
                return input.getPopulation();
            }
        }).or(Long.valueOf(0));

        Assert.assertTrue(population == 0L);
    }


    /*
    Optional in Coupang Code:
    com.coupang.product.interfaces.web.business.vendoritem.goods.AuthHelper#contains
    Optional.fromNullable(user.getRoleIds()).or(ImmutableSet.<String>of()).contains(role);


    com.coupang.product.adapter.coupon.external.proxy.ExternalApiClientProxyImpl#getClient
    public ExternalApiClient getClient(long coupangSrl) {
		return Optional.fromNullable(clients.get(coupangSrl)).or(new UnregisteredExternalApiClient());
	}
	// and NULL-OBJECT pattern

	VendorItemFindService#findMyVendors(com.coupang.backofficeweb.authentication.User, java.lang.Boolean)
	return FluentIterable.from(vendorIds).transform(new Function<String, VendorDetailDto>() {
            @Override
            public VendorDetailDto apply(String vendorId) {
                return Optional.fromNullable(vendorFinder.findVendorById(vendorId)).or(createNullVendorDetailDto(vendorId));
            }
    }).toList();



    */
}
