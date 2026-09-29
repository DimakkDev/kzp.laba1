package ua.lpnu.kzp;

import java.util.Locale;
import java.util.Objects;

/**
 * Описує сутність рослини з розсадника.
 */
public final class Plant {
    private final String species;
    private final String name;
    private final double heightCm;
    private final double price;
    private final int wateringDays;

    public Plant(final String species, final String name, final double heightCm, final double price, final int wateringDays) {
        this.species = Objects.requireNonNull(species, "Вид не може бути null");
        if (this.species.isBlank()) {
            throw new IllegalArgumentException("Вид не може бути порожнім");
        }

        this.name = Objects.requireNonNull(name, "Назва не може бути null");
        if (this.name.isBlank()) {
            throw new IllegalArgumentException("Назва не може бути порожньою");
        }

        if (heightCm <= 0 || !Double.isFinite(heightCm)) {
            throw new IllegalArgumentException("Висота має бути додатним скінченним числом");
        }

        if (price < 0 || !Double.isFinite(price)) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }

        if (wateringDays <= 0) {
            throw new IllegalArgumentException("Інтервал поливу має бути більшим за 0");
        }

        this.heightCm = heightCm;
        this.price = price;
        this.wateringDays = wateringDays;
    }

    public static Plant fromCsv(final String line) {
        final String inputLine = Objects.requireNonNull(line, "Рядок не може бути null");
        final String[] fields = inputLine.split(";", -1);

        if (fields.length != 5) {
            throw new IllegalArgumentException("Очікується 5 полів, отримано: " + fields.length);
        }

        try {
            return new Plant(
                fields[0].trim(),
                fields[1].trim(),
                Double.parseDouble(fields[2].trim()),
                Double.parseDouble(fields[3].trim()),
                Integer.parseInt(fields[4].trim())
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Числове поле має помилковий формат", e);
        }
    }

    public String getSpecies() { return species; }
    public String getName() { return name; }
    public double getHeightCm() { return heightCm; }
    public double getPrice() { return price; }
    public int getWateringDays() { return wateringDays; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return Double.compare(plant.heightCm, heightCm) == 0 &&
                Double.compare(plant.price, price) == 0 &&
                wateringDays == plant.wateringDays &&
                Objects.equals(species, plant.species) &&
                Objects.equals(name, plant.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, name, heightCm, price, wateringDays);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s \"%s\": %.1f см, %.2f грн, полив кожні %d дн.",
                species, name, heightCm, price, wateringDays);
    }
}