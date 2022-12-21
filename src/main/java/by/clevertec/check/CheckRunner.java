package by.clevertec.check;

import java.util.List;
import java.util.Scanner;

public class CheckRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Check().checkCalculate(List.of(scanner.nextLine().split(" ")));
    }
}
