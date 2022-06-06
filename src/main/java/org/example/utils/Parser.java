package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parser implements Operators {
    private static final Logger LOGGER = LogManager.getLogger(Parser.class);

    public final double parse(final String string) {
        Result result = plusMinus(string);
        if (!result.getRest().isEmpty()) {
            LOGGER.warn("Ошибка! Невозможно распарсить! '{}'",
                    result.getRest());
            return 0;
        }
        return result.getAcc();
    }

    @Override
    public Result bracket(final String string) {
        char zeroChar = string.charAt(0);
        if (zeroChar == '(') {
            Result result = plusMinus(string.substring(1));
            if (!result.getRest().isEmpty()
                    && result.getRest().charAt(0) == ')') {
                result.setRest(result.getRest().substring(1));
            } else {
                LOGGER.warn("Нет закрывающих скобок");
            }
            return result;
        }
        return numbers(string);
    }

    @Override
    public Result plusMinus(final String string) {
        Result current = multipleDivide(string);
        double acc = current.getAcc();
        while (current.getRest().length() > 0) {
            if (!(current.getRest().charAt(0) == '+'
                    || current.getRest().charAt(0) == '-')) {
                break;
            }
            char sign = current.getRest().charAt(0);
            String next = current.getRest().substring(1);
            current = multipleDivide(next);
            if (sign == '+') {
                acc += current.getAcc();
            } else {
                acc -= current.getAcc();
            }
        }
        return new Result(acc, current.getRest());
    }

    @Override
    public Result multipleDivide(final String string) {
        Result current = bracket(string);
        double acc = current.getAcc();
        while (true) {
            if (current.getRest().length() == 0) {
                return current;
            }
            char sign = current.getRest().charAt(0);
            if ((sign != '*' && sign != '/')) {
                return current;
            }
            String next = current.getRest().substring(1);
            Result right = bracket(next);
            if (sign == '*') {
                acc *= right.getAcc();
            } else {
                acc /= right.getAcc();
            }
            current = new Result(acc, right.getRest());
        }
    }

    @Override
    public Result numbers(final String tmp) {
        String stringResult = tmp;
        int i = 0;
        int dotCnt = 0;
        boolean negative = false;
        if (stringResult.charAt(0) == '-') {
            negative = true;
            stringResult = stringResult.substring(1);
        }
        while (i < stringResult.length()
                && (Character.isDigit(stringResult.charAt(i))
                || stringResult.charAt(i) == '.')) {
            if (stringResult.charAt(i) == '.' && ++dotCnt > 1) {
                LOGGER.warn("Не верное число '{}' ", stringResult);
                throw new IllegalArgumentException(
                        "Не верное число " + stringResult.substring(0, i + 1));
            }
            i++;
        }
        if (i == 0) {
            LOGGER.warn("Нет действительного числа '{}' ", stringResult);
            throw new IllegalArgumentException(
                    "Нет действительного числа " + stringResult);
        }
        double dPart = Double.parseDouble(stringResult.substring(0, i));
        if (negative) {
            dPart = -dPart;
        }
        String restPart = stringResult.substring(i);
        return new Result(dPart, restPart);
    }
}
