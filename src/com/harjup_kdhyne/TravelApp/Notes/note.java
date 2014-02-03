package com.harjup_kdhyne.TravelApp.Notes;

import java.util.Date;

/**
 * Created by Paul on 1/28/14.
 * Contains the data
 */
public class Note
{
    private long id;
    private String title;       //Title/short description the note contains
    private String content;     //Text content of the note
    private Date timeStamp;     //Time when the note was made

    private String imageId;     //TODO: Determine how to store picture(s), probably pointing to them somehow

    public Note()
    {}

    public Note(String title, Date timeStamp)
    {
        this.title = title;
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    //TODO: Format note timestamp into a nice string
    public String getTimeStampAsString()
    {
        return "4/20/0420";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
