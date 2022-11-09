package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ActionStrategy {

    private int movesWithutCapturing;

    public int getMovesWithutCapturing() {
        return movesWithutCapturing;
    }

    public void setMovesWithutCapturing(int movesWithutCapturing) {
        this.movesWithutCapturing = movesWithutCapturing;
    }

    private String gameLogName;
    private File gameLog;
    private FileHandler fh;
    private Logger loggGame;
    private List<String> citadels;

    public int getRepeated_moves_allowed() {
        return repeated_moves_allowed;
    }

    public void setRepeated_moves_allowed(int repeated_moves_allowed) {
        this.repeated_moves_allowed = repeated_moves_allowed;
    }

    private int repeated_moves_allowed;

    public String getGameLogName() {
        return gameLogName;
    }

    public void setGameLogName(String gameLogName) {
        this.gameLogName = gameLogName;
    }

    public File getGameLog() {
        return gameLog;
    }

    public void setGameLog(File gameLog) {
        this.gameLog = gameLog;
    }

    public FileHandler getFh() {
        return fh;
    }

    public void setFh(FileHandler fh) {
        this.fh = fh;
    }

    public Logger getLoggGame() {
        return loggGame;
    }

    public void setLoggGame(Logger loggGame) {
        this.loggGame = loggGame;
    }

    public List<String> getCitadels() {
        return citadels;
    }

    public void setCitadels(List<String> citadels) {
        this.citadels = citadels;
    }

    public List<State> getDrawConditions() {
        return drawConditions;
    }

    public void setDrawConditions(List<State> drawConditions) {
        this.drawConditions = drawConditions;
    }

    private List<State> drawConditions;
    public ActionStrategy(ArrayList<String> citadels) {
        this.citadels = citadels;
        this.movesWithutCapturing = 0;
        this.repeated_moves_allowed = 0;
    }

    public State checkCaptureWhite(State state, Action a) {
        // controllo se mangio a destra
        if (a.getColumnTo() < state.getBoard().length - 2
                && state.getPawn(a.getRowTo(), a.getColumnTo() + 1).equalsPawn("B")
                && (state.getPawn(a.getRowTo(), a.getColumnTo() + 2).equalsPawn("W")
                || state.getPawn(a.getRowTo(), a.getColumnTo() + 2).equalsPawn("T")
                || state.getPawn(a.getRowTo(), a.getColumnTo() + 2).equalsPawn("K")
                || (this.citadels.contains(state.getBox(a.getRowTo(), a.getColumnTo() + 2))
                && !(a.getColumnTo() + 2 == 8 && a.getRowTo() == 4)
                && !(a.getColumnTo() + 2 == 4 && a.getRowTo() == 0)
                && !(a.getColumnTo() + 2 == 4 && a.getRowTo() == 8)
                && !(a.getColumnTo() + 2 == 0 && a.getRowTo() == 4)))) {
            state.removePawn(a.getRowTo(), a.getColumnTo() + 1);
            this.movesWithutCapturing = -1;
        }
        // controllo se mangio a sinistra
        if (a.getColumnTo() > 1 && state.getPawn(a.getRowTo(), a.getColumnTo() - 1).equalsPawn("B")
                && (state.getPawn(a.getRowTo(), a.getColumnTo() - 2).equalsPawn("W")
                || state.getPawn(a.getRowTo(), a.getColumnTo() - 2).equalsPawn("T")
                || state.getPawn(a.getRowTo(), a.getColumnTo() - 2).equalsPawn("K")
                || (this.citadels.contains(state.getBox(a.getRowTo(), a.getColumnTo() - 2))
                && !(a.getColumnTo() - 2 == 8 && a.getRowTo() == 4)
                && !(a.getColumnTo() - 2 == 4 && a.getRowTo() == 0)
                && !(a.getColumnTo() - 2 == 4 && a.getRowTo() == 8)
                && !(a.getColumnTo() - 2 == 0 && a.getRowTo() == 4)))) {
            state.removePawn(a.getRowTo(), a.getColumnTo() - 1);
            this.movesWithutCapturing = -1;
            this.loggGame.fine("Pedina nera rimossa in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
        }
        // controllo se mangio sopra
        if (a.getRowTo() > 1 && state.getPawn(a.getRowTo() - 1, a.getColumnTo()).equalsPawn("B")
                && (state.getPawn(a.getRowTo() - 2, a.getColumnTo()).equalsPawn("W")
                || state.getPawn(a.getRowTo() - 2, a.getColumnTo()).equalsPawn("T")
                || state.getPawn(a.getRowTo() - 2, a.getColumnTo()).equalsPawn("K")
                || (this.citadels.contains(state.getBox(a.getRowTo() - 2, a.getColumnTo()))
                && !(a.getColumnTo() == 8 && a.getRowTo() - 2 == 4)
                && !(a.getColumnTo() == 4 && a.getRowTo() - 2 == 0)
                && !(a.getColumnTo() == 4 && a.getRowTo() - 2 == 8)
                && !(a.getColumnTo() == 0 && a.getRowTo() - 2 == 4)))) {
            state.removePawn(a.getRowTo() - 1, a.getColumnTo());
            this.movesWithutCapturing = -1;
            this.loggGame.fine("Pedina nera rimossa in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
        }
        // controllo se mangio sotto
        if (a.getRowTo() < state.getBoard().length - 2
                && state.getPawn(a.getRowTo() + 1, a.getColumnTo()).equalsPawn("B")
                && (state.getPawn(a.getRowTo() + 2, a.getColumnTo()).equalsPawn("W")
                || state.getPawn(a.getRowTo() + 2, a.getColumnTo()).equalsPawn("T")
                || state.getPawn(a.getRowTo() + 2, a.getColumnTo()).equalsPawn("K")
                || (this.citadels.contains(state.getBox(a.getRowTo() + 2, a.getColumnTo()))
                && !(a.getColumnTo() == 8 && a.getRowTo() + 2 == 4)
                && !(a.getColumnTo() == 4 && a.getRowTo() + 2 == 0)
                && !(a.getColumnTo() == 4 && a.getRowTo() + 2 == 8)
                && !(a.getColumnTo() == 0 && a.getRowTo() + 2 == 4)))) {
            state.removePawn(a.getRowTo() + 1, a.getColumnTo());
            this.movesWithutCapturing = -1;
            this.loggGame.fine("Pedina nera rimossa in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
        }
        // controllo se ho vinto
        if (a.getRowTo() == 0 || a.getRowTo() == state.getBoard().length - 1 || a.getColumnTo() == 0
                || a.getColumnTo() == state.getBoard().length - 1) {
            if (state.getPawn(a.getRowTo(), a.getColumnTo()).equalsPawn("K")) {
                state.setTurn(State.Turn.WHITEWIN);
                this.loggGame.fine("Bianco vince con re in " + a.getTo());
            }
        }
        // TODO: implement the winning condition of the capture of the last
        // black checker

        this.movesWithutCapturing++;
        return state;
    }

    public State checkCaptureBlackKingLeft(State state, Action a) {
        // ho il re sulla sinistra
        if (a.getColumnTo() > 1 && state.getPawn(a.getRowTo(), a.getColumnTo() - 1).equalsPawn("K")) {
            // re sul trono
            if (state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("e5")) {
                if (state.getPawn(3, 4).equalsPawn("B") && state.getPawn(4, 3).equalsPawn("B")
                        && state.getPawn(5, 4).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
                }
            }
            // re adiacente al trono
            if (state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("e4")) {
                if (state.getPawn(2, 4).equalsPawn("B") && state.getPawn(3, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
                }
            }
            if (state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("f5")) {
                if (state.getPawn(5, 5).equalsPawn("B") && state.getPawn(3, 5).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
                }
            }
            if (state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("e6")) {
                if (state.getPawn(6, 4).equalsPawn("B") && state.getPawn(5, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
                }
            }
            // sono fuori dalle zone del trono
            if (!state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("e5")
                    && !state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("e6")
                    && !state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("e4")
                    && !state.getBox(a.getRowTo(), a.getColumnTo() - 1).equals("f5")) {
                if (state.getPawn(a.getRowTo(), a.getColumnTo() - 2).equalsPawn("B")
                        || this.citadels.contains(state.getBox(a.getRowTo(), a.getColumnTo() - 2))) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
                }
            }
        }
        return state;
    }

    public State checkCaptureBlackKingRight(State state, Action a) {
        // ho il re sulla destra
        if (a.getColumnTo() < state.getBoard().length - 2
                && (state.getPawn(a.getRowTo(), a.getColumnTo() + 1).equalsPawn("K"))) {
            // re sul trono
            if (state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("e5")) {
                if (state.getPawn(3, 4).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B")
                        && state.getPawn(5, 4).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
                }
            }
            // re adiacente al trono
            if (state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("e4")) {
                if (state.getPawn(2, 4).equalsPawn("B") && state.getPawn(3, 5).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
                }
            }
            if (state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("e6")) {
                if (state.getPawn(5, 5).equalsPawn("B") && state.getPawn(6, 4).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
                }
            }
            if (state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("d5")) {
                if (state.getPawn(3, 3).equalsPawn("B") && state.getPawn(5, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
                }
            }
            // sono fuori dalle zone del trono
            if (!state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("d5")
                    && !state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("e6")
                    && !state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("e4")
                    && !state.getBox(a.getRowTo(), a.getColumnTo() + 1).equals("e5")) {
                if (state.getPawn(a.getRowTo(), a.getColumnTo() + 2).equalsPawn("B")
                        || this.citadels.contains(state.getBox(a.getRowTo(), a.getColumnTo() + 2))) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
                }
            }
        }
        return state;
    }

    public State checkCaptureBlackKingDown(State state, Action a) {
        // ho il re sotto
        if (a.getRowTo() < state.getBoard().length - 2
                && state.getPawn(a.getRowTo() + 1, a.getColumnTo()).equalsPawn("K")) {
            //System.out.println("Ho il re sotto");
            // re sul trono
            if (state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("e5")) {
                if (state.getPawn(5, 4).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B")
                        && state.getPawn(4, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
                }
            }
            // re adiacente al trono
            if (state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("e4")) {
                if (state.getPawn(3, 3).equalsPawn("B") && state.getPawn(3, 5).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
                }
            }
            if (state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("d5")) {
                if (state.getPawn(4, 2).equalsPawn("B") && state.getPawn(5, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
                }
            }
            if (state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("f5")) {
                if (state.getPawn(4, 6).equalsPawn("B") && state.getPawn(5, 5).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
                }
            }
            // sono fuori dalle zone del trono
            if (!state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("d5")
                    && !state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("e4")
                    && !state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("f5")
                    && !state.getBox(a.getRowTo() + 1, a.getColumnTo()).equals("e5")) {
                if (state.getPawn(a.getRowTo() + 2, a.getColumnTo()).equalsPawn("B")
                        || this.citadels.contains(state.getBox(a.getRowTo() + 2, a.getColumnTo()))) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
                }
            }
        }
        return state;
    }

    public State checkCaptureBlackKingUp(State state, Action a) {
        // ho il re sopra
        if (a.getRowTo() > 1 && state.getPawn(a.getRowTo() - 1, a.getColumnTo()).equalsPawn("K")) {
            // re sul trono
            if (state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("e5")) {
                if (state.getPawn(3, 4).equalsPawn("B") && state.getPawn(4, 5).equalsPawn("B")
                        && state.getPawn(4, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
                }
            }
            // re adiacente al trono
            if (state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("e6")) {
                if (state.getPawn(5, 3).equalsPawn("B") && state.getPawn(5, 5).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
                }
            }
            if (state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("d5")) {
                if (state.getPawn(4, 2).equalsPawn("B") && state.getPawn(3, 3).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
                }
            }
            if (state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("f5")) {
                if (state.getPawn(4, 6).equalsPawn("B") && state.getPawn(3, 5).equalsPawn("B")) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
                }
            }
            // sono fuori dalle zone del trono
            if (!state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("d5")
                    && !state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("e6")
                    && !state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("f5")
                    && !state.getBox(a.getRowTo() - 1, a.getColumnTo()).equals("e5")) {
                if (state.getPawn(a.getRowTo() - 2, a.getColumnTo()).equalsPawn("B")
                        || this.citadels.contains(state.getBox(a.getRowTo() - 2, a.getColumnTo()))) {
                    state.setTurn(State.Turn.BLACKWIN);
                    this.loggGame
                            .fine("Nero vince con re catturato in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
                }
            }
        }
        return state;
    }

    public State checkCaptureBlackPawnRight(State state, Action a) {
        // mangio a destra
        if (a.getColumnTo() < state.getBoard().length - 2
                && state.getPawn(a.getRowTo(), a.getColumnTo() + 1).equalsPawn("W")) {
            if (state.getPawn(a.getRowTo(), a.getColumnTo() + 2).equalsPawn("B")) {
                state.removePawn(a.getRowTo(), a.getColumnTo() + 1);
                this.movesWithutCapturing = -1;
                this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
            }
            if (state.getPawn(a.getRowTo(), a.getColumnTo() + 2).equalsPawn("T")) {
                state.removePawn(a.getRowTo(), a.getColumnTo() + 1);
                this.movesWithutCapturing = -1;
                this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
            }
            if (this.citadels.contains(state.getBox(a.getRowTo(), a.getColumnTo() + 2))) {
                state.removePawn(a.getRowTo(), a.getColumnTo() + 1);
                this.movesWithutCapturing = -1;
                this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
            }
            if (state.getBox(a.getRowTo(), a.getColumnTo() + 2).equals("e5")) {
                state.removePawn(a.getRowTo(), a.getColumnTo() + 1);
                this.movesWithutCapturing = -1;
                this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo(), a.getColumnTo() + 1));
            }

        }

        return state;
    }

    public State checkCaptureBlackPawnLeft(State state, Action a) {
        // mangio a sinistra
        if (a.getColumnTo() > 1 && state.getPawn(a.getRowTo(), a.getColumnTo() - 1).equalsPawn("W")
                && (state.getPawn(a.getRowTo(), a.getColumnTo() - 2).equalsPawn("B")
                || state.getPawn(a.getRowTo(), a.getColumnTo() - 2).equalsPawn("T")
                || this.citadels.contains(state.getBox(a.getRowTo(), a.getColumnTo() - 2))
                || (state.getBox(a.getRowTo(), a.getColumnTo() - 2).equals("e5")))) {
            state.removePawn(a.getRowTo(), a.getColumnTo() - 1);
            this.movesWithutCapturing = -1;
            this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo(), a.getColumnTo() - 1));
        }
        return state;
    }

    public State checkCaptureBlackPawnUp(State state, Action a) {
        // controllo se mangio sopra
        if (a.getRowTo() > 1 && state.getPawn(a.getRowTo() - 1, a.getColumnTo()).equalsPawn("W")
                && (state.getPawn(a.getRowTo() - 2, a.getColumnTo()).equalsPawn("B")
                || state.getPawn(a.getRowTo() - 2, a.getColumnTo()).equalsPawn("T")
                || this.citadels.contains(state.getBox(a.getRowTo() - 2, a.getColumnTo()))
                || (state.getBox(a.getRowTo() - 2, a.getColumnTo()).equals("e5")))) {
            state.removePawn(a.getRowTo() - 1, a.getColumnTo());
            this.movesWithutCapturing = -1;
            this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo() - 1, a.getColumnTo()));
        }
        return state;
    }

    public State checkCaptureBlackPawnDown(State state, Action a) {
        // controllo se mangio sotto
        if (a.getRowTo() < state.getBoard().length - 2
                && state.getPawn(a.getRowTo() + 1, a.getColumnTo()).equalsPawn("W")
                && (state.getPawn(a.getRowTo() + 2, a.getColumnTo()).equalsPawn("B")
                || state.getPawn(a.getRowTo() + 2, a.getColumnTo()).equalsPawn("T")
                || this.citadels.contains(state.getBox(a.getRowTo() + 2, a.getColumnTo()))
                || (state.getBox(a.getRowTo() + 2, a.getColumnTo()).equals("e5")))) {
            state.removePawn(a.getRowTo() + 1, a.getColumnTo());
            this.movesWithutCapturing = -1;
            this.loggGame.fine("Pedina bianca rimossa in: " + state.getBox(a.getRowTo() + 1, a.getColumnTo()));
        }
        return state;
    }

    public State checkCaptureBlack(State state, Action a) {

        this.checkCaptureBlackPawnRight(state, a);
        this.checkCaptureBlackPawnLeft(state, a);
        this.checkCaptureBlackPawnUp(state, a);
        this.checkCaptureBlackPawnDown(state, a);
        this.checkCaptureBlackKingRight(state, a);
        this.checkCaptureBlackKingLeft(state, a);
        this.checkCaptureBlackKingDown(state, a);
        this.checkCaptureBlackKingUp(state, a);

        this.movesWithutCapturing++;
        return state;
    }

    public State movePawn(State state, Action a) {
        State.Pawn pawn = state.getPawn(a.getRowFrom(), a.getColumnFrom());
        State.Pawn[][] newBoard = state.getBoard();
        // State newState = new State();
        this.loggGame.fine("Movimento pedina");
        // libero il trono o una casella qualunque
        if (a.getColumnFrom() == 4 && a.getRowFrom() == 4) {
            newBoard[a.getRowFrom()][a.getColumnFrom()] = State.Pawn.THRONE;
        } else {
            newBoard[a.getRowFrom()][a.getColumnFrom()] = State.Pawn.EMPTY;
        }

        // metto nel nuovo tabellone la pedina mossa
        newBoard[a.getRowTo()][a.getColumnTo()] = pawn;
        // aggiorno il tabellone
        state.setBoard(newBoard);
        // cambio il turno
        if (state.getTurn().equalsTurn(State.Turn.WHITE.toString())) {
            state.setTurn(State.Turn.BLACK);
        } else {
            state.setTurn(State.Turn.WHITE);
        }

        return state;
    }

}
