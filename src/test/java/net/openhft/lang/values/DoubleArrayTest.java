package net.openhft.lang.values;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DoubleArrayTest {

    @Test
    public void testSetData() {
        DoubleArray da = new DoubleArray(64);
        da.setData(new double[]{1, 2, 3, 4, 5, 6, 7, 8});
        assertEquals(8, da.length());
        for (int i = 0; i < da.length(); i++)
            assertEquals(i + 1.0, da.getDataAt(i), 0.0);
        da.addData(9);
        da.addData(10);
        double[] ds = new double[64];
        int len = da.getDataUsing(ds);
        assertEquals(10, len);
        for (int i = 0; i < len; i++)
            assertEquals(i + 1.0, ds[i], 0.0);

        for (int i = 0; i < 64; i++)
            ds[i] = i * 1.01;
        da.setData(ds);
        assertEquals(64, da.length());
        for (int i = 0; i < da.length(); i++)
            assertEquals(i * 1.01, da.getDataAt(i), 0.0);

        double[] ds2 = new double[65];
        assertEquals(64, da.getDataUsing(ds2));
        for (int i = 0; i < 64; i++) {
            assertEquals(i * 1.01, da.getDataAt(i), 0.0);
            assertEquals(i * 1.01, ds2[i], 0.0);
        }

        try {
            da.setData(ds2);
            fail();
        } catch (IllegalArgumentException expected) {
            // expected
        }
        // free the memory.
        da.bytes().release();
    }
}