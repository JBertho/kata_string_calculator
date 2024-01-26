package fr.katta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class StringCalculator {
    public static final String FINAL_SEPARATOR = ";";
    public static final List<String> SEPARATORS =
            List.of(FINAL_SEPARATOR, "\n", "l");
    public static final String OR = "|";
    public static final String REGEX = String.join(OR, SEPARATORS);

    public static final String START_WITH_REGEX = "^//((.)|\\[.+\\])\n";
    public static final int MAX_VALUE = 1000;


    public Integer add(String numbers) throws ParsingException {
        if (Objects.isNull(numbers) || numbers.isBlank()) {
            return 0;
        }
        String formatedNumbers = formatNumberInput(numbers);

        if (formatedNumbers.contains(FINAL_SEPARATOR + FINAL_SEPARATOR)) {
            throw new ParsingException();
        }

        String[] numberStrings = formatedNumbers.split(FINAL_SEPARATOR);
        verifyNegativeNumber(numberStrings);

        return Arrays.stream(numberStrings)
                .mapToInt(Integer::valueOf)
                .filter(value -> value < MAX_VALUE)
                .sum();
    }

    private static String formatNumberInput(String numbers) {
        String regex = REGEX;
        Pattern compile = Pattern.compile(START_WITH_REGEX);
        Matcher matcher = compile.matcher(numbers);
        if (matcher.find()) {
            String newSeparatorList = matcher.group(1);

            if (newSeparatorList.contains("[") && newSeparatorList.contains("]")) {

                Pattern delimiterPattern = Pattern.compile("\\[(.*?)\\]");
                Matcher delimiterMatcher = delimiterPattern.matcher(newSeparatorList);

                while (delimiterMatcher.find()) {
                    String newSeparator = delimiterMatcher.group();
                    if (newSeparator.contains("[") && newSeparator.contains("]")) {
                        newSeparator = newSeparator.substring(1, newSeparator.length() - 1);
                    }
                    regex += OR + newSeparator;
                }
            } else {
                regex += OR + newSeparatorList;

            }

            int charactersToRemove = newSeparatorList.length() + 3;

            numbers = numbers.substring(charactersToRemove);
        }

        return numbers.replaceAll(regex, FINAL_SEPARATOR);
    }

    private static void verifyNegativeNumber(String[] numberStrings) {
        IntStream numberStream = Arrays.stream(numberStrings).mapToInt(Integer::valueOf);
        List<Integer> negativeNumbers = numberStream.filter(value -> value < 0).boxed().toList();
        if (!negativeNumbers.isEmpty()) {
            throw new NegativeNumberException(negativeNumbers);
        }
    }
}

class NegativeNumberException extends RuntimeException {

    public static final String NEGATIVE_NUMBER_MESSAGE = "negatives not allowed : ";

    NegativeNumberException(List<Integer> negativeNumbers) {
        super(NEGATIVE_NUMBER_MESSAGE + negativeNumbers.toString());
    }

}
