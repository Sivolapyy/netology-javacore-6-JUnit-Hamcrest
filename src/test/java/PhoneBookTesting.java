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

    // Тест проверяет правильно ли работает класс Contact (cоздание контакта, метод toString() класса Contact).
    @Test
    public void createContactTest() {
        String contactName = "Кирилл";
        String contactNumber = "+71234567890";
        String expected = "Имя: " + contactName + ", Номер телефона: " + contactNumber + ";";

        Contact contact = new Contact(contactName, contactNumber);

        // Задача 1 (Использование JUnit)
        Assertions.assertEquals(contact.toString(), expected);

        // Задача 2 (JUnit + Hamcrest)
        org.hamcrest.MatcherAssert.assertThat(contact.toString(), org.hamcrest.Matchers.equalTo(expected));
    }

    // Тест проверяет праивльно ли метод findContact класса PhoneBook ищет контакты. Ну и по пути проверяется
    // добавляется ли группа контактов в целом :)
    @Test
    public void phoneBookFindContactTest() {
        for (Contact contact : contactGroup) {

            // Задача 1 (Использование JUnit)
            Assertions.assertEquals(phoneBook.findContact(contact.getPhoneNumber()), contact.getName());

            // Задача 2 (JUnit + Hamcrest)
            org.hamcrest.MatcherAssert.assertThat(
                    phoneBook.findContact(contact.getPhoneNumber()), org.hamcrest.Matchers.equalTo(contact.getName()));

        }
    }

    // Тест определяет правильно ли класс MissedCalls возвращает вызовы, те кто есть в телефонной книге по
    // по имени, те, кого нет по номеру
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
    public void hereWeWillTestSomething() {
        List<Contact> contactsFromPhoneBook = phoneBook.showAllContacts();
        org.hamcrest.MatcherAssert.assertThat(contactsFromPhoneBook,
                org.hamcrest.Matchers.isIn(List.of(contactGroup)));
    }

}
