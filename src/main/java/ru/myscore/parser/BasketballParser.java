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


            home.setParticipant(el.selectFirst("div.event__participant--home").ownText());
            away.setParticipant(el.selectFirst("div.event__participant--away").ownText());

            home.setFirstQuarter(el.selectFirst("div.event__part--home.event__part--1").ownText());
            away.setFirstQuarter(el.selectFirst("div.event__part--away.event__part--1").ownText());

            home.setSecondQuarter(el.selectFirst("div.event__part--home.event__part--2").ownText());
            away.setSecondQuarter(el.selectFirst("div.event__part--away.event__part--2").ownText());

            home.setThirdQuarter(el.selectFirst("div.event__part--home.event__part--3").ownText());
            away.setThirdQuarter(el.selectFirst("div.event__part--away.event__part--3").ownText());

            home.setFourthQuarter(el.selectFirst("div.event__part--home.event__part--4").ownText());
            away.setFourthQuarter(el.selectFirst("div.event__part--away.event__part--4").ownText());

            home.setFifthQuarter(el.selectFirst("div.event__part--home.event__part--5").ownText());
            away.setFifthQuarter(el.selectFirst("div.event__part--away.event__part--5").ownText());

            currMatch.setHome(home);
            currMatch.setHome(away);
            currMatch.setCurrPart(getQuarter(el));
            currMatch.setCurrMinute(getMinute(el));

            if (filter.matches(currMatch)) {
                result.add(currMatch);
            }
        }

        return result;
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
