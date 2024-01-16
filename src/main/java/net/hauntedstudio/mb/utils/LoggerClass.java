package net.hauntedstudio.mb.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerClass {

    private final Logger logger = Logger.getLogger(LoggerClass.class.getName());

    public void debug(String message) {
        log(Level.FINE, "[DEBUG] " + message);
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void warning(String message) {
        log(Level.WARNING, message);
    }

    public void severe(String message, Exception exception) {
        log(Level.SEVERE, message +"\n" +
                "Exception: " + exception.toString());
    }

    private void log(Level level, String message) {
        logger.log(level, message);
    }
}
