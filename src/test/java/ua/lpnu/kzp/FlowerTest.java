package ua.lpnu.kzp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowerTest {

    @Test
    @DisplayName("Перевірка розрахунку догляду для квітки")
    void testFlowerCareRequirement() {
        Flower outdoor = new Flower("F1", "Чорнобривець", 0.5, false);
        Flower greenhouse = new Flower("F2", "Антуріум", 1.0, true);

        assertEquals(1.0, outdoor.calculateCareRequirement(), 0.001);
        assertEquals(2.5, greenhouse.calculateCareRequirement(), 0.001);
        assertEquals(PlantKind.FLOWER, outdoor.getKind());
    }

    @Test
    @DisplayName("Перевірка рівняння екземплярів Flower")
    void testFlowerEquals() {
        Flower f1 = new Flower("F1", "Троянда", 1.0, true);
        Flower f2 = new Flower("F1", "Троянда", 1.0, true);
        Flower f3 = new Flower("F1", "Троянда", 1.0, false);

        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
    }
}