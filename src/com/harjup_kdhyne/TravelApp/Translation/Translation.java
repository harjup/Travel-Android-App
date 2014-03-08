package com.harjup_kdhyne.TravelApp.Translation;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Paul on 2/16/14.
 * Contains a list of phrases in different languages and the categories they can be described as
 */
public class Translation implements Serializable
{
    private long id = -1;       //id for insertion in the database
    private String homePhrase;
    private String homeLanguage;
    //private HashMap<String, String> phraseHashMap = new HashMap<String, String>();       //Map of language codes to phrases for each language
    private List<Phrase> phrases = new ArrayList<Phrase>();       //Map of language codes to phrases for each language
    private List<Category> categories = new ArrayList<Category>();       //List of categories that apply to translation
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

    /*public HashMap<String, String> getPhraseHashMap() {
        return phraseHashMap;
    }*/

    public List<Phrase> getPhraseList() {
        return phrases;
    }

    public String getPhrase(String _language){
        //return phraseHashMap.get(_language);

        Iterator<Phrase> it = phrases.iterator();
        while(it.hasNext())
        {
            Phrase p = it.next();
            if(p.getLanguage().equals(_language))
            {
                return p.getContent();
            }
        }
        return null;
    }



    public void setPhrase(Phrase _phrase){
        if (phrases.isEmpty())
        {
            phrases.add(_phrase);
            return;
        }

        Iterator<Phrase> it = phrases.iterator();
        while(it.hasNext())
        {
            Phrase p = it.next();
            if(p.getLanguage().equals(_phrase.getLanguage()))
            {
                p.setContents(_phrase.getLanguage());
                return;
            }
        }

        phrases.add(_phrase);
    }

    public void setPhrase(String _language, String _phrase){
        if (phrases.isEmpty())
        {
            phrases.add(new Phrase(_language, _phrase));
            return;
        }

        ListIterator<Phrase> it = phrases.listIterator();
        while(it.hasNext())
        {
            Phrase p = it.next();
            if(p.getLanguage().equals(_language))
            {
                p.setContents(_phrase);
                return;
            }
        }
        phrases.add(new Phrase(_language, _phrase));

        //return phraseHashMap.put(_language,_phrase);
    }

    /*public void setPhraseHashMap(HashMap<String, String> phraseHashMap) {
        this.phraseHashMap = phraseHashMap;
    }*/

    public List<Category> getCategories() {
        return categories;
    }

    //Return whether it was added
    public Boolean addCategory(Category _category){
        for (Category category : categories)
        {
            if (category.getName().equals(_category.getName()))
            {
                return false;
            }
        }
        categories.add(_category);
        return true;
    }

    //Return whether it was removed
    public Boolean removeCategory(Category _category){
        Boolean existsInList = false;

        for (Category category : categories)
        {
            if (category.getName().equals(_category.getName()))
            {
                categories.remove(category);
                return true;
            }
        }
        return false;
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
