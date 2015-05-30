package pl.dziurdziak.pobrLogoRecognition.model.classification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Range;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
public class MomentInvariantPredicate implements Predicate {

    private final MomentInvariant momentInvariant;
    private final Range<Double> range;

    @JsonCreator
    public MomentInvariantPredicate(@JsonProperty("momentInvariant") MomentInvariant momentInvariant,
                                    @JsonProperty("min") double min,
                                    @JsonProperty("max") double max) {
        this.momentInvariant = momentInvariant;
        range = Range.closed(min, max);
    }

    @Override
    public boolean fulfil(SegmentCalculations segmentCalculations) {
        return range.contains(segmentCalculations.get(momentInvariant));
    }
}
