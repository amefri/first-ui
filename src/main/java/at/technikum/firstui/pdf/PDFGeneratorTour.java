package at.technikum.firstui;

import at.technikum.firstui.entity.Tours;
import at.technikum.firstui.services.TourListService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.IOException;

public class PDFGeneratorTour {

    private final TourListService tourListService;

    String destination = "src/main/java/at/technikum/firstui/pdf/tour_report.pdf";

    public PDFGeneratorTour(TourListService tourListService) {
        this.tourListService = tourListService;
    }

    public void createPdfWithMap(String destination) throws IOException {
        Tours tour = tourListService.getCurrentlySelected();
        if (tour == null) {
            System.out.println("No tour selected.");
            return;
        }

        // Initialize PDF writer and document
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Add tour data to the document
        document.add(new Paragraph("Tour Name: " + tour.getName()));
        document.add(new Paragraph("From: " + tour.getFrom()));
        document.add(new Paragraph("To: " + tour.getTo()));
        document.add(new Paragraph("Transport Type: " + tour.getTransportType()));
        document.add(new Paragraph("Distance (in km): " + tour.getDistance()));
        document.add(new Paragraph("Estimated Time (in min): " + tour.getEstimatedTime()));

        // Add map image to the document
        String tourName = tour.getName().replaceAll("\\s+", "_"); // Replace spaces with underscores
        File imageFile = new File("src/main/java/at/technikum/firstui/images/" + tourName + "_map_snapshot.png");
        if (imageFile.exists()) {
            ImageData imageData = ImageDataFactory.create(imageFile.getAbsolutePath());
            Image pdfImage = new Image(imageData);
            document.add(pdfImage);
        } else {
            System.out.println("Image file does not exist.");
        }

        // Close the document
        document.close();
        System.out.println("PDF created at: " + destination);
    }


}
