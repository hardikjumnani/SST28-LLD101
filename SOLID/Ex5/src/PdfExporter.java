import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        // Capability limitation: still signals via ExportException to follow contract
        if (req.body != null && req.body.length() > 20) {
            throw new ExportException("PDF cannot handle content > 20 chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
