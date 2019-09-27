package com.jy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testSplit() throws Exception{


        int cnt = 10000000;
        String[] numbers = new String[cnt];

        Arrays.fill(numbers, "1");

        long startTime1 = System.currentTimeMillis();

        int result1 = StringAccumulator.add(String.join(",", numbers));

        System.out.println(String.format("time taken: %s", System.currentTimeMillis() - startTime1));

        assertEquals(cnt, result1);

        long startTime2 = System.currentTimeMillis();

        int result2 = StringAccumulator.add(String.join(",", numbers));

        System.out.println(String.format("time taken: %s", System.currentTimeMillis() - startTime2));



        assertEquals(cnt, result2);

    }
}
