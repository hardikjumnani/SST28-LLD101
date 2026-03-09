package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        IncidentTicket.Builder b = new IncidentTicket.Builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(List.of("NEW"));
        return b.build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket t) {
        return t.toBuilder()
                .priority("CRITICAL")
                .tags(appendTag(t.getTags(), "ESCALATED"))
                .build();
    }

    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }

    private List<String> appendTag(List<String> existing, String tag) {
        List<String> copy = new ArrayList<>(existing);
        copy.add(tag);
        return copy;
    }
}
