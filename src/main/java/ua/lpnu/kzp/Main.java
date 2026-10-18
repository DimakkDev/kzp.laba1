package ua.lpnu.kzp;

import java.util.ArrayList;
import java.util.List;

/**
 * Головний клас додатка для демонстрації роботи з поліморфною колекцією та Stream API.
 */
public class Main {
    /**
     * Точка входу в програму.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        List<Plant> nurseryList = new ArrayList<>();

        nurseryList.add(new Tree("T-101", "Дуб Черешчатий", 5.0, 4.2, 1200.0, 3));
        nurseryList.add(new Tree("T-102", "Сосна Звичайна", 3.0, 2.5, 800.0, 2));
        nurseryList.add(new Flower("F-201", "Троянда Гранд", 1.0, true, 0.8, 150.0, 1));
        nurseryList.add(new Flower("F-202", "Ромашка Польова", 0.5, false, 0.4, 40.0, 2));
        nurseryList.add(new Flower("F-203", "Орхідея Фаленопсис", 1.5, true, 0.5, 350.0, 5));

        // Виведення контрольного звіту
        String report = HeightWatering.generateReport(nurseryList);
        System.out.println(report);

        // Демонстрація виконання 5 запитів Stream API
        System.out.println("=== 1. Фільтр (полив <= 2 дні) ===");
        HeightWatering.filterByMaxWateringInterval(nurseryList, 2).forEach(System.out::println);

        System.out.println("\n=== 2. Map (Список назв) ===");
        System.out.println(HeightWatering.extractPlantNames(nurseryList));

        System.out.println("\n=== 3. GroupingBy (Середня ціна за типом) ===");
        HeightWatering.calculateAveragePriceByKind(nurseryList)
                .forEach((kind, price) -> System.out.printf("%s: %.2f грн\n", kind.getDisplayName(), price));

        System.out.println("\n=== 4. Statistics (Висота) ===");
        System.out.println(HeightWatering.getCareHeightStatistics(nurseryList));

        System.out.println("\n=== 5. Top-5 (За висотою та видом) ===");
        HeightWatering.getTopNByHeightAndKind(nurseryList, 5).forEach(System.out::println);

        System.out.println("\n=== Пошук Optional ===");
        HeightWatering.findByName(nurseryList, "Сосна Звичайна")
                .ifPresentOrElse(
                        p -> System.out.println("Знайдено: " + p),
                        () -> System.out.println("Рослину не знайдено")
                );
    }
}