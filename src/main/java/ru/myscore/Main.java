package ru.myscore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.myscore.history.History;
import ru.myscore.nodes.Match;
import ru.myscore.nodes.Team;
import ru.myscore.telegram.Bot;
import ru.myscore.telegram.BotConfig;

import static ru.myscore.logger.Log4j2Logger.MYAPP_MARKER;

public class Main {

    private static final String URL = "https://www.myscore.ru/basketball";
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final History history = new History();

    private static ChromeDriver driver = null;
    private static BotConfig botConfig = null;
    private static Bot bot = null;

    private static void configDriver() {
        String pathToChromeDriver = "lib/chromedriver";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
    }

    private static void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }


    private static void init() {
        botConfig = new BotConfig();
        bot = botConfig.getBot();

        configDriver();
    }

    private static void loadPage() throws InterruptedException {
        driver.get(URL);
        Thread.sleep(8 * 1000);
    }

    private static void mainLogic() throws Exception {

        while (true) {
            String source = "";
            try {
                source = driver.getPageSource();
                driver.navigate().refresh();
            } catch (Exception e) {
                logger.error(MYAPP_MARKER, "Can't get page source \n" + e.getMessage());
                // restard driver
                bot.sendToCreator("Driver recreation");
                closeDriver();
                driver = null;
                configDriver();
                loadPage();
                continue;
            }

            Document document = Jsoup.parse(source);
            Elements els = document.select("div[title*=Подробности матча!]");

            for (Element el : els) {
                if (el.toString().contains("4-я четверть")) {
                    if (el.toString().contains("&nbsp;2")) { // which minute

                        Team home = new Team();
                        Team away = new Team();

                        home.setParticipant(el.selectFirst("div.event__participant--home").ownText());
                        away.setParticipant(el.selectFirst("div.event__participant--away").ownText());

                        home.setFirstQuarter(el.selectFirst("div.event__part--home.event__part--1").ownText());
                        away.setFirstQuarter(el.selectFirst("div.event__part--away.event__part--1").ownText());

                        home.setSecondQuarter(el.selectFirst("div.event__part--home.event__part--2").ownText());
                        away.setSecondQuarter(el.selectFirst("div.event__part--away.event__part--2").ownText());

                        home.setThirdQuarter(el.selectFirst("div.event__part--home.event__part--3").ownText());
                        away.setThirdQuarter(el.selectFirst("div.event__part--away.event__part--3").ownText());

                        Match group = new Match(home, away);

                        if (group.check()) {
                            logger.info(MYAPP_MARKER, "Parsed:\n" + el.toString());
                            if (!history.contains(group)) {
                                bot.send(group);
                                history.add(group);
                            }
                        }
                    }
                }
            }

            Thread.sleep(100 * 1000);
        }

    }

    public static void main(String[] args) throws Exception {
        init();
        bot.sendToCreator("Driver was created");

        try {
            mainLogic();
        } catch (InterruptedException e) {  // ctrl + c
            closeDriver();
            bot.sendToCreator("Driver was closed");
            throw e;
        }
    }
}
