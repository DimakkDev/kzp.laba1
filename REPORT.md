# Звіт про виконання лабораторної роботи №4
**Тема:** Потокова обробка даних за допомогою Stream API  
**Предметна область:** Розсадник рослин (Варіант 12)

## 1. Порівняння циклової реалізації та Stream API

| Операція / Запит | Циклова реалізація | Stream API реалізація |
| :--- | :--- | :--- |
| **1. Фільтрація (`filter`)** | Ручний цикл `for` із перевіркою умови `if` та складанням нового `List` | `plants.stream().filter(p -> p.getWateringIntervalDays() <= 2).toList()` |
| **2. Перетворення (`map`)** | Ручний цикл `for` із витягуванням `getName()` у новий список | `plants.stream().map(Plant::getName).toList()` |
| **3. Групування (`groupingBy`)** | Ручне наповнення `Map<PlantKind, List<Double>>` з підрахунком середнього | `plants.stream().collect(groupingBy(Plant::getKind, averagingDouble(Plant::getPrice)))` |
| **4. Статистика (`summarizingDouble`)** | Ручні змінні `min`, `max`, `sum`, `count` та цикл для обчислення | `plants.stream().collect(summarizingDouble(Plant::getHeightInMeters))` |
| **5. Top-N (`sorted` + `limit`)** | Повне сортування списку методом `Collections.sort` та ручний зріз `subList` | `plants.stream().sorted(cmp).limit(limit).toList()` |
| **6. Пошук (`Optional`)** | Цикл `for` із поверненням `null` при відсутності значення | `plants.stream().filter(...).findFirst()` (повертає `Optional<Plant>`) |

## 2. Результати порівняння та контрольний звіт
Контрольний звіт зберіг формат та повністю еквівалентний за показниками попередній реалізації. Використання Stream API дозволило повністю позбутися ручних циклів `for/while` та операторів `if` за конкретними підтипами.