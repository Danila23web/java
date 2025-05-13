import java.util.Scanner;

public class TriangleAngle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод трёх сторон
        System.out.print("Введите длину первой стороны: ");
        double a = scanner.nextDouble();

        System.out.print("Введите длину второй стороны: ");
        double b = scanner.nextDouble();

        System.out.print("Введите длину третьей стороны: ");
        double c = scanner.nextDouble();

        // Проверка возможности построения треугольника
        if (a + b > c && a + c > b && b + c > a) {
            System.out.println("Треугольник существует.");

            // Найдём углы по теореме косинусов
            double angleA = Math.acos((b*b + c*c - a*a) / (2*b*c));
            double angleB = Math.acos((a*a + c*c - b*b) / (2*a*c));
            double angleC = Math.acos((a*a + b*b - c*c) / (2*a*b));

            // Переводим в градусы
            angleA = Math.toDegrees(angleA);
            angleB = Math.toDegrees(angleB);
            angleC = Math.toDegrees(angleC);

            // Находим наибольший внутренний угол
            double maxInner = Math.max(angleA, Math.max(angleB, angleC));
            double outer = 180 - maxInner;

            System.out.printf("Наибольший внешний угол: %.2f градусов\n", outer);
        } else {
            System.out.println("Треугольник с такими сторонами не существует.");
        }

        scanner.close();
    }
}
