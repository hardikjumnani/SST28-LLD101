import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;
import java.util.List;

public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAfter service updates (original unchanged): " + t);
        System.out.println("Assigned: " + assigned);
        System.out.println("Escalated: " + escalated);

        List<String> tags = t.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
        } catch (UnsupportedOperationException ignored) {
            System.out.println("\nCannot modify tags list; it's immutable");
        }
        System.out.println("\nFinal ticket state: " + t);
    }
}
