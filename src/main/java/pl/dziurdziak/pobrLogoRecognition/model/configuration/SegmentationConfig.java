package pl.dziurdziak.pobrLogoRecognition.model.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.jcip.annotations.Immutable;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
@Getter
public class SegmentationConfig {

    private final int minPixelsForSegment;

    @JsonCreator
    public SegmentationConfig(@JsonProperty("minPixelsForSegment") int minPixelsForSegment) {
        this.minPixelsForSegment = minPixelsForSegment;
    }
}
