package com.harjup_kdhyne.TravelApp.Translation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Paul on 2/16/14.
 * Contains a list of phrases in different languages and the categories they can be described as
 */
public class Translation implements Serializable
{
    private long id = -1;       //id for insertion in the database
    private List<Phrase> phrases;       //Title/short description the note contains
    private List<Category> categories;       //Title/short description the note contains
    private String imageId;

    public Translation(long _id, List<Phrase> _phrases)
    {
        id = _id;
        phrases = _phrases;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }

    public Phrase getPhraseByIndex(int i)
    {
        return phrases.get(i);
    }

    public void setPhrases(List<Phrase> phrases) {
        this.phrases = phrases;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
