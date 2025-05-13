import java.util.Scanner;

public class Main {
    public static boolean isValid(String input) {
        int balance = 0;
        for (char ch : input.toCharArray()) {
            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
                if (balance < 0) return false;
            }
        }
        return balance == 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку со скобками: ");
        String input = scanner.nextLine();

        if (isValid(input)) {
            System.out.println("Скобки расставлены корректно.");
        } else {
            System.out.println("Скобки расставлены некорректно.");
        }

        scanner.close();
    }
}
