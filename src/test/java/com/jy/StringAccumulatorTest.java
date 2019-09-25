package com.jy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        System.out.println(String.format("result: %s",result));
    }

    @Test
    public void add_one_param() {
        int result = StringAccumulator.add("1");
        System.out.println(String.format("result: %s",result));
    }

    @Test
    public void add_two_params_commas() {
        int result = StringAccumulator.add("1,2");
        System.out.println(String.format("result: %s", result));
    }


    @Test
    public void add_two_params_newline() {
        int result = StringAccumulator.add("1\n2");
        System.out.println(String.format("result: %s", result));
    }

    @Test
    public void add_three_params_commas() {
        int result = StringAccumulator.add("1,2,3");
        System.out.println(String.format("result: %s", result));
    }

    @Test
    public void add_three_params_newline() {
        int result = StringAccumulator.add("1\n2\n3");
        System.out.println(String.format("result: %s", result));
    }

    @Test
    public void add_three_params_commas_newline() {
        int result = StringAccumulator.add("1\n2,3");
        System.out.println(String.format("result: %s", result));
    }

}