/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Access.Connection;

/**
 *
 * @author Luciano
 */
public class Main extends Application {

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private final int width = gd.getDisplayMode().getWidth();
    private final int height = gd.getDisplayMode().getHeight();

    @Override
    public void start(Stage stage) throws Exception {
        Connection con = new Connection();
        con.ReadCaminhoBD();
        stage.setOnCloseRequest(event -> System.exit(0));
        Parent root = FXMLLoader.load(getClass().getResource("/view/TelaInicial.fxml"));
        Scene scene = new Scene(root, 1440, 830);
        Image icon = new Image(getClass().getResourceAsStream("/image/Circle.png"));
        stage.setTitle("SYSTEM SET KEY. V2");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaxHeight(height);
        stage.setMaxWidth(width);

        stage.setMinHeight(830);
        stage.setMinWidth(1440);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
