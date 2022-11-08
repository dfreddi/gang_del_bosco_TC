package it.unibo.ai.didattica.competition.tablut.domain;

public class Coordinates {
    private final String column;
    private final int row;

    public Coordinates(String column, int row) {
        this.column = column;
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return column + row;
    }
}
