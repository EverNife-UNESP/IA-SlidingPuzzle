package br.com.finalcraft.unesp.ia.busca.machinestate;

import br.com.finalcraft.unesp.ia.busca.Direction;
import br.com.finalcraft.unesp.ia.busca.Matriz;

import java.util.ArrayList;
import java.util.List;

public class MachineState implements Comparable<MachineState>{

    public int valorHeuristico = 0;
    public int[][] currentMatriz = new int[3][3];
    public List<Direction> pathWalked = new ArrayList<Direction>();
    public String identifier;

    public int posX;
    public int posY;
    public Direction opAnterior;

    public String getIdentifier(){
        return identifier;
    }
    public MachineState() {
        this(new ArrayList<Direction>());
    }

    public MachineState(List<Direction> currentPath) {

        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i < 3;i++){
            for(int j=0; j < 3; j++){
                currentMatriz[i][j] = Matriz.matriz[i][j];
                stringBuilder.append("" + currentMatriz[i][j]);
            }
        }
        this.identifier = stringBuilder.toString();

        this.pathWalked.addAll(currentPath);
        this.valorHeuristico = this.pathWalked.size() + Matriz.getValorHeuristico();

        this.posX = Matriz.posX;
        this.posY = Matriz.posY;

        this.opAnterior = Matriz.opAnterior;
    }

    public void restoreMachineState(){
        Matriz.matriz[0][0] = this.currentMatriz[0][0];
        Matriz.matriz[0][1] = this.currentMatriz[0][1];
        Matriz.matriz[0][2] = this.currentMatriz[0][2];

        Matriz.matriz[1][0] = this.currentMatriz[1][0];
        Matriz.matriz[1][1] = this.currentMatriz[1][1];
        Matriz.matriz[1][2] = this.currentMatriz[1][2];

        Matriz.matriz[2][0] = this.currentMatriz[2][0];
        Matriz.matriz[2][1] = this.currentMatriz[2][1];
        Matriz.matriz[2][2] = this.currentMatriz[2][2];

        Matriz.posX     = this.posX;
        Matriz.posY     = this.posY;

        Matriz.opAnterior = this.opAnterior;
    }

    public List<Direction> copyPathWalked() {
        List<Direction> directionList = new ArrayList<Direction>();
        directionList.addAll(pathWalked);
        return directionList;
    }

    @Override
    public int compareTo(MachineState o) {
        return Integer.compare(this.valorHeuristico,o.valorHeuristico);
    }
}
