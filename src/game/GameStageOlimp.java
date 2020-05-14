package game;

import gui.Controller;

import java.util.ArrayList;
import java.util.Arrays;

public class GameStageOlimp extends GameStage {

    /**
     * @see GameStage#participants
     */

    ArrayList<Participant[]> groups = new ArrayList<>();

    public GameStageOlimp(Participant[] participants) {
        this.participants = participants;
        newGroups();
    }

    private void newGroups() {
        for (int i = 0; i < participants.length / 2; i++) {
            groups.add(new Participant[2]);
        }
    }

    public ArrayList<Participant[]> getGroups() {
        return groups;
    }

    @Override
    public String gameProduce(Player player, Controller controller) {
        if (participants.length == 1) {
            return "Winner already defined";
        }
        int id;
        id = 0;
        Participant[] tmp = new Participant[groups.size()];
        for (Participant[] group :
                groups) {
            tmp[id++] = max(group);
        }

        participants = tmp;


        groups.clear();
        newGroups();

        return player.takeStakes(participants);
    }

    public void doGroups() {

        for (Participant p : participants){
            System.out.println(p.toStringFull());
        }

        for (Participant p :
                participants) {
            p.initPO();
            p.initC5();
            p.round();
        }
        participants[0].clearB();

        int id = participants.length;
        for (Participant[] group : groups) {
            for (int j = 0; j < group.length; j++) {
                int index = (int) (Math.random() * id--);
                group[j] = participants[index];
                participants[index].clearS();
                Arrays.sort(participants);
            }
        }
    }

    Participant max(Participant[] participants) {
        if (participants[0].s > participants[1].s) {
            return participants[0];
        } else {
            return participants[1];
        }
    }

    @Override
    public void simulateStage() {
        for (Participant p :
                participants) {
            for (int i = 0; i < 10 * participants.length; i++) {
                p.addS(Math.random() * 10 * p.c5);
            }
        }
    }
}

