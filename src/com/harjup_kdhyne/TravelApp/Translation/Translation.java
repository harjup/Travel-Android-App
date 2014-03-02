package com.harjup_kdhyne.TravelApp.Translation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Paul on 2/16/14.
 * Contains a list of phrases in different languages and the categories they can be described as
 */
public class Translation implements Serializable
{
    private long id = -1;       //id for insertion in the database
    private String homePhrase;
    private String homeLanguage;
    private HashMap<String, Phrase> phraseHashMap;       //Title/short description the note contains
    private List<Category> categories;       //Title/short description the note contains
    private String imageId;

    public Translation(){}

    public Translation(long _id, String _homePhrase, String _homeLanguage)
    {
        id = _id;
        homePhrase = _homePhrase;
        homeLanguage = _homeLanguage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHomePhrase() {
        return homePhrase;
    }

    public void setHomePhrase(String homePhrase) {
        this.homePhrase = homePhrase;
    }

    public String getHomeLanguage() {
        return homeLanguage;
    }

    public void setHomeLanguage(String homeLanguage) {
        this.homeLanguage = homeLanguage;
    }

    public HashMap<String, Phrase> getPhraseHashMap() {
        return phraseHashMap;
    }

    public Phrase getPhrase(String _language){
        return phraseHashMap.get(_language);
    }

    public Phrase setPhrase(String _language, Phrase _phrase){
        return phraseHashMap.put(_language,_phrase);
    }

    public void setPhraseHashMap(HashMap<String, Phrase> phraseHashMap) {
        this.phraseHashMap = phraseHashMap;
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
