package ru.enfester.laun;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.control.ProgressBar;
import javax.swing.JOptionPane;
import static ru.enfester.laun.Launch.launcherFile;


/**
 *
 * @author Антон
 */
public class Update {

    ProgressBar prograssBar;
    
    public Update(ProgressBar bar) {
        prograssBar = bar; // передаем бар в переменну.
        if (needsUpdate()) { // Если есть обновелние
            update(); // качаем
        }
        launchLauncher(); // запускаем сам лаунчер 

    }

    /***
     * Запуск лаунчера
     */
    private void launchLauncher() {

        if (!launcherFile.exists()) { // Дополнительная проверка на существоание файла 
            // если файла нет ошибка и выход
            JOptionPane.showMessageDialog(null, "Файл лаунчера, для запуска, не найден! ", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

       

        ArrayList<String> prg = new ArrayList<String>();
        if (Util.getPlatform().ordinal() == 2) {
            prg.add("java");
        } else {
            prg.add("java");
        }
        prg.add("-Xmx" + Config.ram + "m");
        prg.add("-jar");
        prg.add(launcherFile.toString());
        ProcessBuilder pb = new ProcessBuilder(prg);
        try {
            pb.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Не удалось запустить лаунчер:\n"
                    + e,
                    "ERROR: FAILED TO START LAUNCHER",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        System.exit(0);

    }

    // Функия загрузки файла 
    private void update() {
        try {
            // качаем файл, передаем ссылку до файла, путь куда качать и прогрессбар
            Util.download(new URL(Config.sysdir + "/" + Config.launchername), launcherFile, prograssBar); 

        } catch (Exception e) { // если не получаеться, то ошибка
            JOptionPane.showMessageDialog(
                    null,
                    "Ошибка загрузки:\n"
                    + e,
                    "ERROR: FAILED TO START LAUNCHER",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private boolean needsUpdate() {
        
        // Передаем скрипту на сайте имя файла методом GET
        // Читаем ответ и получаем MD5 файла лаунчера на сайте
        String s = Util.runGET(Config.phpdir, "file=" + Config.launchername).replace("\n", "").trim(); 
        if (s == null || s.equals("")) { // есил пусто возвращаем false
            return false;
        }
        return !s.equals(Util.getMD5(launcherFile)); // сверка md5 с сайта и на компе| возврат true - false
    }

}
