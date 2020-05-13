package game;

import java.util.Arrays;

public class GameController {
    Participant[] participants;

    public GameController(int k) {
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

    public String gameProduce(Player player) {
        if (participants.length == 1) {
            return "Winner already defined";
        }

        Participant[] tmp = new Participant[(int) (participants.length * 0.5)];
        Arrays.sort(participants);

        for (Participant participant : participants) {
            System.out.println(participant.toStringFull());
        }

        if (tmp.length >= 0) System.arraycopy(participants, 0, tmp, 0, tmp.length);

        participants = tmp;

        int level = participants.length / 2;
        int id = participants.length;

        for (Participant p :
                participants) {
            p.initP();
            p.initC();
            p.initC5(id-- > level);
            p.round();
        }

        participants[0].clearB();

        return player.takeStakes(participants);
    }

    public void simulateStage() {
        for (int i = 0; i < 1000; i++) {
            int id = 1;
            for (Participant p :
                    participants) {
                switch ((int) (Math.random() * 2)) {
                    case 0:
                        p.addS((int) (Math.random() * (10 * (p.c + p.c5))));
                        break;
                    case 1:
                        p.addS((int) (Math.random() * (100 / id++)));
                        break;
                }
            }
        }
    }

    public void simulateStage0() {
        for (int i = 0; i < 1000; i++) {
            for (Participant p :
                    participants) {
                p.addS((int) (Math.random() * (10 * (p.c + p.c5))));
            }
        }
    }
}

