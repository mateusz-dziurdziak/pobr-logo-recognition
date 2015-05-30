package pl.dziurdziak.pobrLogoRecognition.util;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mateusz Dziurdziak
 */
public class CommonUtilsTest {

    @Test
    public void testCopy() {
        Pixel[][] pixels = new Pixel[][]{{new Pixel(1, 1, 1), new Pixel(2, 2, 2)}, {new Pixel(3, 3, 3), new Pixel(4, 4, 4)}};
        Pixel[][] copy = CommonUtils.copy(pixels);
        assertThat(copy).isEqualTo(pixels);
    }

}