package com.harjup_kdhyne.TravelApp.Translation;

import java.util.Locale;

/**
 * Created by Paul on 2/10/14.
 * Object representing a phrase translated between the native language and a target language
 */
public class Phrase
{
    private long id = -1;
    private long translationId = -1;
    private String language;
    private String contents;

    public Phrase(String _language, String _contents)
    {
        language = _language;
        contents = _contents;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTranslationId() {
        return translationId;
    }

    public void setTranslationId(long translationId) {
        this.translationId = translationId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


}
