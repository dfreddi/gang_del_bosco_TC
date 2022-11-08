package it.unibo.ai.didattica.competition.tablut.client;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.model.AI;
import it.unibo.ai.didattica.competition.tablut.model.AIMock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class TablutAIClient extends TablutClient {

    public TablutAIClient(String player) throws UnknownHostException, IOException {
        super(player, "AIInterface");
    }

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

        if (args.length == 0) {
            System.out.println("You must specify which player you are (WHITE or BLACK)!");
            System.exit(-1);
        }
        System.out.println("Selected this: " + args[0]);

        TablutClient client = new TablutAIClient(args[0]);

        client.run();
    }

    @Override
    public void run() {

        AI ai = new AIMock();

        System.out.println("You are player " + this.getPlayer().toString() + "!");
        String actionStringFrom = "";
        String actionStringTo = "";
        Action action;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            this.declareName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.getPlayer() == State.Turn.WHITE) {
            System.out.println("You are player " + this.getPlayer().toString() + "!");
            while (true) {
                try {
                    this.read();

                    System.out.println("Current state:");
                    System.out.println(this.getCurrentState().toString());
                    if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
                        System.out.println("Player " + this.getPlayer().toString() + ", do your move: ");

                        //NOSTRO CODICE MOSTRUOSO ALLUCINANTE
                        action = ai.getMove(this.getCurrentState());
                        this.write(action);
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
                        System.out.println("Waiting for your opponent move... ");
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                        System.out.println("YOU WIN!");
                        System.exit(0);
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                        System.out.println("YOU LOSE!");
                        System.exit(0);
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.DRAW)) {
                        System.out.println("DRAW!");
                        System.exit(0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        } else {
            System.out.println("You are player " + this.getPlayer().toString() + "!");
            while (true) {
                try {
                    this.read();
                    System.out.println("Current state:");
                    System.out.println(this.getCurrentState().toString());
                    if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACK)) {
                        System.out.println("Player " + this.getPlayer().toString() + ", do your move: ");
                        action = ai.getMove(this.getCurrentState());
                        this.write(action);
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITE)) {
                        System.out.println("Waiting for your opponent move... ");
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.WHITEWIN)) {
                        System.out.println("YOU LOSE!");
                        System.exit(0);
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.BLACKWIN)) {
                        System.out.println("YOU WIN!");
                        System.exit(0);
                    } else if (this.getCurrentState().getTurn().equals(StateTablut.Turn.DRAW)) {
                        System.out.println("DRAW!");
                        System.exit(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
