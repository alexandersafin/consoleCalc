package org.example.utils;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class ParserTest {
    @Parameterized.Parameters(name = "{index} : Сумма {0} = {1}")
    public static Iterable<Object> data() {
        return Arrays.asList(new Object[][] {
                { "2+3", 5 },
                { "10-6", 4 },
                { "10*2", 20 },
                { "20/2", 10 },
                { "10-6", 4 },
                { "(2*6)*10+2", 122 },
                { "(-2*6)*10+2/10-10", -129.8 }
        });
    }
    private final String input;
    private final double resultExpected;

    public ParserTest(final String input, final double result) {
        this.input = input;
        this.resultExpected = result;
    }

    @Test
    public void test_operators() {
        Assert.assertEquals(resultExpected, new Parser().parse(input), 0);
        System.out.printf("expected: %.1f ===> actual %s \n",
                resultExpected, new Parser().parse(input));
    }
}
