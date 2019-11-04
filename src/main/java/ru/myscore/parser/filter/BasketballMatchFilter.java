package ru.myscore.parser.filter;

import ru.myscore.nodes.BasketballMatch;

public interface BasketballMatchFilter {
    boolean matches(BasketballMatch m);
}
