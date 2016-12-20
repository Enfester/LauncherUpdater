/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Антон
 */
public class Launch extends Application {

    // путь до папки с игрой где лежит лаунчер
    public static File launcherFile = new File(Util.getMcDir(Config.mcdir) + File.separator + Config.launchername);

    /***
     * Начало запуска, подгрузка картинок и fxml документа
     * @param stage
     * @throws Exception 
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image(Launch.class.getResourceAsStream("/launch/logo.png")));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setTitle("Enfester");
        stage.setScene(scene);
        stage.show();

    }

    // Точка входа
    public static void main(String[] args) {
        launch(args);
    }
}
