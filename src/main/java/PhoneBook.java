import java.util.*;

public class PhoneBook {

    private final Map<String, List<Contact>> phoneGroup = new HashMap<>();

    public void addGroup(String name, List<Contact> contacts) {
        if (!phoneGroup.containsKey(name)) {
            System.out.println("Группа контактов \"" + name + "\" создана");
            System.out.println();
        }

        phoneGroup.put(name, contacts);
    }

    public String findContact(String number) {
        for (Map.Entry<String, List<Contact>> entry : phoneGroup.entrySet()) {
            for (Contact contact : entry.getValue()) {
                if (contact.getPhoneNumber().equals(number)) {
                    return contact.getName();
                }
            }
        }
        return "";
    }

    public Contact findContactObj(String number) {
        for (Map.Entry<String, List<Contact>> entry : phoneGroup.entrySet()) {
            for (Contact contact : entry.getValue()) {
                if (contact.getPhoneNumber().equals(number)) {
                    return contact;
                }
            }
        }
        return new Contact("EmptyContact", "+00000000000");
    }

    public List<Contact> showAllContacts() {
        return phoneGroup.values().iterator().hasNext()
                ? phoneGroup.values().iterator().next()
                : new ArrayList<>();
    }

}
