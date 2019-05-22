/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.finalcraft.unesp.ia.busca;

import java.util.ArrayList;

public class Movimento {

    public static int calculosRealizados = 0;

    public static void resetCalcs(){
        calculosRealizados = 0;
    }

    public static void addCalcs(){
        calculosRealizados++;
    }

    public static ArrayList<Direction> montaListaMov(){
        ArrayList<Direction> movPossiveis = new ArrayList<Direction>();

        if((Matriz.posX > 0) && (!Matriz.opAnterior.equals(Direction.BAIXO))){
            movPossiveis.add(Direction.CIMA);
        }
        if((Matriz.posX < 2) && (!Matriz.opAnterior.equals(Direction.CIMA))){
            movPossiveis.add(Direction.BAIXO);
        }
        if((Matriz.posY < 2) && (!Matriz.opAnterior.equals(Direction.ESQUERDA))){
            movPossiveis.add(Direction.DIREITA);
        }
        if((Matriz.posY > 0) && (!Matriz.opAnterior.equals(Direction.DIREITA))){
            movPossiveis.add(Direction.ESQUERDA);
        }

        return movPossiveis;
    }

    public static void realizaMov(Direction direcao){
        addCalcs();
        switch(direcao){
            case CIMA:
                Movimento.cima();
                //System.out.println("Foi para Cima");
                break;

            case BAIXO:
                Movimento.baixo();
                //System.out.println("Foi para Baixo");
                break;

            case DIREITA:
                Movimento.direita();
                //System.out.println("Foi para Direita");
                break;

            case ESQUERDA:
                Movimento.esquerda();
                //System.out.println("Foi para Esquerda");
                break;
        }
    }

    private static void cima(){
        Matriz.opAnteriorValor = Matriz.matriz[Matriz.posX-1][Matriz.posY];
        Matriz.opAnterior = Direction.CIMA;
        Matriz.matriz[Matriz.posX][Matriz.posY] = Matriz.matriz[Matriz.posX-1][Matriz.posY];
        Matriz.matriz[Matriz.posX-1][Matriz.posY] = 99;
        Matriz.posX = Matriz.posX - 1;
//        System.out.println("Cima");

    }

    private static void baixo(){
        Matriz.opAnteriorValor = Matriz.matriz[Matriz.posX+1][Matriz.posY];
        Matriz.opAnterior = Direction.BAIXO;
        Matriz.matriz[Matriz.posX][Matriz.posY] = Matriz.matriz[Matriz.posX+1][Matriz.posY];
        Matriz.matriz[Matriz.posX+1][Matriz.posY] = 99;
        Matriz.posX = Matriz.posX + 1;
//        System.out.println("Baixo");
    }

    private static void direita(){
        Matriz.opAnteriorValor = Matriz.matriz[Matriz.posX][Matriz.posY+1];
        Matriz.opAnterior = Direction.DIREITA;
        Matriz.matriz[Matriz.posX][Matriz.posY] = Matriz.matriz[Matriz.posX][Matriz.posY+1];
        Matriz.matriz[Matriz.posX][Matriz.posY+1] = 99;
        Matriz.posY = Matriz.posY + 1;

//        System.out.println("Direita");
    }

    private static void esquerda(){
        Matriz.opAnteriorValor = Matriz.matriz[Matriz.posX][Matriz.posY-1];
        Matriz.opAnterior = Direction.ESQUERDA;
        Matriz.matriz[Matriz.posX][Matriz.posY] = Matriz.matriz[Matriz.posX][Matriz.posY-1];
        Matriz.matriz[Matriz.posX][Matriz.posY-1] = 99;
        Matriz.posY = Matriz.posY - 1;
//        System.out.println("Esquerda");
    }

}
