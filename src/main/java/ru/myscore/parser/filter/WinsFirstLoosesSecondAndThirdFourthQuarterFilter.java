package ru.myscore.parser.filter;

import ru.myscore.nodes.BasketballMatch;
import ru.myscore.nodes.BasketballQuarter;
import ru.myscore.nodes.BasketballTeam;

public class WinsFirstLoosesSecondAndThirdFourthQuarterFilter implements BasketballMatchFilter {


    @Override
    public boolean matches(BasketballMatch m) {
        BasketballTeam a = m.getHome();
        BasketballTeam b = m.getAway();

        return m.getCurrPart() == BasketballQuarter.FOURTH && m.getCurrMinute() == 2 &&
                (_check(a, b) || _check(b, a));
    }

    private boolean _check(BasketballTeam a, BasketballTeam b) {
        return a.getFirstQuarter() > b.getFirstQuarter() &&
                a.getSecondQuarter() < b.getSecondQuarter() &&
                a.getThirdQuarter() < b.getThirdQuarter();
    }
}
