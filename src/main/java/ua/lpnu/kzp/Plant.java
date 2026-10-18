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
    private final double price;
    private final int wateringIntervalDays;

    /**
     * Конструктор для ініціалізації спільних полів рослини.
     *
     * @param id                   унікальний ідентифікатор рослини
     * @param name                 назва рослини
     * @param ageInYears           вік у роках
     * @param kind                 категорія рослини (PlantKind)
     * @param price                ціна рослини
     * @param wateringIntervalDays періодичність поливу в днях
     * @throws IllegalArgumentException якщо входження невалідні
     */
    public Plant(String id, String name, double ageInYears, PlantKind kind, double price, int wateringIntervalDays) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ідентифікатор рослини не може бути порожнім.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Назва рослини не може бути порожньою.");
        }
        if (ageInYears < 0) {
            throw new IllegalArgumentException("Вік рослини не може бути від'ємним.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною.");
        }
        if (wateringIntervalDays <= 0) {
            throw new IllegalArgumentException("Періодичність поливу повинна бути більше 0.");
        }
        this.id = id;
        this.name = name;
        this.ageInYears = ageInYears;
        this.kind = Objects.requireNonNull(kind, "Категорія рослини не може бути null.");
        this.price = price;
        this.wateringIntervalDays = wateringIntervalDays;
    }

    /**
     * Поліморфний метод обчислення потреби в догляді (у годинах/місяць).
     *
     * @return час догляду в годинах на місяць
     */
    public abstract double calculateCareRequirement();

    /**
     * Поліморфний метод повернення висоти рослини у метрах.
     *
     * @return висота в метрах
     */
    public abstract double getHeightInMeters();

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

    public double getPrice() {
        return price;
    }

    public int getWateringIntervalDays() {
        return wateringIntervalDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Double.compare(plant.ageInYears, ageInYears) == 0 &&
                Double.compare(plant.price, price) == 0 &&
                wateringIntervalDays == plant.wateringIntervalDays &&
                Objects.equals(id, plant.id) &&
                Objects.equals(name, plant.name) &&
                kind == plant.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ageInYears, kind, price, wateringIntervalDays);
    }

    @Override
    public String toString() {
        return String.format("%s [ID: %s, Назва: %s, Вік: %.1f р., Вид: %s, Ціна: %.2f грн, Полив: кожні %d дн.]",
                getClass().getSimpleName(), id, name, ageInYears, kind.getDisplayName(), price, wateringIntervalDays);
    }
}