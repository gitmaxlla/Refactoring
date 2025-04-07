package com.directi.training.codesmells;

import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.chess.ChessBoard;
import com.directi.training.codesmells.refactored.GameEngine;
import com.directi.training.codesmells.refactored.chess.Player;
import com.directi.training.codesmells.refactored.pieces.Bishop;
import com.directi.training.codesmells.refactored.pieces.King;
import com.directi.training.codesmells.refactored.pieces.Knight;
import com.directi.training.codesmells.refactored.pieces.Pawn;
import com.directi.training.codesmells.refactored.pieces.Queen;
import com.directi.training.codesmells.refactored.pieces.Rook;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class ChessGameTest
{

    private GameEngine _gameEngine;

    private boolean isValidMoveHelper(Position from, Position to) {
        return _gameEngine.isValidMove(from, to);
    }
    
    private void makeMoveHelper(Position from, Position to)
    {
        _gameEngine.forceMove(from, to);
    }

    //DO NOT CHANGE ANY STATEMENT FROM CODE BELOW

    @Before
    public void initGame()
    {
        Player player1 = new Player("A");
        Player player2 = new Player("B");
        _gameEngine = new GameEngine(player1, player2);
        _gameEngine.initGame();
    }

    private void testIfOneSideOfStartingBoardIsValid(ChessBoard board, int firstRow, int secondRow) {
        assertTrue(board.getPiece(new Position(firstRow, 0)) instanceof Rook);
        assertTrue(board.getPiece(new Position(firstRow, 1)) instanceof Knight);
        assertTrue(board.getPiece(new Position(firstRow, 2)) instanceof Bishop);
        assertTrue(board.getPiece(new Position(firstRow, 3)) instanceof King);
        assertTrue(board.getPiece(new Position(firstRow, 4)) instanceof Queen);
        assertTrue(board.getPiece(new Position(firstRow, 5)) instanceof Bishop);
        assertTrue(board.getPiece(new Position(firstRow, 6)) instanceof Knight);
        assertTrue(board.getPiece(new Position(firstRow, 7)) instanceof Rook);

        for (int i = 0; i < 8; i++) {
            assertTrue(board.getPiece(new Position(secondRow, i)) instanceof Pawn);
        }
    }

    @Test
    public void testIfStartingBoardIsValid() {
        ChessBoard board = _gameEngine.getChessBoard();
        testIfOneSideOfStartingBoardIsValid(board, 0, 1);
        testIfOneSideOfStartingBoardIsValid(board, 7, 6);
    }

    @Test
    public void testNoMovementOfPieceForFirstPlayer()
    {
        Position fromPosition = new Position(6, 0);
        Position toPosition = new Position(6, 0);
        assertFalse(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testNoMovementOfPieceForSecondPlayer()
    {
        Position fromPosition = new Position(1, 0);
        Position toPosition = new Position(1, 0);
        assertFalse(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testIsValidMoveOfPawnForFirstPlayer()
    {
        Position fromPosition = new Position(6, 1);
        Position toPosition = new Position(5, 1);
        assertTrue(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testMovementOfPawnForFirstPlayer()
    {
        Position fromPosition = new Position(6, 6);
        Position toPosition = new Position(4, 6);
        makeMoveHelper(fromPosition, toPosition);
        ChessBoard chessBoard = _gameEngine.getChessBoard();
        assertNull(chessBoard.getPiece(new Position(6, 6)));
        assertTrue(chessBoard.getPiece(new Position(4, 6)) instanceof Pawn);
    }

    @Test
    public void testNotAllowsSecondPlayerWhenNotInTurn()
    {
        Position fromPosition = new Position(0, 1);
        Position toPosition = new Position(2, 0);
        assertFalse(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testIsValidMoveOfKnightForSecondPlayer()
    {
        _gameEngine.makeMove(new Position(6, 1), new Position(5, 1));
        Position fromPosition = new Position(0, 1);
        Position toPosition = new Position(2, 0);
        assertTrue(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testMovementOfKnightForSecondPlayer()
    {
        Position fromPosition = new Position(0, 1);
        Position toPosition = new Position(2, 0);
        makeMoveHelper(fromPosition, toPosition);
        ChessBoard chessBoard = _gameEngine.getChessBoard();
        assertNull(chessBoard.getPiece(new Position(0, 1)));
        assertTrue(chessBoard.getPiece(new Position(2, 0)) instanceof Knight);
    }

    @Test
    public void testInvalidMoveOfPawn()
    {
        Position fromPosition = new Position(6, 0);
        Position toPosition = new Position(5, 1);
        assertFalse(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testInvalidMoveWhenPieceInBetween()
    {
        Position fromPosition = new Position(7, 7);
        Position toPosition = new Position(5, 7);
        assertFalse(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testMovePieceToNonEmptyPlace()
    {
        Position fromPosition = new Position(7, 2);
        Position toPosition = new Position(6, 1);
        assertFalse(isValidMoveHelper(fromPosition, toPosition));
    }

    @Test
    public void testCapturePawnFromSideways() {
        makeMoveHelper(new Position(6, 1),
                       new Position(4, 1));

        makeMoveHelper(new Position(1, 0),
                       new Position(3, 0));


        assertTrue(isValidMoveHelper(new Position(4, 1),
                                     new Position(3, 0)));
    }
}
