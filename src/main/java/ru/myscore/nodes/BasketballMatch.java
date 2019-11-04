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

    public String getQuarter() {
        switch (currPart) {
            case NOT_STARTED:
                return "NOT STARTED";
            case FIRST:
                return "FIRST QUARTER";
            case SECOND:
                return "SECOND QUARTER";
            case THIRD:
                return "THIRD QUARTER";
            case FOURTH:
                return "FOURTH QUARTER";
            case ENDED_OVERTIME:
                return "OVERTIME";
            case ENDED:
                return "ENDED";
        }

        return "";
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
