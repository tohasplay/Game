package game;

import gui.Controller;

import java.util.ArrayList;

public class GameStageOlimp extends GameStage {

    /**
     *  @see GameStage#participants
     */

    ArrayList<Participant[]> groups = new ArrayList<>();

    public GameStageOlimp(Participant[] participants) {
        for (int i = 0; i < 4; i++) {
            groups.add(new Participant[2]);
        }
        this.participants = participants;
    }


    @Override
    public String gameProduce(Player player, Controller controller) {
        return null;
    }

    @Override
    public void simulateStage() {

    }
}
