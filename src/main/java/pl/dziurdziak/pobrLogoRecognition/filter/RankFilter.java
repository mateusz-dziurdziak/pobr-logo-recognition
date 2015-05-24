package pl.dziurdziak.pobrLogoRecognition.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.sqrt;
import static java.lang.System.currentTimeMillis;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
public class RankFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RankFilter.class);

    private final int windowSize;
    private final int position;

    @JsonCreator
    public RankFilter(@JsonProperty("windowSize") int windowSize, @JsonProperty("position") int position) {
        checkArgument(windowSize > 0, "windowSize has to be greater then 0");
        checkArgument((int) sqrt(windowSize) * (int) sqrt(windowSize) == windowSize, "windowSize sqrt should be perfect");
        checkArgument(position >= 0 && position < windowSize, "position has to be greater then 0 and less then windowSize");
        this.windowSize = windowSize;
        this.position = position;
    }

    @NotNull
    @Override
    public Image filter(@NotNull Image image) {
        LOG.info("Running rank filter. windowSize: {}, position: {}", windowSize, position);
        long startTime = currentTimeMillis();
        Image result = FilterUtils.filterUsingWindow(image, windowSize, this::doFilter);
        LOG.info("End rank filter. Took {} ms", currentTimeMillis() - startTime);
        return result;
    }

    private Pixel doFilter(Pixel[][] window) {
        List<Pixel> flatWindow = CommonUtils.flatArrays(window);
        return new Pixel(get(flatWindow, Pixel::getRed),
                get(flatWindow, Pixel::getGreen),
                get(flatWindow, Pixel::getBlue));
    }

    private <T> int get(List<T> list, ToIntFunction<T> function) {
        return list.stream().mapToInt(function).sorted().toArray()[position];
    }
}
