package ua.lpnu.kzp;

import java.util.Objects;

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