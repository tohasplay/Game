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
        } else if (stage.getClass().getName().equals(GameStageTable.class.getName())) {
            stage.simulateStage();
        }

        log.setText(stage.gameProduce(player, this));
        refresh();
    }

    private void refresh() {
        acc.setText(String.valueOf(player.getAccount()));
        StringBuilder stringBuffer = new StringBuilder();

        int id = 1;
        if (stage.getClass().getName().equals(GameStageTable.class.getName())) {
            for (Participant p :
                    stage.getParticipants()) {
                Text text = new Text("#" + id++ + " " + p + '\n');
                text.setFill (p.getColor());
                scoreTable.getChildren().add(text);
            }
        } else {
            if (stage.getParticipants().length == 1) {
                stringBuffer.append("#1").append(' ').append("winner").append('\n');
            } else {
                firstInStage.setText("");
                firstInStage.setDisable(true);
                first.setDisable(true);
                stage.simulateStage();
                ((GameStageOlimp) stage).doGroups();
                int index = 1;
                for (Participant[] group :
                        ((GameStageOlimp) stage).getGroups()) {
                    stringBuffer.append("Group #").append(id++).append("=======").append('\n');
                    for (Participant p :
                            group) {
                        stringBuffer.append('#').append(index++).append(' ').append(p.toStringOne()).append('\n');
                    }
                }
            }
        }
    }
}
