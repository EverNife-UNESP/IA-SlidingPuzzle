package br.com.finalcraft.unesp.ia.javafx.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MyFXMLs {

    public static Parent main;
    public static Parent relatoriomaker;

    public static Parent initialize(){
        try {
            main = FXMLLoader.load(MyFXMLs.class.getResource("/assets/client.fxml"));
            relatoriomaker = FXMLLoader.load(MyFXMLs.class.getResource("/assets/relatoriomaker.fxml"));
            return main;
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

}
