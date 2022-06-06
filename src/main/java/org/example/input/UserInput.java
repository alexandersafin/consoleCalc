package org.example.input;

import java.util.Scanner;

public final class UserInput {
    public static String getLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private UserInput() {
    }
}
