package com.example.tasks;

import java.util.*;
import java.math.BigInteger;

public class Practice3 {
    // Задача 28: Шифр Цезаря
    public static String caesarCipher(String text, int shift, boolean right) {
        if (text == null || text.isEmpty()) return "";
        
        StringBuilder result = new StringBuilder();
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        
        if (!right) shift = -shift;
        
        for (char c : text.toLowerCase().toCharArray()) {
            int index = alphabet.indexOf(c);
            if (index != -1) {
                int newIndex = (index + shift) % alphabet.length();
                if (newIndex < 0) newIndex += alphabet.length();
                result.append(alphabet.charAt(newIndex));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // Задача 29: Разница квадратов
    public static int[] findSquares(int n) {
        int i = 1;
        while (true) {
            int square = i * i;
            int nextSquare = (i + 1) * (i + 1);
            if (nextSquare - square == n) {
                return new int[]{square, nextSquare};
            }
            i++;
        }
    }

    // Задача 30: Сумма степеней цифр
    public static boolean checkSequentialPowers(int number, int start) {
        String digits = String.valueOf(number);
        int sum = 0;
        int power = start;
        
        for (char digit : digits.toCharArray()) {
            int d = Character.getNumericValue(digit);
            sum += Math.pow(d, power++);
        }
        
        // Проверяем, является ли сумма произведением исходного числа на степень двойки
        for (int i = 0; i <= 6; i++) {
            if (sum == number * (1 << i)) {
                return true;
            }
        }
        return false;
    }

    // Задача 31: Выбор городов
    public static List<Integer> chooseBestSum(int maxDistance, int citiesCount, List<Integer> distances) {
        List<Integer> bestCombination = new ArrayList<>();
        int[] bestSum = {0};
        
        combinationUtil(distances, citiesCount, 0, new ArrayList<>(), maxDistance, bestSum, bestCombination);
        
        return bestCombination;
    }
    
    private static void combinationUtil(List<Integer> distances, int citiesCount, int start,
                                      List<Integer> current, int maxDistance, int[] bestSum,
                                      List<Integer> bestCombination) {
        if (current.size() == citiesCount) {
            int sum = current.stream().mapToInt(Integer::intValue).sum();
            if (sum <= maxDistance && sum > bestSum[0]) {
                bestSum[0] = sum;
                bestCombination.clear();
                bestCombination.addAll(current);
            }
            return;
        }
        
        for (int i = start; i < distances.size(); i++) {
            current.add(distances.get(i));
            combinationUtil(distances, citiesCount, i + 1, current, maxDistance, bestSum, bestCombination);
            current.remove(current.size() - 1);
        }
    }

    // Задача 32: Числа Фибоначчи
    public static Map.Entry<Integer, Integer> fibonacciDigitCount(int n) {
        BigInteger fib = calculateFibonacci(n);
        String fibStr = fib.toString();
        
        int[] counts = new int[10];
        for (char c : fibStr.toCharArray()) {
            counts[c - '0']++;
        }
        
        int maxCount = 0;
        int maxDigit = 0;
        for (int i = 0; i < 10; i++) {
            if (counts[i] >= maxCount) {
                maxCount = counts[i];
                maxDigit = i;
            }
        }
        
        return Map.entry(maxCount, maxDigit);
    }
    
    private static BigInteger calculateFibonacci(int n) {
        if (n < 2) return BigInteger.valueOf(n);
        
        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ONE;
        
        for (int i = 2; i < n; i++) {
            BigInteger c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }

    // Задачи 33-34: Игра с палочками
    public static class StickGame {
        public static long playOptimal(long n) {
            if (n <= 1) return 0;
            if (n % 2 == 0) return n / 2;
            return 1;
        }
        
        public static long calculateTanyaSticks(long n) {
            long tanyaSticks = 0;
            while (n > 0) {
                if (n % 2 == 0) {
                    long take = Math.max(1, n / 2);
                    n -= take;
                    if (n > 0) {
                        tanyaSticks += playOptimal(n);
                        n -= playOptimal(n);
                    }
                } else {
                    tanyaSticks += 1;
                    n -= 1;
                    if (n > 0) {
                        tanyaSticks += playOptimal(n);
                        n -= playOptimal(n);
                    }
                }
            }
            return tanyaSticks;
        }
    }

    // Задача 35: Сортировка по сумме цифр
    public static String sortByDigitSum(String numbers) {
        String[] nums = numbers.split("\\s+");
        Arrays.sort(nums, (a, b) -> {
            int sumA = a.chars().map(c -> c - '0').sum();
            int sumB = b.chars().map(c -> c - '0').sum();
            return sumA == sumB ? b.compareTo(a) : sumA - sumB;
        });
        return String.join(" ", nums);
    }

    // Задача 36: Расшифровка Цезаря с использованием словаря
    public static String breakCaesarCipher(String text, String dictionary) {
        String bestDecryption = "";
        int bestMatch = 0;
        
        for (int shift = 0; shift < 33; shift++) { // для русского алфавита
            String decrypted = caesarCipher(text, shift, false);
            int matches = countWordMatches(decrypted, dictionary);
            if (matches > bestMatch) {
                bestMatch = matches;
                bestDecryption = decrypted;
            }
        }
        
        return bestDecryption;
    }
    
    private static int countWordMatches(String text, String dictionary) {
        Set<String> dictWords = new HashSet<>(Arrays.asList(dictionary.toLowerCase().split("\\s+")));
        return (int) Arrays.stream(text.toLowerCase().split("\\s+"))
                         .filter(dictWords::contains)
                         .count();
    }

    // Задача 37: Игра "Угадай код"
    public static class CodeBreaker {
        private final int[] code;
        private int attempts;
        
        public CodeBreaker() {
            code = new int[4];
            Random rand = new Random();
            for (int i = 0; i < 4; i++) {
                code[i] = rand.nextInt(10);
            }
            attempts = 0;
        }
        
        public int guess(int[] attempt) {
            attempts++;
            if (attempts > 20) throw new RuntimeException("Превышено количество попыток");
            
            int matches = 0;
            boolean[] used = new boolean[4];
            boolean[] attemptUsed = new boolean[4];
            
            // Проверяем точные совпадения
            for (int i = 0; i < 4; i++) {
                if (attempt[i] == code[i]) {
                    matches++;
                    used[i] = true;
                    attemptUsed[i] = true;
                }
            }
            
            return matches;
        }
    }

    // Задача 38: Игра "Каждый третий"
    public static List<Integer> josephus(int n, int start) {
        List<Integer> players = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            players.add(i);
        }
        
        int currentIndex = start - 1;
        
        while (!players.isEmpty()) {
            currentIndex = (currentIndex + 2) % players.size();
            result.add(players.remove(currentIndex));
            if (players.isEmpty()) break;
            if (currentIndex == players.size()) currentIndex = 0;
        }
        
        return result;
    }

    // Задача 39: Преобразование слов
    public static String transformWords(String text) {
        StringBuilder result = new StringBuilder();
        String[] sentences = text.split("(?<=[.!?])\\s+");
        
        for (String sentence : sentences) {
            String[] words = sentence.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (word.matches(".*[а-яА-Яa-zA-Z].*")) {
                    String transformed = word.substring(1) + word.charAt(0) + "ауч";
                    words[i] = transformed;
                }
            }
            result.append(String.join(" ", words)).append(" ");
        }
        
        return result.toString().trim();
    }

    public static void main(String[] args) {
        // Тесты для всех задач
        System.out.println("=== Задача 28 ===");
        System.out.println(caesarCipher("привет", 3, true));

        System.out.println("\n=== Задача 29 ===");
        System.out.println(Arrays.toString(findSquares(9))); // [16, 25]

        System.out.println("\n=== Задача 30 ===");
        System.out.println(checkSequentialPowers(89, 1)); // true

        System.out.println("\n=== Задача 31 ===");
        List<Integer> distances = Arrays.asList(50, 55, 56, 57, 58);
        System.out.println(chooseBestSum(163, 3, distances));

        System.out.println("\n=== Задача 32 ===");
        System.out.println(fibonacciDigitCount(10)); // (2, 5)

        System.out.println("\n=== Задача 33-34 ===");
        System.out.println(StickGame.calculateTanyaSticks(10));

        System.out.println("\n=== Задача 35 ===");
        System.out.println(sortByDigitSum("56 65 74 100 99 68 86 180 90"));

        System.out.println("\n=== Задача 38 ===");
        System.out.println(josephus(7, 1));

        System.out.println("\n=== Задача 39 ===");
        System.out.println(transformWords("Привет мир! Как дела?"));
    }
} 