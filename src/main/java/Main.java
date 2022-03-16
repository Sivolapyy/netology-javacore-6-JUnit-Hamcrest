import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Contact a = new Contact("Кирилл", "+70123456789");
        Contact b = new Contact("Михаил", "+71234567890");
        Contact c = new Contact("Степан", "+72789012345");
        Contact d = new Contact("Снежана", "+73754612345");

        List<Contact> contactGroup = new ArrayList<>(){};
        contactGroup.add(a);
        contactGroup.add(b);
        contactGroup.add(c);
        contactGroup.add(d);

        PhoneBook myPhoneBook = new PhoneBook();
        myPhoneBook.addGroup("Семья", contactGroup);

        MissedCalls myMissedCalls = new MissedCalls();

        // Добавляем пропущенный звонок с номера, которого нет в списке контактов myPhoneBook
        myMissedCalls.addMissedCall(LocalDateTime.now(), "+79423456789");
        Thread.sleep(1000);

        for (Contact cnt : contactGroup) {
            System.out.println("Звоним с номера " + cnt.getPhoneNumber());
            Thread.sleep(1500);
            double accepted = Math.random();

            if (accepted > 0.7) {
                System.out.println("Вызов с номера " + cnt.getPhoneNumber() + " принят в " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")));
            } else {
                System.out.println("Вызов " + cnt.getPhoneNumber() + " пропущен в " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")));

                myMissedCalls.addMissedCall(LocalDateTime.now(), cnt.getPhoneNumber());
            }

            System.out.println();
        }

        System.out.println(myMissedCalls.showMissedCalls(myPhoneBook));

    }
}
