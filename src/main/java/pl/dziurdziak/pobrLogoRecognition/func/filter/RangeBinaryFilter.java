package pl.dziurdziak.pobrLogoRecognition.func.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Range;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@Immutable
public class RangeBinaryFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RangeBinaryFilter.class);

    private final Range<Integer> redRange;
    private final Range<Integer> greenRange;
    private final Range<Integer> blueRange;

    @JsonCreator
    public RangeBinaryFilter(@JsonProperty("minRed") int minRed, @JsonProperty("maxRed") int maxRed,
                             @JsonProperty("minGreen") int minGreen, @JsonProperty("maxGreen") int maxGreen,
                             @JsonProperty("minBlue") int minBlue, @JsonProperty("maxBlue") int maxBlue) {
        redRange = Range.closed(minRed, maxRed);
        greenRange = Range.closed(minGreen, maxGreen);
        blueRange = Range.closed(minBlue, maxBlue);
    }

    @NotNull
    @Override
    public Image filter(@NotNull Image image) {
        LOG.info("Running range binary filter. Red range: {}, green range: {}, blue range: {}", redRange, greenRange, blueRange);
        Pixel[][] pixels = new Pixel[image.height()][image.width()];

        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel px = image.getPixel(row, col);
                if (redRange.contains(px.getRed())
                        && greenRange.contains(px.getGreen())
                        && blueRange.contains(px.getBlue())) {
                    pixels[row][col] = Pixel.WHITE;
                } else {
                    pixels[row][col] = Pixel.BLACK;
                }
            }
        }
        LOG.info("End range binary filter");
        return new Image(pixels);
    }
}
