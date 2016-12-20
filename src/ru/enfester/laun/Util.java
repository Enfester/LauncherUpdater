package ru.enfester.laun;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;


/**
 *
 * @author Антон
 */

public class Util {

    public static File getMcDir(String applicationName) {
        String userHome = System.getProperty("user.home", ".");
        File workingDirectory;
        switch (getPlatform().ordinal()) {
            case 0:
            case 1:
                workingDirectory = new File(userHome, applicationName + '/');
                break;
            case 2:
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData,
                            applicationName + '/');
                } else {
                    workingDirectory = new File(userHome, applicationName + '/');
                }
                break;
            case 3:
                workingDirectory = new File(userHome,
                        "Library" + File.separator + "Application Support" + File.separator + applicationName);
                break;
            default:
                workingDirectory = new File(userHome, applicationName + '/');
        }
        workingDirectory.mkdirs();
        if ((!workingDirectory.exists()) && (!workingDirectory.mkdirs())) {
            throw new RuntimeException(
                    "The working directory could not be created: "
                    + workingDirectory);
        }
        return workingDirectory;

    }

    public static OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.windows;
        }
        if (osName.contains("mac")) {
            return OS.macos;
        }
        if (osName.contains("solaris")) {
            return OS.solaris;
        }
        if (osName.contains("sunos")) {
            return OS.solaris;
        }
        if (osName.contains("linux")) {
            return OS.linux;
        }
        if (osName.contains("unix")) {
            return OS.linux;
        }
         if (osName.contains("fedora")) {
            return OS.linux;
        }
        return OS.unknown;
    }

    public static enum OS {

        linux, solaris, windows, macos, unknown;
    }

    public static String readFileAsString(String filePath, String defaultVar) {
        try {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        } catch (Exception e) {
            return defaultVar;
        }
    }

    public static String getMD5(File f) {
        try {
            return calculateHash(MessageDigest.getInstance("MD5"), f.toString());
        } catch (Exception e) {
            return "";
        }
    }

    public static String calculateHash(MessageDigest algorithm, String fileName)
            throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DigestInputStream dis = new DigestInputStream(bis, algorithm);

        while (dis.read() != -1)
			;
        byte[] hash = algorithm.digest();
        dis.close();
        return byteArray2Hex(hash);
    }

    public static String byteArray2Hex(byte[] hash) {
        @SuppressWarnings("resource")
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static void download(URL url, File f, final ProgressBar progressbar)
            throws Exception {
        f.mkdirs();

        f.delete();
        f.createNewFile();
        URLConnection connection = url.openConnection();

        final long down = connection.getContentLength();

        long downm = f.length();

        if (downm != down) {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            try (FileOutputStream fw = new FileOutputStream(f)) {
                byte[] b = new byte[1024];
                int count = 0;
                long total = 0;

                while ((count = bis.read(b)) != -1) {
                    total += count;
                    fw.write(b, 0, count);
                    final long t = total;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setProgress((((100 * t) / down) / 100D));

                        }
                    });
                }
            }
        }
    }

    public static String runGET(String URL, String param) {
        try {
            URL localURL;
            localURL = new URL(URL + "?" + param);

            BufferedReader localBufferedReader = new BufferedReader(
                    new InputStreamReader(localURL.openStream()));
            StringBuffer sb = new StringBuffer();
            String result;
            while ((result = localBufferedReader.readLine()) != null) {
                sb.append(result + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error: " + e.getLocalizedMessage());
            e.printStackTrace();
            return "";
        }

    }

    // Проверка сети
    public static boolean isOnline() {
        URL url;
        URLConnection urlconn;
        try {
            url = new URL("http://google.com/");
        } catch (MalformedURLException e) {
            return false;
        }
        try {
            urlconn = url.openConnection();
            urlconn.connect();
            return true;
        } catch (IOException e1) {
            return false;
        }

    }

}
