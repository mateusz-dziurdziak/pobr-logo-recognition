package pl.dziurdziak.pobrLogoRecognition.func.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.primitives.Ints;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;
import pl.dziurdziak.pobrLogoRecognition.util.CommonUtils;
import pl.dziurdziak.pobrLogoRecognition.util.FilterUtils;

import java.util.List;
import java.util.function.ToIntFunction;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.IntStream.range;
import static pl.dziurdziak.pobrLogoRecognition.util.CommonUtils.normalize256;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
public class MaskFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(MaskFilter.class);

    private final int[] flattedMask;
    private final int divisor;

    @JsonCreator
    public MaskFilter(@JsonProperty("mask") int[][] mask, @JsonProperty("divisor") int divisor) {
        // TODO validate mask
        flattedMask = Ints.concat(mask);
        this.divisor = divisor;
    }

    @Override
    @NotNull
    public Image filter(@NotNull Image image) {
        LOG.info("Running mask filter. Mask: {}, divisor: {}", flattedMask, divisor);
        long startTime = currentTimeMillis();
        Image result = FilterUtils.filterUsingWindow(image, flattedMask.length, this::doFilter);
        LOG.info("End mask filter. Took {} ms", currentTimeMillis() - startTime);
        return result;
    }

    private Pixel doFilter(Pixel[][] window) {
        List<Pixel> flatWindow = CommonUtils.flatArrays(window);

        int red = calculateColour(flatWindow, Pixel::getRed);
        int green = calculateColour(flatWindow, Pixel::getGreen);
        int blue = calculateColour(flatWindow, Pixel::getBlue);

        return new Pixel(red, green, blue);
    }

    private int calculateColour(List<Pixel> window, ToIntFunction<Pixel> function) {
        int[] intValues = window.stream().mapToInt(function).toArray();
        return normalize256(range(0, flattedMask.length).map(i -> flattedMask[i] * intValues[i]).sum() / divisor);
    }

}
