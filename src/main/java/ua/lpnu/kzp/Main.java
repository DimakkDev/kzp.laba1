package ua.lpnu.kzp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Головний клас для обробки даних розсадника рослин (Варіант 12).
 */
public final class Main {

    private Main() {
        // Приватний конструктор службового класу
    }

    /**
     * Точка входу в програму.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        // Якщо аргументи не передані з консолі, встановлюємо дефолтні значення
        if (args == null || args.length == 0) {
            args = new String[]{"--input", "data/plants.csv", "--output", "out/report.txt"};
        }

        Path inputPath = Path.of("data", "plants.csv");
        Path outputPath = Path.of("out", "report.txt");

        // Обробка аргументів командного рядка
        for (int i = 0; i < args.length; i++) {
            if ("--help".equals(args[i])) {
                System.out.println("Використання: java -jar lab01.jar [--help] [--input <файл>] [--output <файл>]");
                return;
            } else if ("--input".equals(args[i]) && i + 1 < args.length) {
                inputPath = Path.of(args[i + 1]);
            } else if ("--output".equals(args[i]) && i + 1 < args.length) {
                outputPath = Path.of(args[i + 1]);
            }
        }

        if (!Files.exists(inputPath)) {
            System.err.println("Помилка: вхідний файл не знайдено за шляхом " + inputPath);
            return;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Помилка читання файла: " + e.getMessage());
            return;
        }

        List<String> errors = new ArrayList<>();
        int validCount = 0;
        double totalHeight = 0.0;
        double maxPrice = Double.NEGATIVE_INFINITY;
        String mostExpensivePlant = "—";
        int minWateringDays = Integer.MAX_VALUE;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue; // Пропускаємо порожні рядки
            }

            String[] fields = line.split(";", -1);
            if (fields.length != 5) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: очікується 5 полів, отримано %d", i + 1, fields.length));
                continue;
            }

            String species = fields[0].trim();
            String name = fields[1].trim();

            if (species.isBlank() || name.isBlank()) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: порожня вид або назва рослини", i + 1));
                continue;
            }

            try {
                double heightCm = Double.parseDouble(fields[2].trim());
                double price = Double.parseDouble(fields[3].trim());
                int wateringDays = Integer.parseInt(fields[4].trim());

                if (heightCm <= 0 || price <= 0 || wateringDays <= 0) {
                    errors.add(String.format(Locale.ROOT, "Рядок %d: від'ємні або нульові значення числових полів", i + 1));
                    continue;
                }

                // Успішний запис
                validCount++;
                totalHeight += heightCm;

                if (price > maxPrice) {
                    maxPrice = price;
                    mostExpensivePlant = name + " (" + species + ")";
                }

                if (wateringDays < minWateringDays) {
                    minWateringDays = wateringDays;
                }

            } catch (NumberFormatException e) {
                errors.add(String.format(Locale.ROOT, "Рядок %d: некоректний числовий формат", i + 1));
            }
        }

        double avgHeight = (validCount > 0) ? (totalHeight / validCount) : 0.0;
        String finalMaxPrice = (validCount > 0) ? String.format(Locale.ROOT, "%.2f грн [%s]", maxPrice, mostExpensivePlant) : "—";
        String finalMinWatering = (validCount > 0) ? String.format(Locale.ROOT, "%d днів", minWateringDays) : "—";

        // Формування звіту
        StringBuilder report = new StringBuilder();
        report.append("=== ЗВІТ ПО РОЗСАДНИКУ РОСЛИН ===\n");
        report.append(String.format(Locale.ROOT, "Кількість коректних записів: %d%n", validCount));
        report.append(String.format(Locale.ROOT, "Середня висота рослин:       %.2f см%n", avgHeight));
        report.append(String.format(Locale.ROOT, "Найдорожча рослина:          %s%n", finalMaxPrice));
        report.append(String.format(Locale.ROOT, "Найменший інтервал поливу:   %s%n", finalMinWatering));
        report.append(String.format(Locale.ROOT, "Знайдено помилок у файлі:    %d%n", errors.size()));

        if (!errors.isEmpty()) {
            report.append("\nДеталі помилок:\n");
            for (String err : errors) {
                report.append(" - ").append(err).append("\n");
            }
        }

        // Вивід у консоль
        System.out.print(report);

        // Запис у файл
        try {
            Path parent = outputPath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(outputPath, report.toString(), StandardCharsets.UTF_8);
            System.out.println("\nЗвіт успішно збережено у файл: " + outputPath);
        } catch (IOException e) {
            System.err.println("Помилка запису файла звіту: " + e.getMessage());
        }
    }
}