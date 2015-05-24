package pl.dziurdziak.pobrLogoRecognition.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import net.jcip.annotations.Immutable;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@Immutable
public class MaxFilter extends RankFilter {

    @JsonCreator
    public MaxFilter(@JsonProperty("windowSize") int windowSize) {
        super(windowSize, windowSize - 1);
    }

}
