import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class PhoneBookTesting {

    private static final PhoneBook phoneBook = new PhoneBook();

    private static final Contact[] contacts = {
            new Contact("Михаил", "+71234567890"),
            new Contact("Степан", "+72789012345"),
            new Contact("Снежана", "+73754612345")
    };

    private static final List<Contact> contactGroup = Arrays.asList(contacts);

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Создаём группу контактов в телефонной книге");
        phoneBook.addGroup("testGroup", contactGroup);
        System.out.println("Начинаем тестирование\n");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Тестирование завершено");
    }

    // Ваш комментарий, Юрий:
    // void createContactTest() - бесполезный тест. Вы тестируете конструктор класса, по сути, причем проверяете не
    // получившийся объект, а как он переводится в строку. Странное решение

    // Переделал. Будем тестировать методы getName и getPhoneNumber класса Contact
    @Test
    public void createContactTest() {
        String contactName = "Кирилл";
        String contactNumber = "+71234567890";

        Contact contact = new Contact(contactName, contactNumber);

        // Задача 1 (Использование JUnit)
        Assertions.assertEquals(contact.getName(), contactName);
        Assertions.assertEquals(contact.getPhoneNumber(), contactNumber);

        // Задача 2 (JUnit + Hamcrest)
        org.hamcrest.MatcherAssert.assertThat(contact.getName(), org.hamcrest.Matchers.equalTo(contactName));
        org.hamcrest.MatcherAssert.assertThat(contact.getPhoneNumber(), org.hamcrest.Matchers.equalTo(contactNumber));
    }

    // Ваш комментарий, Юрий:
    // Assertions.assertEquals(phoneBook.findContact(contact.getPhoneNumber()), contact.getName()); -
    // а вот тут мы найденный контакт сравниваем с его именем? Непонятно… Хотя это вопрос больше к самой телефонной
    // книге, нежели к ее тесту

    // Да, тут мы тестируем метод findContact класса PhoneBook. Возможно, Вам что то непонятно, как Вы указали в
    // комментарии, только вот я из вашего комментария не понимаю, что я тут должен переделать. Было бы здорово,
    // если бы вы давали комментарии как преподаватель, объяснив учащемуся, хотя бы в двух словах, где он ошибся
    // и как сделать чтоб это было правильно...
    // Я сделал метод в классе PhoneBook, который возвращает объект контакта и теперь сравниваю объекты, я подумал,
    // что возможно, Вам было непонятно что я не объекты сравниваю...
    @Test
    public void phoneBookFindContactTest() {
        for (Contact contact : contactGroup) {

            // Задача 1 (Использование JUnit)
            Assertions.assertEquals(phoneBook.findContactObj(contact.getPhoneNumber()), contact);

            // Задача 2 (JUnit + Hamcrest)
            org.hamcrest.MatcherAssert.assertThat(
                    phoneBook.findContactObj(contact.getPhoneNumber()), org.hamcrest.Matchers.equalTo(contact));

        }
    }

    // Ваш комменатрий, Юрий:
    // Assertions.assertEquals(missedCallsExecution.toString(), missedCalls.showMissedCalls(phoneBook)); -
    // почему вы сравниваете строковые представления объектов, а не сами объекты?

    // Юрий, задача телефонная книга - это одно из домашних заданий, оно выполнено в строгом соответствии с заданием.
    // В этом тесте, я тестирую МЕТОД который показывает пропущенные вызовы, этот метод НЕ ВОЗВРАЩАЕТ ОБЪЕКТОВ,
    // он возвращает строку, причём, если вызов пропущен от номера, который есть в телефонной книге, то метод
    // должен вставить в строку имя Контакта, а если контакта нет в телефонной книге, то метод должен вставить в строку
    // номер, с которого был пропущен звонок. Исходя из ваших комментариев, я не совсем понимаю что я должен тут переделать,
    // было бы здорово, если бы дали комментарий опять же, как преподаватель, указав, хотя бы в двух словах, что именно
    // я должен переделать учитывая что я тестирую метод класса, который не возвращает объектов?
    // Я не очень понимаю, как мне сравнивать два объекта MissedCalls, создать два одинаковых объекта
    // MissedCalls, добавить туда одинаковые звонки, а потом сравнить их? Поясните пожалуйста?
    @ParameterizedTest
    @ValueSource(strings = {"+71234567890", "+72789012345", "+73754612345", "+79423456789", "+79423813789"})
    public void missedCallsTest(String argument) throws InterruptedException {

        MissedCalls missedCalls = new MissedCalls();
        StringBuilder missedCallsExecution = new StringBuilder();

        Thread.sleep(1500);
        double accepted = Math.random();

        if (accepted < 0.7) { // Вызов пропущен
            LocalDateTime now = LocalDateTime.now();
            missedCalls.addMissedCall(now, argument);
            missedCallsExecution.append(phoneBook.findContact(argument).equals("")
                    ? "Пропущенные вызовы:\r\n" +
                        argument + " в " + now.format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))
                    : "Пропущенные вызовы:\r\n" + phoneBook.findContact(argument)
                        + " в " + now.format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")));
        } else { // Вызов принят
            missedCallsExecution.append("Пропущенных вызовов нет.");
        }

        // Задача 1 (Использование JUnit)
        Assertions.assertEquals(missedCallsExecution.toString(), missedCalls.showMissedCalls(phoneBook));

        // Задача 2 (JUnit + Hamcrest)
        org.hamcrest.MatcherAssert.assertThat(missedCallsExecution.toString(),
                org.hamcrest.Matchers.equalTo(missedCalls.showMissedCalls(phoneBook)));

    }

    // Задача 2 (JUnit + Hamcrest)
    // Тест проверяет соответствует ли список contactGroup списку контактов находящемуся в phoneBook
    @Test
    public void phoneBookTest() {
        List<Contact> contactsFromPhoneBook = phoneBook.showAllContacts();
        org.hamcrest.MatcherAssert.assertThat(contactsFromPhoneBook,
                org.hamcrest.Matchers.isIn(List.of(contactGroup)));
    }

}
