package ru.myscore.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.bot.AbilityBot;
import ru.myscore.nodes.BasketballMatch;
import ru.myscore.parser.BasketballParser;
import ru.myscore.parser.filter.WinsFirstLoosesSecondAndThirdFilter;
import ru.myscore.parser.filter.WinsFirstLoosesSecondAndThirdThirdQuarterFilter;

import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.myscore.logger.Log4j2Logger.MYAPP_MARKER;

public class BasketballService {

    private final Logger logger = LogManager.getLogger(AbilityBot.class);

    private BasketballParser basketballParser = null;
    private BrowserService browserService = null;
    private String url = "";

    // Filters
    private final WinsFirstLoosesSecondAndThirdFilter winsFirstLoosesSecondAndThirdFilter = new WinsFirstLoosesSecondAndThirdFilter();
    private final WinsFirstLoosesSecondAndThirdThirdQuarterFilter winsFirstLoosesSecondAndThirdThirdQuarterFilter =
            new WinsFirstLoosesSecondAndThirdThirdQuarterFilter();

    public BasketballService(@NotNull BrowserService browserService, @NotNull BasketballParser basketballParser, @NotNull String url) {
        this.browserService = browserService;
        this.basketballParser = basketballParser;
        this.url = url;

        browserService.getSource(url);
    }

    public List<BasketballMatch> getAll() {
        String src = browserService.getCurrSource();
        return basketballParser.parseMatches(src);
    }

    public List<BasketballMatch> getWonFirstLostSecondAndThird() {
        String src = browserService.getCurrSource();
        return basketballParser.parseMatches(src, winsFirstLoosesSecondAndThirdFilter);
    }

    public List<BasketballMatch> getWonFirstLostSecondAndThirdThirdQuarter() {
        String src = browserService.getCurrSource();
        return basketballParser.parseMatches(src, winsFirstLoosesSecondAndThirdThirdQuarterFilter);
    }

    public void reload() {
        browserService.reload();
    }

    public void debug() {
        logger.info(MYAPP_MARKER, "DEBUG " + browserService.getCurrSource());
    }
}
