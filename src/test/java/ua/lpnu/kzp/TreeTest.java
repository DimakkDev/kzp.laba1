package ua.lpnu.kzp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    @DisplayName("Перевірка розрахунку догляду для дерева")
    void testTreeCareRequirement() {
        Tree tree = new Tree("T1", "Сосна", 3.0, 2.5);
        // Formula: 2.0 + (2.5 * 0.5) + (3.0 * 0.1) = 3.55
        assertEquals(3.55, tree.calculateCareRequirement(), 0.001);
        assertEquals(PlantKind.TREE, tree.getKind());
    }

    @Test
    @DisplayName("Перевірка від'ємної висоти")
    void testInvalidTreeHeight() {
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "Сосна", 2.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Tree("T1", "Сосна", 2.0, -1.0));
    }

    @Test
    @DisplayName("Перевірка рівняння екземплярів Tree")
    void testTreeEquals() {
        Tree tree1 = new Tree("T1", "Дуб", 5.0, 4.0);
        Tree tree2 = new Tree("T1", "Дуб", 5.0, 4.0);
        Tree tree3 = new Tree("T1", "Дуб", 5.0, 4.5);

        assertEquals(tree1, tree2);
        assertNotEquals(tree1, tree3);
    }
}