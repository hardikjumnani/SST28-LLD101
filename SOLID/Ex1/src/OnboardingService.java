import java.util.List;

public class OnboardingService {
    private final StudentStore store;
    private final InputParser parser;
    private final Validator validator;
    private final ConsolePrinter printer;

    public OnboardingService(StudentStore store, InputParser parser, Validator validator, ConsolePrinter printer) {
        this.store = store;
        this.parser = parser;
        this.validator = validator;
        this.printer = printer;
    }

    public void registerFromRawInput(String raw) {
        printer.printInput(raw);

        StudentInput in = parser.parse(raw);

        List<String> errors = validator.validate(in);
        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(store.count());
        StudentRecord rec = new StudentRecord(id, in.name, in.email, in.phone, in.program);

        store.save(rec);

        printer.printCreated(id);
        printer.printSaved(store.count());
        printer.printConfirmation(rec);
    }
}
