package pl.dziurdziak.pobrLogoRecognition;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Segment;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;
import pl.dziurdziak.pobrLogoRecognition.util.SegmentUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Mateusz Dziurdziak
 */
public class SegmentCalculator {

    public static void main(String[] params) throws IOException {
        Pixel colour = new Pixel(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2]));
        String[] filesPaths = Arrays.copyOfRange(params, 3, params.length);

        Map<String, SegmentCalculations> segmentCalculationsMap = new LinkedHashMap<>();
        for (String filePath : filesPaths) {
            Image image = FileUtils.readImageFromFile(filePath);
            List<Segment> segments = SegmentUtils.getSegments(image, colour);
            int i = 0;
            for (Segment segment : segments) {
                SegmentCalculations segmentCalculations = new SegmentCalculations(segment);
                segmentCalculationsMap.put(filePath + i, segmentCalculations);
                i++;
            }
        }

        exportMoments(segmentCalculationsMap);
    }

    private static void exportMoments(Map<String, SegmentCalculations> segmentCalculationsMap) throws IOException {
        File outputFile = new File("calculations.txt");
        if (outputFile.exists()) {
            checkState(outputFile.delete(), "File calculations.txt cannot be deleted");
        }
        checkState(outputFile.createNewFile(), "File calculations.txt cannot be created");

        CharSink charSink = Files.asCharSink(outputFile, Charsets.UTF_8);
        try (Writer writer = charSink.openBufferedStream()) {
            writeHeader(writer);

            for (Map.Entry<String, SegmentCalculations> entry : segmentCalculationsMap.entrySet()) {
                writer.write(entry.getKey() + ",");

                for (Coefficient coefficient : Coefficient.values()) {
                    writer.write(entry.getValue().get(coefficient) + ",");
                }

                for (MomentInvariant momentInvariant : MomentInvariant.values()) {
                    writer.write(entry.getValue().get(momentInvariant) + ",");
                }
                writer.write("\n");
            }

            writer.flush();
        }
    }

    private static void writeHeader(Writer writer) throws IOException {
        writer.write("Segment,");
        for (Coefficient coefficient : Coefficient.values()) {
            writer.write(coefficient.name() + ",");
        }
        for (MomentInvariant momentInvariant : MomentInvariant.values()) {
            writer.write(momentInvariant.name() + ",");
        }
        writer.write("\n");
    }

}
