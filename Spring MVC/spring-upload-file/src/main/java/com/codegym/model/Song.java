package com.codegym.model;

public class Song {
    private int id;
    private String name;
    private String artist;
    private String genre;
    private String song;

    public Song() {
    }

    public Song(int id, String name, String artist, String genre, String song) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.song = song;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
}
