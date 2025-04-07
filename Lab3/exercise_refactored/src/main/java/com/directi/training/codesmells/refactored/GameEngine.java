package com.directi.training.codesmells.refactored;

import com.directi.training.codesmells.refactored.chess.ChessBoard;
import com.directi.training.codesmells.refactored.chess.Player;
import com.directi.training.codesmells.refactored.pieces.Color;

import java.util.Scanner;

public class GameEngine
{
    private static final Scanner scanner = new Scanner(System.in);
    private final ChessBoard _chessBoard;
    private final Player _player1;
    private final Player _player2;
    private Player _currentPlayer;

    private static Player getPlayersName(int order) {
        String prompt = String.format("Enter Player %d Name: ", order);
        System.out.print(prompt);
        return new Player(scanner.nextLine());
    }

    public GameEngine(Player player1, Player player2)
    {
        _chessBoard = new ChessBoard();
        _player1 = player1;
        _player2 = player2;
    }

    public GameEngine()
    {
        _chessBoard = new ChessBoard();
        _player1 = getPlayersName(1);
        _player2 = getPlayersName(2);
    }

    private String playerColorMessage(Player player) {
        return "Player " + player.getName() + " has Color " + player.getColor();
    }

    private Player getSecondPlayer() {
        return _player2;
    }

    private Player getCurrentPlayer() {
        return _currentPlayer;
    }

    private void setCurrentPlayer(Player player) {
        _currentPlayer = player;
    }

    private Player getFirstPlayer() {
        return _player1;
    }

    private void determinePlayOrder() {
        if (getFirstPlayer().getColor().isBlack()) {
            setCurrentPlayer(getFirstPlayer());
            getFirstPlayer().setColor(Color.WHITE);
        } else {
            setCurrentPlayer(getSecondPlayer());;
            getFirstPlayer().setColor(Color.BLACK);
        }

        getSecondPlayer().setColor(getFirstPlayer().getColor().inverse());
    }

    public void initGame()
    {
        resetBoard();
        determinePlayOrder();

        showInitMessage();
        showBoard(false);
    }

    private void showInitMessage() {
        System.out.println("\nGame initialized");
        System.out.println(playerColorMessage(getFirstPlayer()));
        System.out.println(playerColorMessage(getSecondPlayer()) + "\n");
    }

    private void resetBoard() {
        getChessBoard().reset();
    }

    private void showBoard(boolean newLine) {
        if (newLine) { System.out.println(); }
        System.out.println(getChessBoard());
    }

    public boolean isValidMove(Position from, Position to)
    {
        return isPlayerMovingTheirOwnPiece(from)
               && getChessBoard().isValidMove(from, to);
    }

    private void restartGame() {
        endGame();
        initGame();
    }

    private boolean endgameConditionReached() {
        return getChessBoard().isCheckmate();
    }

    private void invalidMoveError() {
        System.out.println("Invalid move!");
    }

    public void makeMove(Position from, Position to) {
        if (isValidMove(from, to)) {
            forceMove(from, to);
        } else {
            invalidMoveError();
        }
    }

    public void forceMove(Position from, Position to)
    {
        getChessBoard().movePiece(from, to);
        showBoard(true);

        if (endgameConditionReached()) {
            restartGame();
        } else {
            passTurn();
        }
    }

    public void startGame()
    {
        while (true) {
            System.out.println("Next move is of " + _currentPlayer.getName()
                               + " [" + _currentPlayer.getColor() + "]");
            System.out.print("Enter position (row col) of piece to move: ");
            Position from = inputPosition();
            System.out.print("Enter destination position: ");
            Position to = inputPosition();
            makeMove(from, to);
        }
    }

    private Position inputPosition()
    {
        int row = scanner.nextInt() - 1;
        int col = scanner.nextInt() - 1;
        return new Position(row, col);
    }

    private void showWinner(Player winner) {
        System.out.println("WINNER - " + winner + "\n\n");
    }

    private void showGameEndedMessage() {
        System.out.println("Game Ended");
    }

    private void processWinner() {
        Player winner = _currentPlayer;
        winner.incrementWinCount();
        showWinner(winner);
    }


    private void endGame()
    {
        showGameEndedMessage();
        processWinner();
    }

    private void passTurn()
    {
        setCurrentPlayer(
            getCurrentPlayer() == getFirstPlayer()
                ? getSecondPlayer(): getFirstPlayer()
        );
    }

    private boolean isPlayerMovingTheirOwnPiece(Position from) {
        return getChessBoard().pieceThere(from)
               && getChessBoard().getPiece(from).getColor() == getCurrentPlayer().getColor();
    }

    public ChessBoard getChessBoard()
    {
        return _chessBoard;
    }
}
