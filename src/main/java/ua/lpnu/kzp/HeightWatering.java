package ua.lpnu.kzp;

import java.util.Objects;

/**
 * Record для збереження підсумкових аналітичних показників розсадника.
 *
 * @param validCount кількість коректних записів
 * @param averageHeight середня висота
 * @param maxPrice найвища ціна
 * @param maxPricePlant назва найдорожчої рослини
 * @param minWateringDays найменший інтервал поливу
 * @param minWateringPlant назва рослини з найчастішим поливом
 */
public record HeightWatering(
    int validCount,
    double averageHeight,
    double maxPrice,
    String maxPricePlant,
    int minWateringDays,
    String minWateringPlant
) {
    public HeightWatering {
        if (validCount < 0 || averageHeight < 0 || maxPrice < 0 || minWateringDays < 0) {
            throw new IllegalArgumentException("Показники не можуть бути від'ємними");
        }
        maxPricePlant = Objects.requireNonNullElse(maxPricePlant, "N/A");
        minWateringPlant = Objects.requireNonNullElse(minWateringPlant, "N/A");
    }
}