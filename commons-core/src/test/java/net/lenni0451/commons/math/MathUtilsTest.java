package net.lenni0451.commons.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void floorFloatToInt() {
        assertEquals(1, MathUtils.floorInt(1.5F));
        assertEquals(1, MathUtils.floorInt(1F));
    }

    @Test
    void floorDoubleToInt() {
        assertEquals(1, MathUtils.floorInt(1.5D));
        assertEquals(1, MathUtils.floorInt(1D));
    }

    @Test
    void floorFloatToLong() {
        assertEquals(1L, MathUtils.floorLong(1.5F));
        assertEquals(1L, MathUtils.floorLong(1F));
    }

    @Test
    void floorDoubleToLong() {
        assertEquals(1L, MathUtils.floorLong(1.5D));
        assertEquals(1L, MathUtils.floorLong(1D));
    }

    @Test
    void ceilFloatToInt() {
        assertEquals(2, MathUtils.ceilInt(1.5F));
        assertEquals(1, MathUtils.ceilInt(1F));
    }

    @Test
    void ceilDoubleToInt() {
        assertEquals(2, MathUtils.ceilInt(1.5D));
        assertEquals(1, MathUtils.ceilInt(1D));
    }

    @Test
    void ceilFloatToLong() {
        assertEquals(2L, MathUtils.ceilLong(1.5F));
        assertEquals(1L, MathUtils.ceilLong(1F));
    }

    @Test
    void ceilDoubleToLong() {
        assertEquals(2L, MathUtils.ceilLong(1.5D));
        assertEquals(1L, MathUtils.ceilLong(1D));
    }

    @Test
    void clampInt() {
        assertEquals(10, MathUtils.clamp(100, 1, 10));
        assertEquals(1, MathUtils.clamp(-100, 1, 10));
        assertEquals(5, MathUtils.clamp(5, 1, 10));
    }

    @Test
    void clampLong() {
        assertEquals(10L, MathUtils.clamp(100L, 1L, 10L));
        assertEquals(1L, MathUtils.clamp(-100L, 1L, 10L));
        assertEquals(5L, MathUtils.clamp(5L, 1L, 10L));
    }

    @Test
    void clampFloat() {
        assertEquals(10F, MathUtils.clamp(100F, 1F, 10F));
        assertEquals(1F, MathUtils.clamp(-100F, 1F, 10F));
        assertEquals(5F, MathUtils.clamp(5F, 1F, 10F));
    }

    @Test
    void clampDouble() {
        assertEquals(10D, MathUtils.clamp(100D, 1D, 10D));
        assertEquals(1D, MathUtils.clamp(-100D, 1D, 10D));
        assertEquals(5D, MathUtils.clamp(5D, 1D, 10D));
    }

    @Test
    void isFloatDecimal() {
        assertTrue(MathUtils.isDecimal(1.5F));
        assertTrue(MathUtils.isDecimal(1.0001F));
        assertFalse(MathUtils.isDecimal(1F));
    }

    @Test
    void isDoubleDecimal() {
        assertTrue(MathUtils.isDecimal(1.5D));
        assertTrue(MathUtils.isDecimal(1.0001D));
        assertFalse(MathUtils.isDecimal(1D));
    }

    @Test
    void mapInt() {
        assertEquals(0, MathUtils.map(0, 0, 1, 0, 100));
        assertEquals(50, MathUtils.map(5, 0, 10, 0, 100));
        assertEquals(100, MathUtils.map(5, 0, 10, 50, 150));
    }

    @Test
    void mapLong() {
        assertEquals(0L, MathUtils.map(0L, 0L, 1L, 0L, 100L));
        assertEquals(50L, MathUtils.map(5L, 0L, 10L, 0L, 100L));
        assertEquals(100L, MathUtils.map(5L, 0L, 10L, 50L, 150L));
    }

    @Test
    void mapFloat() {
        assertEquals(0F, MathUtils.map(0F, 0F, 1F, 0F, 100F));
        assertEquals(50F, MathUtils.map(5F, 0F, 10F, 0F, 100F));
        assertEquals(100F, MathUtils.map(5F, 0F, 10F, 50F, 150F));
        assertEquals(0.5F, MathUtils.map(5, 0F, 10F, 0F, 1F));
    }

    @Test
    void mapDouble() {
        assertEquals(0D, MathUtils.map(0D, 0D, 1D, 0D, 100D));
        assertEquals(50D, MathUtils.map(5D, 0D, 10D, 0D, 100D));
        assertEquals(100D, MathUtils.map(5D, 0D, 10D, 50D, 150D));
        assertEquals(0.5D, MathUtils.map(5, 0D, 10D, 0D, 1D));
    }

    @Test
    void formatBytes() {
        assertEquals("0 B", MathUtils.formatBytes(0));
        assertEquals("1,0 KiB", MathUtils.formatBytes(1024));
        assertEquals("1,0 GiB", MathUtils.formatBytes(1024 * 1024 * 1024));
        assertEquals("8,0 EiB", MathUtils.formatBytes(Long.MAX_VALUE));
        assertEquals("-8,0 EiB", MathUtils.formatBytes(Long.MIN_VALUE + 1));
    }

    @Test
    void roundFloat() {
        float[] in = {5.0043F, 5.0053F, 5.001F, 5.006F, 0.000001F, 10F, -2.333F, 0F};
        float[] out = {5.00F, 5.01F, 5.00F, 5.01F, 0F, 10.00F, -2.33F, 0F};
        for (int i = 0; i < in.length; i++) assertEquals(out[i], MathUtils.round(in[i], 2));
        assertArrayEquals(out, MathUtils.round(in, 2));
    }

    @Test
    void roundDouble() {
        double[] in = {5.0043D, 5.0053D, 5.001D, 5.006D, 0.000001D, 10D, -2.333D, 0D};
        double[] out = {5.00D, 5.01D, 5.00D, 5.01D, 0D, 10.00D, -2.33D, 0D};
        for (int i = 0; i < in.length; i++) assertEquals(out[i], MathUtils.round(in[i], 2));
        assertArrayEquals(out, MathUtils.round(in, 2));
    }

}
