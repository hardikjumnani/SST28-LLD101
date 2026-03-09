public interface InvoiceStore {
    void save(String id, String content);
    int countLines(String id);
}
