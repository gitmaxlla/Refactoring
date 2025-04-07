package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;

public final class Rook extends Piece
{
    public Rook(Color color)
    {
        super(color, 'r');
    }

    @Override
    public boolean isValidMove(Position from, Position to) {
        return from.inCrossReachability(to);
    }
}
