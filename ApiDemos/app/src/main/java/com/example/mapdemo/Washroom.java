package com.example.mapdemo;

/**
 * Created by nexar on 7/15/2016.
 */
public class Washroom {
    private int id;
    private String name;
    private double lng;
    private double lat;
    private int is_public;
    private int thumbs_up;
    private int thumbs_down;


    public Washroom(){
    }

    public Washroom(int id, String name, double lng, double lat, int isPublic){
        this.id = id;
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.is_public = isPublic;
        this.thumbs_up = 0;
        this.thumbs_down = 0;
    }

    public Washroom(int id, String name, double lng, double lat,
                    int isPublic, int thumbs_up, int thumbs_down){
        this.id = id;
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.is_public = isPublic;
        this.thumbs_up = thumbs_up;
        this.thumbs_down = thumbs_down;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLng(double lng){
        this.lng = lng;
    }

    public void setLat(double lat){
        this.lat = lat;
    }


    public void setIs_public(int is_public){
        this.is_public = is_public;
    }
    public void setThumps_up(int thumps_up){
        this.thumbs_up = thumps_up;
    }
    public void setThumbs_down(int thumbs_down){
        this.thumbs_down = thumbs_down;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getThumbs_up() {
        return thumbs_up;
    }

    public int getIs_public() {
        return is_public;
    }

    public int getThumbs_down() {
        return thumbs_down;
    }

    public void increment_thumbs_up(){
        this.thumbs_up += 1;
    }

    public void increment_thumbs_down(){
        this.thumbs_down += 1;
    }
}
