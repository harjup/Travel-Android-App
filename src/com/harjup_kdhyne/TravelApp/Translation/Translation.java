package com.harjup_kdhyne.TravelApp.Translation;

import java.util.List;

/**
 * Created by Paul on 2/16/14.
 * Contains a list of phrases in different languages and the categories they can be described as
 */
public class Translation
{
    private long id = -1;       //id for insertion in the database
    private List<Phrase> phrases;       //Title/short description the note contains
    private List<Category> categories;       //Title/short description the note contains
    private String imageId;
}
