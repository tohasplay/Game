package game;

import java.util.HashMap;

public class Player {
    double account = 100;
    final HashMap<Participant, HashMap<Double, Double>> stake_coefficient = new HashMap<>();

    public double getAccount() {
        return account;
    }

    public void doStake(Participant participant, double stake, boolean winner) {

        if (account > 0 && stake > 0 && account - stake >= 0) {
            account -= stake;
            participant.addS(stake);
            stake_coefficient.computeIfAbsent(participant, k -> new HashMap<>());
            if (winner)
                stake_coefficient.get(participant).put(stake, participant.getC());
            else
                stake_coefficient.get(participant).put(stake, participant.getC5());
        }
    }

    public String takeStakes(Participant[] participants) {
        StringBuilder stringBuffer = new StringBuilder();
        for (Participant p :
                stake_coefficient.keySet()) {
            for (Participant s :
                    participants) {
                if (p.equals(s)) {
                    double stakes = 0;
                    for (Double d :
                            stake_coefficient.get(p).keySet()) {
                        stakes += d * stake_coefficient.get(p).get(d);
                    }
                    stringBuffer.append("Won: ").append(stakes).append("\n");
                    account += stakes;
                }
            }
            stake_coefficient.get(p).clear();
        }

        return stringBuffer.toString();
    }


}
