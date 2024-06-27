package at.technikum.firstui.pdf;

import at.technikum.firstui.entity.TourLog;
import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TourLogPDFGenerator {

    private TourLogService tourLogService;
    private TourListService tourListService;

    public TourLogPDFGenerator(TourLogService tourLogService, TourListService tourListService) {
        this.tourLogService = tourLogService;
        this.tourListService=tourListService;
    }

    public void createTourLogPdf(String destination) throws IOException {

        Tours tour = tourListService.getCurrentlySelected();
        Long id = tour.getId();
        List<TourLog> tourLogs = tourLogService.getTourLogsByTourId(id);


        if (tourLogs == null || tourLogs.isEmpty()) {
            System.out.println("No tour logs found.");
            return;
        }

        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        for (TourLog log : tourLogs) {
            document.add(new Paragraph("Log Name: " + log.getName()));
            document.add(new Paragraph("Date: " + log.getDate()));
            document.add(new Paragraph("Rating: " + log.getRating()));
            document.add(new Paragraph("Info: " + log.getInfo()));
            document.add(new Paragraph("\n"));
        }

        document.close();
        System.out.println("PDF created at: " + destination);
    }
}