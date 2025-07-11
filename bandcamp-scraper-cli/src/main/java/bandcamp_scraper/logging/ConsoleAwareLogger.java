package bandcamp_scraper.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLFJ Logger Wrapper, with capabilities to tee STDIO and Logging
 */
public class ConsoleAwareLogger {

    private final Logger logger;

    public static ConsoleAwareLogger getLogger(Class<?> clazz) {
      return new ConsoleAwareLogger(clazz);
    }

    public ConsoleAwareLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void printOut(String msg) {
        System.out.println(msg);
        logger.info("[STDOUT] " + msg);
    }

    public void printErr(String msg) {
        System.err.println(msg);
        logger.error("[STDERR] " + msg);
    }
}
 
