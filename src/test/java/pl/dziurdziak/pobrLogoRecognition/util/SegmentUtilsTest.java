package pl.dziurdziak.pobrLogoRecognition.util;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Segment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.dziurdziak.pobrLogoRecognition.model.image.Pixel.BLACK;
import static pl.dziurdziak.pobrLogoRecognition.model.image.Pixel.WHITE;

/**
 * @author Mateusz Dziurdziak
 */
public class SegmentUtilsTest {

    @Test
    public void testGetSegments() {
        Pixel[][] pixels = new Pixel[][] {
                {BLACK, WHITE, BLACK},
                {BLACK, WHITE, BLACK},
                {BLACK, BLACK, WHITE}};
        Image image = new Image(pixels);
        List<Segment> segments = SegmentUtils.getSegments(image);
        assertThat(segments).hasSize(2);
        assertThat(segments.get(0).getSize()).isEqualTo(6);
        assertThat(segments.get(1).getSize()).isEqualTo(3);
    }

}