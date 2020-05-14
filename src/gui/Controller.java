package gui;

import game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Controller {
    @FXML
    public VBox scoreTable;
    public TextArea log;
    public TextField numOfPar;
    public TextField toStage;
    public TextField firstInStage;
    public TextField acc;
    public Button first;


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
        if (stage.getClass().getName().equals(GameStageTable.class.getName()))
            stake(toStage, false);
        else
            stakeO();
    }

    private void stakeO() {
        try {
            if (Integer.parseInt(numOfPar.getText()) > this.stage.getParticipants().length || Integer.parseInt(numOfPar.getText()) <= 0)
                throw new NumberFormatException();
            if (Integer.parseInt(toStage.getText()) <= 0)
                throw new NumberFormatException();
            player.doStake(
                    ((GameStageOlimp) this.stage).getGroups().get(
                            (Integer.parseInt(numOfPar.getText()) - 1) / 2)
                            [Integer.parseInt(numOfPar.getText()) - (1 + (((Integer.parseInt(numOfPar.getText()) - 1) / 2) * 2))]
                    , Integer.parseInt(toStage.getText()), false
            );
            log.setText("success bet: " + toStage.getText());
            acc.setText(String.valueOf(player.getAccount()));
        } catch (NumberFormatException e) {
            log.setText("Number Format Is Incorrect");
        }
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
        } else if (stage.getClass().getName().equals(GameStageTable.class.getName())) {
            stage.simulateStage();
        }

        log.setText(stage.gameProduce(player, this));
        refresh();
    }

    private void refresh() {
        acc.setText(String.valueOf(player.getAccount()));

        int id = 1;
        if (stage.getClass().getName().equals(GameStageTable.class.getName())) {
            scoreTable.getChildren().clear();
            for (Participant p :
                    stage.getParticipants()) {
                Text text = new Text("#" + id++ + " " + p);
                text.setFill(p.getColor());
                scoreTable.getChildren().add(text);
            }
        } else {
            if (stage.getParticipants().length == 1) {
                scoreTable.getChildren().clear();
                Text text1 = new Text("Winner!");
                text1.setFill(stage.getParticipants()[0].getColor());
                scoreTable.getChildren().add(text1);
            } else {
                firstInStage.setText("");
                firstInStage.setDisable(true);
                first.setDisable(true);
                scoreTable.getChildren().clear();
                stage.simulateStage();
                ((GameStageOlimp) stage).doGroups();
                int index = 1;
                for (Participant[] group :
                        ((GameStageOlimp) stage).getGroups()) {
                    Text text = new Text("Group #" + id++ + "========");
                    scoreTable.getChildren().add(text);
                    for (Participant p :
                            group) {
                        Text text1 = new Text("#" + index++ + " " + p);
                        text1.setFill(p.getColor());
                        scoreTable.getChildren().add(text1);
                    }
                }
            }
        }
    }
}
