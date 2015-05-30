package pl.dziurdziak.pobrLogoRecognition.model.segment;

import lombok.Getter;

import java.util.function.IntFunction;

/**
 * @author Mateusz Dziurdziak
 */
public enum Direction {
    NORTH(row -> row - 1, col -> col),
    NORTH_EAST(row -> row - 1, col -> col + 1),
    EAST(row -> row, col -> col + 1),
    SOUTH_EAST(row -> row + 1, col -> col + 1),
    SOUTH(row -> row + 1, col -> col),
    SOUTH_WEST(row -> row + 1, col -> col - 1),
    WEST(row -> row, col -> col - 1),
    NORTH_WEST(row -> row - 1, col -> col - 1);

    @Getter
    private final IntFunction<Integer> rowFunction;

    @Getter
    private final IntFunction<Integer> columnFunction;

    Direction(IntFunction<Integer> rowFunction, IntFunction<Integer> columnFunction) {
        this.rowFunction = rowFunction;
        this.columnFunction = columnFunction;
    }

}
