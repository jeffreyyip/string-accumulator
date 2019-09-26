package com.jy;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringAccumulator {

    private static final String COMMAS = ",";
    private static final String RETURN = "\n";
    private static final String[] DEFAULTS_DELIMITORS = {COMMAS, RETURN};
    private static final String DELIMITERS_PREFIX = "//";
    private static final String DELIMITERS_SUFFIX = "\n";
    private static final String DELIMITERS_SEPARATOR = "|";
    private static final String REGEX_OR = "|";


    private static String[] parseDelimitors(String delimiterAndNumbers){
        // delimiter format: //del1|del2|del3\n<numbers>

        int newLineIndex = delimiterAndNumbers.indexOf(DELIMITERS_SUFFIX);
        String delimiterStr = delimiterAndNumbers.substring(DELIMITERS_PREFIX.length() , newLineIndex);

        return delimiterStr.split(escapeRegex(DELIMITERS_SEPARATOR));


    }

    private static String[] parseNumber(String delimitedNumber, String[] delimitors)  {

        return delimitedNumber.split(String.join(REGEX_OR, escapeRegex(delimitors)));


    }

    private static void validateNegativeNumbers(String[] numbers) throws Exception {
        List<String> negativeNumbers = Arrays.stream(numbers).filter(x -> x.startsWith("-")).collect(Collectors.toList());

        if (negativeNumbers.size() > 0 ){
            throw new Exception("negatives not allowed - " + negativeNumbers);
        }
    }

    private static String[] escapeRegex(String[] delimiters){

        return Arrays.stream(delimiters).map(StringAccumulator::escapeRegex).toArray(String[]::new);

    }

    private static String escapeRegex(String delimiter){


        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < delimiter.length(); i++){
            if (delimiter.charAt(i) == '*' || delimiter.charAt(i) == '|'){
                sb.append("\\").append(delimiter.charAt(i));
            } else {
                sb.append(delimiter.charAt(i));
            }
        }

        return sb.toString();


    }

    /*
    private static void printArray(String[] strs){
        System.out.println(String.format("length: %s", strs.length));
        for (String s: strs){
            System.out.println(s);
        }
    }

     */

    private static String removeDelimitors(String delimiterAndNumbers){

        int newlineIndex = delimiterAndNumbers.indexOf(DELIMITERS_SUFFIX);
        return delimiterAndNumbers.substring(newlineIndex+1);
    }

    private static int sumIgnoreOverThousand(String[] numbers){
        //System.out.println("=== sumStream ===");

        return Arrays.asList(numbers).parallelStream().mapToInt( Integer::parseInt).filter( i -> i <= 1000).sum();
    }

    /*
    private static int sumLoop(String[] numbers){
        System.out.println("=== sumLoop ===");
        int result = 0;
        int n;

        for (String s : numbers){
             n = Integer.parseInt(s);

             if (n <= 1000 ){
                 result += n;
             }
        }

        return result;
    }

     */

    private static boolean startWithDelimiters(String delimiterAndNumbers){
        return delimiterAndNumbers.startsWith(DELIMITERS_PREFIX) && delimiterAndNumbers.contains(DELIMITERS_SUFFIX);
    }

    public static int add(String delimiterAndNumbers) throws Exception {
        String[] delimiters;
        String numberStr;

        if (delimiterAndNumbers.length() == 0)
            return 0;

        if (startWithDelimiters(delimiterAndNumbers)) {
            delimiters = parseDelimitors(delimiterAndNumbers);
            numberStr = removeDelimitors(delimiterAndNumbers);
        } else {
            delimiters = DEFAULTS_DELIMITORS;
            numberStr = delimiterAndNumbers;
        }

        String[] numbers = parseNumber(numberStr, delimiters);

        validateNegativeNumbers(numbers);

        return sumIgnoreOverThousand(numbers);
    }


    public static void main(String[] args){

        try {
            int result = StringAccumulator.add(args[0]);

            System.out.println(String.format("result: %s", result));

        }catch (Exception e){
            e.printStackTrace(System.out);
        }

    }
}
