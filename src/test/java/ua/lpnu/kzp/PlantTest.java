package ua.lpnu.kzp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    @Test
    @DisplayName("Перевірка валідації входів у базовому класі")
    void testPlantValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Tree("", "Дуб", 2.0, 3.0));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "   ", 2.0, 3.0));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "Дуб", -1.0, 3.0));
    }

    @Test
    @DisplayName("Перевірка enum PlantKind")
    void testPlantKind() {
        assertEquals("Дерево", PlantKind.TREE.getDisplayName());
        assertEquals("Квітка", PlantKind.FLOWER.getDisplayName());
    }

    @Test
    @DisplayName("Перевірка узгодженості equals та hashCode у HashSet")
    void testEqualsAndHashCodeInHashSet() {
        Set<Plant> set = new HashSet<>();
        Tree tree1 = new Tree("T1", "Дуб", 5.0, 4.0);
        Tree tree2 = new Tree("T1", "Дуб", 5.0, 4.0);
        Flower flower = new Flower("F1", "Троянда", 1.0, true);

        set.add(tree1);
        set.add(tree2); // Дублікат, не повинен додатися
        set.add(flower);

        assertEquals(2, set.size());
        assertEquals(tree1, tree2);
        assertEquals(tree1.hashCode(), tree2.hashCode());
    }

    @Test
    @DisplayName("Перевірка поліморфної обробки колекції без if/switch")
    void testPolymorphicCareCalculation() {
        List<Plant> plants = new ArrayList<>();
        plants.add(new Tree("T1", "Дуб", 5.0, 4.0));         // 2.0 + 2.0 + 0.5 = 4.5
        plants.add(new Flower("F1", "Ромашка", 1.0, false)); // 1.0
        plants.add(new Flower("F2", "Орхідея", 1.0, true));   // 1.0 + 1.5 = 2.5

        double total = HeightWatering.calculateTotalCare(plants);
        assertEquals(8.0, total, 0.001);
    }
}