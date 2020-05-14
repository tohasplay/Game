package game;

import java.util.Arrays;

abstract class Stage {
    Participant[] participants;

    public Stage(){}

    public Stage(int k) {
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

    public abstract String gameProduce(Player player);

    public abstract void simulateStage();


}
