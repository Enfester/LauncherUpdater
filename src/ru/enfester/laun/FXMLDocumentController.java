package ru.enfester.laun;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * Когда загружаеться документ верстки fxml к нему подключаеться это класс
 *
 * @author Антон
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public ProgressBar bar; // Прогресс бар загрузки

    /**
     * *
     * Начальная функция
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Thread myThready = new Thread(new Runnable() {
            @Override
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                new Update(bar); // запускаем проверку на обновление и передаем прогрессбар в функцию для изменение значений
            }
        });
        myThready.start();
    }

}
