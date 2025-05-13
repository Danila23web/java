import java.util.*;

public class chet {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int[] secret = new int[4];
        for (int i = 0; i < 4; i++) {
            secret[i] = random.nextInt(10); // цифры от 0 до 9
        }

        int attempts = 0;
        boolean guessed = false;

        System.out.println("Угадайте 4-значный код. Введите 4 цифры от 0 до 9 (через пробел). У вас 20 попыток.");

        while (attempts < 20) {
            attempts++;
            System.out.print("Попытка " + attempts + ": ");
            int[] guess = new int[4];

            try {
                for (int i = 0; i < 4; i++) {
                    guess[i] = scanner.nextInt();
                    if (guess[i] < 0 || guess[i] > 9) throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Введите 4 корректных цифры от 0 до 9.");
                scanner.nextLine(); // очистка ввода
                attempts--;
                continue;
            }

            int matches = 0;
            boolean[] usedSecret = new boolean[4];
            boolean[] usedGuess = new boolean[4];

            for (int i = 0; i < 4; i++) {
                if (guess[i] == secret[i]) {
                    matches++;
                    usedSecret[i] = true;
                    usedGuess[i] = true;
                }
            }

            for (int i = 0; i < 4; i++) {
                if (!usedGuess[i]) {
                    for (int j = 0; j < 4; j++) {
                        if (!usedSecret[j] && guess[i] == secret[j]) {
                            matches++;
                            usedSecret[j] = true;
                            break;
                        }
                    }
                }
            }

            if (matches == 4) {
                guessed = true;
                break;
            } else {
                System.out.println("Совпадений: " + matches);
            }
        }

        if (guessed) {
            System.out.println("Поздравляем! Вы угадали код за " + attempts + " попыток.");
        } else {
            System.out.print("Вы проиграли. Код был: ");
            for (int digit : secret) System.out.print(digit);
            System.out.println();
        }

        scanner.close();
    }
}
