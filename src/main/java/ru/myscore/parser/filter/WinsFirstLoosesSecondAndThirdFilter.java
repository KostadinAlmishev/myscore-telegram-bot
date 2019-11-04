package ru.myscore.parser.filter;

import ru.myscore.nodes.BasketballMatch;
import ru.myscore.nodes.BasketballQuarter;
import ru.myscore.nodes.BasketballTeam;

public class WinsFirstLoosesSecondAndThirdFilter implements BasketballMatchFilter {


    @Override
    public boolean matches(BasketballMatch m) {
        BasketballTeam a = m.getHome();
        BasketballTeam b = m.getHome();

        return m.getCurrPart() == BasketballQuarter.THIRD &&
                (_check(a, b) || _check(b, a));
    }

    private boolean _check(BasketballTeam a, BasketballTeam b) {
        return a.getFirstQuarter() > b.getFirstQuarter() &&
                a.getSecondQuarter() < b.getSecondQuarter() &&
                a.getThirdQuarter() < b.getThirdQuarter();
    }
}
