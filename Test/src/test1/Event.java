package test1;

import java.util.Date;

public class Event
{
	private int eventUserAccuont;

	private int eventId;
	private int calendarId;
	
	private String tilte;
	
	private Date startDate;
	private Date endDate;
	private boolean isAllDay;
	
	private String location;
	private String notes;
	private String url;
	private String reminder;
	
	private boolean isNew;
	private boolean isPublic;

	public int getEventId()
	{
		return eventId;
	}

	public void setEventId(int eventId)
	{
		this.eventId = eventId;
	}

	public int getCalendarId()
	{
		return calendarId;
	}

	public void setCalendarId(int calendarId)
	{
		this.calendarId = calendarId;
	}

	public String getTilte()
	{
		return tilte;
	}

	public void setTilte(String tilte)
	{
		this.tilte = tilte;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isAllDay()
	{
		return isAllDay;
	}

	public void setAllDay(boolean isAllDay)
	{
		this.isAllDay = isAllDay;
	}

	public String getReminder()
	{
		return reminder;
	}

	public void setReminder(String reminder)
	{
		this.reminder = reminder;
	}

	public boolean isNew()
	{
		return isNew;
	}

	public void setNew(boolean isNew)
	{
		this.isNew = isNew;
	}

	public boolean isPublic()
	{
		return isPublic;
	}

	public void setPublic(boolean isPublic)
	{
		this.isPublic = isPublic;
	}

	public int getEventUserAccuont()
	{
		return eventUserAccuont;
	}

	public void setEventUserAccuont(int eventUserAccuont)
	{
		this.eventUserAccuont = eventUserAccuont;
	}

	@Override
	public String toString()
	{
		return "Event [eventUserAccuont=" + eventUserAccuont
				+ ", eventId="
				+ eventId
				+ ", calendarId="
				+ calendarId
				+ ", tilte="
				+ tilte
				+ ", startDate="
				+ startDate
				+ ", endDate="
				+ endDate
				+ ", isAllDay="
				+ isAllDay
				+ ", location="
				+ location
				+ ", notes="
				+ notes
				+ ", url="
				+ url
				+ ", reminder="
				+ reminder
				+ ", isNew="
				+ isNew
				+ ", isPublic="
				+ isPublic
				+ "]";
	}
}

