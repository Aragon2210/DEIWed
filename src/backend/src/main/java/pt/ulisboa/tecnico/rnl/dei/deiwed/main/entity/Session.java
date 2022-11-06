package pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions.DeiwedException;
import pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions.ErrorMessage;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.SessionDto;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Attendee;

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

    /* @Column(name = "poster", nullable = true)
	private guardar imagem aqui */

    //lunch_dishes 

    @ManyToMany(mappedBy = "sessions")
    private List<Attendee> attendees = new ArrayList<>();

	protected Session() {
	}

	public Session(LocalDateTime date, String speaker, String theme) {
		
		if(date.getDayOfWeek().getValue() != 3){
			new DeiwedException(ErrorMessage.SESSION_DATE_NOT_WEDNESDAY);
		}

		this.date = date;
        this.speaker = speaker;
        this.theme = theme;
	}

	public Session(SessionDto sessionDto) {
		this(LocalDateTime.parse(sessionDto.getDate(), DateTimeFormatter.ISO_DATE_TIME), sessionDto.getSpeaker(), 
            sessionDto.getTheme());
	}

    public void remove() {
        this.attendees.forEach(att -> att.removeSession(this));
        this.attendees.clear();
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

    public List<Attendee> getAttendees() {
        return this.attendees;
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

	
	@Override
	public String toString() {
		return String.format(
				"Session[id=%d, theme='%s', speaker='%s', date='%s']",
				id, theme, speaker, date.format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
