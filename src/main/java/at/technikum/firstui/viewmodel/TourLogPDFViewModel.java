package at.technikum.firstui.viewmodel;

import at.technikum.firstui.pdf.TourLogPDFGenerator;
import at.technikum.firstui.services.TourListService;
import at.technikum.firstui.services.TourLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class TourLogPDFViewModel {

    private static final Logger logger = LogManager.getLogger(TourLogPDFViewModel.class);

    private final TourLogService tourLogService;
    private final TourLogPDFGenerator pdfGenerator;

    public TourLogPDFViewModel(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
        this.pdfGenerator = new TourLogPDFGenerator(tourLogService);
    }

    public void generatePdf() {
        try {
            pdfGenerator.createTourLogPdf("src/main/java/at/technikum/firstui/pdf/tour_logs.pdf");
        } catch (IOException e) {
            logger.error("Failed to create PDF", e);
        }
    }
}
