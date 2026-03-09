public abstract class Exporter {
    // Template method: enforces base preconditions and delegates to subclass
    public final ExportResult export(ExportRequest req) {
        ensureRequest(req);
        return doExport(req);
    }

    protected void ensureRequest(ExportRequest req) {
        if (req == null) throw new ExportException("request cannot be null");
        if (req.title == null) throw new ExportException("title cannot be null");
        // body may be null — subclasses may handle it
    }

    protected abstract ExportResult doExport(ExportRequest req);
}
