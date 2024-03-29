package ru.myscore.config;

import ru.myscore.parser.BasketballParser;
import ru.myscore.service.BasketballService;
import ru.myscore.service.BrowserService;
import ru.myscore.service.RealTimeService;
import ru.myscore.telegram.Bot;
import ru.myscore.telegram.BotConfig;

import java.util.ArrayList;
import java.util.List;

public class Configuration implements AutoCloseable, Runnable {

    private final String URL = "https://www.myscore.ru/basketball";
    // Bot
    private BotConfig botConfig = null;
    private Bot bot = null;

    // Parser
    private BasketballParser basketballParser = null;

    // Services
    private BrowserService browserService = null;
    private BasketballService basketballService = null;
    private RealTimeService realTimeService = null;

    private final List<Thread> threadsInside = new ArrayList<>();

    public Configuration() {
        basketballParser = new BasketballParser();
        browserService = new BrowserService(URL);
        basketballService = new BasketballService(browserService, basketballParser, URL);

        botConfig = new BotConfig(basketballService);
        bot = botConfig.getBot();

        realTimeService = new RealTimeService(bot, basketballService);
    }

    @Override
    public void close() throws Exception {
        bot.sendToCreator("Tried to close wtf...");
        browserService.close();
        for (Thread curr : threadsInside) {
            curr.interrupt();
        }
    }

    @Override
    public void run() {
        Thread realTimeServiceThread = new Thread(realTimeService, "realTimeService");
        realTimeServiceThread.start();
        threadsInside.add(realTimeServiceThread);
    }
}
