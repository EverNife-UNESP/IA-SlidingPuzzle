package br.com.finalcraft.unesp.ia.javafx;

import br.com.finalcraft.unesp.ia.javafx.controller.RelatorioController;
import br.com.finalcraft.unesp.ia.javafx.view.MyFXMLs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class TrueMain extends Application {

    public static Parent the_current_root_parent;
    public static Stage primaryStage;

    @Override
    public void start(Stage thePrimaryStage) throws Exception{
        primaryStage = thePrimaryStage;
        TrueMain.primaryStage.initStyle(StageStyle.TRANSPARENT);
        showScene();
        RelatorioController.setUp();
    }
    private static double x;
    private static double y;
    private static void showScene(){
        if (the_current_root_parent == null){
            System.out.println("root is null...");
            return;
        }

        Scene scene = new Scene(the_current_root_parent);
        scene.setFill(Color.TRANSPARENT);

        the_current_root_parent.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        the_current_root_parent.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        the_current_root_parent = MyFXMLs.initialize();
        launch(args);
    }
}