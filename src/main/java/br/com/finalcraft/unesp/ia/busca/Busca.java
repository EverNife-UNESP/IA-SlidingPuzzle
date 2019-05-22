package br.com.finalcraft.unesp.ia.busca;

import br.com.finalcraft.unesp.ia.busca.machinestate.MachineState;

import java.util.*;

public class Busca {

    private static final int[][] correctMatriz = {{1, 2, 3}, {4, 5, 6}, {7, 8, 99}};

    public static List<Direction> aleatoria() {
        Random radom = new Random();
        List<Direction> futurePath = new ArrayList<Direction>();
        while (true) {
            if (Arrays.deepEquals(Matriz.matriz, correctMatriz)) {
                return futurePath;
            }
            ArrayList<Direction> movPossiveis = Movimento.montaListaMov();
            Direction randomOperation = movPossiveis.get(radom.nextInt(movPossiveis.size()));
            Movimento.realizaMov(randomOperation);
            futurePath.add(randomOperation);
        }
    }

    public static Map<String,MachineState> latestMoves = new HashMap<String, MachineState>();

    public static boolean hasLooped(MachineState machineState){
        return latestMoves.containsKey(machineState.getIdentifier());
    }

    public static List<Direction> heuristica1(){
        Movimento.resetCalcs();
        Matriz.opAnterior = Direction.NULO;
        //Heap para ordenar os estados por peso
        PriorityQueue<MachineState> priorityQueue = new PriorityQueue<MachineState>();
        latestMoves.clear();

        List<Direction> futurePath = new ArrayList<Direction>();
        MachineState startingMachineState = new MachineState(futurePath);
        priorityQueue.add(startingMachineState);

        while (true){
            MachineState bestOption = priorityQueue.poll();
            bestOption.restoreMachineState();

            if(Arrays.deepEquals(Matriz.matriz, correctMatriz)){
                return bestOption.pathWalked;
            }
            if (hasLooped(bestOption)){
                continue;
            }

            latestMoves.put(bestOption.getIdentifier(),bestOption);

            ArrayList<Direction> movPossiveis = Movimento.montaListaMov();

            for (Direction direction : movPossiveis){
                bestOption.restoreMachineState();
                futurePath = bestOption.copyPathWalked();
                futurePath.add(direction);
                Movimento.realizaMov(direction);
                MachineState futureState = new MachineState(futurePath);
                priorityQueue.offer(futureState);
            }
        }
    }

    public static List<Direction> heuristica2(){
        Movimento.resetCalcs();
        Matriz.opAnterior = Direction.NULO;
        //Heap para ordenar os estados por peso
        PriorityQueue<MachineState> priorityQueue = new PriorityQueue<MachineState>();
        latestMoves.clear();

        List<Direction> futurePath = new ArrayList<Direction>();
        MachineState startingMachineState = new MachineState(futurePath);
        priorityQueue.add(startingMachineState);

        while (true){
            MachineState bestFirstOption = priorityQueue.poll();
            bestFirstOption.restoreMachineState();

            if(Arrays.deepEquals(Matriz.matriz, correctMatriz)){
                return bestFirstOption.pathWalked;
            }
            if (hasLooped(bestFirstOption)){
                continue;
            }
            latestMoves.put(bestFirstOption.getIdentifier(),bestFirstOption);
            ArrayList<Direction> movPossiveis = Movimento.montaListaMov();

            for (Direction direction : movPossiveis){
                bestFirstOption.restoreMachineState();
                futurePath = bestFirstOption.copyPathWalked();
                futurePath.add(direction);
                Movimento.realizaMov(direction);
                MachineState firstSon = new MachineState(futurePath);

                if(Arrays.deepEquals(Matriz.matriz, correctMatriz)){
                    return firstSon.pathWalked;
                }
                if (hasLooped(firstSon)){
                    continue;
                }

                ArrayList<Direction> secondMoves = Movimento.montaListaMov();
                for (Direction secondDirection : secondMoves){
                    firstSon.restoreMachineState();
                    futurePath = firstSon.copyPathWalked();
                    futurePath.add(secondDirection);
                    Movimento.realizaMov(secondDirection);
                    MachineState futureState = new MachineState(futurePath);
                    priorityQueue.add(futureState);
                }

            }
        }
    }

    public static List<Direction> heuristica3(){
        Movimento.resetCalcs();
        Matriz.opAnterior = Direction.NULO;
        //Heap para ordenar os estados por peso
        PriorityQueue<MachineState> priorityQueue = new PriorityQueue<MachineState>();
        latestMoves.clear();

        List<Direction> futurePath = new ArrayList<Direction>();
        MachineState startingMachineState = new MachineState(futurePath);
        priorityQueue.add(startingMachineState);

        while (true){
            MachineState bestFirstOption = priorityQueue.poll();
            bestFirstOption.restoreMachineState();

            if(Arrays.deepEquals(Matriz.matriz, correctMatriz)){
                return bestFirstOption.pathWalked;
            }
            if (hasLooped(bestFirstOption)){
                continue;
            }
            latestMoves.put(bestFirstOption.getIdentifier(),bestFirstOption);
            ArrayList<Direction> movPossiveis = Movimento.montaListaMov();

            for (Direction direction : movPossiveis){
                bestFirstOption.restoreMachineState();
                futurePath = bestFirstOption.copyPathWalked();
                futurePath.add(direction);
                Movimento.realizaMov(direction);
                MachineState firstSon = new MachineState(futurePath);

                if(Arrays.deepEquals(Matriz.matriz, correctMatriz)){
                    return firstSon.pathWalked;
                }
                if (hasLooped(firstSon)){
                    continue;
                }

                ArrayList<Direction> secondMoves = Movimento.montaListaMov();
                for (Direction secondDirection : secondMoves){
                    firstSon.restoreMachineState();
                    futurePath = firstSon.copyPathWalked();
                    futurePath.add(secondDirection);
                    Movimento.realizaMov(secondDirection);
                    MachineState secondSon = new MachineState(futurePath);

                    if(Arrays.deepEquals(Matriz.matriz, correctMatriz)){
                        return secondSon.pathWalked;
                    }
                    if (hasLooped(secondSon)){
                        continue;
                    }

                    ArrayList<Direction> thirdMoves = Movimento.montaListaMov();
                    for (Direction thirdDirections : thirdMoves){
                        secondSon.restoreMachineState();
                        futurePath = secondSon.copyPathWalked();
                        futurePath.add(thirdDirections);
                        Movimento.realizaMov(thirdDirections);
                        MachineState thirdSon = new MachineState(futurePath);

                        priorityQueue.add(thirdSon);
                    }
                }

            }
        }
    }
}
