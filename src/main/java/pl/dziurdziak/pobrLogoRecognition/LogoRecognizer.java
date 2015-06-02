package pl.dziurdziak.pobrLogoRecognition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.func.filter.Filter;
import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;
import pl.dziurdziak.pobrLogoRecognition.model.configuration.Configuration;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.recognition.Logo;
import pl.dziurdziak.pobrLogoRecognition.func.recognition.Recognizer;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;
import pl.dziurdziak.pobrLogoRecognition.util.ImageUtils;
import pl.dziurdziak.pobrLogoRecognition.util.SegmentUtils;

import java.io.File;
import java.util.List;

/**
 * @author Mateusz Dziurdziak
 */
public class LogoRecognizer {

    private static final Logger LOG = LoggerFactory.getLogger(LogoRecognizer.class);

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        LOG.info("Running LogoRecognizer");
        Configuration configuration = FileUtils.readConfiguration(args[0]);
        final Image initialImage = FileUtils.readImageFromFile(args[1]);

        new File(configuration.getOutputDir()).mkdirs();

        Image image = initialImage;
        int i = 0;
        for (Filter filter : configuration.getFilters()) {
            image = filter.filter(image);
            if (configuration.isExportFilesAfterEachStep()) {
                FileUtils.writeImageToFile(image, configuration.getOutputDir() + "/" + configuration.getOutputFileName()
                        + i + "." + configuration.getOutFileType(), configuration.getOutFileType());
            }
            i++;
        }

        List<ClassifiedSegment> classifiedSegments = SegmentUtils.getClassifiedSegments(configuration, image);

        Recognizer recognizer = (Recognizer) Class.forName(configuration.getRecognizerClass()).newInstance();

        List<Logo> logos = recognizer.recognize(classifiedSegments);

        Image result = ImageUtils.drawRectanglesOnLogos(initialImage, logos);

        FileUtils.writeImageToFile(result, configuration.getOutputDir() + "/" + configuration.getOutputFileName() + "."
                + configuration.getOutFileType(), configuration.getOutFileType());
    }

}
