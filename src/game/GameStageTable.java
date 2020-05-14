package game;

import gui.Controller;

import java.util.Arrays;

public class GameStageTable extends GameStage {

    public GameStageTable(int k) {
        super(k);
    }

    @Override
    public String gameProduce(Player player, Controller controller) {
        if (participants.length / 2 <= 8) {
            Participant[] tmp = new Participant[8];
            Arrays.sort(participants);
            System.arraycopy(participants, 0, tmp, 0, tmp.length);
            participants = tmp;
            controller.setStage(new GameStageOlimp(participants));
            for (Participant p :
                    participants) {
                p.initC5();
                p.round();
            }
            return player.takeStakes(participants);
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
            p.clearS();
            p.round();
        }

        participants[0].clearB();

        return player.takeStakes(participants);
    }

    @Override
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
                        p.addS((int) (Math.random() * (25 / id++)));
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

