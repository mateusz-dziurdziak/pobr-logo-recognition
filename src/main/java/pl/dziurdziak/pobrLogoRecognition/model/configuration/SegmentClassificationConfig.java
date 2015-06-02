package pl.dziurdziak.pobrLogoRecognition.model.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.func.classification.Predicate;

import java.util.List;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
@Getter
public class SegmentClassificationConfig {

    private final List<Predicate> predicates;
    private final String classifyAs;

    @JsonCreator
    public SegmentClassificationConfig(@JsonProperty("predicates") List<Predicate> predicates,
                                       @JsonProperty("classifyAs") String classifyAs) {
        this.predicates = ImmutableList.copyOf(predicates);
        this.classifyAs = classifyAs;
    }
}
