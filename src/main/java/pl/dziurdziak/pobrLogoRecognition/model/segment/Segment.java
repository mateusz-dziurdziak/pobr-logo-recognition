package pl.dziurdziak.pobrLogoRecognition.model.segment;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
public class Segment {

    private final boolean[][] members;
    @Getter
    private int size;

    public Segment(int imageHeight, int imageWidth) {
        members = new boolean[imageHeight][imageWidth];
    }

    public void add(Point point) {
        members[point.getRow()][point.getColumn()] = true;
        size++;
    }

    public boolean isMember(int row, int column) {
        return members[row][column];
    }
}
