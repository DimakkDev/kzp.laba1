package ua.lpnu.kzp;

import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Утилітарний клас для обробки колекцій рослин за допомогою Stream API.
 */
public class HeightWatering {

    /**
     * Запит 1 (filter): Відбір рослин з періодичністю поливу не більше заданої кількості днів (Завдання 12: <= 2 дні).
     *
     * @param plants  список рослин
     * @param maxDays максимальний інтервал поливу в днях
     * @return відфільтрований список рослин
     */
    public static List<Plant> filterByMaxWateringInterval(List<Plant> plants, int maxDays) {
        if (plants == null) return Collections.emptyList();
        return plants.stream()
                .filter(p -> p.getWateringIntervalDays() <= maxDays)
                .collect(Collectors.toList());
    }

    /**
     * Запит 2 (map): Перетворення елементів колекції в список їх видів/назв.
     *
     * @param plants список рослин
     * @return список назв рослин
     */
    public static List<String> extractPlantNames(List<Plant> plants) {
        if (plants == null) return Collections.emptyList();
        return plants.stream()
                .map(Plant::getName)
                .collect(Collectors.toList());
    }

    /**
     * Запит 3 (groupingBy): Групування за типом та розрахунок середньої ціни за типом.
     *
     * @param plants список рослин
     * @return мапа із середньою ціною для кожного типу рослин
     */
    public static Map<PlantKind, Double> calculateAveragePriceByKind(List<Plant> plants) {
        if (plants == null) return Collections.emptyMap();
        return plants.stream()
                .collect(Collectors.groupingBy(
                        Plant::getKind,
                        Collectors.averagingDouble(Plant::getPrice)
                ));
    }

    /**
     * Запит 4 (summarizingDouble): Зведена статистика за висотою рослин.
     *
     * @param plants список рослин
     * @return об'єкт статистики за висотою
     */
    public static DoubleSummaryStatistics getCareHeightStatistics(List<Plant> plants) {
        if (plants == null) return new DoubleSummaryStatistics();
        return plants.stream()
                .collect(Collectors.summarizingDouble(Plant::getHeightInMeters));
    }

    /**
     * Запит 5 (sorted + limit): Top-N елементів за висотою (за спаданням) та видом (драйвером складеного компаратора).
     *
     * @param plants список рослин
     * @param limit  кількість елементів для вибірки (наприклад, top-5)
     * @return відсортований список top-N рослин
     */
    public static List<Plant> getTopNByHeightAndKind(List<Plant> plants, int limit) {
        if (plants == null) return Collections.emptyList();

        Comparator<Plant> heightDescendingThenKind = Comparator
                .comparing(Plant::getHeightInMeters, Comparator.reverseOrder())
                .thenComparing(p -> p.getKind().getDisplayName());

        return plants.stream()
                .sorted(heightDescendingThenKind)
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Окремий метод пошуку за назвою, що повертає Optional.
     *
     * @param plants список рослин
     * @param name   назва для пошуку
     * @return Optional з знайденою рослиною або порожній Optional
     */
    public static Optional<Plant> findByName(List<Plant> plants, String name) {
        if (plants == null || name == null) return Optional.empty();
        return plants.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name.trim()))
                .findFirst();
    }

    /**
     * Поліморфно підраховує загальну потребу в догляді за допомогою Stream API.
     *
     * @param plants список об'єктів підтипів Plant
     * @return сумарні години догляду
     */
    public static double calculateTotalCare(List<Plant> plants) {
        if (plants == null) return 0.0;
        return plants.stream()
                .mapToDouble(Plant::calculateCareRequirement)
                .sum();
    }

    /**
     * Формує текстовий звіт у зовнішньому форматі через потокову обробку.
     *
     * @param plants список рослин
     * @return форматований рядок звіту
     */
    public static String generateReport(List<Plant> plants) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ЗВІТ РОЗСАДНИКА РОСЛИН ===\n");
        sb.append(String.format("%-10s %-15s %-10s %-15s\n", "ID", "Назва", "Вид", "Потреба (год)"));
        sb.append("--------------------------------------------------\n");

        if (plants != null) {
            plants.stream()
                    .map(plant -> String.format("%-10s %-15s %-10s %-15.2f\n",
                            plant.getId(),
                            plant.getName(),
                            plant.getKind().getDisplayName(),
                            plant.calculateCareRequirement()))
                    .forEach(sb::append);
        }

        sb.append("--------------------------------------------------\n");
        sb.append(String.format("Загальна потреба в догляді: %.2f год/міс\n", calculateTotalCare(plants)));
        return sb.toString();
    }
}