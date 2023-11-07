# REST API в Spring Boot

## Ссылки

* [Начало работы со Spring Boot](https://spring.io/quickstart)

## src/main/java/exercise/controller/users/PostsController.java

## Задачи

Реализуйте дополнительные обработчики маршрутов для `Post`:

* *GET /api/users/{id}/posts* - Список всех постов, написанных пользователем с таким же `userId`, как `id` в маршруте
* *POST /api/users/{id}/posts* – Создание нового поста, привязанного к пользователю по `id`. Должен возвращаться статус 201. Тело запроса должно содержать `slug`, `title`, `body`. А `userId` берется из маршрута
