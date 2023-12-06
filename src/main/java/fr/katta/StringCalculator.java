package fr.katta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    public static final String FINAL_SEPARATOR = ";";
    public static final List<String> SEPARATORS =
            List.of(FINAL_SEPARATOR, "\n", "l");
    public static final String REGEX = String.join("|", SEPARATORS);

    public static final String START_WITH_REGEX = "//.\n";


    public Integer add(String numbers) throws ParsingException {
        if (Objects.isNull(numbers) || numbers.isBlank()) {
            return 0;
        }
        String regex = REGEX;
        String finalSeparator = FINAL_SEPARATOR;
        Pattern compile = Pattern.compile("^//(.)\n");
        Matcher matcher = compile.matcher(numbers);
        if (matcher.find()) {
            regex = numbers.charAt(2) + "";
            finalSeparator = regex;
            numbers = numbers.substring(4);

        }

        String formatedNumbers = numbers.replaceAll(regex, finalSeparator);

        if (formatedNumbers.contains(finalSeparator + finalSeparator)) {
            throw new ParsingException();
        }

        String[] split = formatedNumbers.split(regex);
        return Arrays.stream(split)
                .mapToInt(Integer::valueOf)
                .sum();


    }
}
