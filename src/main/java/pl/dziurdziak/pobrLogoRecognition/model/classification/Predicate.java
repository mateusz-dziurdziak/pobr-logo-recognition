package pl.dziurdziak.pobrLogoRecognition.model.classification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;

/**
 * @author Mateusz Dziurdziak
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = MomentInvariantPredicate.class, name = "MomentInvariant"),
        @JsonSubTypes.Type(value = NumberOfPixelsPredicate.class, name = "Pixels")})
public interface Predicate {

    boolean fulfil(SegmentCalculations segmentCalculations);

}
