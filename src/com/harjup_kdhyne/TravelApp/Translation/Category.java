package com.harjup_kdhyne.TravelApp.Translation;

import java.io.Serializable;

/**
 * Created by Paul on 2/16/14.
 * Single name describing a set of phrases, like "food"
 */
public class Category implements Serializable
{
    private long id = -1;       //id for insertion in the database
    private String name;       //Title/short description the note contains

    public Category(long _id, String _name)
    {
        id = _id;
        name = _name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
