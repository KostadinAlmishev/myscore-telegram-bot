package ru.myscore.nodes;

public class Team {
    private String participant = "";
    private int firstQuarter = 0;
    private int secondQuarter = 0;
    private int thirdQuarter = 0;

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public int getFirstQuarter() {
        return firstQuarter;
    }

    public void setFirstQuarter(int firstQuarter) {
        this.firstQuarter = firstQuarter;
    }

    public void setFirstQuarter(String firstQuarter) {
        this.firstQuarter = Integer.parseInt(firstQuarter);
    }

    public int getSecondQuarter() {
        return secondQuarter;
    }

    public void setSecondQuarter(int secondQuarter) {
        this.secondQuarter = secondQuarter;
    }

    public void setSecondQuarter(String secondQuarter) {
        this.secondQuarter = Integer.parseInt(secondQuarter);
    }

    public int getThirdQuarter() {
        return thirdQuarter;
    }

    public void setThirdQuarter(int thirdQuarter) {
        this.thirdQuarter = thirdQuarter;
    }

    public void setThirdQuarter(String thirdQuarter) {
        this.thirdQuarter = Integer.parseInt(thirdQuarter);
    }

    @Override
    public String toString() {
        return "Team{" +
                "participant='" + participant + '\'' +
                ", firstQuarter=" + firstQuarter +
                ", secondQuarter=" + secondQuarter +
                ", thirdQuarter=" + thirdQuarter +
                '}';
    }
}
