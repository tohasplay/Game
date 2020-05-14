package gui;

import game.GameStage;
import game.GameStageTable;
import game.Participant;
import game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public TextArea scoreTable;
    public TextArea log;
    public TextField numOfPar;
    public TextField toStage;
    public TextField firstInStage;
    public TextField acc;


    private GameStage stage = new GameStageTable(100);
    private final Player player = new Player();
    private boolean zeroStage = true;

    public void initialize() {
        refresh();
    }

    public GameStage getStage() {
        return stage;
    }

    public void setStage(GameStage stage) {
        this.stage = stage;
    }

    public void stakeOnStage() {

        stake(toStage, false);
    }

    public void stakeFirstInStage() {
        stake(firstInStage, true);
    }

    private void stake(TextField stage, boolean winner) {
        try {
            if (Integer.parseInt(numOfPar.getText()) > this.stage.getParticipants().length || Integer.parseInt(numOfPar.getText()) <= 0)
                throw new NumberFormatException();
            if (Integer.parseInt(stage.getText()) <= 0)
                throw new NumberFormatException();
            player.doStake(
                    this.stage.getParticipants()[Integer.parseInt(numOfPar.getText()) - 1], Integer.parseInt(stage.getText()), winner
            );
            log.setText("success bet: " + stage.getText());
            acc.setText(String.valueOf(player.getAccount()));
        } catch (NumberFormatException e) {
            log.setText("Number Format Is Incorrect");
        }
    }

    public void nextStage() {
        if (zeroStage) {
            ((GameStageTable) stage).simulateStage0();
            zeroStage = false;
        } else
            stage.simulateStage();

        log.setText(stage.gameProduce(player, this));
        refresh();
    }

    private void refresh() {
        acc.setText(String.valueOf(player.getAccount()));
        StringBuilder stringBuffer = new StringBuilder();
        int id = 1;
        for (Participant p :
                stage.getParticipants()) {
            stringBuffer.append('#').append(id++).append(' ').append(p).append('\n');
        }
        scoreTable.setText(stringBuffer.toString());
    }
}