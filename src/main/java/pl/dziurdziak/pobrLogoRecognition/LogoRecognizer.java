package pl.dziurdziak.pobrLogoRecognition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;

/**
 * @author Mateusz Dziurdziak
 */
public class LogoRecognizer {

    private static final Logger LOG = LoggerFactory.getLogger(LogoRecognizer.class);

    public static void main(String[] args) {
        LOG.info("Running LogoRecognizer");
        LOG.info("Reading configuration");
        FileUtils.readConfiguration(args[0]);
    }

}
