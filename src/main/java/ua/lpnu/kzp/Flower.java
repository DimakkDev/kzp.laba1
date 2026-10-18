package ua.lpnu.kzp;

import java.util.Objects;

/**
 * Клас, що представляє квітку в розсаднику.
 */
public class Flower extends Plant {
    private final boolean requiresGreenhouse;
    private final double heightInMeters;

    /**
     * Конструктор для Flower.
     *
     * @param id                   ідентифікатор
     * @param name                 назва
     * @param ageInYears           вік у роках
     * @param requiresGreenhouse   потреба в тепличному догляді
     * @param heightInMeters       висота в метрах
     * @param price                ціна
     * @param wateringIntervalDays періодичність поливу в днях
     */
    public Flower(String id, String name, double ageInYears, boolean requiresGreenhouse, double heightInMeters, double price, int wateringIntervalDays) {
        super(id, name, ageInYears, PlantKind.FLOWER, price, wateringIntervalDays);
        if (heightInMeters <= 0) {
            throw new IllegalArgumentException("Висота квітки повинна бути більшою за 0.");
        }
        this.requiresGreenhouse = requiresGreenhouse;
        this.heightInMeters = heightInMeters;
    }

    @Override
    public double calculateCareRequirement() {
        return 1.0 + (requiresGreenhouse ? 1.5 : 0.0);
    }

    @Override
    public double getHeightInMeters() {
        return heightInMeters;
    }

    public boolean isRequiresGreenhouse() {
        return requiresGreenhouse;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Flower flower = (Flower) o;
        return requiresGreenhouse == flower.requiresGreenhouse &&
                Double.compare(flower.heightInMeters, heightInMeters) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requiresGreenhouse, heightInMeters);
    }
}