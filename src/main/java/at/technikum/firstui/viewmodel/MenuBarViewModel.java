package at.technikum.firstui.viewmodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuBarViewModel{

    private static final Logger logger = LogManager.getLogger(AddRouteLogViewModel.class);

    public void onClose() {
        System.exit(0);
    }

    public void onDelete() {
        logger.info("Delete happens here");
    }

    public void onAbout() {
        logger.info("fancy things that happen in about");
    }
}































































