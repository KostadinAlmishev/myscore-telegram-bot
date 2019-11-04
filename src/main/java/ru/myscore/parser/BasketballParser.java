package ru.myscore.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.myscore.nodes.BasketballMatch;
import ru.myscore.nodes.BasketballQuarter;
import ru.myscore.nodes.BasketballTeam;
import ru.myscore.parser.filter.BasketballDummyFilter;
import ru.myscore.parser.filter.BasketballMatchFilter;

import java.util.ArrayList;
import java.util.List;

public class BasketballParser {
    private final BasketballDummyFilter dummyFilter = new BasketballDummyFilter();

    public List<BasketballMatch> parseMatches(String src) {
        return parseMatches(src, dummyFilter);
    }

    public List<BasketballMatch> parseMatches(String src, BasketballMatchFilter filter) {
        List<BasketballMatch> result = new ArrayList<>();

        Document document = Jsoup.parse(src);

        Elements els = document.select("div[title*=Подробности матча!]");
        for (Element el : els) {
            BasketballMatch currMatch = new BasketballMatch();
            BasketballTeam home = new BasketballTeam();
            BasketballTeam away = new BasketballTeam();

            home.setParticipant(getElementData(el, "div.event__participant--home"));
            away.setParticipant(getElementData(el, "div.event__participant--away"));

            home.setFirstQuarter(getElementData(el, "div.event__part--home.event__part--1"));
            away.setFirstQuarter(getElementData(el, "div.event__part--away.event__part--1"));

            home.setSecondQuarter(getElementData(el, "div.event__part--home.event__part--2"));
            away.setSecondQuarter(getElementData(el, "div.event__part--away.event__part--2"));

            home.setThirdQuarter(getElementData(el, "div.event__part--home.event__part--3"));
            away.setThirdQuarter(getElementData(el, "div.event__part--away.event__part--3"));

            home.setFourthQuarter(getElementData(el, "div.event__part--home.event__part--4"));
            away.setFourthQuarter(getElementData(el, "div.event__part--away.event__part--4"));

            home.setFifthQuarter(getElementData(el, "div.event__part--home.event__part--5"));
            away.setFifthQuarter(getElementData(el, "div.event__part--away.event__part--5"));

            currMatch.setHome(home);
            currMatch.setAway(away);
            currMatch.setCurrPart(getQuarter(el));
            currMatch.setCurrMinute(getMinute(el));

            if (filter.matches(currMatch)) {
                result.add(currMatch);
            }
        }

        return result;
    }

    private String getElementData(Element el, String selector) {
        Element selected = el.selectFirst(selector);
        if (selected == null)
            return "";

        return selected.ownText();
    }


    private BasketballQuarter getQuarter(Element el) {
        if (el.toString().contains("1-я четверть")) {
            return BasketballQuarter.FIRST;
        } else if (el.toString().contains("2-я четверть")) {
            return BasketballQuarter.SECOND;
        } else if (el.toString().contains("3-я четверть")) {
            return BasketballQuarter.THIRD;
        } else if (el.toString().contains("4-я четверть")) {
            return BasketballQuarter.FOURTH;
        } else if (el.toString().contains("Завершен")) {
            return BasketballQuarter.ENDED;
        } else if (el.toString().contains("После овертайма")) {
            return BasketballQuarter.ENDED_OVERTIME;
        }
        return BasketballQuarter.NOT_STARTED;
    }

    private int getMinute(Element el) {
        for (int i = 0; i < 15; i++) {
            if (el.toString().contains("&nbsp;2")) {
                return i;
            }
        }

        return 0;
    }
}
