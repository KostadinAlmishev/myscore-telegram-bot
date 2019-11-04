package ru.myscore.service;

import ru.myscore.history.History;
import ru.myscore.nodes.BasketballMatch;
import ru.myscore.telegram.Bot;

import java.util.List;

public class RealTimeService implements Runnable {
    private Bot bot = null;
    private BasketballService basketballService = null;
    private History history = null;

    public RealTimeService(Bot bot, BasketballService basketballService) {
        this.bot = bot;
        this.basketballService = basketballService;
        this.history = new History();
    }

    @Override
    public void run() {
        while (true) {

            List<BasketballMatch> matches = basketballService.getWonFirstLostSecondAndThird();

            for (BasketballMatch match : matches) {
                if (!history.contains(match)) {
                    bot.send(match);
                    history.add(match);
                }
            }

            try {
                Thread.sleep(100 * 1000);
            } catch (InterruptedException ignored) {

            }
        }

    }
}
