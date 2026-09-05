# 🤖 План автоматизации (Flutter + Flame)

Стратегия и примеры автоматизированных тестов для пазл-игры на Flutter + Flame. Тесты выполняются в рамках Flutter-проекта на Dart и не входят в Java-портфолио — данный документ описывает подход, структуру и примеры кода.

---

## 1. Уровни тестирования

| Уровень | Тип | Что проверяет | Инструмент | Скорость |
| :--- | :--- | :--- | :--- | :--- |
| Unit | Widget test | Логику игровых компонентов в изоляции | `flutter test` | Быстро (<1 с) |
| Visual | Golden test | Визуальную регрессию UI и игровых сцен | `matchesGoldenFile` | Средне (1–3 с) |
| E2E | Integration test | Сквозные сценарии игрового цикла | `integration_test` | Медленно (10–30 с) |
| Performance | Benchmark | FPS, время отрисовки кадра | `integration_test` + `benchmark` | Медленно |

---

## 2. Структура тестов в проекте

```dart
game/
├── lib/
│   ├── game/
│   │   ├── puzzle_game.dart          # FlameGame — главный класс
│   │   ├── components/
│   │   │   ├── puzzle_piece.dart      # Component + Draggable
│   │   │   ├── puzzle_board.dart      # Component — игровое поле
│   │   │   └── score_manager.dart     # Логика подсчёта очков (чистый Dart)
│   ├── models/
│   │   ├── level.dart                 # Конфигурация уровня
│   │   └── game_state.dart            # Состояние игры
│   └── ui/
│       └── overlays/                  # Overlay-экраны (победа, поражение)
│
├── test/
│   ├── game/
│   │   ├── score_manager_test.dart    # Unit: логика очков
│   │   ├── puzzle_piece_test.dart     # Unit: компонент пазл-элемента
│   │   └── game_state_test.dart       # Unit: состояние игры
│   ├── golden/
│   │   ├── main_menu_golden_test.dart # Golden: главное меню
│   │   └── game_screen_golden_test.dart # Golden: игровое поле
│   └── widget/
│       └── overlay_test.dart          # Widget: экраны победы/поражения
│
├── integration_test/
│   ├── game_loop_test.dart            # E2E: полный игровой цикл
│   └── performance_test.dart          # Benchmark: FPS и отрисовка
│
└── test/
    └── golden/
        └── golden_files/              # Эталонные скриншоты (.png)
```

---

## 3. Unit-тесты: логика компонентов

### 3.1. Логика подсчёта очков

Самый высокий приоритет автоматизации — чистая логика без UI-зависимостей.

```dart
// lib/game/components/score_manager.dart

class ScoreManager {
  final int baseScore;
  final int movePenalty;
  final int maxMoves;

  int _moves = 0;
  int get moves => _moves;
  int get score {
    final calculated = baseScore - (_moves * movePenalty);
    return calculated < 0 ? 0 : calculated;
  }
  int get stars {
    if (_moves <= maxMoves ~/ 2) return 3;
    if (_moves <= maxMoves * 3 ~/ 4) return 2;
    return 1;
  }
  bool get isOutOfMoves => _moves >= maxMoves;

  ScoreManager({
    required this.baseScore,
    required this.movePenalty,
    required this.maxMoves,
  });

  void registerMove() => _moves++;
  void reset() => _moves = 0;
}
```

```dart
// test/game/score_manager_test.dart

import 'package:flutter_test/flutter_test.dart';
import 'package:game/game/components/score_manager.dart';

void main() {
  late ScoreManager scoreManager;

  setUp(() {
    scoreManager = ScoreManager(baseScore: 1000, movePenalty: 50, maxMoves: 20);
  });

  group('Score calculation', () {
    test('initial score equals base score', () {
      expect(scoreManager.score, 1000);
    });

    test('score decreases by movePenalty per move', () {
      scoreManager.registerMove();
      scoreManager.registerMove();
      expect(scoreManager.score, 900);
    });

    test('score never goes below zero', () {
      for (int i = 0; i < 30; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.score, 0);
    });

    test('moves counter increments correctly', () {
      for (int i = 0; i < 5; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.moves, 5);
    });
  });

  group('Stars calculation', () {
    test('3 stars when moves <= 50% of max', () {
      for (int i = 0; i < 10; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.stars, 3);
    });

    test('2 stars when moves <= 75% of max', () {
      for (int i = 0; i < 15; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.stars, 2);
    });

    test('1 star when moves > 75% of max', () {
      for (int i = 0; i < 18; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.stars, 1);
    });
  });

  group('Out of moves', () {
    test('isOutOfMoves is false when moves < maxMoves', () {
      for (int i = 0; i < 15; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.isOutOfMoves, isFalse);
    });

    test('isOutOfMoves is true when moves >= maxMoves', () {
      for (int i = 0; i < 20; i++) {
        scoreManager.registerMove();
      }
      expect(scoreManager.isOutOfMoves, isTrue);
    });
  });

  group('Reset', () {
    test('reset returns moves and score to initial state', () {
      for (int i = 0; i < 10; i++) {
        scoreManager.registerMove();
      }
      scoreManager.reset();
      expect(scoreManager.moves, 0);
      expect(scoreManager.score, 1000);
    });
  });
}
```

### 3.2. Состояние игры

```dart
// lib/models/game_state.dart

enum GameStatus { playing, won, lost, paused }

class GameState {
  GameStatus status = GameStatus.playing;
  final Set<int> completedLevels = {};
  int currentLevel = 1;

  bool get isLevelUnlocked => completedLevels.contains(currentLevel - 1);
  bool get isPlaying => status == GameStatus.playing;
  bool get isFinished => status == GameStatus.won || status == GameStatus.lost;

  void completeLevel(int level) {
    completedLevels.add(level);
    status = GameStatus.won;
  }

  void lose() => status = GameStatus.lost;
  void pause() => status = GameStatus.paused;
  void resume() {
    if (status == GameStatus.paused) status = GameStatus.playing;
  }
  void startLevel(int level) {
    currentLevel = level;
    status = GameStatus.playing;
  }
}
```

```dart
// test/game/game_state_test.dart

import 'package:flutter_test/flutter_test.dart';
import 'package:game/models/game_state.dart';

void main() {
  late GameState state;

  setUp(() => state = GameState());

  group('Level progression', () {
    test('level 1 is unlocked by default', () {
      state.currentLevel = 1;
      expect(state.isLevelUnlocked, isTrue);
    });

    test('level 2 is locked until level 1 is completed', () {
      state.currentLevel = 2;
      expect(state.isLevelUnlocked, isFalse);
    });

    test('completing level 1 unlocks level 2', () {
      state.completeLevel(1);
      state.currentLevel = 2;
      expect(state.isLevelUnlocked, isTrue);
    });

    test('completing a level sets status to won', () {
      state.completeLevel(1);
      expect(state.status, GameStatus.won);
    });
  });

  group('Game status transitions', () {
    test('lose sets status to lost', () {
      state.lose();
      expect(state.status, GameStatus.lost);
      expect(state.isFinished, isTrue);
    });

    test('pause and resume cycle correctly', () {
      state.pause();
      expect(state.status, GameStatus.paused);
      expect(state.isPlaying, isFalse);
      state.resume();
      expect(state.isPlaying, isTrue);
    });

    test('resume does nothing if not paused', () {
      state.startLevel(1);
      state.resume();
      expect(state.status, GameStatus.playing);
    });

    test('startLevel resets status to playing', () {
      state.lose();
      state.startLevel(3);
      expect(state.status, GameStatus.playing);
      expect(state.currentLevel, 3);
    });
  });
}
```

### 3.3. Компонент пазл-элемента (с FlameGameTest)

```dart
// test/game/puzzle_piece_test.dart

import 'package:flutter_test/flutter_test.dart';
import 'package:flame_test/flame_test.dart';
import 'package:game/game/puzzle_game.dart';
import 'package:game/game/components/puzzle_piece.dart';

void main() {
  final gameTester = FlameTester(PuzzleGame.new);

  gameTester.testGame('puzzle piece initializes at correct position', (game) async {
    final piece = PuzzlePiece(
      id: 1,
      gridX: 2,
      gridY: 3,
      cellSize: 64.0,
    );
    await game.ensureAdd(piece);
    expect(piece.position.x, 2 * 64.0);
    expect(piece.position.y, 3 * 64.0);
  });

  gameTester.testGame('puzzle piece can be moved to new grid position', (game) async {
    final piece = PuzzlePiece(id: 1, gridX: 0, gridY: 0, cellSize: 64.0);
    await game.ensureAdd(piece);
    piece.moveTo(gridX: 3, gridY: 4);
    expect(piece.gridX, 3);
    expect(piece.gridY, 4);
  });

  gameTester.testGame('puzzle piece reports correct isAtTarget', (game) async {
    final piece = PuzzlePiece(
      id: 1,
      gridX: 0,
      gridY: 0,
      cellSize: 64.0,
      targetGridX: 2,
      targetGridY: 3,
    );
    await game.ensureAdd(piece);
    expect(piece.isAtTarget, isFalse);
    piece.moveTo(gridX: 2, gridY: 3);
    expect(piece.isAtTarget, isTrue);
  });
}
```

---

## 4. Widget-тесты: экраны и Overlay

```dart
// test/widget/overlay_test.dart

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game/ui/overlays/win_overlay.dart';
import 'package:game/ui/overlays/lose_overlay.dart';

void main() {
  group('WinOverlay', () {
    testWidgets('displays score and stars', (tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: WinOverlay(
            score: 850,
            stars: 2,
            onContinue: () {},
            onRestart: () {},
          ),
        ),
      );

      expect(find.text('850'), findsOneWidget);
      expect(find.text('★★☆'), findsOneWidget);
      expect(find.text('Continue'), findsOneWidget);
      expect(find.text('Restart'), findsOneWidget);
    });

    testWidgets('calls onContinue when Continue button tapped', (tester) async {
      var continueCalled = false;
      await tester.pumpWidget(
        MaterialApp(
          home: WinOverlay(
            score: 1000,
            stars: 3,
            onContinue: () => continueCalled = true,
            onRestart: () {},
          ),
        ),
      );

      await tester.tap(find.text('Continue'));
      expect(continueCalled, isTrue);
    });
  });

  group('LoseOverlay', () {
    testWidgets('displays retry and menu buttons', (tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: LoseOverlay(
            onRetry: () {},
            onMenu: () {},
          ),
        ),
      );

      expect(find.text('Retry'), findsOneWidget);
      expect(find.text('Main Menu'), findsOneWidget);
    });

    testWidgets('calls onRetry when Retry button tapped', (tester) async {
      var retryCalled = false;
      await tester.pumpWidget(
        MaterialApp(
          home: LoseOverlay(
            onRetry: () => retryCalled = true,
            onMenu: () {},
          ),
        ),
      );

      await tester.tap(find.text('Retry'));
      expect(retryCalled, isTrue);
    });
  });
}
```

---

## 5. Golden-тесты: визуальная регрессия

Golden-тесты рендерят UI в фиксированном размере и сравнивают с эталонным PNG. При первом запуске создаются эталоны (`--update-goldens`), при последующих — сравнение.

```dart
// test/golden/game_screen_golden_test.dart

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game/ui/screens/game_screen.dart';
import 'package:game/models/level.dart';

void main() {
  testGoldens('GameScreen renders correctly at 1080p', (tester) async {
    tester.view.physicalSize = const Size(1920, 1080);
    tester.view.devicePixelRatio = 1.0;
    addTearDown(tester.view.resetPhysicalSize);

    await tester.pumpWidget(
      MaterialApp(
        home: GameScreen(
          level: Level(
            id: 1,
            gridSize: 4,
            maxMoves: 15,
            pieces: _mockPieces(),
          ),
        ),
      ),
    );

    await expectLater(
      find.byType(GameScreen),
      matchesGoldenFile('golden_files/game_screen_1080p.png'),
    );
  });

  testGoldens('GameScreen renders correctly at 4:3', (tester) async {
    tester.view.physicalSize = const Size(1024, 768);
    tester.view.devicePixelRatio = 1.0;
    addTearDown(tester.view.resetPhysicalSize);

    await tester.pumpWidget(
      MaterialApp(
        home: GameScreen(
          level: Level(
            id: 1,
            gridSize: 4,
            maxMoves: 15,
            pieces: _mockPieces(),
          ),
        ),
      ),
    );

    await expectLater(
      find.byType(GameScreen),
      matchesGoldenFile('golden_files/game_screen_4_3.png'),
    );
  });

  testGoldens('WinOverlay renders correctly', (tester) async {
    tester.view.physicalSize = const Size(800, 600);
    tester.view.devicePixelRatio = 1.0;
    addTearDown(tester.view.resetPhysicalSize);

    await tester.pumpWidget(
      MaterialApp(
        home: WinOverlay(
          score: 850,
          stars: 2,
          onContinue: () {},
          onRestart: () {},
        ),
      ),
    );

    await expectLater(
      find.byType(WinOverlay),
      matchesGoldenFile('golden_files/win_overlay.png'),
    );
  });
}

List<PuzzlePieceData> _mockPieces() {
  return List.generate(4, (i) => PuzzlePieceData(id: i, gridX: i, gridY: 0));
}
```

### Создание и обновление эталонов

```bash
# Первичная генерация (или после намеренного изменения UI)
flutter test --update-goldens

# Регулярный запуск (сравнение)
flutter test test/golden/
```

### Когда обновлять эталоны

- Изменение дизайна UI (намеренное)
- Обновление Flutter / Flame версии
- Изменение шрифтов

Если Golden-тест падает **без** намеренного изменения UI — это визуальный регрессионный баг.

---

## 6. Integration-тесты: сквозной игровой цикл

```dart
// integration_test/game_loop_test.dart

import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:game/main.dart' as app;

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  group('Game loop E2E', () {
    testWidgets('complete level 1 from launch to win screen', (tester) async {
      app.main();
      await tester.pumpAndSettle();

      // 1. На главном меню — нажать "Play"
      await tester.tap(find.text('Play'));
      await tester.pumpAndSettle();

      // 2. Выбрать уровень 1
      await tester.tap(find.text('Level 1'));
      await tester.pumpAndSettle();

      // 3. Проверить, что игровое поле загрузилось
      expect(find.byKey(const Key('puzzle_board')), findsOneWidget);
      expect(find.text('15'), findsOneWidget); // счётчик ходов

      // 4. Переместить элементы (симуляция drag)
      final pieces = find.byKey(const Key('puzzle_piece'));
      expect(pieces, findsWidgets);

      // Перетаскиваем первый элемент на целевую позицию
      await tester.drag(pieces.first, const Offset(128, 0));
      await tester.pumpAndSettle();

      // Повторяем для остальных элементов (упрощённый сценарий)
      for (int i = 1; i < 4; i++) {
        await tester.drag(find.byKey(Key('puzzle_piece_$i')), const Offset(128, 0));
        await tester.pumpAndSettle();
      }

      // 5. Проверить, что появился экран победы
      expect(find.text('Level Complete'), findsOneWidget);
      expect(find.text('Continue'), findsOneWidget);
    });

    testWidgets('lose condition triggers when out of moves', (tester) async {
      app.main();
      await tester.pumpAndSettle();

      await tester.tap(find.text('Play'));
      await tester.pumpAndSettle();

      // Выбрать уровень с малым лимитом ходов
      await tester.tap(find.text('Level 2'));
      await tester.pumpAndSettle();

      // Сделать максимальное количество неверных ходов
      final piece = find.byKey(const Key('puzzle_piece_0'));
      for (int i = 0; i < 10; i++) {
        await tester.drag(piece, const Offset(-64, 0));
        await tester.drag(piece, const Offset(64, 0));
        await tester.pumpAndSettle();
      }

      // Проверить экран поражения
      expect(find.text('Game Over'), findsOneWidget);
      expect(find.text('Retry'), findsOneWidget);
    });

    testWidgets('restart resets level state', (tester) async {
      app.main();
      await tester.pumpAndSettle();

      await tester.tap(find.text('Play'));
      await tester.pumpAndSettle();
      await tester.tap(find.text('Level 1'));
      await tester.pumpAndSettle();

      // Сделать несколько ходов
      await tester.drag(
        find.byKey(const Key('puzzle_piece_0')),
        const Offset(64, 0),
      );
      await tester.pumpAndSettle();

      // Нажать Restart
      await tester.tap(find.byKey(const Key('restart_button')));
      await tester.pumpAndSettle();

      // Проверить, что счётчик ходов сбросился
      expect(find.text('15'), findsOneWidget);
      // Проверить, что элементы вернулись на исходные позиции
      // (через визуальную проверку или состояние)
    });
  });
}
```

Запуск:

```bash
# На desktop (Windows)
flutter test integration_test/game_loop_test.dart -d windows

# На web
flutter test integration_test/game_loop_test.dart -d chrome
```

---

## 7. Performance-тесты: FPS и отрисовка

```dart
// integration_test/performance_test.dart

import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:game/main.dart' as app;

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('FPS stays above 45 during active gameplay', (tester) async {
    app.main();
    await tester.pumpAndSettle();

    await tester.tap(find.text('Play'));
    await tester.pumpAndSettle();
    await tester.tap(find.text('Level 3'));
    await tester.pumpAndSettle();

    final timeline = await tester.traceAction(
      () async {
        // Симуляция активного геймплея: 10 секунд перетаскиваний
        final piece = find.byKey(const Key('puzzle_piece_0'));
        for (int i = 0; i < 20; i++) {
          await tester.drag(piece, const Offset(64, 64));
          await tester.pump(const Duration(milliseconds: 500));
        }
      },
      reports: [TimelineStream.devTools],
    );

    // Анализ timeline
    final frames = timeline.frames;
    final fpsBelow45 = frames.where((f) => f.buildDuration.inMilliseconds > 22);
    
    // Не более 5% кадров ниже 45 FPS
    expect(
      fpsBelow45.length / frames.length,
      lessThan(0.05),
      reason: 'More than 5% of frames had FPS below 45',
    );
  });
}
```

---

## 8. Приоритеты и дорожная карта

| Этап | Что | Статус | Приоритет |
| :--- | :--- | :--- | :--- |
| 1 | Unit-тесты логики (ScoreManager, GameState) | Запланировано | 🔴 Высокий |
| 2 | Widget-тесты Overlay-экранов | Запланировано | 🔴 Высокий |
| 3 | Flame-тесты компонентов (PuzzlePiece) | Запланировано | 🟡 Средний |
| 4 | Golden-тесты UI (основные разрешения) | Запланировано | 🟡 Средний |
| 5 | Integration-тесты: игровой цикл E2E | Запланировано | 🟡 Средний |
| 6 | Performance-тесты (FPS benchmark) | Запланировано | 🟢 Низкий |
| 7 | Golden-тесты для всех разрешений из матрицы | Запланировано | 🟢 Низкий |

### Принципы приоритизации

1. **Чистая логика первой.** ScoreManager и GameState не зависят от Flame-движка и тестируются мгновенно. Они же содержат максимум бизнес-правил.
2. **Widget-тесты вторыми.** Overlay-экраны — это стандартный Flutter, без Flame. Быстро и надёжно.
3. **Flame-тесты третьими.** Компоненты требуют `FlameTester` и асинхронного `ensureAdd` — сложнее, но проверяют интеграцию с движком.
4. **Golden-тесты и E2E — последними.** Самые медленные и хрупкие, но дают максимальное доверие перед релизом.

---

## 9. CI/CD интеграция

```yaml
# .github/workflows/game-tests.yml (фрагмент)

jobs:
  flutter-test:
    runs-on: windows-latest
    steps:
      - uses: actions/checkout@v4
      - uses: subosito/flutter-action@v2
        with:
          flutter-version: '3.x'
      - run: flutter pub get
      - run: flutter test test/game/          # Unit-тесты
      - run: flutter test test/widget/        # Widget-тесты
      - run: flutter test test/golden/         # Golden-тесты
      - run: flutter test integration_test/ -d windows  # E2E
```

Для Web-сборки E2E тестов можно добавить отдельный job с `flutter test integration_test/ -d chrome`.

---

## 10. Связь с ручным тестированием

| Ручная проверка (из чек-листа) | Автотест | Статус |
| :--- | :--- | :--- |
| Расчёт очков (формула) | `score_manager_test.dart` | Запланировано |
| Условие победы | `game_state_test.dart` + `game_loop_test.dart` | Запланировано |
| Условие поражения (лимит ходов) | `game_state_test.dart` + `game_loop_test.dart` | Запланировано |
| Сброс уровня (Restart) | `game_loop_test.dart` | Запланировано |
| Разблокировка уровней | `game_state_test.dart` | Запланировано |
| Визуальная корректность UI | Golden-тесты | Запланировано |
| Краш при коллизии (race condition) | `puzzle_piece_test.dart` + stress-сценарий | Запланировано |
| Адаптивность UI (4:3, 21:9) | Golden-тесты | Запланировано |
| FPS при активном геймплее | `performance_test.dart` | Запланировано |
| Кроссплатформенность (Web vs Windows) | Integration-тесты на двух платформах | Запланировано |
| UX, баланс, кривая сложности | — | 🔴 Остаётся ручным |
| Звук и аудио | — | 🔴 Остаётся ручным |
| Прерывания (сворачивание, потеря фокуса) | — | 🔴 Остаётся ручным |
