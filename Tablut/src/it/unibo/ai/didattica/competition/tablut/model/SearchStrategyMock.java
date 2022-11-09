package it.unibo.ai.didattica.competition.tablut.model;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public class SearchStrategyMock implements SearchStrategy{

    @Override
    public Action getBestMove(State state) {
        return null;
    }
}
