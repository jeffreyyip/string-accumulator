package com.jy;

import java.util.Arrays;

public class StringAccumulator {

    private static final String COMMAS = ",";
    private static final String RETURN = "\n";
    private static final String[] DEFAULTS_DELIMITORS = {COMMAS, RETURN};
    /*
    private String delimiterAndNumbers;
    private String[] delimitors;
    private String[] numbers;
    */



    private static String[] parseDelimitors(String str){
        return DEFAULTS_DELIMITORS;
    }

    private static String[] parseNumber(String delimitedNumber, String[] delimitors){

        System.out.println(String.format("regex : %s", String.join("|", delimitors)));

        String[] strings =  delimitedNumber.split(String.join("|", delimitors));

        printArray(strings);

        return strings;
    }

    private static void printArray(String[] strs){
        System.out.println(String.format("length: %s", strs.length));
        for (String s: strs){
            System.out.println(s);
        }
    }

    private static String removeDelimitors(String delimiterAndNumbers){
        return "";
    }

    private static int sum(String[] numbers){
        int result = 0;

        for (String s : numbers){
            result += Integer.parseInt(s);
        }

        return result;
    }

    public static int add(String numberStr){
        //String[] delimiters = parseDelimitors(delimiterAndNumbers);
        //String numberStr = delimiterAndNumbers;

        //removeDelimitors(delimiterAndNumbers);

        if (numberStr.length() == 0)
            return 0;

        String[] numbers = parseNumber(numberStr, DEFAULTS_DELIMITORS);



        return sum(numbers);
    }


    public static void main(String[] args){


        int result = StringAccumulator.add(args[0]);

        System.out.println(String.format("result: %s", result));
    }
}
