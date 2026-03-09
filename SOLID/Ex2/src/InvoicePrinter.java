public class InvoicePrinter {
    public String format(String s) { return InvoiceFormatter.identityFormat(s); }
    public void print(String s) { System.out.print(s); }
}
