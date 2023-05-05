package com.xm.crypto.dto;

import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceRangeDetailsTest {

    @Test
    void testNormalizedRange() {
        assertEquals(9.0f, new PriceRangeDetails("BTC",  TEN, ONE, ONE, TEN).normalizedRange());
    }

    @Test
    void whenMaxIsEqualMin_then_normalizedRangeIsZero() {
        assertEquals(0.0f, new PriceRangeDetails("BTC",  ONE, ONE, ONE, ONE).normalizedRange());
    }

}