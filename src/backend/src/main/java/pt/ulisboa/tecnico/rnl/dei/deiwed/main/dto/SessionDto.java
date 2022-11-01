package pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Session;

// Data Transfer Object, to communicate with frontend
public class SessionDto implements Serializable {
	private long id;
	private LocalDateTime date;
	private String speaker;
	private String theme;

	public SessionDto() {
	}

	public SessionDto(long id, LocalDateTime date, String speaker, String theme) {
		this.id = id;
		this.date = date;
		this.speaker = speaker;
		this.theme = theme;
	}

	public SessionDto(Session session) {
		this(session.getId(), session.getDate(), session.getSpeaker(),
				session.getTheme());
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public LocalDateTime getDate(){
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

	public String getSpeaker() {
		return this.speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

    @Override
	public String toString() {
		return String.format(
				"SessionDto[id=%d, theme='%s', speaker='%s', date='%s']",
				id, theme, speaker, date.format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
