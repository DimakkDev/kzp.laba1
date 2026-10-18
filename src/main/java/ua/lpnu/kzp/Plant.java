package ua.lpnu.kzp;

import java.util.Objects;

/**
 * Абстрактний базовий клас, що визначає спільні поля та інваріанти рослини.
 */
public abstract class Plant {
    private final String id;
    private final String name;
    private final double ageInYears;
    private final PlantKind kind;

    /**
     * Конструктор для ініціалізації спільних полів рослини.
     *
     * @param id унікальний ідентифікатор рослини
     * @param name назва рослини
     * @param ageInYears вік у роках
     * @param kind категорія рослини (PlantKind)
     * @throws IllegalArgumentException якщо id чи name порожні, або вік від'ємний
     */
    public Plant(String id, String name, double ageInYears, PlantKind kind) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ідентифікатор рослини не може бути порожнім.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва рослини не може бути порожньою.");
        }
        if (ageInYears < 0) {
            throw new IllegalArgumentException("Вік рослини не може бути від'ємним.");
        }
        this.id = id;
        this.name = name;
        this.ageInYears = ageInYears;
        this.kind = Objects.requireNonNull(kind, "Категорія рослини не може бути null.");
    }

    /**
     * Поліморфний метод обчислення потреби в догляді (у годинах/місяць).
     *
     * @return час догляду в годинах на місяць
     */
    public abstract double calculateCareRequirement();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAgeInYears() {
        return ageInYears;
    }

    public PlantKind getKind() {
        return kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Double.compare(plant.ageInYears, ageInYears) == 0 &&
                Objects.equals(id, plant.id) &&
                Objects.equals(name, plant.name) &&
                kind == plant.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ageInYears, kind);
    }

    @Override
    public String toString() {
        return String.format("%s [ID: %s, Назва: %s, Вік: %.1f р., Вид: %s]",
                getClass().getSimpleName(), id, name, ageInYears, kind.getDisplayName());
    }
}