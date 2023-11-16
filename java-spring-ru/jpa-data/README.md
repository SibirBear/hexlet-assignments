# JPA Entity

В этом упражнении мы продолжим работу с CRUD сущностей. Но данные нашего приложения будут храниться в базе данных и для работы с ними мы будем использовать Spring Data JPA

## Ссылки

* [Аннотации Jakarta Persistence](https://jakarta.ee/specifications/persistence/3.0/apidocs/jakarta.persistence/jakarta/persistence/package-summary)

## build.gradle.kts

## Задачи

Подключите в приложение базу данных H2

## src/main/java/exercise/model/Person.java

## Задачи

Создайте модель `Person` со свойства, которая будет представлять человека в нашем приложении. У человека есть уникальный идентификатор, имя *firstName* и фамилия *lastName*. Идентификатор должен генерироваться автоматически

## src/main/java/exercise/repository/PersonRepository.java

## Задачи

Создайте репозиторий для работы с сущностью `Person`

## src/main/java/resources/application.yml

## Задачи

Настройте приложение таким образом, чтобы при старте приложения создавалась и обновлялась схема базы данных

## src/main/java/exercise/controller/PeopleController.java

## Задачи

В контроллере для примера уже создан обработчик для маршрута просмотра конкретного человека. Добавьте в приложение обработчики для следующих маршрутов:

* *GET /people* - Список всех персон
* *POST /people* – Создание новой персоны
* *DELETE /people/{id}* – Удаление персоны
