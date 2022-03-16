import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class MissedCalls {

    private final Map<LocalDateTime, String> missedCalls = new TreeMap<>();

    public void addMissedCall(LocalDateTime dt, String phoneNumber) {
        missedCalls.put(dt, phoneNumber);
    }

    public String showMissedCalls(PhoneBook phoneBook) {
        if (!missedCalls.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Пропущенные вызовы:\r\n");

            for (Map.Entry<LocalDateTime, String> entry : missedCalls.entrySet()) {
                String name = phoneBook.findContact(entry.getValue());
                if (!name.equals("")) {
                    sb.append(name).append(" в ").append(entry.getKey().
                            format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))).append("\r\n");
                } else {
                    sb.append(entry.getValue()).append(" в ").append(entry.getKey().
                            format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))).append("\r\n");
                }
            }

            return sb.toString().trim();
        }

        return "Пропущенных вызовов нет.";
    }

}
