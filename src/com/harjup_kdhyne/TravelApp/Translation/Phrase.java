package com.harjup_kdhyne.TravelApp.Translation;

import java.util.Locale;

/**
 * Created by Paul on 2/10/14.
 * Object representing a phrase translated between the native language and a target language
 */
public class Phrase
{
    long id = -1;
    private String language;
    private String contents;

    public Phrase(String _language, String _contents)
    {
        language = _language;
        contents = _contents;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
