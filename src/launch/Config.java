package launch;

/**
 *
 * @author Антон
 */
public class Config {
    
  // НЕ ИСПОЛЬЗОВАТЬ https://
    static String mcdir = ".enfester"; // Папка с клиентом (можно с точкой и с маленькой буквы)
    static String launchername = "EnfesterLauncherFX.jar"; // Имя файла лаунчера (какой файл качает и запускает)
    static String sysdir = "http://laun.enfester.ru/"; // Путь на сайте до папки с лаунчером
    static String phpdir = "http://laun.enfester.ru/launcher.php"; // Путь на сайте до файла проверки обновления лаунчера
    static int ram = 64; // Сколько памяти выделять под лаунчер (не советую много выделять, это же не под клиент)
    static int ramClient = 512; // Сколько памяти будет стоять поумолчанию на клиент (в лаунчере меняется в настройках)
    static String confname = "launcher.yml"; // Имя файла конфига (лучше не трогать)
   
    
}
