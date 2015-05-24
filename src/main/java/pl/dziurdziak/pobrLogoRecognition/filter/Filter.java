package pl.dziurdziak.pobrLogoRecognition.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jetbrains.annotations.NotNull;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;

/**
 * @author Mateusz Dziurdziak
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = BinaryFilter.class, name = "Binary"),
        @JsonSubTypes.Type(value = MaskFilter.class, name = "Mask"),
        @JsonSubTypes.Type(value = MaxFilter.class, name = "Max"),
        @JsonSubTypes.Type(value = MinFilter.class, name = "Min"),
        @JsonSubTypes.Type(value = RankFilter.class, name = "Rank")})
public interface Filter {

    @NotNull
    Image filter(@NotNull Image image);

}
