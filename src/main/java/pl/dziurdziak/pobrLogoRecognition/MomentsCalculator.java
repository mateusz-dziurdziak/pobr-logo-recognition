package pl.dziurdziak.pobrLogoRecognition;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Segment;
import pl.dziurdziak.pobrLogoRecognition.util.FileUtils;
import pl.dziurdziak.pobrLogoRecognition.util.SegmentUtils;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Mateusz Dziurdziak
 */
public class MomentsCalculator {

    public static void main(String[] filesPaths) throws IOException {
        for (String filePath : filesPaths) {
            Image image = FileUtils.readImageFromFile(filePath);
            List<Segment> segments = SegmentUtils.getSegments(image);
            int i = 0;
            for (Segment segment : segments) {
                if (segment.getSize() < 50) {
                    continue;
                }
                SegmentCalculations segmentCalculations = new SegmentCalculations(segment);
                exportMoments(filePath, i, segmentCalculations);
                i++;
            }
        }
    }

    private static void exportMoments(String filePath, int seq, SegmentCalculations segmentCalculations) throws IOException {
        String name = new File(filePath).getName() + "_moments_" + seq + ".txt";
        File outputFile = new File(name);
        if (outputFile.exists()) {
            checkState(outputFile.delete(), "File %s cannot be deleted", filePath);
        }
        checkState(outputFile.createNewFile(), "File %s cannot be created", filePath);

        CharSink charSink = Files.asCharSink(outputFile, Charsets.UTF_8);
        try (Writer writer = charSink.openBufferedStream()) {
            for (MomentInvariant momentInvariant : MomentInvariant.values()) {
                writer.write(momentInvariant.name() + "," + segmentCalculations.get(momentInvariant) + "\n");
            }

            writer.flush();
        }
    }

}
