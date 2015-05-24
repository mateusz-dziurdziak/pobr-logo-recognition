package pl.dziurdziak.pobrLogoRecognition.util;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mateusz Dziurdziak
 */
public class FilterUtilsTest {

    private Image image;

    @Before
    public void setUp() throws Exception {
        Pixel[][] pixels = new Pixel[][]{
                {px(1), px(2), px(3), px(4), px(5)},
                {px(2), px(3), px(4), px(5), px(6)},
                {px(3), px(4), px(5), px(6), px(7)},
                {px(4), px(5), px(6), px(7), px(8)},
                {px(5), px(6), px(7), px(8), px(9)}
        };
        image = new Image(pixels);
    }

    @Test
    public void testExtractWindow() {
        // top left
        assertThat(FilterUtils.extractWindow(image, 3, 1, 1))
                .isEqualTo(new Pixel[][]{
                        {px(1), px(2), px(3)},
                        {px(2), px(3), px(4)},
                        {px(3), px(4), px(5)}});

        // center
        assertThat(FilterUtils.extractWindow(image, 3, 2, 2))
                .isEqualTo(new Pixel[][]{
                        {px(3), px(4), px(5)},
                        {px(4), px(5), px(6)},
                        {px(5), px(6), px(7)}});

        // bottom right
        assertThat(FilterUtils.extractWindow(image, 3, 3, 3))
                .isEqualTo(new Pixel[][]{
                        {px(5), px(6), px(7)},
                        {px(6), px(7), px(8)},
                        {px(7), px(8), px(9)}});

        // single bottom right
        assertThat(FilterUtils.extractWindow(image, 1, 4, 4))
                .isEqualTo(new Pixel[][]{
                        {px(9)}});
    }

    @Test
    public void testFilterUsingWindowNoChange() {
        Image result = FilterUtils.filterUsingWindow(image, 9, window -> window[1][1]);

        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                assertThat(result.getPixel(i, j)).isEqualTo(image.getPixel(i, j));
            }
        }
    }

    @Test
    public void testFilterUsingWindow() {
        Image result = FilterUtils.filterUsingWindow(image, 9, window -> window[0][0]);
        Pixel[][] expected = new Pixel[][]{
                {px(1), px(2), px(3), px(4), px(5)},
                {px(2), px(1), px(2), px(3), px(6)},
                {px(3), px(2), px(3), px(4), px(7)},
                {px(4), px(3), px(4), px(5), px(8)},
                {px(5), px(6), px(7), px(8), px(9)}
        };
        for (int i = 0; i < result.height(); i++) {
            for (int j = 0; j < result.width(); j++) {
                assertThat(result.getPixel(i, j)).isEqualTo(expected[i][j]);
            }
        }
    }

    @NotNull
    private Pixel px(int all) {
        return new Pixel(all, all, all);
    }
}