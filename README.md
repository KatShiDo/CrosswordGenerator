## **Генератор кроссвордов**
### **Общее описание**
&emsp;&emsp; Проект предназначен для создания и распространения пользовательских кроссвордов. На сайте можно создать свой кроссворд из ведённых слов, или же решить созданный другим человеком кроссворд. Если слово введено неправильно, система показывает, где была допущена ошибка.
Функции системы:
- Создание кроссоврда с возможностью добавить вопросы к кроссворду и сохраненить его в аккаунте пользователя
- Передача кроссвордов по ссылке
- Личный кабинет пользователя с возможностью просмотра сохраненных кроссвордов
- Функция решения кроссвордов с проверкой правильности решения
### **Зависимости проекта**
|Зависимость|Назначение|
|:-|:-|
|Spring Web|Основной компонент Spring для создания Web приложений|
|Spring JPA|Компонент для работы с базами данных используя Hibernate|
|Spring Mail|Компонент для отправки электрнных писем|
|Spring Security| Фреймворк для реализации функции аутентификации и контроля доступа|
|Project Lombok|Библиотека для автоматического написания шаблонного кода|
|PostgreSQL|Библиотека для работы с БД PostgreSQL|
|Thymeleaf|Обработчик шаблонов|

### **Команда для запуска**
Из консоли - ` java com.example.crosswordgenerator.CrosswordGeneratorApplication`