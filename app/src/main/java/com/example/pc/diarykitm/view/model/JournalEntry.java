package com.example.pc.diarykitm.view.model;

/**
 * Created by PC on 4/7/2018.
 */

public class JournalEntry {
    private String date;
    private String title;
    private String description;
    private String mood;
    private String pace;
    private String type;

    public JournalEntry(String date, String title, String description, String mood, String pace, String type) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.mood = mood;
        this.pace = pace;
        this.type = type;
    }

    public JournalEntry() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getPace() {
        return pace;
    }

    public void setPace(String pace) {
        this.pace = pace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
