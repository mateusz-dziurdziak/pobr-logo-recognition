package pl.dziurdziak.pobrLogoRecognition.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;

/**
 * Converts image to black and white picture. If pixel red > {@link #maxRed} and pixel green > {@link #maxGreen}
 * and pixel blue > {@link #maxBlue} pixel is considered to be {@link Pixel#WHITE}
 *
 * @author Mateusz Dziurdziak
 */
@ToString
@Immutable
public class BinaryFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(BinaryFilter.class);

    private final int maxRed;
    private final int maxGreen;
    private final int maxBlue;

    @JsonCreator
    public BinaryFilter(@JsonProperty("maxRed") int maxRed, @JsonProperty("maxGreen") int maxGreen,
                        @JsonProperty("maxBlue") int maxBlue) {
        this.maxRed = maxRed;
        this.maxGreen = maxGreen;
        this.maxBlue = maxBlue;
    }

    @NotNull
    @Override
    public Image filter(@NotNull Image image) {
        LOG.info("Running binary filter. maxRed: {}, maxGreen: {}, maxBlue: {}", maxRed, maxGreen, maxBlue);
        Pixel[][] pixels = new Pixel[image.height()][image.width()];

        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel px = image.getPixel(row, col);
                if (px.getRed() > maxRed && px.getGreen() > maxGreen && px.getBlue() > maxBlue) {
                    pixels[row][col] = Pixel.WHITE;
                } else {
                    pixels[row][col] = Pixel.BLACK;
                }
            }
        }
        LOG.info("End binary filter");
        return new Image(pixels);
    }
}
