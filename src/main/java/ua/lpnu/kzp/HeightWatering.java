package ua.lpnu.kzp;

import java.util.List;

/**
 * Утилітарний клас для обробки колекцій рослин та формування звітів.
 */
public class HeightWatering {

    /**
     * Поліморфно підраховує загальну потребу в догляді для списку рослин.
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
     * Формує текстовий звіт у зовнішньому форматі.
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
            for (Plant plant : plants) {
                // Чистий поліморфний виклик без if/switch
                sb.append(String.format("%-10s %-15s %-10s %-15.2f\n",
                        plant.getId(),
                        plant.getName(),
                        plant.getKind().getDisplayName(),
                        plant.calculateCareRequirement()));
            }
        }

        sb.append("--------------------------------------------------\n");
        sb.append(String.format("Загальна потреба в догляді: %.2f год/міс\n", calculateTotalCare(plants)));
        return sb.toString();
    }
}