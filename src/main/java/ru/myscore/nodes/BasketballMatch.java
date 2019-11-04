package ru.myscore.nodes;

public class BasketballMatch {

    private BasketballTeam home = null;
    private BasketballTeam away = null;
    private BasketballQuarter currPart = BasketballQuarter.NOT_STARTED;
    private int currMinute = 0;


    public BasketballMatch() {
    }

    public BasketballMatch(BasketballTeam home, BasketballTeam away, BasketballQuarter part) {
        this.home = home;
        this.away = away;
    }

    public BasketballQuarter getCurrPart() {
        return currPart;
    }

    public void setCurrPart(BasketballQuarter currPart) {
        this.currPart = currPart;
    }

    public BasketballTeam getHome() {
        return home;
    }

    public void setHome(BasketballTeam home) {
        this.home = home;
    }

    public BasketballTeam getAway() {
        return away;
    }

    public void setAway(BasketballTeam away) {
        this.away = away;
    }

    public int getCurrMinute() {
        return currMinute;
    }

    public void setCurrMinute(int currMinute) {
        this.currMinute = currMinute;
    }

    public boolean check() {
        if (home == null || away == null) {
            return false;
        }

        return _check(home, away) || _check(away, home);
    }

    private boolean _check(BasketballTeam a, BasketballTeam b) {
        return a.getFirstQuarter() > b.getFirstQuarter() &&
            a.getSecondQuarter() < b.getSecondQuarter() &&
            a.getThirdQuarter() < b.getThirdQuarter();
    }

    @Override
    public String toString() {
        return "Founded{" +
                "home=" + home.toString() +
                ", away=" + away.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        BasketballMatch other = (BasketballMatch) obj;
        return home.equals(other.getHome()) &&
                away.equals(other.getAway());
    }

    @Override
    public int hashCode() {
        return home.hashCode() + away.hashCode();
    }
}
