package pl.dziurdziak.pobrLogoRecognition.filter;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;

/**
 * @author Mateusz Dziurdziak
 */
public class BinaryFilterTest {

    @Test
    public void testFilter() {
        Image image = FileUtils.readImageFromFile("src/test/resources/logo_resized.jpg");
        Filter filter = new BinaryFilter(150, 150, 30);
        Image filteredImage = filter.filter(image);
        FileUtils.writeImageToFile(filteredImage, "build/tmp/binaryFilterTest.jpg");
    }

}