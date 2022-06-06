package org.example;

import org.example.input.UserInput;
import org.example.utils.Parser;

public final class Main {
    private Main() {
    }

    public static void main(final String[] args) {
        while (true) {
            System.out.println("Введите числа. Для выхода введите 'exit'");
            String input = UserInput.getLine();
            if (input.equals("")) {
                break;
            }
            Parser parser = new Parser();
                System.out.printf("Результат: %s = %.2f", input
                                .replace("[", "")
                                .replace("]", ""),
                        parser.parse(input));
                System.out.println();
        }
    }
}
