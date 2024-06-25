package at.technikum.firstui.viewmodel;


import at.technikum.firstui.PDFGeneratorTour;
import at.technikum.firstui.services.TourListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PDFViewModel {

    private static final Logger logger = LogManager.getLogger(PDFViewModel.class);
    private int i = 0;
    private final TourListService tourListService;
    private final PDFGeneratorTour pdfGenerator;

    public PDFViewModel(TourListService tourListService) {
        this.tourListService = tourListService;
        this.pdfGenerator = new PDFGeneratorTour(tourListService);
    }

    public void generatePdf() {
        try {
            pdfGenerator.createPdfWithMap("src/main/java/at/technikum/firstui/pdf/tour_report.pdf");
        } catch (IOException e) {
            logger.error("Failed to create PDF", e);
        }
    }
}
