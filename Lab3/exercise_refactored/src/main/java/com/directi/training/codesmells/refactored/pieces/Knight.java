package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;

public final class Knight extends Piece
{
    public Knight(Color color) { super(color, 'k'); }

    @Override
    public boolean isValidMove(Position from, Position to)
    {
        int columnsMoved = from.columnDistance(to);
        int rowsMoved = from.rowDistance(to);
        return (columnsMoved == 2 && rowsMoved == 1)
               || (columnsMoved == 1 && rowsMoved == 2);
    }
}
