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

        Match other = (Match) obj;
        return home.equals(other.getHome()) &&
                away.equals(other.getAway());
    }

    @Override
    public int hashCode() {
        return home.hashCode() + away.hashCode();
    }
}
