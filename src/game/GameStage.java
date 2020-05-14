package game;

import gui.Controller;

public abstract class GameStage {
    Participant[] participants;

    public GameStage(){}

    public GameStage(int k) {
        participants = new Participant[k];
        for (int i = 0; i < participants.length; i++) {
            participants[i] = new Participant();
        }
        for (Participant p :
                participants) {
            p.initC(participants.length);
            p.round();
        }
    }

    public Participant[] getParticipants() {
        return participants;
    }

    public abstract String gameProduce(Player player, Controller controller);

    public abstract void simulateStage();


}
