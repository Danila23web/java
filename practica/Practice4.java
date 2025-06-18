package com.example.tasks;

import java.util.*;

public class Practice4 {
    // Задачи 45-46: Конвертация римских чисел
    private static final TreeMap<Integer, String> ARABIC_TO_ROMAN = new TreeMap<>();
    private static final TreeMap<String, Integer> ROMAN_TO_ARABIC = new TreeMap<>();

    static {
        ARABIC_TO_ROMAN.put(1000, "M");
        ARABIC_TO_ROMAN.put(900, "CM");
        ARABIC_TO_ROMAN.put(500, "D");
        ARABIC_TO_ROMAN.put(400, "CD");
        ARABIC_TO_ROMAN.put(100, "C");
        ARABIC_TO_ROMAN.put(90, "XC");
        ARABIC_TO_ROMAN.put(50, "L");
        ARABIC_TO_ROMAN.put(40, "XL");
        ARABIC_TO_ROMAN.put(10, "X");
        ARABIC_TO_ROMAN.put(9, "IX");
        ARABIC_TO_ROMAN.put(5, "V");
        ARABIC_TO_ROMAN.put(4, "IV");
        ARABIC_TO_ROMAN.put(1, "I");

        ARABIC_TO_ROMAN.forEach((k, v) -> ROMAN_TO_ARABIC.put(v, k));
    }

    // Задача 45: Конвертация в римские числа
    public static String toRoman(int number) {
        if (number < 1 || number > 9999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 9999");
        }

        StringBuilder result = new StringBuilder();
        while (number > 0) {
            int key = ARABIC_TO_ROMAN.floorKey(number);
            result.append(ARABIC_TO_ROMAN.get(key));
            number -= key;
        }
        return result.toString();
    }

    // Задача 46: Конвертация из римских чисел
    public static int fromRoman(String roman) {
        if (roman == null || roman.isEmpty()) {
            throw new IllegalArgumentException("Строка не может быть пустой");
        }

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            String currentSymbol = i > 0 ? 
                roman.substring(i - 1, i + 1) : 
                String.valueOf(roman.charAt(i));

            Integer value = ROMAN_TO_ARABIC.get(currentSymbol);

            if (value != null) {
                result += value;
                i--;
                continue;
            }

            currentSymbol = String.valueOf(roman.charAt(i));
            value = ROMAN_TO_ARABIC.get(currentSymbol);

            if (value == null) {
                throw new IllegalArgumentException("Неверный римский символ: " + currentSymbol);
            }

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }

            prevValue = value;
        }

        return result;
    }

    // Задача 47: Сортировка змейкой
    public static List<Integer> snail(int[][] array) {
        if (array == null || array.length == 0) return new ArrayList<>();

        List<Integer> result = new ArrayList<>();
        int n = array.length;
        int m = array[0].length;
        
        int top = 0, bottom = n - 1;
        int left = 0, right = m - 1;
        
        while (top <= bottom && left <= right) {
            // Движение вправо
            for (int i = left; i <= right; i++) {
                result.add(array[top][i]);
            }
            top++;
            
            if (top > bottom) break;
            
            // Движение вниз
            for (int i = top; i <= bottom; i++) {
                result.add(array[i][right]);
            }
            right--;
            
            if (left > right) break;
            
            // Движение влево
            for (int i = right; i >= left; i--) {
                result.add(array[bottom][i]);
            }
            bottom--;
            
            if (top > bottom) break;
            
            // Движение вверх
            for (int i = bottom; i >= top; i--) {
                result.add(array[i][left]);
            }
            left++;
        }
        
        return result;
    }

    // Задача 48: Генерация последовательности u
    public static List<Integer> generateSequence(int x) {
        Set<Integer> sequence = new TreeSet<>();
        sequence.add(1); // u(0) = 1
        
        for (int i = 0; i <= x; i++) {
            int y = 2 * i + 1;
            int z = 3 * i + 1;
            sequence.add(y);
            sequence.add(z);
        }
        
        return new ArrayList<>(sequence);
    }

    // Задача 49: Реализация функции fusc
    public static long fusc(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        
        if (n % 2 == 0) {
            return fusc(n / 2);
        } else {
            return fusc((n - 1) / 2) + fusc((n - 1) / 2 + 1);
        }
    }

    // Оптимизированная версия fusc с использованием итеративного подхода
    public static long fuscIterative(long n) {
        if (n == 0) return 0;
        
        long a = 1, b = 0;
        while (n > 0) {
            if (n % 2 == 0) {
                a = a + b;
            } else {
                b = a + b;
            }
            n = n / 2;
        }
        return b;
    }

    public static void main(String[] args) {
        // Тесты для задач 45-46
        System.out.println("=== Задачи 45-46 ===");
        System.out.println(toRoman(1994)); // MCMXCIV
        System.out.println(fromRoman("MCMXCIV")); // 1994

        // Тест для задачи 47
        System.out.println("\n=== Задача 47 ===");
        int[][] array = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println(snail(array)); // [1, 2, 3, 6, 9, 8, 7, 4, 5]

        // Тест для задачи 48
        System.out.println("\n=== Задача 48 ===");
        System.out.println(generateSequence(5));

        // Тест для задачи 49
        System.out.println("\n=== Задача 49 ===");
        System.out.println("fusc(5) = " + fuscIterative(5));
    }
} 