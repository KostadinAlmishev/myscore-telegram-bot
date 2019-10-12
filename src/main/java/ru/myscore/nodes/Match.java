package ru.myscore.nodes;

public class Match {
    private Team home = null;
    private Team away = null;

    public Match() {
    }

    public Match(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public boolean check() {
        if (home == null || away == null) {
            return false;
        }

        return _check(home, away) || _check(away, home);
    }

    private boolean _check(Team a, Team b) {
        return a.getFirstQuarter() > b.getSecondQuarter() &&
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

}
