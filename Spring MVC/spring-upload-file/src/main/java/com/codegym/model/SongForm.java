package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

public class SongForm {
    private int id;
    private String name;
    private String artist;
    private String genre;
    private MultipartFile song;

    public SongForm() {
    }

    public SongForm(int id, String name, String artist, String genre, MultipartFile song) {
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

    public MultipartFile getSong() {
        return song;
    }

    public void setSong(MultipartFile song) {
        this.song = song;
    }
}
