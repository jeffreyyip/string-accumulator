package com.jy;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class StringAddCalculatorTest {

    @Test
    public void add() {
    }

    @Test
    public void add_zero_number() throws Exception {
        int result = StringAddCalculator.add("");
        assertEquals(0, result);
    }

    @Test
    public void add_one_number()  throws Exception {
        int result = StringAddCalculator.add("1");
        assertEquals(1, result);
    }

    @Test
    public void add_two_numbers()  throws Exception {
        int result = StringAddCalculator.add("1,2");
        assertEquals(3, result);
    }

    @Test
    public void add_three_numbers()  throws Exception {
        int result = StringAddCalculator.add("1,2,3");
        assertEquals(6, result);
    }


    @Test
    public void add_unknown_numbers()  throws Exception {
        int cnt = 1000;
        String[] numbers = new String[cnt];

        Arrays.fill(numbers, "1");


        int result = StringAddCalculator.add(String.join(",", numbers));


        assertEquals(cnt, result);
    }

    @Test
    public void add_two_numbers_newline()  throws Exception {
        int result = StringAddCalculator.add("1\n2");
        assertEquals(3, result);
    }


    @Test
    public void add_three_numbers_newline()  throws Exception {
        int result = StringAddCalculator.add("1\n2\n3");
        assertEquals(6, result);
    }

    @Test
    public void add_three_numbers_commas_newline()  throws Exception {
        int result = StringAddCalculator.add("1\n2,3");
        assertEquals(6, result);
    }

    @Test
    public void add_with_delimiter_semicolon()  throws Exception {
        int result = StringAddCalculator.add("//;\n1;2");
        assertEquals(3, result);
    }

    @Test(expected = Exception.class)
    public void add_negatives_then_exception_message()  throws Exception {
        String negative1 = "-2";
        String negative2 = "-4";

        try{
            StringAddCalculator.add("//;\n1;" + negative1 + ";3;" + negative2);

        }catch(Exception e){


            assertTrue(e.getMessage().startsWith("negatives not allowed"));
            assertTrue(e.getMessage().contains(negative1));
            assertTrue(e.getMessage().contains(negative2));

            throw e;
        }
    }

    @Test
    public void add_ignore_greater_thousand() throws Exception {

        int result = StringAddCalculator.add("2,1001,3,2001");
        assertEquals(5, result);

    }

    @Test
    public void add_with_delimiter_multipleLength()  throws Exception {
        int result = StringAddCalculator.add("//***\n1***2***3");
        assertEquals(6, result);
    }

    @Test
    public void add_multiple_delimiters()  throws Exception {
        int result = StringAddCalculator.add("//*|%\n1*2%3");
        assertEquals(6, result);
    }

    @Test
    public void add_multiple_delimiters_multiple_chars()  throws Exception {
        int result = StringAddCalculator.add("//***|%|*\n1*2%3***4");
        assertEquals(10, result);
    }

}