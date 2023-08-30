# Переезжаем с ORM Ebean и шаблонизатора Thymeleaf на JDBC и шаблонизатор Jte

## Переезжаем с ORM Ebean на JDBC

1. Пройдите курс [Java JDBC: Работа с базой данных](https://ru.hexlet.io/courses/java_jdbc) и урок [Работа с базой данных](https://ru.hexlet.io/courses/java-web/lessons/database/theory_unit)

2. Из проекта уберите все зависимости и плагины, связанные с Ebean. Они нам больше не пригодятся. Уберите конфигурационные файлы приложения, они тоже относятся к Ebean. Добавьте зависимость hikari
3. Из моделей уберите все, что относится к Ebean. Наследование от класса `Model` и аннотации `javax.persistence`. Из модели `Url` уберите свойство `urlChecks`. В модели `UrlCheck` свойство url поменяйте на url_id, теперь это просто число - идентификатор урла, для которого запускалась эта проверка
4. В директории resources создайте sql-скрипт с запросами для начальной инициализации БД. Запросы можно взять из миграций Ebean, чтобы не писать заново вручную. Обратите внимание, что базы данных имеют некоторые различия и что запрос должен подойти как для H2, так и для PosgreSql. Удобнее всего взять миграцию H2 и немного поправить ее
5. В главном классе приложения в методе `getApp()` создайте соединение с базой данных и передайте его в базовый репозиторий. Чтобы была возможность работать с разными базами данных локально и в продакшене, при создании соединения сделайте возможность получить Url базы данных через переменную окружения `JDBC_DATABASE_URL` по аналогии с портом
6. Создайте в приложении репозитории для каждой сущности для работы с базой данных при помощи JDBC. Обратите внимание, что запросы должны без проблем выполняться и в базе данных H2 и в PosgreSql
7. Перепишите контроллеры на работу с репозиториями вместо ORM

## Переезжаем с шаблонизатора hikari на Jte

Этот шаг не обязательный. Если у вас уже готовы все шаблоны на Thymeleaf, можно не менять. Тесты проверяют уже готовый HTML и никак не проверяют, при помощи какого шаблонизатора он сформирован

1. Уберите все зависимости Thymeleaf и добавьте зависимости для Jte
2. Создайте новый движок шаблонизатора и подключите его к Javalin в методе `getApp()`:

    ```java
    import gg.jte.ContentType;
    import gg.jte.TemplateEngine;
    import io.javalin.rendering.template.JavalinJte;

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
        return templateEngine;
    }
    ```

    ```java
    JavalinJte.init(createTemplateEngine());
    ```

3. Создайте новые шаблоны и расположите их в директории `src/main/resources/templates`
