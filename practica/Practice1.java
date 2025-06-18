package com.example.tasks;

import java.util.*;

public class Practice1 {
    // Задача 1: Проверка окончания строки
    public static boolean isEnding(String str1, String str2) {
        if (str1 == null || str2 == null || str2.length() > str1.length()) {
            return false;
        }
        return str1.endsWith(str2);
    }

    // Задача 2: Форматирование приветствия
    public static String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Привет, гость!";
        }
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return "Привет, " + name + "!";
    }

    // Задача 3: Удаление гласных
    public static String removeVowels(String str) {
        if (str == null) return "";
        return str.replaceAll("[аеёиоуыэюяАЕЁИОУЫЭЮЯaeiouAEIOU]", "");
    }

    // Задача 4: Квадраты чисел
    public static List<Integer> getSquareSequence(int num1, int num2) {
        List<Integer> result = new ArrayList<>();
        int start = Math.min(num1, num2);
        int end = Math.max(num1, num2);
        for (int i = start; i <= end; i++) {
            result.add(i * i);
        }
        return result;
    }

    // Задача 5: Арифметическая прогрессия
    public static List<Integer> getArithmeticSequence(int start, int step, int count) {
        List<Integer> result = new ArrayList<>();
        int current = start;
        for (int i = 0; i < count; i++) {
            result.add(current);
            current += step;
        }
        return result;
    }

    // Задача 6: Хранилище трех чисел
    public static class NumberStorage {
        private Integer[] numbers = new Integer[3];

        public void addNumber(int number) {
            // Поиск пустой ячейки
            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i] == null) {
                    numbers[i] = number;
                    return;
                }
            }

            // Если пустых ячеек нет, находим минимальное число
            int minIndex = 0;
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] < numbers[minIndex]) {
                    minIndex = i;
                }
            }
            if (number > numbers[minIndex]) {
                numbers[minIndex] = number;
            }
        }

        public Integer[] getNumbers() {
            return numbers.clone();
        }
    }

    // Задача 7: Удаление дубликатов слов
    public static String removeDuplicateWords(String str) {
        if (str == null || str.isEmpty()) return "";
        LinkedHashSet<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(str.split("\\s+")));
        StringBuilder result = new StringBuilder();
        for (String word : uniqueWords) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(word);
        }
        return result.toString();
    }

    // Задача 8: Преобразование регистра
    public static String convertCase(String str) {
        if (str == null || str.isEmpty()) return str;

        int upperCount = 0, lowerCount = 0;
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) upperCount++;
            else if (Character.isLowerCase(c)) lowerCount++;
        }

        return upperCount > lowerCount ? str.toUpperCase() : str.toLowerCase();
    }

    public static void main(String[] args) {
        // Тесты для всех задач
        System.out.println("=== Задача 1 ===");
        System.out.println(isEnding("abc", "bc")); // true
        System.out.println(isEnding("abc", "d")); // false

        System.out.println("\n=== Задача 2 ===");
        System.out.println(greet("аНнА")); // Привет, Анна!

        System.out.println("\n=== Задача 3 ===");
        System.out.println(removeVowels("привет мир")); // првт мр

        System.out.println("\n=== Задача 4 ===");
        System.out.println(getSquareSequence(1, 5)); // [1, 4, 9, 16, 25]

        System.out.println("\n=== Задача 5 ===");
        System.out.println(getArithmeticSequence(1, 2, 5)); // [1, 3, 5, 7, 9]

        System.out.println("\n=== Задача 6 ===");
        NumberStorage storage = new NumberStorage();
        storage.addNumber(5);
        storage.addNumber(3);
        storage.addNumber(8);
        storage.addNumber(10); // Должно заменить 3
        System.out.println(Arrays.toString(storage.getNumbers()));

        System.out.println("\n=== Задача 7 ===");
        System.out.println(removeDuplicateWords("abc abc ab abc ab acs acs")); // abc ab acs

        System.out.println("\n=== Задача 8 ===");
        System.out.println(convertCase("ABccAAr")); // ABCCAAR
        System.out.println(convertCase("abCCaaR")); // abccaar
    }
} 