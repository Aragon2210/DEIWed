package pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.SessionDto;

@Entity
@Table(name = "session")
public class Session {

	@Id
	@GeneratedValue
	private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

	@Column(name = "speaker", nullable = false)
	private String speaker;

    @Column(name = "theme", nullable = false)
	private String theme;

    /* @Column(name = "posterId", nullable = true)
	private Long posterId; */

    //lunch_dishes 
    //participants

	protected Session() {
	}

	public Session(LocalDateTime date, String speaker, String theme) {
		this.date = date;
        this.speaker = speaker;
        this.theme = theme;
	}

	public Session(SessionDto sessionDto) {
		this(sessionDto.getDate(), sessionDto.getSpeaker(), 
            sessionDto.getTheme());
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public LocalDateTime getDate() {
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
				"Session[id=%d, theme='%s', speaker='%s', date='%s']",
				id, theme, speaker, date.format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
