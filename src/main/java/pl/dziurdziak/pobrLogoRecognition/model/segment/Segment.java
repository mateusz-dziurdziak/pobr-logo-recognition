package pl.dziurdziak.pobrLogoRecognition.model.segment;

import com.google.common.collect.ImmutableList;
import lombok.ToString;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
public class Segment {

    private final boolean[][] members;
    private final List<Point> points;

    public Segment(int imageHeight, int imageWidth) {
        members = new boolean[imageHeight][imageWidth];
        points = newArrayList();
    }

    public void add(Point point) {
        members[point.getRow()][point.getColumn()] = true;
        points.add(point);
    }

    public boolean isMember(int row, int column) {
        return members[row][column];
    }

    public int imageHeight() {
        return members.length;
    }

    public int imageWidth() {
        return members.length > 0 ? members[0].length : 0;
    }

    public int getSize() {
        return points.size();
    }

    public List<Point> getPoints() {
        return ImmutableList.copyOf(points);
    }
}
