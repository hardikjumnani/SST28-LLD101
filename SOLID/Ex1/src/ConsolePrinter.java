import java.util.List;

public class ConsolePrinter {
    public void printInput(String raw) { System.out.println("INPUT: " + raw); }

    public void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }

    public void printCreated(String id) { System.out.println("OK: created student " + id); }

    public void printSaved(int total) { System.out.println("Saved. Total students: " + total); }

    public void printConfirmation(StudentRecord r) {
        System.out.println("CONFIRMATION:");
        System.out.println(r);
    }
}
