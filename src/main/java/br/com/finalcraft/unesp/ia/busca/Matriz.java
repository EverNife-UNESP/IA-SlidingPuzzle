/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finalcraft.unesp.ia.busca;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Matriz {
    public static int[][] matriz = {{1,2,3},{4,5,6},{7,8,99}};
    public static int posX = 2;
    public static int posY = 2;
    public static Direction opAnterior = Direction.NULO;
    public static int opAnteriorValor = 0;
    public static int qtdMovimentos = 0;
    private static final Random radom  = new Random();

    public static List<Direction> embaralhar(int qtd) {
        opAnterior = Direction.NULO;
        qtdMovimentos = qtd;
        List<Direction> historyMoves = new ArrayList<Direction>();
        while(qtd > 0){
            ArrayList<Direction> movPossiveis = new ArrayList<Direction>();

            if((posX > 0) && (opAnterior != Direction.BAIXO)){
                movPossiveis.add(Direction.CIMA);
            }
            if((posX < 2) && (opAnterior != Direction.CIMA)){
                movPossiveis.add(Direction.BAIXO);
            }
            if((posY < 2) && (opAnterior != Direction.ESQUERDA)){
                movPossiveis.add(Direction.DIREITA);
            }
            if((posY > 0) && (opAnterior != Direction.DIREITA)){
                movPossiveis.add(Direction.ESQUERDA);
            }

            Direction randomDirection = movPossiveis.get(radom.nextInt(movPossiveis.size()));

            Movimento.realizaMov(randomDirection);
            historyMoves.add(randomDirection);
            //event.fireEvent();
            qtd--;
        }
        Matriz.opAnterior = Direction.NULO;
        return historyMoves;
    }

    private static int doubleValue(int a){
        return (int)Math.pow(a,2);
    }
    public static int getValorHeuristico(){

        int dif = doubleValue((matriz[0][0] != 99 ? matriz[0][0] : 0)  - 1);
        dif += doubleValue((matriz[0][1] != 99 ? matriz[0][1] : 0) - 2);
        dif += doubleValue((matriz[0][2] != 99 ? matriz[0][2] : 0) - 3);
        dif += doubleValue((matriz[1][0] != 99 ? matriz[1][0] : 0) - 4);
        dif += doubleValue((matriz[1][1] != 99 ? matriz[1][1] : 0) - 5);
        dif += doubleValue((matriz[1][2] != 99 ? matriz[1][2] : 0) - 6);
        dif += doubleValue((matriz[2][0] != 99 ? matriz[2][0] : 0) - 7);
        dif += doubleValue((matriz[2][1] != 99 ? matriz[2][1] : 0) - 8);
        dif += doubleValue((matriz[2][2] != 99 ? matriz[2][2] : 0) - 0);

        return dif;
    }
}
