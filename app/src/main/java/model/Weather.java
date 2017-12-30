package model;

/**
 * Created by Lincoln on 12/27/2017.
 */

public class Weather { // Hub Class
    public Place place;
    public String iconData;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();


}
