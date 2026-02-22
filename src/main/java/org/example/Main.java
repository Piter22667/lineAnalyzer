package org.example;

import java.util.Scanner;

public class Main {
    private static final int MAX = 143;
    private static final int MIN = -143;

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        Line line1 = inputLine1(scanner);
        Line line2 = inputLine2(scanner);
        Line line3 = inputLine3(scanner);

        LineHelper lineHelper = new LineHelper();
        String result = lineHelper.analyzePlacement(line1, line2, line3);

        System.out.println("\n Результат: ");
        System.out.println(result);
    }

    private static Line inputLine1(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Введіть координати прямої, що проходить через задані точки (x1, y1); (x2, y2): ");
                double[] numbers = readNumbers(scanner, 4);

                return Line.fromTwoPoints(numbers[0], numbers[1], numbers[2], numbers[3]);

            } catch (NumberFormatException e) {
                printErrorAndSolutionMessages("Введено текст або нерозпізнані символи замість числа.", "Будь ласка, введіть 4 числа через пробіл.");
            } catch (IllegalArgumentException e) {
                printErrorAndSolutionMessages(e.getMessage(), "Перевірте правильність введених даних і спробуйте ще раз.");
            }
        }
    }

    private static Line inputLine2(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Введіть значення кутового коефіцієнта k та вільного члена b: ");
                double[] numbers = readNumbers(scanner, 2);

                return Line.fromSlopeAndIntercept(numbers[0], numbers[1]);

            } catch (NumberFormatException e) {
                printErrorAndSolutionMessages("Введено текст або нерозпізнані символи замість числа.", "Будь ласка, введіть 4 числа через пробіл.");
            } catch (IllegalArgumentException e) {
                printErrorAndSolutionMessages(e.getMessage(), "Перевірте правильність введених даних і спробуйте ще раз.");
            }
        }
    }

    private static Line inputLine3(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Введіть координати точки (x0; y0) та вектора нормалі (a, b): ");
                double[] numbers = readNumbers(scanner, 4);

                return Line.fromPointAndNormal(numbers[0], numbers[1], numbers[2], numbers[3]);

            } catch (NumberFormatException e) {
                printErrorAndSolutionMessages("Введено текст або нерозпізнані символи замість числа.", "Будь ласка, введіть 4 числа через пробіл.");
            } catch (IllegalArgumentException e) {
                printErrorAndSolutionMessages(e.getMessage(), "Перевірте правильність введених даних і спробуйте ще раз.");
            }
        }
    }

    private static double[] readNumbers(Scanner scanner, int expectedCount) {
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            throw new IllegalArgumentException("Введено порожній рядок");
        }

        String[] parts = input.split("\\s++");

        if (parts.length != expectedCount) {
            throw new IllegalArgumentException(String.format("Неправильна кількість параметрів. Очікується: %d, введено: %d.", expectedCount, parts.length));
        }

        double[] result = new double[expectedCount];
        for (int i = 0; i < expectedCount; i++) {
            result[i] = Double.parseDouble(parts[i]);

            if (result[i] < MIN || result[i] > MAX) {
                throw new IllegalArgumentException(String.format("Значення %.1f виходить за межі допустимого діапазону [%d; %d].", result[i], MIN, MAX));
            }
        }
        return result;
    }

    private static void printErrorAndSolutionMessages(String errorMessage, String solution) {
        System.out.println(errorMessage);
        System.out.println(solution + "\n");
    }
}

