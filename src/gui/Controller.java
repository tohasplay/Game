package gui;

import game.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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

    int stageN = 1;
    public TextField stageNum;


    private GameStage stage = new GameStageTable(100);
    private Player player = new Player();
    private boolean zeroStage = true;

    public void initialize() {
        refresh();
    }

// --Commented out by Inspection START (5/15/2020 2:30 AM):
//    public GameStage getStage() {
//        return stage;
//    }
// --Commented out by Inspection STOP (5/15/2020 2:30 AM)

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

    private void counter() {
        stageNum.setText(String.valueOf(stageN++));
    }

    private void refresh() {
        acc.setText(String.valueOf(player.getAccount()));

        int id = 1;
        if (stage.getClass().getName().equals(GameStageTable.class.getName())) {
            counter();
            firstInStage.setDisable(false);
            first.setDisable(false);
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
                counter();
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
                        Text text1 = new Text("#" + index++ + " " + p.toStringOne());
                        text1.setFill(p.getColor());
                        scoreTable.getChildren().add(text1);
                    }
                }
            }
        }
    }

    public void restart() {
        stageN = 0;
        counter();

        stage = new GameStageTable(100);
        player = new Player();
        zeroStage = true;
        refresh();
    }
}
