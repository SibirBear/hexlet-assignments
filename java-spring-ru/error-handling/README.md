# Обработка ошибок

## src/main/java/exercise/exception/ResourceNotFoundException.java

## Задачи

Создайте исключение `ResourceNotFoundException` для обработки ошибки `404`

## src/main/java/exercise/handler/GlobalExceptionHandler.java

## Задачи

Создайте глобальный обработчик ошибок `GlobalExceptionHandler` для нашего приложения . Реализуйте в нем возврат ответа с кодом `404` в случае, если запрашиваемый товар не найден в базе данных

## src/main/java/exercise/controller/ProductsController.java

## Задачи

Добавьте в контроллер обработчики для следующих маршрутов:

* *GET /products/{id}* - Просмотр конкретного товара
* *PUT /products/{id}* – Обновление данных товара

Реализуйте в этих методах обработку ошибок. Если запрашиваемый товар не найден в базе данных, должен вернуться ответ с кодом `404` и сообщением типа *Product with id 3 not found*
