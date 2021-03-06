## JUnit

Нужно сделать из TwitterStream переиспользуемую библиотеку. Покрыть все библиотечные и публичные методы тестами используя заглушки для сетевых сервисов.

* Функциональность типа геокодирования, форматирования временного периода, разбора входных параметров, получения и форматирования твитов нужно выделить в отдельные классы и оформить как библиотеку полезных классов в отдельном пакете ```ru.fizteh.fivt.students.<student>.moduletests.library```: 
    * Если есть несколько реализаций, как у геокодирования, выделить интерфейсы
    * Библиотечные классы не должны ничего выводить на экран (stdout и stderr). Результат работы должен возвращаться методами.
    * Если есть логика вывода на экран достойная тестирования (содержит хотя бы одно ветвление), то лучше в библиотечные методы или в конструктор класса передавать в качестве параметра типа Writer System.out. Так его можно будет заменить заглушкой и проверить правильность вывода в тестах. И библиотеку можно будет использовать для печати в файл, при желании.
    * Никаких System.exit() из библиотечных классов, только исключения.
    * Ваш ```main()``` из [второго задания](/tasks/01-TwitterStream.md) должен использовать новые библиотечные классы.
* Покрытие тестами должно быть не менее 70% строк кода, лучше более.
* Тесты должны покрывать все требования из [TwitterStream](/tasks/01-TwitterStream.md) + возможные исключения. То есть, если у вас какой-нибудь метод при каком-то понятном условии может выкинуть Exception - то этот случай должен быть покрыт тестом.

Тесты можно запускать командой ```mvn test``` или из IDE, так же как и ```main()```.

## Рекомендации:
1. Нужно отрефакторить классы так, чтобы внешние сервисы можно было заменять на заглушки. Например, делать фабричные статические методы или конструкторы, которые принимают классы для соединения с сервисами.
2. Не реализуйте никакой логики в конструкторе, в нём только сохранение параметров. 
2. Классы разбить на небольшие, с обособленной функциональностью. 
3. Смотрите мои примеры: 
    * [Библиотека](/akormushin/src/main/java/ru/fizteh/fivt/students/akormushin/moduletests/library)
    * [Тесты](/akormushin/src/test/java/ru/fizteh/fivt/students/akormushin/moduletests/library)


