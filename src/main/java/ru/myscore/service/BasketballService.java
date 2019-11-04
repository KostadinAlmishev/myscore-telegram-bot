package ru.myscore.service;

import ru.myscore.nodes.BasketballMatch;
import ru.myscore.parser.BasketballParser;
import ru.myscore.parser.filter.BasketballDummyFilter;
import ru.myscore.parser.filter.WinsFirstLoosesSecondAndThirdFilter;

import javax.validation.constraints.NotNull;
import java.util.List;

public class BasketballService {
    private BasketballParser basketballParser = null;
    private BrowserService browserService = null;
    private String url = "";

    // Filters
    private BasketballDummyFilter dummyFilter = new BasketballDummyFilter();
    private WinsFirstLoosesSecondAndThirdFilter winsFirstLoosesSecondAndThirdFilter = new WinsFirstLoosesSecondAndThirdFilter();

    public BasketballService(@NotNull BrowserService browserService, @NotNull BasketballParser basketballParser, @NotNull String url) {
        this.browserService = browserService;
        this.basketballParser = basketballParser;
        this.url = url;
    }

    public List<BasketballMatch> getAll() {
        String src = browserService.getCurrSource();
        return basketballParser.parseMatches(src);
    }

    public List<BasketballMatch> getWonFirstLostSecondAndThird() {
        String src = browserService.getSource(url);
        return basketballParser.parseMatches(src, winsFirstLoosesSecondAndThirdFilter);
    }

}
