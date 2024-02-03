package net.hauntedstudio.mb.utils;

import net.hauntedstudio.mb.MBApi;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerClass {

    private final Logger logger = Logger.getLogger(LoggerClass.class.getName());

    public void debug(String message) {
        if (MBApi.getMain().debug)
            log(Level.FINE, "[DEBUG] " + message);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void severe(String message, String exception) {
        log(Level.SEVERE, message +"\n" +
                "Exception: " + exception);
    }

    private void log(Level level, String message) {
        logger.log(level, message);
    }
}
