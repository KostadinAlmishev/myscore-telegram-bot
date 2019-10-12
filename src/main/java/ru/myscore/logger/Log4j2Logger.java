package ru.myscore.logger;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Log4j2Logger {
    public static final Marker MYAPP_MARKER = MarkerManager.getMarker("MYAPP");
}
