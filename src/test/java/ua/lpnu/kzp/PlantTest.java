package ua.lpnu.kzp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    @Test
    @DisplayName("Перевірка валідації входів у базовому класі")
    void testPlantValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Tree("", "Дуб", 2.0, 3.0, 100.0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "   ", 2.0, 3.0, 100.0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "Дуб", -1.0, 3.0, 100.0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "Дуб", 2.0, 3.0, -10.0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "Дуб", 2.0, 3.0, 100.0, 0));
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
        Tree tree1 = new Tree("T1", "Дуб", 5.0, 4.0, 500.0, 3);
        Tree tree2 = new Tree("T1", "Дуб", 5.0, 4.0, 500.0, 3);
        Flower flower = new Flower("F1", "Троянда", 1.0, true, 0.5, 100.0, 1);

        set.add(tree1);
        set.add(tree2);
        set.add(flower);

        assertEquals(2, set.size());
        assertEquals(tree1, tree2);
        assertEquals(tree1.hashCode(), tree2.hashCode());
    }
}