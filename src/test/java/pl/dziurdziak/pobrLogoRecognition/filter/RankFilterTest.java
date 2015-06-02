package pl.dziurdziak.pobrLogoRecognition.filter;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;

/**
 * @author Mateusz Dziurdziak
 */
public class RankFilterTest {

    @Test
    public void testFilter() {
        Image image = FileUtils.readImageFromFile("src/test/resources/logo_resized.jpg");
        Filter filter = new RankFilter(25, 12);
        Image filteredImage = filter.filter(image);
        FileUtils.writeImageToFile(filteredImage, "build/tmp/medianFilterTest.jpg", "jpg");
    }

}