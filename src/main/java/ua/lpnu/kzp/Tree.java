package ua.lpnu.kzp;

import java.util.Objects;

/**
 * Клас, що представляє дерево в розсаднику.
 */
public class Tree extends Plant {
    private final double heightInMeters;

    /**
     * Конструктор для Tree.
     *
     * @param id ідентифікатор
     * @param name назва
     * @param ageInYears вік у роках
     * @param heightInMeters висота в метрах
     * @throws IllegalArgumentException якщо висота <= 0
     */
    public Tree(String id, String name, double ageInYears, double heightInMeters) {
        super(id, name, ageInYears, PlantKind.TREE);
        if (heightInMeters <= 0) {
            throw new IllegalArgumentException("Висота дерева повинна бути більшою за 0.");
        }
        this.heightInMeters = heightInMeters;
    }

    /**
     * Перевизначений поліморфний метод розрахунку догляду для дерева.
     *
     * @return години догляду на місяць
     */
    @Override
    public double calculateCareRequirement() {
        return 2.0 + (heightInMeters * 0.5) + (getAgeInYears() * 0.1);
    }

    public double getHeightInMeters() {
        return heightInMeters;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) return false;
        Tree tree = (Tree) o;
        return Double.compare(tree.heightInMeters, heightInMeters) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), heightInMeters);
    }
}