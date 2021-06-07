package ru.mirea.tourist.Model;

public class City {
    public String lions,metro,about;


    public City(){

    }

    public City(String lions, String metro, String about) {
        this.lions = lions;
        this.metro = metro;
        this.about = about;
    }

    public String getLions() {
        return lions;
    }

    public void setLions(String lions) {
        this.lions = lions;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
