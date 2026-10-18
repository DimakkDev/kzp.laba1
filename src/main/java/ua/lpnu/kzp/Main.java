package ua.lpnu.kzp;

import java.util.ArrayList;
import java.util.List;

/**
 * Головний клас додатка для демонстрації роботи з поліморфною колекцією.
 */
public class Main {
    /**
     * Точка входу в програму.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        List<Plant> nurseryList = new ArrayList<>();

        // Зберігаємо різнорідні об'єкти підтипів у колекції спільного типу List<Plant>
        nurseryList.add(new Tree("T-101", "Дуб Черешчатий", 5.0, 4.2));
        nurseryList.add(new Tree("T-102", "Сосна Звичайна", 3.0, 2.5));
        nurseryList.add(new Flower("F-201", "Троянда Гранд", 1.0, true));
        nurseryList.add(new Flower("F-202", "Ромашка Польова", 0.5, false));

        // Виведення поліморфно згенерованого звіту
        String report = HeightWatering.generateReport(nurseryList);
        System.out.println(report);
    }
}