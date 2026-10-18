package ua.lpnu.kzp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StreamQueriesTest {

    private List<Plant> samplePlants;

    @BeforeEach
    void setUp() {
        samplePlants = new ArrayList<>();
        samplePlants.add(new Tree("T1", "Дуб", 10.0, 5.0, 1000.0, 3));
        samplePlants.add(new Tree("T2", "Сосна", 5.0, 3.0, 600.0, 2));
        samplePlants.add(new Flower("F1", "Троянда", 1.0, true, 0.8, 150.0, 1));
        samplePlants.add(new Flower("F2", "Ромашка", 0.5, false, 0.3, 50.0, 2));
        samplePlants.add(new Flower("F3", "Орхідея", 2.0, true, 0.5, 300.0, 5));
    }

    @Test
    @DisplayName("Запит 1: Фільтрація за інтервалом поливу <= 2 дні")
    void testFilterByMaxWateringInterval() {
        List<Plant> filtered = HeightWatering.filterByMaxWateringInterval(samplePlants, 2);
        assertEquals(3, filtered.size());
        assertTrue(filtered.stream().allMatch(p -> p.getWateringIntervalDays() <= 2));
    }

    @Test
    @DisplayName("Запит 2: Трансформація елементів у список назв (map)")
    void testExtractPlantNames() {
        List<String> names = HeightWatering.extractPlantNames(samplePlants);
        assertEquals(List.of("Дуб", "Сосна", "Троянда", "Ромашка", "Орхідея"), names);
    }

    @Test
    @DisplayName("Запит 3: Групування та обчислення середньої ціни за видом")
    void testCalculateAveragePriceByKind() {
        Map<PlantKind, Double> avgPrices = HeightWatering.calculateAveragePriceByKind(samplePlants);
        
        assertEquals(800.0, avgPrices.get(PlantKind.TREE), 0.001);
        assertEquals(166.666, avgPrices.get(PlantKind.FLOWER), 0.01);
    }

    @Test
    @DisplayName("Запит 4: Зведена статистика висоти рослин")
    void testGetCareHeightStatistics() {
        DoubleSummaryStatistics stats = HeightWatering.getCareHeightStatistics(samplePlants);
        
        assertEquals(5, stats.getCount());
        assertEquals(5.0, stats.getMax(), 0.001);
        assertEquals(0.3, stats.getMin(), 0.001);
        assertEquals(9.6, stats.getSum(), 0.001);
        assertEquals(1.92, stats.getAverage(), 0.001);
    }

    @Test
    @DisplayName("Запит 5: Top-N за висотою спаданням та видом")
    void testGetTopNByHeightAndKind() {
        List<Plant> top3 = HeightWatering.getTopNByHeightAndKind(samplePlants, 3);
        assertEquals(3, top3.size());
        assertEquals("Дуб", top3.get(0).getName());
        assertEquals("Сосна", top3.get(1).getName());
        assertEquals("Троянда", top3.get(2).getName());
    }

    @Test
    @DisplayName("Пошук рослини (Optional) — існуючий і відсутній випадок")
    void testFindByName() {
        Optional<Plant> found = HeightWatering.findByName(samplePlants, "Сосна");
        assertTrue(found.isPresent());
        assertEquals("T2", found.get().getId());

        Optional<Plant> notFound = HeightWatering.findByName(samplePlants, "Кактус");
        assertTrue(notFound.isEmpty());
    }

    @Test
    @DisplayName("Перевірка роботи з порожньою колекцією та null")
    void testEmptyAndNullInput() {
        assertTrue(HeightWatering.filterByMaxWateringInterval(null, 2).isEmpty());
        assertTrue(HeightWatering.extractPlantNames(null).isEmpty());
        assertTrue(HeightWatering.calculateAveragePriceByKind(null).isEmpty());
        assertEquals(0, HeightWatering.getCareHeightStatistics(null).getCount());
        assertTrue(HeightWatering.getTopNByHeightAndKind(null, 5).isEmpty());
        assertTrue(HeightWatering.findByName(null, "Дуб").isEmpty());
    }

    @Test
    @DisplayName("Перевірка згенерованого звіту через Stream API")
    void testGenerateReport() {
        String report = HeightWatering.generateReport(samplePlants);
        assertTrue(report.contains("=== ЗВІТ РОЗСАДНИКА РОСЛИН ==="));
        assertTrue(report.contains("Загальна потреба в догляді:"));
    }
}