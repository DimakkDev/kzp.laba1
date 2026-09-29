package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    @Test
    void validPlantCreationAndGetters() {
        Plant plant = new Plant("Фікус", "Бенджаміна", 45.5, 350.0, 5);
        assertEquals("Фікус", plant.getSpecies());
        assertEquals("Бенджаміна", plant.getName());
        assertEquals(45.5, plant.getHeightCm(), 0.001);
        assertEquals(350.0, plant.getPrice(), 0.001);
        assertEquals(5, plant.getWateringDays());
    }

    @Test
    void fromCsvParsesValidLine() {
        Plant plant = Plant.fromCsv("Монстера;Деліціоза;120.0;850.0;7");
        assertEquals("Монстера", plant.getSpecies());
        assertEquals(120.0, plant.getHeightCm(), 0.001);
    }

    @Test
    void constructorRejectsNegativePrice() {
        assertThrows(IllegalArgumentException.class, () ->
            new Plant("Фікус", "Бенджаміна", 45.5, -100.0, 5)
        );
    }

    @Test
    void constructorRejectsZeroWateringDays() {
        assertThrows(IllegalArgumentException.class, () ->
            new Plant("Фікус", "Бенджаміна", 45.5, 350.0, 0)
        );
    }

    @Test
    void fromCsvRejectsInvalidFieldsCount() {
        assertThrows(IllegalArgumentException.class, () ->
            Plant.fromCsv("Фікус;Бенджаміна;45.5;350.0")
        );
    }

    @Test
    void fromCsvRejectsInvalidNumberFormat() {
        assertThrows(IllegalArgumentException.class, () ->
            Plant.fromCsv("Фікус;Бенджаміна;ABC;350.0;5")
        );
    }

    @Test
    void recordEqualityCheck() {
        HeightWatering hw1 = new HeightWatering(2, 50.0, 300.0, "Фікус", 3, "Кактус");
        HeightWatering hw2 = new HeightWatering(2, 50.0, 300.0, "Фікус", 3, "Кактус");
        assertEquals(hw1, hw2);
    }
}