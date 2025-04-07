package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;

public final class King extends Piece
{
    public King(Color color)
    {
        super(color, 'K');
    }

    @Override
    public boolean isValidMove(Position from, Position to) {
        return (from.rowDistance(to) == 1)
               && (from.columnDistance(to) == 1);
    }
}
