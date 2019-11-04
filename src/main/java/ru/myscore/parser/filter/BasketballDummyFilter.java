package ru.myscore.parser.filter;

import ru.myscore.nodes.BasketballMatch;

public class BasketballDummyFilter implements BasketballMatchFilter {
    @Override
    public boolean matches(BasketballMatch m) {
        return true;
    }
}
