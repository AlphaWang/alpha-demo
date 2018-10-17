package com.alphawang.jdk.guava.optional;

import com.alphawang.jdk.guava.model.VitaminLocale;
import com.google.common.base.Enums;
import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alpha on Mar/24/15.
 */
public class Test3_Enums {

    @Test
    public void testEnums() {
        // BEFORE
        VitaminLocale vitaminLocale = VitaminLocale.valueOf("ko_KR");
        if (vitaminLocale != null) {

        }

        // AFTER
        Optional<VitaminLocale> locale = Enums.getIfPresent(VitaminLocale.class, "ko_KR");
        if (locale.isPresent()) {
            locale.get();
        }



        Assert.assertTrue(locale.isPresent());
        Assert.assertTrue(locale.get() == VitaminLocale.ko_KR);

        locale = Enums.getIfPresent(VitaminLocale.class, "NO-EXIST");
        Assert.assertTrue(!locale.isPresent());
    }
}
