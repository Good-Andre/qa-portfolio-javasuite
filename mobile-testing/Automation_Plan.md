# 🤖 Automation Plan (Mobile)

## Уровни тестирования

| Уровень | Тип | Что проверяет | Инструмент | Скорость |
| :--- | :--- | :--- | :--- | :--- |
| Unit | Dart Test | Чистая логика, модели, утилиты | `flutter test` | Быстро |
| Widget | Flutter Test | UI‑компоненты без платформы | `flutter test` | Быстро |
| Integration | Integration Test | Сквозные сценарии на реальном устройстве/эмуляторе | `integration_test` | Средне |
| E2E | Integration + Driver | Сложные сценарии с платформенными вызовами | `flutter drive` | Медленно |
| Performance | Benchmark + Profiler | FPS, память, энергопотребление | DevTools, Instruments | Медленно |

---

## План автоматизации

| Компонент | Тип теста | Статус | Приоритет |
| :--- | :--- | :--- | :--- |
| Модели данных (Level, GameState) | Unit | Запланировано | 🔴 Высокий |
| UI‑компоненты (кнопки, списки) | Widget | Запланировано | 🔴 Высокий |
| Основной сценарий (вход → пазл → победа) | Integration | Запланировано | 🟡 Средний |
| Оффлайн‑режим (кэширование) | Integration | Запланировано | 🟡 Средний |
| Жизненный цикл (сворачивание/восстановление) | Integration | Запланировано | 🟡 Средний |
| Производительность (FPS) | Benchmark | Запланировано | 🟢 Низкий |
| Доступность (TalkBack/VoiceOver) | Manual | — | 🔴 Ручной |

---

## Пример Integration‑теста (E2E)

```dart
// integration_test/login_flow_test.dart

import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:game/main.dart' as app;

void main() {
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  testWidgets('login flow: valid credentials → main screen', (tester) async {
    app.main();
    await tester.pumpAndSettle();

    // Ввод логина/пароля
    await tester.enterText(find.byKey(const Key('email_field')), 'test@example.com');
    await tester.enterText(find.byKey(const Key('password_field')), 'password123');
    await tester.tap(find.text('Войти'));
    await tester.pumpAndSettle();

    // Проверка перехода на главный экран
    expect(find.text('Добро пожаловать'), findsOneWidget);
    expect(find.byKey(const Key('play_button')), findsOneWidget);
  });
}
```

Запуск:

```bash
# Android эмулятор
flutter test
