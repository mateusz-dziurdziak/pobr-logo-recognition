package pl.dziurdziak.pobrLogoRecognition.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;
import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;
import pl.dziurdziak.pobrLogoRecognition.model.classification.Predicate;
import pl.dziurdziak.pobrLogoRecognition.model.configuration.Configuration;
import pl.dziurdziak.pobrLogoRecognition.model.configuration.SegmentClassificationConfig;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Direction;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Point;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Segment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.currentTimeMillis;

/**
 * @author Mateusz Dziurdziak
 */
public class SegmentUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    private SegmentUtils() {
    }

    @NotNull
    public static List<ClassifiedSegment> getClassifiedSegments(Configuration configuration, Image image) {
        LOG.info("Getting classified segments");
        long startTime = currentTimeMillis();

        // holds information if pixel is already assigned to some segment
        boolean[][] assigned = new boolean[image.height()][image.width()];

        List<ClassifiedSegment> classifiedSegments = newArrayList();
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                if (!assigned[row][col]) {
                    // if pixel is not assigned to segment we initialize new segment with current point as start point
                    Segment segment = getSegment(image, assigned, new Point(row, col));
                    if (segment.getSize() < configuration.getMinPixelsInSegment()) {
                        continue;
                    }
                    SegmentCalculations segmentCalculations = new SegmentCalculations(segment);
                    classifiedSegments.addAll(classify(segmentCalculations, configuration));
                }
            }
        }

        LOG.info("Found {} classified segments. Took {} ms", classifiedSegments.size(), currentTimeMillis() - startTime);
        return classifiedSegments;
    }

    /**
     * Gets list of segments from image. One segment is created from connected, same colour pixels.
     *
     * @param image image to be analized
     * @return list of segments
     */
    public static List<Segment> getSegments(Image image, @Nullable Pixel colour) {
        LOG.info("Getting segments...");
        long startTime = currentTimeMillis();

        // holds information if pixel is already assigned to some segment
        boolean[][] assigned = new boolean[image.height()][image.width()];

        List<Segment> segments = newArrayList();
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                if (!assigned[row][col]
                        && (colour == null || colour.equals(image.getPixel(row, col)))) {
                    // if pixel is not assigned to segment we initialize new segment with current point as start point
                    segments.add(getSegment(image, assigned, new Point(row, col)));
                }
            }
        }
        LOG.info("Found {} segments. Took {} ms", segments.size(), currentTimeMillis() - startTime);
        return segments;
    }

    /**
     * Initializes and returns segment which started point is located at provided location. Next points are searched
     * with bread-first algorithm
     *
     * @param image      image
     * @param assigned   array containing information if pixel is already assigned (ATTENTION: array IS modified by function)
     * @param startPoint start point
     * @return segment
     */
    private static Segment getSegment(Image image, boolean[][] assigned, Point startPoint) {
        // holds information if pixel was already selected as candidate
        boolean[][] unavailable = new boolean[image.height()][image.width()];
        Segment segment = new Segment(image.height(), image.width());
        Pixel colour = image.getPixel(startPoint.getRow(), startPoint.getColumn());

        Queue<Point> candidates = new LinkedList<>();
        addCandidate(candidates, unavailable, startPoint.getRow(), startPoint.getColumn());
        while (!candidates.isEmpty()) {
            Point candidate = candidates.poll();
            if (!assigned[candidate.getRow()][candidate.getColumn()]
                    && colour.equals(image.getPixel(candidate.getRow(), candidate.getColumn()))) {
                addPointToSegment(assigned, unavailable, segment, candidate);
                addNextCandidates(image, assigned, unavailable, candidates, candidate);
            }
        }
        return segment;
    }

    private static void addPointToSegment(boolean[][] assigned, boolean[][] unavailable, Segment segment, Point candidate) {
        assigned[candidate.getRow()][candidate.getColumn()] = true;
        unavailable[candidate.getRow()][candidate.getColumn()] = true;
        segment.add(candidate);
    }

    private static void addNextCandidates(Image image, boolean[][] assigned, boolean[][] unavailable, Queue<Point> candidates, Point candidate) {
        for (Direction direction : Direction.values()) {
            int row = direction.getRowFunction().apply(candidate.getRow());
            int col = direction.getColumnFunction().apply(candidate.getColumn());
            if (row >= 0
                    && row < image.height()
                    && col >= 0
                    && col < image.width()
                    && !unavailable[row][col]
                    && !assigned[row][col]) {
                addCandidate(candidates, unavailable, row, col);
            }
        }
    }

    private static void addCandidate(Queue<Point> candidates, boolean[][] unavailable, int row, int column) {
        candidates.add(new Point(row, column));
        unavailable[row][column] = true;
    }

    private static List<ClassifiedSegment> classify(SegmentCalculations segment, Configuration configuration) {
        List<ClassifiedSegment> classifiedSegments = newArrayList();
        for (SegmentClassificationConfig config : configuration.getSegmentClassificationConfigs()) {
            boolean fulfil = true;
            Iterator<Predicate> predicateIterator = config.getPredicates().iterator();
            while (predicateIterator.hasNext() && fulfil) {
                fulfil = predicateIterator.next().fulfil(segment);
            }

            if (fulfil) {
                classifiedSegments.add(new ClassifiedSegment(segment, config.getClassifyAs()));
            }
        }
        return classifiedSegments;
    }
}
