package pl.dziurdziak.pobrLogoRecognition.model.calculation;

import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Segment;
import pl.dziurdziak.pobrLogoRecognition.util.SegmentUtils;

import java.util.List;

import static pl.dziurdziak.pobrLogoRecognition.model.image.Pixel.BLACK;
import static pl.dziurdziak.pobrLogoRecognition.model.image.Pixel.WHITE;

/**
 * @author Mateusz Dziurdziak
 */
public class SegmentCalculationsTest {

    @Test
    public void testNoErrors() {
        Pixel[][] pixels = new Pixel[][] {
                {BLACK, WHITE, BLACK},
                {BLACK, WHITE, BLACK},
                {BLACK, BLACK, WHITE}};
        Image image = new Image(pixels);
        List<Segment> segments = SegmentUtils.getSegments(image, null);
        segments.forEach(segment -> new SegmentCalculations(segment));
    }

}