package br.com.finalcraft.unesp.ia.javafx.controller;

import br.com.finalcraft.unesp.ia.busca.Busca;
import br.com.finalcraft.unesp.ia.busca.Direction;
import br.com.finalcraft.unesp.ia.busca.Matriz;
import br.com.finalcraft.unesp.ia.busca.Movimento;
import br.com.finalcraft.unesp.ia.javafx.TrueMain;
import br.com.finalcraft.unesp.ia.busca.machinestate.MachineState;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class TrueMainController {

    public static TrueMainController instance;

    @FXML
    void initialize() {
        instance = this;
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private JFXButton slot7;

    @FXML
    private JFXButton slot8;

    @FXML
    private JFXButton slot9;

    @FXML
    private JFXButton slot4;

    @FXML
    private JFXButton slot5;

    @FXML
    private JFXButton slot6;

    @FXML
    private JFXButton slot1;

    @FXML
    private JFXButton slot2;

    @FXML
    private JFXButton slot3;

    @FXML
    private JFXSlider executionDelay;

    @FXML
    private JFXToggleButton showMoves;

    @FXML
    private JFXTextField textField;

    @FXML
    private JFXButton aleatorio;

    @FXML
    private JFXButton busca1;

    @FXML
    private JFXButton busca2;

    @FXML
    private JFXButton busca3;

    @FXML
    private JFXButton busca4;

    @FXML
    private JFXButton logMove2;

    @FXML
    private JFXButton logMove1;

    @FXML
    private JFXButton logMove4;

    @FXML
    private JFXButton logMove3;

    @FXML
    private JFXButton logMove5;

    @FXML
    private JFXButton saveTemplate;

    @FXML
    private JFXButton loadTemplate;

    @FXML
    private Label movesLabel;

    @FXML
    private Label calculosLabel;

    public static void updateMoves(){
        instance.movesLabel.setText(moves + "");
    }

    public static void resetMoves(){
        moves = 0;
        updateMoves();
    }

    public static void addMoves(){
        moves++;
        updateMoves();
    }

    private static JFXButton lastButtonSide = null;
    private static int moves = 0;
    public static void animateMoveTo(int value, Direction direction){
        if (lastButtonSide != null){
            lastButtonSide.setText("");
        }
        instance.logMove3.setText("" + value);
        switch (direction){
            case BAIXO:
                instance.logMove1.setText("⬆");
                lastButtonSide = instance.logMove1;
                break;
            case CIMA:
                instance.logMove5.setText("⬇");
                lastButtonSide = instance.logMove5;
                break;
            case DIREITA:
                instance.logMove2.setText("⬅");
                lastButtonSide = instance.logMove2;
                break;
            case ESQUERDA:
                instance.logMove4.setText("➡");
                lastButtonSide = instance.logMove4;
                break;
        }
        updateMatriz();
    }

    public static void updateMatriz(){
        instance.slot1.setText("" + (Matriz.matriz[0][0] != 99 ? Matriz.matriz[0][0] : " "));
        instance.slot2.setText("" + (Matriz.matriz[0][1] != 99 ? Matriz.matriz[0][1] : " "));
        instance.slot3.setText("" + (Matriz.matriz[0][2] != 99 ? Matriz.matriz[0][2] : " "));
        instance.slot4.setText("" + (Matriz.matriz[1][0] != 99 ? Matriz.matriz[1][0] : " "));
        instance.slot5.setText("" + (Matriz.matriz[1][1] != 99 ? Matriz.matriz[1][1] : " "));
        instance.slot6.setText("" + (Matriz.matriz[1][2] != 99 ? Matriz.matriz[1][2] : " "));
        instance.slot7.setText("" + (Matriz.matriz[2][0] != 99 ? Matriz.matriz[2][0] : " "));
        instance.slot8.setText("" + (Matriz.matriz[2][1] != 99 ? Matriz.matriz[2][1] : " "));
        instance.slot9.setText("" + (Matriz.matriz[2][2] != 99 ? Matriz.matriz[2][2] : " "));
    }

    public static void disableAllButtons(){
        instance.aleatorio.setDisable(true);
        instance.busca1.setDisable(true);
        instance.busca2.setDisable(true);
        instance.busca3.setDisable(true);
        instance.busca4.setDisable(true);
        instance.saveTemplate.setDisable(true);
        instance.loadTemplate.setDisable(true);
        instance.calculosLabel.setText("...");
        resetMoves();
    }

    public static void enableAllButtons(){
        instance.aleatorio.setDisable(false);
        instance.busca1.setDisable(false);
        instance.busca2.setDisable(false);
        instance.busca3.setDisable(false);
        instance.busca4.setDisable(false);
        instance.saveTemplate.setDisable(false);
        instance.loadTemplate.setDisable(false);
    }

    @FXML
    void onEmbaralhar(ActionEvent event) {

        int textFildNumber = 0;
        try {
            textFildNumber = Integer.parseInt(textField.getText());
        }catch (Exception ignored){

        }
        final int amount = textFildNumber;

        if (amount <= 0){
            return;
        }

        disableAllButtons();

        disableAllButtons();
        Thread th = new Thread(){
            @Override
            public void run() {
                MachineState startingMachineState = new MachineState();
                List<Direction> directionList = Matriz.embaralhar(amount);
                startingMachineState.restoreMachineState();
                moves = 0;
                for (Direction direction : directionList){
                    Movimento.realizaMov(direction);
                    if (showMoves()){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addMoves();
                                animateMoveTo(Matriz.opAnteriorValor,Matriz.opAnterior);
                            }
                        });
                        try {
                            Thread.sleep(getDelay());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        moves++;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateMoves();
                        enableAllButtons();
                        updateMatriz();
                    }
                });
            }
        };
        th.setDaemon(true);
        th.start();
    }

    @FXML
    void onBuscaAleatoria(ActionEvent event) {
        disableAllButtons();
        Thread th = new Thread(){
            @Override
            public void run() {
                MachineState startingMachineState = new MachineState();
                List<Direction> directionList = Busca.aleatoria();
                startingMachineState.restoreMachineState();
                moves = 0;
                for (Direction direction : directionList){
                    Movimento.realizaMov(direction);
                    if (showMoves()){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addMoves();
                                animateMoveTo(Matriz.opAnteriorValor,Matriz.opAnterior);
                            }
                        });
                        try {
                            Thread.sleep(getDelay());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        moves++;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateMoves();
                        enableAllButtons();
                        updateMatriz();
                    }
                });
            }
        };
        th.setDaemon(true);
        th.start();
    }

    @FXML
    void onHeuristicaOne(ActionEvent event) {
        disableAllButtons();
        Thread th = new Thread(){
            @Override
            public void run() {
                MachineState startingMachineState = new MachineState();
                List<Direction> directionList = Busca.heuristica1();
                startingMachineState.restoreMachineState();
                moves = 0;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        instance.calculosLabel.setText("" + Movimento.calculosRealizados);
                    }
                });
                for (Direction direction : directionList){
                    Movimento.realizaMov(direction);
                    if (showMoves()){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addMoves();
                                animateMoveTo(Matriz.opAnteriorValor,Matriz.opAnterior);
                            }
                        });
                        try {
                            Thread.sleep(getDelay());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        moves++;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateMoves();
                        enableAllButtons();
                        updateMatriz();
                    }
                });
            }
        };
        th.setDaemon(true);
        th.start();
    }

    @FXML
    void onHeuristicaPersonal(ActionEvent event) {
        disableAllButtons();
        Thread th = new Thread(){
            @Override
            public void run() {
                MachineState startingMachineState = new MachineState();
                List<Direction> directionList = Busca.heuristica3();
                startingMachineState.restoreMachineState();
                moves = 0;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        instance.calculosLabel.setText("" + Movimento.calculosRealizados);
                    }
                });
                for (Direction direction : directionList){
                    Movimento.realizaMov(direction);
                    if (showMoves()){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addMoves();
                                animateMoveTo(Matriz.opAnteriorValor,Matriz.opAnterior);
                            }
                        });
                        try {
                            Thread.sleep(getDelay());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        moves++;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateMoves();
                        enableAllButtons();
                        updateMatriz();
                    }
                });
            }
        };
        th.setDaemon(true);
        th.start();
    }

    @FXML
    void onHeuristicaTwo(ActionEvent event) {
        disableAllButtons();
        Thread th = new Thread(){
            @Override
            public void run() {
                MachineState startingMachineState = new MachineState();
                List<Direction> directionList = Busca.heuristica2();
                startingMachineState.restoreMachineState();
                moves = 0;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        instance.calculosLabel.setText("" + Movimento.calculosRealizados);
                    }
                });
                for (Direction direction : directionList){
                    Movimento.realizaMov(direction);
                    if (showMoves()){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addMoves();
                                animateMoveTo(Matriz.opAnteriorValor,Matriz.opAnterior);
                            }
                        });
                        try {
                            Thread.sleep(getDelay());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        moves++;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateMoves();
                        enableAllButtons();
                        updateMatriz();
                    }
                });
            }
        };
        th.setDaemon(true);
        th.start();
    }

    @FXML
    void onMatrizClick(ActionEvent event) {
        JFXButton clickedButton = (JFXButton) event.getSource();

        int slot = Integer.parseInt(clickedButton.getId().charAt(4) + "");
        int number = 0;

        if (slot == 1) number = Matriz.matriz[0][0];
        else if (slot == 2) number = Matriz.matriz[0][1];
        else if (slot == 3) number = Matriz.matriz[0][2];
        else if (slot == 4) number = Matriz.matriz[1][0];
        else if (slot == 5) number = Matriz.matriz[1][1];
        else if (slot == 6) number = Matriz.matriz[1][2];
        else if (slot == 7) number = Matriz.matriz[2][0];
        else if (slot == 8) number = Matriz.matriz[2][1];
        else if (slot == 9) number = Matriz.matriz[2][2];

        if (number == 99) return;

        Direction direction = null;
        Matriz.opAnterior = Direction.NULO;
        for (Direction direcao : Movimento.montaListaMov()){
            switch(direcao){
                case CIMA:
                    if (number == Matriz.matriz[Matriz.posX-1][Matriz.posY]) direction = Direction.CIMA;
                    break;
                case BAIXO:
                    if (number == Matriz.matriz[Matriz.posX+1][Matriz.posY]) direction = Direction.BAIXO;                    //System.out.println("Foi para Baixo");
                    break;
                case DIREITA:
                    if (number == Matriz.matriz[Matriz.posX][Matriz.posY+1]) direction = Direction.DIREITA;
                    break;
                case ESQUERDA:
                    if (number == Matriz.matriz[Matriz.posX][Matriz.posY-1]) direction = Direction.ESQUERDA;
                    break;
            }
        }
        if (direction != null){
            Movimento.realizaMov(direction);
            animateMoveTo(number,direction);
        }
    }

    private static MachineState machineState = null;
    @FXML
    void onSalvarTemplate(ActionEvent event) {
        machineState = new MachineState();
    }

    @FXML
    void onCarregarTemplate(ActionEvent event) {
        if (machineState != null){
            machineState.restoreMachineState();
            updateMatriz();
        }
    }

    @FXML
    void onClose(MouseEvent event) {
        TrueMain.primaryStage.close();
    }

    @FXML
    void onTestarEficiencia() {
        RelatorioController.show();
    }
    
    private static boolean showMoves(){
        return instance.showMoves.isSelected() && instance.executionDelay.getValue() > 0;
    }

    private static int getDelay(){
        return (int) instance.executionDelay.getValue();
    }


}
