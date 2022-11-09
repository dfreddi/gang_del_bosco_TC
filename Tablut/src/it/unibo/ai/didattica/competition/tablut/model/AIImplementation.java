package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public class AIImplementation implements AI{

    private final OpeningManager openingManager = new OpeningManagerMock();
    private final SearchStrategy searchStrategy = new SearchStrategyMock();

    @Override
    public Action getMove(State state) {
        if(openingManager.isOpeningMoveAvailable(state)){
            return openingManager.getOpeningMove(state);
        }
        return searchStrategy.getBestMove(state);
    }
}
