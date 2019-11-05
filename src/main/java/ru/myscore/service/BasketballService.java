package ru.myscore.service;

import ru.myscore.nodes.BasketballMatch;
import ru.myscore.parser.BasketballParser;
import ru.myscore.parser.filter.WinsFirstLoosesSecondAndThirdFilter;
import ru.myscore.parser.filter.WinsFirstLoosesSecondAndThirdThirdQuarterFilter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class BasketballService {
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
}
