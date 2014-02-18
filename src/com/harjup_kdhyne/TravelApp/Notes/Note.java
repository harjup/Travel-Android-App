package com.harjup_kdhyne.TravelApp.Notes;

import java.io.Serializable;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paul on 1/28/14.
 * Note object
 */
public class Note implements Serializable {
    private long id = -1;       //id for insertion in the database
    private String title;       //Title/short description the note contains
    private String content;     //Text content of the note
    private Date timeStamp;     //Time when the note was made

    private String imageId;     //TODO: Determine how to store picture(s), probably pointing to them somehow

    public Note()
    {
        this.timeStamp = new Date();
    }

    public Note(String title, String content)
    {
        this.title = title;
        this.content = content;
        this.timeStamp = new Date();
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

    public String getTimeStampAsString()
    {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //formattedDate += timeStamp.getMonth().ToString();
        String date = dateFormat.format(timeStamp);

        return date;
    }

    public void setTimeStampFromString(String time)
    {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.timeStamp = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Timestamp date = Timestamp.


    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
