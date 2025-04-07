package com.directi.training.codesmells.refactored.chess;

import com.directi.training.codesmells.refactored.pieces.Color;
import com.directi.training.codesmells.refactored.Direction;
import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.context.PawnContext;
import com.directi.training.codesmells.refactored.pieces.Bishop;
import com.directi.training.codesmells.refactored.pieces.King;
import com.directi.training.codesmells.refactored.pieces.Knight;
import com.directi.training.codesmells.refactored.pieces.Pawn;
import com.directi.training.codesmells.refactored.pieces.Piece;
import com.directi.training.codesmells.refactored.pieces.Queen;
import com.directi.training.codesmells.refactored.pieces.Rook;

import java.lang.reflect.Constructor;
import java.util.Map;

public class ChessBoard
{
    private final int BOARD_LEN = 8;
    private final int SECOND_FROM_UP = BOARD_LEN - 2;
    private final int SECOND_FROM_BOTTOM = 1;

    private static Map<Integer, Constructor<? extends Piece>> firstRowSetup = null;


    private final Cell[][] _board =
        new Cell[BOARD_LEN][BOARD_LEN];

    public boolean _checkmate;

    private Color cellColorAt(int row, int column) {
        return (row + column) % 2 == 0 ? Color.WHITE : Color.BLACK;
    }

    public ChessBoard()
    {
        for (int row = 0; row < BOARD_LEN; row++) {
            for (int column = 0; column < BOARD_LEN; column++) {
                Color color = cellColorAt(row, column);
                _board[row][column] = new Cell(color);
            }
        }

        try {
            firstRowSetup = Map.of(
                0, Rook.class.getConstructor(Color.class),
                1, Knight.class.getConstructor(Color.class),
                2, Bishop.class.getConstructor(Color.class),
                3, King.class.getConstructor(Color.class),
                4, Queen.class.getConstructor(Color.class),
                5, Bishop.class.getConstructor(Color.class),
                6, Knight.class.getConstructor(Color.class),
                7,Rook.class.getConstructor(Color.class)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Cell[][] getBoard()
    {
        return _board;
    }

    private boolean coordinateOutOfBounds(int coord) {
        return coord < 0 || coord >= BOARD_LEN;
    }

    private boolean isPositionOutOfBounds(Position position)
    {
        return (coordinateOutOfBounds(position.getRow())
                || coordinateOutOfBounds(position.getColumn()));
    }

    public boolean isEmpty(Position position)
    {
        return isPositionOutOfBounds(position) || getCell(position).isEmpty();
    }

    private Cell getCell(Position position)
    {
        return getBoard()[position.getRow()][position.getColumn()];
    }

    private Cell getCell(int row, int column) {
        return getBoard()[row][column];
    }

    public Piece getPiece(Position position)
    {
        return (isEmpty(position))
                ? null
                : getCell(position).getPiece();
    }

    public boolean pieceThere(Position position) {
        return !isEmpty(position);
    }

    private boolean isKnight(Position position) {
        return getPiece(position) instanceof Knight;
    }


    private boolean noPieceInbetween(Position from, Position to)
    {
        if (!from.isStraightLineTo(to)) {
            return false;
        }

        Direction shift = Direction.getUnitShift(from, to);
        from = from.translate(shift);

        while (!from.equals(to)) {
            if (pieceThere(from))
                return false;
            from = from.translate(shift);
        }

        return true;
    }

    private boolean opponentOnDestination(Position from, Position opponentPos) {
        return getPiece(from).getColor() != getPiece(opponentPos).getColor();
    }

    private boolean validPositions(Position a, Position b) {
        return !(isPositionOutOfBounds(a) || isPositionOutOfBounds(b));
    }

    public boolean isValidMove(Position from, Position to)
    {
        Piece piece = getPiece(from);

        if (piece instanceof Pawn pawn) {
            pawn.setContext(new PawnContext(
                from.getRow() == (piece.getColor().isWhite()
                ? SECOND_FROM_UP : SECOND_FROM_BOTTOM),

                isOpponentAtForwardLeftFlank(from, piece.getColor()),

                isOpponentAtForwardRightFlank(from, piece.getColor())
            ));
        }

        return !from.equals(to)
               && validPositions(from, to)
               && pieceThere(from)
               && (!pieceThere(to) || opponentOnDestination(from, to))
               && (isKnight(from) || noPieceInbetween(from, to))
               && piece.isValidMove(from, to);
    }

    public void movePiece(Position from, Position to)
    {
        Piece currentPiece = getPiece(from);
        Cell destination = getCell(to);
        Cell source = getCell(from);

        updateCheckmateStatus(to);
        if (pieceThere(to))
            destination.removePiece();

        destination.setPiece(currentPiece);
        source.removePiece();
    }

    private void updateCheckmateStatus(Position moveDestination)
    {
        _checkmate = getPiece(moveDestination) instanceof King;
    }

    private boolean isOpponentAtForwardFlank(Position from, Color ownPieceColor, boolean left) {
        int localForwardShift = (ownPieceColor.isWhite() ? -1 : 1);
        int localFlankShift = localForwardShift;
        if (left) { localFlankShift *= -1; }

        int nextPos = from.getRow() + localForwardShift;
        Position nextFlank = new Position(nextPos,
            from.getColumn() + localFlankShift);

        return pieceThere(nextFlank)
               && getPiece(nextFlank).getColor() != ownPieceColor;
    }

    private boolean isOpponentAtForwardLeftFlank(Position from, Color ownPieceColor) {
        return isOpponentAtForwardFlank(from, ownPieceColor, true);
    }

    private boolean isOpponentAtForwardRightFlank(Position from, Color ownPieceColor) {
        return isOpponentAtForwardFlank(from, ownPieceColor, false);
    }

    public void reset()
    {
        for (int column = 0; column < BOARD_LEN; column++) {
            try {
                Constructor<? extends Piece> pieceToPlace = firstRowSetup.get(column);
                getBoard()[BOARD_LEN - 1][column]
                    .setPiece(pieceToPlace.newInstance(Color.WHITE));
                getBoard()[0][column]
                    .setPiece(pieceToPlace.newInstance(Color.BLACK));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        for (int column = 0; column < BOARD_LEN; column++) {
            getBoard()[SECOND_FROM_UP][column].setPiece(new Pawn(Color.WHITE));
            getBoard()[SECOND_FROM_BOTTOM][column].setPiece(new Pawn(Color.BLACK));
        }

        _checkmate = false;
    }

    public boolean isCheckmate()
    {
        return _checkmate;
    }

    private StringBuilder getColumnsIndex() {
        StringBuilder result = new StringBuilder(" ");

        for (int column = 0; column < BOARD_LEN; column++) {
            result.append("  ")
                         .append(column + 1)
                         .append("  ");
        }

        result.append("\n");
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = getColumnsIndex();

        for (int row = 0; row < BOARD_LEN; row++) {
            stringBuilder.append(row + 1);

            for (int column = 0; column < BOARD_LEN; column++) {
                stringBuilder.append(" ")
                        .append(getCell(row, column))
                        .append(" ");
            }

            stringBuilder.append("\n\n");
        }
        return stringBuilder.toString();
    }
}
