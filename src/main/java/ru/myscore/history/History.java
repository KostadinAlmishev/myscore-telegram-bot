package ru.myscore.history;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.myscore.nodes.BasketballMatch;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.myscore.logger.Log4j2Logger.MYAPP_MARKER;

public class History {
    private final HashSet<BasketballMatch> basketballMatchHashSet = new HashSet<BasketballMatch>();
    private BasketballMatch prev = null;
    private BasketballMatch prevPrev = null;
    private final Logger logger = LogManager.getLogger(History.class);

    public History() {
        scheduleClear();
    }

    public boolean add(BasketballMatch basketballMatch) {
        prevPrev = prev;
        prev = basketballMatch;

        return basketballMatchHashSet.add(basketballMatch);
    }

    public boolean contains(BasketballMatch basketballMatch) {
        return basketballMatchHashSet.contains(basketballMatch);
    }

    // Don't delete last two
    public void clear() {
        logger.info(MYAPP_MARKER, "History was cleared");
        basketballMatchHashSet.clear();
        if (prev != null)
            basketballMatchHashSet.add(prev);
        if (prevPrev != null)
            basketballMatchHashSet.add(prevPrev);
    }

    private void scheduleClear() {
        // TODO add Russia??
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime nextRun = now.withHour(1).withMinute(0).withSecond(0);
        if (now.compareTo(nextRun) > 0) {
            nextRun = nextRun.plusDays(1);
        }

        Duration duration = Duration.between(now, nextRun);
        long initialDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::clear,
                initialDelay,
                TimeUnit.DAYS.toSeconds(1), // everyday
                TimeUnit.SECONDS);
    }
}
