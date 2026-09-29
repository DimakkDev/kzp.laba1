package ua.lpnu.kzp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Головний клас програми аналізу розсадника рослин.
 */
public final class Main {

    private Main() {
        // Приватний конструктор для запобігання створенню екземплярів
    }

    public static void main(final String[] args) {
        final Path inputPath = args != null && args.length > 0 ? Paths.get(args[0]) : Paths.get("data", "plants.csv");
        final Path outputPath = args != null && args.length > 1 ? Paths.get(args[1]) : Paths.get("out", "report.txt");

        if (!Files.exists(inputPath)) {
            System.err.println("Файл не знайдено: " + inputPath);
            return;
        }

        final List<Plant> plants = new ArrayList<>();
        final List<String> errors = new ArrayList<>();

        try {
            final List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);

            for (int i = 0; i < lines.size(); i++) {
                final String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }

                try {
                    final Plant plant = Plant.fromCsv(line);
                    plants.add(plant);
                } catch (IllegalArgumentException e) {
                    errors.add("Рядок " + (i + 1) + ": " + e.getMessage());
                }
            }

            final HeightWatering summary = calculateSummary(plants);
            final String reportText = generateReport(summary, errors);

            System.out.println(reportText);

            final Path parentDir = outputPath.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            Files.writeString(outputPath, reportText, StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("Помилка читання/запису файлу: " + e.getMessage());
        }
    }

    public static HeightWatering calculateSummary(final List<Plant> plants) {
        if (plants == null || plants.isEmpty()) {
            return new HeightWatering(0, 0.0, 0.0, "N/A", 0, "N/A");
        }

        double totalHeight = 0;
        double maxPrice = -1;
        String maxPricePlant = "";
        int minWateringDays = Integer.MAX_VALUE;
        String minWateringPlant = "";

        for (final Plant p : plants) {
            totalHeight += p.getHeightCm();

            if (p.getPrice() > maxPrice) {
                maxPrice = p.getPrice();
                maxPricePlant = p.getSpecies() + " \"" + p.getName() + "\"";
            }

            if (p.getWateringDays() < minWateringDays) {
                minWateringDays = p.getWateringDays();
                minWateringPlant = p.getSpecies() + " \"" + p.getName() + "\"";
            }
        }

        final double avgHeight = totalHeight / plants.size();
        return new HeightWatering(plants.size(), avgHeight, maxPrice, maxPricePlant, minWateringDays, minWateringPlant);
    }

    public static String generateReport(final HeightWatering summary, final List<String> errors) {
        if (summary == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("=== ЗВІТ РОЗСАДНИКА РОСЛИН ===\n");
        sb.append(String.format(Locale.ROOT, "Опрацьовано записів: %d\n", summary.validCount()));
        sb.append(String.format(Locale.ROOT, "Середня висота рослин: %.2f см\n", summary.averageHeight()));
        sb.append(String.format(Locale.ROOT, "Найдорожча рослина: %s (%.2f грн)\n", summary.maxPricePlant(), summary.maxPrice()));
        sb.append(String.format(Locale.ROOT, "Найчастіший полив: %s (кожні %d дн.)\n", summary.minWateringPlant(), summary.minWateringDays()));

        if (errors != null && !errors.isEmpty()) {
            sb.append("\nВиявлені помилки (").append(errors.size()).append("):\n");
            for (final String err : errors) {
                sb.append("- ").append(err).append("\n");
            }
        }
        return sb.toString();
    }
}