package pl.dziurdziak.pobrLogoRecognition.model.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.filter.Filter;

import java.util.List;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
@Getter
public class Configuration {

    private final String outputDir;
    private final String outputFileName;
    private final boolean exportFilesAfterEachStep;
    private final SegmentationConfig segmentationConfig;
    private final List<Filter> filters;

    @JsonCreator
    public Configuration(@JsonProperty("outputDir") String outputDir,
                         @JsonProperty("outputFileName") String outputFileName,
                         @JsonProperty("exportFileAfterEachStep") boolean exportFileAfterEachStep,
                         @JsonProperty("filters") List<Filter> filters,
                         @JsonProperty("segmentationConfig") SegmentationConfig segmentationConfig) {
        this.outputDir = outputDir;
        this.outputFileName = outputFileName;
        this.exportFilesAfterEachStep = exportFileAfterEachStep;
        this.segmentationConfig = segmentationConfig;
        this.filters = ImmutableList.copyOf(filters);
    }
}
