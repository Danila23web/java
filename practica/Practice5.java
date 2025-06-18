package com.example.tasks;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Practice5 {
    private static final String HISTORY_FILE = "calculator_history.txt";

    // Задача 50: Калькулятор
    public static double evaluate(String expression) {
        // Удаляем пробелы и проверяем на пустоту
        expression = expression.replaceAll("\\s+", "");
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Пустое выражение");
        }

        // Сохраняем выражение в историю
        saveToHistory(expression);

        return evaluateExpression(expression);
    }

    private static double evaluateExpression(String expression) {
        // Обработка модуля числа
        while (expression.contains("|")) {
            int start = expression.indexOf("|");
            int end = expression.indexOf("|", start + 1);
            if (end == -1) {
                throw new IllegalArgumentException("Незакрытый модуль");
            }
            double value = evaluateExpression(expression.substring(start + 1, end));
            expression = expression.substring(0, start) + Math.abs(value) + expression.substring(end + 1);
        }

        // Разбиваем выражение на токены
        List<String> tokens = tokenize(expression);
        
        // Вычисляем выражение с учетом приоритета операций
        return evaluateTokens(tokens);
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.' || 
                (c == '-' && number.length() == 0 && 
                (tokens.isEmpty() || isOperator(tokens.get(tokens.size() - 1))))) {
                number.append(c);
            } else if (isOperator(String.valueOf(c))) {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }
                tokens.add(String.valueOf(c));
            }
        }
        
        if (number.length() > 0) {
            tokens.add(number.toString());
        }

        return tokens;
    }

    private static double evaluateTokens(List<String> tokens) {
        // Сначала обрабатываем степени
        evaluateOperation(tokens, "^");
        
        // Затем умножение, деление и остаток
        while (tokens.contains("*") || tokens.contains("/") || tokens.contains("//") || tokens.contains("%")) {
            for (int i = 0; i < tokens.size(); i++) {
                if (tokens.get(i).equals("*") || tokens.get(i).equals("/") || 
                    tokens.get(i).equals("//") || tokens.get(i).equals("%")) {
                    evaluateOperation(tokens, i);
                    i--;
                }
            }
        }
        
        // В конце сложение и вычитание
        while (tokens.size() > 1) {
            evaluateOperation(tokens, 1);
        }

        return Double.parseDouble(tokens.get(0));
    }

    private static void evaluateOperation(List<String> tokens, String operator) {
        for (int i = 1; i < tokens.size() - 1; i++) {
            if (tokens.get(i).equals(operator)) {
                double result = performOperation(
                    Double.parseDouble(tokens.get(i - 1)),
                    Double.parseDouble(tokens.get(i + 1)),
                    operator
                );
                tokens.set(i - 1, String.valueOf(result));
                tokens.remove(i);
                tokens.remove(i);
                i--;
            }
        }
    }

    private static void evaluateOperation(List<String> tokens, int pos) {
        double result = performOperation(
            Double.parseDouble(tokens.get(pos - 1)),
            Double.parseDouble(tokens.get(pos + 1)),
            tokens.get(pos)
        );
        tokens.set(pos - 1, String.valueOf(result));
        tokens.remove(pos);
        tokens.remove(pos);
    }

    private static double performOperation(double a, double b, String operator) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case "//": return Math.floor(a / b);
            case "%": return a % b;
            case "^": return Math.pow(a, b);
            default: throw new IllegalArgumentException("Неизвестный оператор: " + operator);
        }
    }

    private static boolean isOperator(String token) {
        return token.matches("[+\\-*/%^]") || token.equals("//");
    }

    private static void saveToHistory(String expression) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.println(expression + " = " + evaluateExpression(expression));
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в историю: " + e.getMessage());
        }
    }

    public static List<String> getHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении истории: " + e.getMessage());
        }
        return history;
    }

    public static void main(String[] args) {
        // Примеры использования
        String[] expressions = {
            "7 + 3 * |(-2^3 - 4)| % 23 / 2",
            "2 + 2 * 2",
            "3 ^ 2",
            "-5 + 8",
            "10 // 3",
            "10 % 3"
        };

        System.out.println("Вычисление выражений:");
        for (String expr : expressions) {
            try {
                System.out.printf("%s = %.2f%n", expr, evaluate(expr));
            } catch (Exception e) {
                System.out.printf("Ошибка в выражении '%s': %s%n", expr, e.getMessage());
            }
        }

        System.out.println("\nИстория вычислений:");
        List<String> history = getHistory();
        for (String entry : history) {
            System.out.println(entry);
        }
    }
} 