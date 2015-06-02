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
    private final String outFileType;
    private final boolean exportFilesAfterEachStep;
    private final List<Filter> filters;
    private final int minPixelsInSegment;
    private final List<SegmentClassificationConfig> segmentClassificationConfigs;
    private final String recognizerClass;

    @JsonCreator
    public Configuration(@JsonProperty("outputDir") String outputDir,
                         @JsonProperty("outputFileName") String outputFileName,
                         @JsonProperty("outFileType") String outFileType,
                         @JsonProperty("exportFileAfterEachStep") boolean exportFileAfterEachStep,
                         @JsonProperty("filters") List<Filter> filters,
                         @JsonProperty("minPixelsInSegment") int minPixelsInSegment,
                         @JsonProperty("segmentClassificationConfigs") List<SegmentClassificationConfig> segmentClassificationConfigs,
                         @JsonProperty("recognizerClass") String recognizerClass) {
        this.outputDir = outputDir;
        this.outputFileName = outputFileName;
        this.outFileType = outFileType;
        this.exportFilesAfterEachStep = exportFileAfterEachStep;
        this.minPixelsInSegment = minPixelsInSegment;
        this.filters = ImmutableList.copyOf(filters);
        this.segmentClassificationConfigs = ImmutableList.copyOf(segmentClassificationConfigs);
        this.recognizerClass = recognizerClass;
    }
}
