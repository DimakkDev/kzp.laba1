package ua.lpnu.kzp;

/**
 * Перелічення (enum) для категорій рослин у розсаднику.
 */
public enum PlantKind {
    /** Категорія "Дерево" */
    TREE("Дерево"),
    
    /** Категорія "Квітка" */
    FLOWER("Квітка");

    private final String displayName;

    /**
     * Конструктор для елементу Enum PlantKind.
     *
     * @param displayName відображувана назва категорії
     */
    PlantKind(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Повертає читабельну назву категорії українською мовою.
     *
     * @return назва категорії
     */
    public String getDisplayName() {
        return displayName;
    }
}