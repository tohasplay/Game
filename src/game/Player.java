package game;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import tools.Staker;

import java.util.HashMap;

public class Player {
    double account = 100;
    final HashMap<Participant, Staker<Double, Double>> stake_coefficient = new HashMap<>();
    final HashMap<Participant, Staker<Double,Double>> firstPlace = new HashMap<>();

    public double getAccount() {
        return account;
    }

    public void doStake(Participant participant, double stake, boolean winner) {

        if (account > 0 && stake > 0 && account - stake >= 0) {
            account -= stake;
            participant.addS(stake);
            if (winner) {
                firstPlace.computeIfAbsent(participant, k -> new Staker<>());
                firstPlace.get(participant).put(stake, participant.getC());
            }
            else {
                stake_coefficient.computeIfAbsent(participant, k -> new Staker<>());
                stake_coefficient.get(participant).put(stake, participant.getC5());
            }
        }
    }

    public String takeStakes(Participant[] participants) {
        StringBuilder stringBuffer = new StringBuilder();
        for (Participant p :
                stake_coefficient.keySet()) {
            for (Participant s :
                    participants) {
                if (p.equals(s)) {
                    calcWin(stringBuffer, p, stake_coefficient);
                }
            }
            stake_coefficient.get(p).clear();
        }

        for (Participant p :
                firstPlace.keySet()) {
                if (p.equals(participants[0])) {
                    calcWin(stringBuffer, p, firstPlace);
                }
            firstPlace.get(p).clear();
        }

        account = (double) Math.round(account * 100) / 100;


        return stringBuffer.toString();
    }

    private void calcWin(StringBuilder stringBuffer, Participant p, HashMap<Participant, Staker<Double, Double>> stakeMap) {
        double stakes = 0;
        stakes += stakeMap.get(p).getWin();
        stringBuffer.append("Won: ").append(stakes).append("\n");
        account += stakes;
    }


}
