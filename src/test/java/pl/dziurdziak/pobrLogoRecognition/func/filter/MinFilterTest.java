package pl.dziurdziak.pobrLogoRecognition.func.filter;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;

/**
 * @author Mateusz Dziurdziak
 */
public class MinFilterTest {

    @Test
    public void testFilter() {
        Image image = FileUtils.readImageFromFile("src/test/resources/logo_resized.jpg");
        Filter filter = new MinFilter(25);
        Image filteredImage = filter.filter(image);
        FileUtils.writeImageToFile(filteredImage, "build/tmp/minFilterTest.jpg", "jpg");
    }

}