package it.unibo.ai.didattica.competition.tablut.model;

import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameImpl implements Game<State, Action, Player> {

    private final GameEvaluator gameEvaluator;

    public GameImpl(GameEvaluator gameEvaluator) {
        this.gameEvaluator = gameEvaluator;
    }

    @Override
    public State getInitialState() {
        return new StateTablut();
    }

    @Override
    public Player[] getPlayers() {
        return Player.values();
    }

    @Override
    public Player getPlayer(State state) {
        if(state.getTurn().equals(State.Turn.WHITE) || state.getTurn().equals(State.Turn.WHITEWIN)){
            return Player.WHITE;
        }
        return Player.BLACK;
    }

    @Override
    public List<Action> getActions(State state) {
        return new ArrayList<>(gameEvaluator.getLegalMoves(state));
    }

    @Override
    public State getResult(State state, Action action) {
        return gameEvaluator.performAction(state, action);
    }

    @Override
    public boolean isTerminal(State state) {
        return state.getTurn().equals(State.Turn.WHITEWIN) || state.getTurn().equals(State.Turn.BLACKWIN) ||
                state.getTurn().equals(State.Turn.DRAW);
    }

    @Override
    public double getUtility(State state, Player player) {
        return 0;
    }
}
