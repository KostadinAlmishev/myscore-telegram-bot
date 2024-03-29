package ru.myscore.telegram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.myscore.service.BasketballService;

import static ru.myscore.logger.Log4j2Logger.MYAPP_MARKER;

public class BotConfig {

    private final Logger logger = LogManager.getLogger(BotConfig.class);
    private TelegramBotsApi botsApi;
    private final String BOT_TOKEN = "";
    private final String BOT_USERNAME = "";

    // private final String PROXY_HOST = "";
    // private final Integer PROXY_PORT = 1080;

    private Bot bot;

    public BotConfig(BasketballService basketballService) {
        // Initializes dependencies necessary for the base bot - Guice
        ApiContextInitializer.init();

        // Create the TelegramBotsApi object to register your bots
        botsApi = new TelegramBotsApi();
        // Setup Http proxy
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        // botOptions.setProxyHost(PROXY_HOST);
        // botOptions.setProxyPort(PROXY_PORT);
        // botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        bot = new Bot(BOT_TOKEN, BOT_USERNAME, botOptions, basketballService);
        try {
            // Register your newly created AbilityBot
            botsApi.registerBot(bot);
            logger.info(MYAPP_MARKER, "Bot was registered!");
        } catch (TelegramApiException e) {
            logger.error(MYAPP_MARKER, "Can not register bot!", e);
        }

    }

    public Bot getBot() {
        return bot;
    }
}
