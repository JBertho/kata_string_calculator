package fr.katta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringCalculatorTest {

    StringCalculator stringCalculator;

    @BeforeEach
    void setup() {
        stringCalculator = new StringCalculator();
    }

    @Test
    void should_return_0_when_null() throws ParsingException {
        Integer sut = stringCalculator.add(null);
        Assertions.assertEquals(0, sut);
    }

    @Test
    void should_return_0_when_empty_string() throws ParsingException {
        Integer sut = stringCalculator.add("");
        Assertions.assertEquals(0, sut);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "9", "253"})
        // six numbers
    void should_return_number_when_string_contains_only_one_number(String number) throws ParsingException {
        Integer sut = stringCalculator.add(number);
        Assertions.assertEquals(Integer.valueOf(number), sut);
    }

    @ParameterizedTest(name = "{index} => stringNumber={0}, expectedResult={1}")
    @CsvSource({
            "1;2,3",
            "48;5,53",
            "22;158,180",
    })
    void should_return_sum_when_string_contains_two_numbers(String stringNumber, Integer expectedResult) throws ParsingException{
        Integer sut = stringCalculator.add(stringNumber);
        Assertions.assertEquals(expectedResult, sut);
    }

    @ParameterizedTest(name = "{index} => stringNumber={0}, expectedResult={1}")
    @CsvSource({
            "1;2;3;4,10",
            "48;5;22,75",
            "1;1;1;1;1;1;1;1;1;1;1;1;1;1,14",
    })
    void should_return_sum_when_string_contains_any_amount_of_number(String stringNumber, Integer expectedResult) throws ParsingException {
        Integer sut = stringCalculator.add(stringNumber);
        Assertions.assertEquals(expectedResult, sut);
    }

    @Test
    void should_return_sum_when_string_contains_diferent_separator() throws ParsingException {
        Integer sut = stringCalculator.add("1;2\n3");
        Assertions.assertEquals(6, sut);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1;2;\n3","1;2;;3","1;2\n\n3", "1;2l\n3"})
    void should_throw_error_when_string_contains_two_separator_chain(String numbers) {
        Assertions.assertThrows(ParsingException.class, () -> {
            stringCalculator.add(numbers);
        });
    }
    
    @Test
    void should_return_sum_when_containing_custom_delimiter() throws ParsingException {
        Integer sut = stringCalculator.add("//_\n5_89");
        Assertions.assertEquals(94,sut);
    }

}