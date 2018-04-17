package co.example.user.myapp;

/**
 * Created by roman on 16.04.18.
 */

public class SearchEntity {
    public String city;
    public String datetime;
    public int category;
    public SearchEntity(String _city, String _datetime, int _category)
    {
        this.city = _city;
        this.datetime = _datetime;
        this.category = _category;
    }
}
