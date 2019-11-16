package ru.myscore.nodes;

import ru.myscore.mytypes.MyInteger;

public class BasketballTeam {
    private String participant = "";
    private int firstQuarter = 0;
    private int secondQuarter = 0;
    private int thirdQuarter = 0;
    private int fourthQuarter = 0;
    private int fifthQuarter = 0;

    public BasketballTeam() {
    }

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
        this.firstQuarter = MyInteger.parseInt(firstQuarter);
    }

    public int getSecondQuarter() {
        return secondQuarter;
    }

    public void setSecondQuarter(int secondQuarter) {
        this.secondQuarter = secondQuarter;
    }

    public void setSecondQuarter(String secondQuarter) {
        this.secondQuarter = MyInteger.parseInt(secondQuarter);
    }

    public int getThirdQuarter() {
        return thirdQuarter;
    }

    public void setThirdQuarter(int thirdQuarter) {
        this.thirdQuarter = thirdQuarter;
    }

    public void setThirdQuarter(String thirdQuarter) {
        this.thirdQuarter = MyInteger.parseInt(thirdQuarter);
    }

    public int getFourthQuarter() {
        return fourthQuarter;
    }

    public void setFourthQuarter(int fourthQuarter) {
        this.fourthQuarter = fourthQuarter;
    }

    public void setFourthQuarter(String fourthQuarter) {
        this.fourthQuarter = MyInteger.parseInt(fourthQuarter);
    }

    public int getFifthQuarter() {
        return fifthQuarter;
    }

    public void setFifthQuarter(int fifthQuarter) {
        this.fifthQuarter = fifthQuarter;
    }

    public void setFifthQuarter(String fifthQuarter) {
        this.fifthQuarter = MyInteger.parseInt(fifthQuarter);
    }

    @Override
    public String toString() {
        return "Team{" +
                "participant='" + participant + '\'' +
                ", firstQuarter=" + firstQuarter +
                ", secondQuarter=" + secondQuarter +
                ", thirdQuarter=" + thirdQuarter +
                ", fourthQuarter=" + fourthQuarter +
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

        BasketballTeam other = (BasketballTeam) obj;
        return participant.equals(other.getParticipant())
                /*
                && firstQuarter == other.firstQuarter &&
                secondQuarter == other.secondQuarter &&
                thirdQuarter == other.thirdQuarter &&
                fourthQuarter == other.fourthQuarter &&
                fifthQuarter == other.fifthQuarter
                */
                ;
    }

    @Override
    public int hashCode() {
        return participant.hashCode() + firstQuarter + secondQuarter + thirdQuarter + fourthQuarter + fifthQuarter;
    }
}
