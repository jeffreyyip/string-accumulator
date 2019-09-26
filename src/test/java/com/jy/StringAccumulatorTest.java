package com.jy;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class StringAccumulatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add_zero_param() {
        int result = StringAccumulator.add("");
        assertEquals(0, result);
    }

    @Test
    public void add_one_param() {
        int result = StringAccumulator.add("1");
        assertEquals(1, result);
    }

    @Test
    public void add_two_params_commas() {
        int result = StringAccumulator.add("1,2");
        assertEquals(3, result);
    }

    @Test
    public void add_three_params_commas() {
        int result = StringAccumulator.add("1,2,3");
        assertEquals(6, result);
    }

    @Test
    public void add_unknown_params_commas() {
        int start = 1;
        int end = 65000;
        IntStream intStream = IntStream.range(start, end+1);
        String numbersStr = intStream.mapToObj(String::valueOf).collect(Collectors.joining(","));
        long startTime = System.currentTimeMillis();
        int result = StringAccumulator.add(numbersStr);
        System.out.println(System.currentTimeMillis() - startTime);
        assertEquals((end/2)*(end+1), result);
    }

    @Test
    public void add_two_params_newline() {
        int result = StringAccumulator.add("1\n2");
        assertEquals(3, result);
    }


    @Test
    public void add_three_params_newline() {
        int result = StringAccumulator.add("1\n2\n3");
        assertEquals(6, result);
    }

    @Test
    public void add_three_params_commas_newline() {
        int result = StringAccumulator.add("1\n2,3");
        assertEquals(6, result);
    }

    @Test
    public void add_different_delimiters_semi() {
        int result = StringAccumulator.add("//;\n1;2");
        assertEquals(3, result);
    }

    @Test
    public void add_different_delimiters_multipleLength() {
        int result = StringAccumulator.add("//***\n1***2");
        assertEquals(3, result);
    }

    @Test
    public void add_different_delimiters_semi_star() {
        int result = StringAccumulator.add("//;|%\n1;2%3");
        assertEquals(6, result);
    }

    @Test
    public void add_different_delimiters_semi_perc_star() {
        int result = StringAccumulator.add("//;|%|***\n1;2%3***4");
        assertEquals(10, result);
    }

    @Test
    public void test_split(){
        String str = "1**2,3";
        String[] token = str.split("\\*\\*|,");
        for (String t : token){
            System.out.println(t);
        }
    }
}