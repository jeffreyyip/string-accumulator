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



    private static String[] parseDelimitors(String delimiterAndNumbers){
        // delimiter format: //del1|del2|del3\n

        int newLineIndex = delimiterAndNumbers.indexOf("\n");
        String delimitors = delimiterAndNumbers.substring(2, newLineIndex);
        String[] token = delimitors.split("\\|");
        printArray(token);
        return token;
    }

    private static String[] parseNumber(String delimitedNumber, String[] delimitors){


        String[] strings =  delimitedNumber.split(String.join("|", escapeRegex(delimitors)));


        return strings;
    }

    private static String[] escapeRegex(String[] delimiters){
        String[] escapeDelimiters = new String[delimiters.length];

        for (int i = 0; i < delimiters.length; i++) {
            escapeDelimiters[i] = escapeRegex(delimiters[i]);
        }

        return escapeDelimiters;
    }
    private static String escapeRegex(String delimiter){
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < delimiter.length(); i++){
            if (delimiter.charAt(i) == '*'){
                sb.append("\\").append(delimiter.charAt(i));
            } else {
                sb.append(delimiter.charAt(i));
            }
        }

        return sb.toString();
    }
    private static void printArray(String[] strs){
        System.out.println(String.format("length: %s", strs.length));
        for (String s: strs){
            System.out.println(s);
        }
    }

    private static String removeDelimitors(String delimiterAndNumbers){
        int newlineIndex = delimiterAndNumbers.indexOf("\n");
        return delimiterAndNumbers.substring(newlineIndex+1);
    }

    private static int sum(String[] numbers){
        printArray(numbers);

        int result = 0;

        for (String s : numbers){
            result += Integer.parseInt(s);
        }

        return result;
    }

    private static boolean startWithDelimiters(String delimiterAndNumbers){
        return delimiterAndNumbers.startsWith("//") && delimiterAndNumbers.contains("\n");
    }

    public static int add(String delimiterAndNumbers){
        String[] delimiters;
        String numberStr;

        if (delimiterAndNumbers.length() == 0)
            return 0;

        if (startWithDelimiters(delimiterAndNumbers)){
            delimiters = parseDelimitors(delimiterAndNumbers);
            numberStr = removeDelimitors(delimiterAndNumbers);
        } else {
            delimiters = DEFAULTS_DELIMITORS;
            numberStr = delimiterAndNumbers;
        }

        String[] numbers = parseNumber(numberStr, delimiters);



        return sum(numbers);
    }


    public static void main(String[] args){


        int result = StringAccumulator.add(args[0]);

        System.out.println(String.format("result: %s", result));
    }
}
