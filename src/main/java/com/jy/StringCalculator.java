package com.jy;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {

    private static final String COMMAS = ",";
    private static final String RETURN = "\n";
    private static final List<String> DEFAULTS_DELIMITER_LIST = Arrays.asList( COMMAS, RETURN);
    private static final String DELIMITERS_PREFIX = "//";
    private static final String DELIMITERS_SUFFIX = "\n";
    private static final String DELIMITERS_SEPARATOR = "|";
    private static final String REGEX_OR = "|";
    private static final String REGEX_ESCAPE = "\\";
    private static final char REGEX_ESCAPE_CHAR = '\\';
    private static final Set<Character> REGEX_META_SET = new HashSet<>(Arrays.asList('*', '|'));
    private static final Set<String> REGEX_META_SET_STR = new HashSet<>(Arrays.asList("*", "|"));

    private static final int MAX_INT_VALUE = 1000;
    private static final String EXP_MSG_NEG = "negatives not allowed";

    /**
     * This method is to extract all the delimiters in the user input
     * @param delimiterAndNumbers - user input - e.g. //del1|del2|del3\n<numbers>
     * @return List of delimiter
     */
    private static List<String> parseDelimiterList(String delimiterAndNumbers){

        int suffixIndex = delimiterAndNumbers.indexOf(DELIMITERS_SUFFIX);
        String delimiterStr = delimiterAndNumbers.substring(DELIMITERS_PREFIX.length() , suffixIndex);

        return Arrays.asList(delimiterStr.split(escapeRegex(DELIMITERS_SEPARATOR)));
    }


    /**
     * This method is to encode a list of delimiters with the escape character for regular expression
     * @param delimiters - list of delimiters
     * @return String array of encoded delimiters
     */
    private static String[] escapeRegex(List<String> delimiters){

        return delimiters.stream().map(StringCalculator::escapeRegex).toArray(String[]::new);

    }

    /**
     * This method is to enode the delimiter with the escape character for regular expression
     * @param delimiter - individual delimiter
     * @return encoded delimiter
     */
    private static String escapeRegex(String delimiter) {
        return delimiter.chars()
                .mapToObj(c -> (char) c)
                .flatMap( c -> (REGEX_META_SET.contains(c) ? Stream.of(REGEX_ESCAPE_CHAR, c) : Stream.of(c)))
                .collect( Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString)
                );
    }

    /**
     * This method is to remove the delimiters from the input parameter e.g. //del1|del2|del3\n<numbers>
     * @param delimiterAndNumbers - input parameter of string of optional delimiter list and numbers
     * @return numbers in delimited format <number> e.g. 1,2,3
     */
    private static String removeDelimiters(String delimiterAndNumbers){

        int newlineIndex = delimiterAndNumbers.indexOf(DELIMITERS_SUFFIX);
        return delimiterAndNumbers.substring(newlineIndex+1);
    }


    /**
     * This method is to test whether the input parameter contains optional delimiter list
     * @param delimiterAndNumbers - input parameter of string of optional delimiter list and numbers e.g. //del1|del2|del3\n<numbers>
     * @return Ture if there is optional delimiter list; False if not
     */
    private static boolean startWithDelimiters(String delimiterAndNumbers){
        return delimiterAndNumbers.startsWith(DELIMITERS_PREFIX) && delimiterAndNumbers.contains(DELIMITERS_SUFFIX);
    }


    /**
     * This method is to add the number in delimited String format; with optional delimiter list provided by user
     * and return the summation.
     * Condition 1: The number greater than 1000 will be ignored.
     * Condition 2: The number in negative value will be rejected.
     * e.g //%|#|*\n1%2#3*4
     * e.g 1,2,3,4
     * @param delimiterAndNumbers - input parameter of string of optional delimiter list and numbers e.g. //del1|del2|del3\n<numbers>
     * @return the summation of all the number fulfill given conditions mentioned above.
     * @throws Exception when there is negative number, the exception message will contains the negative numbers
     */
    public static int add(String delimiterAndNumbers) throws Exception {


        if (delimiterAndNumbers.length() == 0)
            return 0;


        List<String> delimiters = new ArrayList<>();

        ConcurrentMap<Boolean, List<Integer>> positiveNegativeMap =
                Stream.of(delimiterAndNumbers)
                        .map( s -> {
                            if (startWithDelimiters(s)){
                                delimiters.addAll( parseDelimiterList(s) );
                                return removeDelimiters(delimiterAndNumbers);
                            }else {
                                delimiters.addAll( DEFAULTS_DELIMITER_LIST);
                                return delimiterAndNumbers;
                            } })
                .flatMap( delimNums -> Stream.of(delimNums.split(String.join(REGEX_OR, escapeRegex(delimiters)))))
                .map(Integer::valueOf)
                .collect(Collectors.groupingByConcurrent( i -> i >= 0));

        if (positiveNegativeMap.get(Boolean.FALSE) != null){
            throw new Exception(EXP_MSG_NEG + " " + positiveNegativeMap.get(Boolean.FALSE));
        }

        return positiveNegativeMap.get(Boolean.TRUE).parallelStream().mapToInt( Integer::intValue ).filter( i -> i < MAX_INT_VALUE).sum();


    }



}
