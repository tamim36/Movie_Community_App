package com.example.addmovie;

public class Blog {
    private String Title;
    private String Description;
    private String Image;
    private String rating;
    private String genre;
    private String release_year;

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public String getRelease_year() {
        return release_year;
    }

    public Blog(){

    }

    public Blog(String title, String description, String image, String rating, String genre, String release_year) {
        Title = title;
        Description = description;
        Image = image;
        this.rating = rating;
        this.genre = genre;
        this.release_year = release_year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
