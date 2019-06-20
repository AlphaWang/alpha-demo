package com.alphawang.jdk.guava.collection;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Alpha on Mar/23/15.
 */
public class Test_Practice {

    private BigDecimal getBenefitPrice_Before(Long vendorItemId, BigDecimal originalPrice,
        APIResponse<Map<String, OptimizedBenefitSummaryDTO>> benefitResponse) {
        BigDecimal convertedPrice = null;

        if (benefitResponse.isSuccess() && originalPrice != null) {
            Map<String, OptimizedBenefitSummaryDTO> contents = benefitResponse.getContents();
            if (contents != null) {
                OptimizedBenefitSummaryDTO benefitSummary = contents.get(vendorItemId.toString());
                if (benefitSummary != null) {
                    convertedPrice = originalPrice.subtract(new BigDecimal(benefitSummary.getDiscountAmount()));
                }
            }
        }

        return convertedPrice;
    }

    /**
     * 1. don't return null
     * 2. Optional.or
     * 3. String.valueOf
     * 4. Optional.transform
     */
    private BigDecimal getBenefitPrice_After(Long vendorItemId, BigDecimal originalPrice,
        APIResponse<Map<String, OptimizedBenefitSummaryDTO>> benefitResponse) {
        if (!benefitResponse.isSuccess() || originalPrice == null) {
            return BigDecimal.ZERO; //1
        }
        Map<String, OptimizedBenefitSummaryDTO> contents = Optional.fromNullable(benefitResponse.getContents()).or(
            ImmutableMap.<String, OptimizedBenefitSummaryDTO>of());  //2
        Long discount = Optional.fromNullable(contents.get(String.valueOf(vendorItemId))).transform(   //3
            new Function<OptimizedBenefitSummaryDTO, Long>() {
                @Override public Long apply(OptimizedBenefitSummaryDTO input) {
                    return input.getDiscountAmount();
                }
            }).or(0L);    //4
        return originalPrice.subtract(BigDecimal.valueOf(discount));
    }

    // MOCK classes
    @Data
    private static class APIResponse<T> {
        private boolean success;
        private T contents;
    }

    @Data
    private static class OptimizedBenefitSummaryDTO {
        Long discountAmount;
    }
}
