package com.directi.training.codesmells.refactored;

public class Position
{
    private final int _row;
    private final int _column;

    public Position(int row, int column)
    {
        _row = row;
        _column = column;
    }

    public int getRow()
    {
        return _row;
    }

    public int getColumn()
    {
        return _column;
    }

    @Override
    public String toString()
    {
        String positionStr = "(ROW: %d, COLUMN: %d)";
        return String.format(positionStr, _row, _column);
    }

    private boolean sharesCoordinates(Position other) {
        return getRow() == other.getRow() && getColumn() == other.getColumn();
    }

    public boolean inCrossReachability(Position other) {
        return getRow() == other.getRow() || getColumn() == other.getColumn();
    }

    public int rowDistance(Position other) {
        return Math.abs(other.getRow() - getRow());
    }

    public int columnDistance(Position other) {
        return Math.abs(other.getColumn() - getColumn());
    }

    public Position translate(Direction dir) {
        return new Position(getRow() + dir.rowOffset(),
            getColumn() + dir.columnOffset());
    }

    public boolean sharesDiagonal(Position pos) {
        return rowDistance(pos) == columnDistance(pos);
    }

    public boolean isStraightLineTo(Position pos)
    {
        return this.sharesDiagonal(pos)
               || getRow() == pos.getRow()
               || getColumn() == pos.getColumn();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Position otherPos))
            return false;
        return this == obj || sharesCoordinates(otherPos);
    }
}
