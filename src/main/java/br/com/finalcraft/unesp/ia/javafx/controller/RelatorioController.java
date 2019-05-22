package br.com.finalcraft.unesp.ia.javafx.controller;

import br.com.finalcraft.unesp.ia.busca.Busca;
import br.com.finalcraft.unesp.ia.busca.Direction;
import br.com.finalcraft.unesp.ia.busca.Matriz;
import br.com.finalcraft.unesp.ia.javafx.TrueMain;
import br.com.finalcraft.unesp.ia.javafx.view.MyFXMLs;
import br.com.finalcraft.unesp.ia.busca.machinestate.MachineState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RelatorioController {

    private static Stage dialog;

    public static RelatorioController instance;
    public static AlgoritmStats[] algoritmStats = new AlgoritmStats[4];
    public static List<AlgoritmStats> algoritmStatsList = new ArrayList<AlgoritmStats>();

    @FXML
    void initialize() {
        instance = this;

        algoritmStats[0] = new AlgoritmStats(tempo1,solucao1,resultado1,posicao1);
        algoritmStats[1] = new AlgoritmStats(tempo2,solucao2,resultado2,posicao2);
        algoritmStats[2] = new AlgoritmStats(tempo3,solucao3,resultado3,posicao3);
        algoritmStats[3] = new AlgoritmStats(tempo4,solucao4,resultado4,posicao4);

        algoritmStatsList.addAll(Arrays.asList(algoritmStats));
    }

    public static void show(){
        dialog.show();
    }

    public static void setUp(){
        dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        // Defines a modal window that blocks events from being
        // delivered to any other application window.
        dialog.initOwner(TrueMain.primaryStage);

        Scene newSceneWindow1 = new Scene(MyFXMLs.relatoriomaker);
        newSceneWindow1.setFill(Color.TRANSPARENT);

        dialog.setScene(newSceneWindow1);
    }

    @FXML
    private Label operationNumber;

    @FXML
    private Label posicao1;

    @FXML
    private Label posicao2;

    @FXML
    private Label posicao3;

    @FXML
    private Label posicao4;

    @FXML
    private Label tempo1;

    @FXML
    private Label tempo2;

    @FXML
    private Label tempo3;

    @FXML
    private Label tempo4;

    @FXML
    private Label solucao1;

    @FXML
    private Label solucao2;

    @FXML
    private Label solucao3;

    @FXML
    private Label solucao4;

    @FXML
    private Label resultado1;

    @FXML
    private Label resultado2;

    @FXML
    private Label resultado3;

    @FXML
    private Label resultado4;


    private static MachineState machineState = null;
    private static void saveTemplate() {
        machineState = new MachineState();
    }

    private static void loadTemplate() {
        if (machineState != null){
            machineState.restoreMachineState();
        }
    }

    @FXML
    void onClose() {
        dialog.close();
    }

    private static int nNumber = 0;
    @FXML
    void onSimulacao() {
        Thread th = new Thread(){
            @Override
            public void run() {
                try {
                    long start;
                    long end;
                    List<Direction> resultado;

                    for (int i = 0; i < 100; i++){
                        nNumber++;
                        Matriz.embaralhar(nNumber);
                        saveTemplate();

                        //Computandado Dados
                        start = System.currentTimeMillis();
                        resultado = Busca.aleatoria();
                        end = System.currentTimeMillis();
                        algoritmStats[0].compute(end - start,resultado.size());

                        loadTemplate();
                        //Computandado Dados
                        start = System.currentTimeMillis();
                        resultado = Busca.heuristica1();
                        end = System.currentTimeMillis();
                        algoritmStats[1].compute(end - start,resultado.size());

                        loadTemplate();
                        //Computandado Dados
                        start = System.currentTimeMillis();
                        resultado = Busca.heuristica2();
                        end = System.currentTimeMillis();
                        algoritmStats[2].compute(end - start,resultado.size());

                        loadTemplate();
                        //Computandado Dados
                        start = System.currentTimeMillis();
                        resultado = Busca.heuristica3();
                        end = System.currentTimeMillis();
                        algoritmStats[3].compute(end - start,resultado.size());


                        updateLabels();
                        Thread.sleep(100);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        th.setDaemon(true);
        th.start();
    }

    public static void updateLabels(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                instance.operationNumber.setText("N == " + nNumber);
                for (AlgoritmStats algoritmStats : algoritmStats){
                    algoritmStats.tempo.setText("" + algoritmStats.lastTime);
                    algoritmStats.solucao.setText("" + algoritmStats.lastSolution);
                    algoritmStats.resultado.setText("" + algoritmStats.totalPontos);
                }

                Collections.sort(algoritmStatsList);
                int i = 0;
                for (AlgoritmStats algoritmStats : algoritmStatsList){
                    i++;
                    algoritmStats.posicao.setText( i + "º lugar");
                }
            }
        });
    }

    private class AlgoritmStats implements Comparable<AlgoritmStats>{

        public long lastTime = 0;
        public long lastSolution = 0;

        public long totalPontos;

        public Label tempo;
        public Label solucao;
        public Label resultado;
        public Label posicao;

        public AlgoritmStats(Label tempo, Label solucao, Label resultado, Label posicao) {
            this.tempo = tempo;
            this.solucao = solucao;
            this.resultado = resultado;
            this.posicao = posicao;
        }

        @Override
        public int compareTo(AlgoritmStats o) {
            return Long.compare(this.totalPontos,o.totalPontos);
        }

        public void compute(long time, long solution){
            this.lastTime = time;
            this.lastSolution = solution;
            this.totalPontos = this.totalPontos + time + (solution * 10);
        }
    }
}
