package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.Coordinates;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GameEvaluator {

    private final ArrayList<String> citadels;

    public GameEvaluator() {
        this.citadels = new ArrayList<String>();
        this.citadels.add("a4");
        this.citadels.add("a5");
        this.citadels.add("a6");
        this.citadels.add("b5");
        this.citadels.add("d1");
        this.citadels.add("e1");
        this.citadels.add("f1");
        this.citadels.add("e2");
        this.citadels.add("i4");
        this.citadels.add("i5");
        this.citadels.add("i6");
        this.citadels.add("h5");
        this.citadels.add("d9");
        this.citadels.add("e9");
        this.citadels.add("f9");
        this.citadels.add("e8");
    }

    private String indexToLetterPosition(int index){
        List<String> columnsCoordinates = Arrays.asList("a","b","c","d","e","f","g","h","i");
        return columnsCoordinates.get(index - 1);
    }

    public Set<Coordinates> getAvailablePawns(State state, State.Turn turn){
        if(turn != State.Turn.BLACK && turn != State.Turn.WHITE){
            return new HashSet<>();
        }
        State.Pawn[][] board = state.getBoard();
        Set<Coordinates> activePositions = new HashSet<>();
        for(int r = 0; r < state.getBoard()[0].length; r++){
            for(int c = 0; c < state.getBoard()[0].length; c++){
                if(turn == State.Turn.WHITE) {
                    if(board[r][c].equals(State.Pawn.WHITE) || board[r][c].equals(State.Pawn.KING)){
                        activePositions.add(new Coordinates(indexToLetterPosition(c+1), r+1));
                    }
                }
                else {
                    if(board[r][c].equals(State.Pawn.BLACK)){
                        activePositions.add(new Coordinates(indexToLetterPosition(c+1), r+1));
                    }
                }
            }
        }
        return activePositions;
    }

    public Set<Action> getLegalMoves(State state){
        Set<Action> possibleActions = new HashSet<>();
        Set<Coordinates> availablePawns = getAvailablePawns(state, state.getTurn());
        List<String> columnsCoordinates = Arrays.asList("a","b","c","d","e","f","g","h","i");
        List<Integer> rowsCoordinates = Arrays.asList(1,2,3,4,5,6,7,8,9);

        //get all possible moves
        for(Coordinates c : availablePawns){
            Set<Coordinates> endingPoints = new HashSet<>();
            for(Integer row: rowsCoordinates){
                endingPoints.add(new Coordinates(c.getColumn(), row));
            }
            for(String column: columnsCoordinates) {
                endingPoints.add(new Coordinates(column, c.getRow()));
            }
            for(Coordinates endPoint: endingPoints){
                try {
                    possibleActions.add(new Action(c.toString(), endPoint.toString(), state.getTurn()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //filter legal moves
        return possibleActions.stream().filter(a -> isLegalMove(state, a)).collect(Collectors.toSet());
    }

    private boolean isLegalMove(State state, Action a){
        // controllo la mossa
        if (a.getTo().length() != 2 || a.getFrom().length() != 2) {
            return false;
        }
        int columnFrom = a.getColumnFrom();
        int columnTo = a.getColumnTo();
        int rowFrom = a.getRowFrom();
        int rowTo = a.getRowTo();

        // controllo se sono fuori dal tabellone
        if (columnFrom > state.getBoard().length - 1 || rowFrom > state.getBoard().length - 1
                || rowTo > state.getBoard().length - 1 || columnTo > state.getBoard().length - 1 || columnFrom < 0
                || rowFrom < 0 || rowTo < 0 || columnTo < 0) {
            return false;
        }
        // controllo che non vada sul trono
        if (state.getPawn(rowTo, columnTo).equalsPawn(State.Pawn.THRONE.toString())) {
            return false;
        }
        // controllo la casella di arrivo
        if (!state.getPawn(rowTo, columnTo).equalsPawn(State.Pawn.EMPTY.toString())) {
            return false;
        }
        if (this.citadels.contains(state.getBox(rowTo, columnTo))
                && !this.citadels.contains(state.getBox(rowFrom, columnFrom))) {
            return false;
        }
        if (this.citadels.contains(state.getBox(rowTo, columnTo))
                && this.citadels.contains(state.getBox(rowFrom, columnFrom))) {
            if (rowFrom == rowTo) {
                if (columnFrom - columnTo > 5 || columnFrom - columnTo < -5) {
                    return false;
                }
            } else {
                if (rowFrom - rowTo > 5 || rowFrom - rowTo < -5) {
                    return false;
                }
            }
        }
        // controllo se cerco di stare fermo
        if (rowFrom == rowTo && columnFrom == columnTo) {
            return false;
        }
        // controllo se sto muovendo una pedina giusta
        if (state.getTurn().equalsTurn(State.Turn.WHITE.toString())) {
            if (!state.getPawn(rowFrom, columnFrom).equalsPawn("W")
                    && !state.getPawn(rowFrom, columnFrom).equalsPawn("K")) {
                return false;
            }
        }
        if (state.getTurn().equalsTurn(State.Turn.BLACK.toString())) {
            if (!state.getPawn(rowFrom, columnFrom).equalsPawn("B")) {
                return false;
            }
        }
        // controllo di non muovere in diagonale
        if (rowFrom != rowTo && columnFrom != columnTo) {
            return false;
        }
        // controllo di non scavalcare pedine
        if (rowFrom == rowTo) {
            if (columnFrom > columnTo) {
                for (int i = columnTo; i < columnFrom; i++) {
                    if (!state.getPawn(rowFrom, i).equalsPawn(State.Pawn.EMPTY.toString())) {
                        return false;
                    }
                    if (this.citadels.contains(state.getBox(rowFrom, i))
                            && !this.citadels.contains(state.getBox(a.getRowFrom(), a.getColumnFrom()))) {
                        return false;
                    }
                }
            } else {
                for (int i = columnFrom + 1; i <= columnTo; i++) {
                    if (!state.getPawn(rowFrom, i).equalsPawn(State.Pawn.EMPTY.toString())) {
                        return false;
                    }
                    if (this.citadels.contains(state.getBox(rowFrom, i))
                            && !this.citadels.contains(state.getBox(a.getRowFrom(), a.getColumnFrom()))) {
                        return false;
                    }
                }
            }
        } else {
            if (rowFrom > rowTo) {
                for (int i = rowTo; i < rowFrom; i++) {
                    if (!state.getPawn(i, columnFrom).equalsPawn(State.Pawn.EMPTY.toString())) {
                        return false;
                    }
                    if (this.citadels.contains(state.getBox(i, columnFrom))
                            && !this.citadels.contains(state.getBox(a.getRowFrom(), a.getColumnFrom()))) {
                        return false;
                    }
                }
            } else {
                for (int i = rowFrom + 1; i <= rowTo; i++) {
                    if (!state.getPawn(i, columnFrom).equalsPawn(State.Pawn.EMPTY.toString())) {
                        return false;
                    }
                    if (this.citadels.contains(state.getBox(i, columnFrom))
                            && !this.citadels.contains(state.getBox(a.getRowFrom(), a.getColumnFrom()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
