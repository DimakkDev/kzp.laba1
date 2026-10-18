package ua.lpnu.kzp;

import java.util.Objects;

/**
 * Клас, що представляє квітку в розсаднику.
 */
public class Flower extends Plant {
    private final boolean requiresGreenhouse;

    /**
     * Конструктор для Flower.
     *
     * @param id ідентифікатор
     * @param name назва
     * @param ageInYears вік у роках
     * @param requiresGreenhouse прапорець потреби в тепличному догляді
     */
    public Flower(String id, String name, double ageInYears, boolean requiresGreenhouse) {
        super(id, name, ageInYears, PlantKind.FLOWER);
        this.requiresGreenhouse = requiresGreenhouse;
    }

    /**
     * Перевизначений поліморфний метод розрахунку догляду для квітки.
     *
     * @return години догляду на місяць
     */
    @Override
    public double calculateCareRequirement() {
        return 1.0 + (requiresGreenhouse ? 1.5 : 0.0);
    }

    public boolean isRequiresGreenhouse() {
        return requiresGreenhouse;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Flower flower = (Flower) o;
        return requiresGreenhouse == flower.requiresGreenhouse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requiresGreenhouse);
    }
}