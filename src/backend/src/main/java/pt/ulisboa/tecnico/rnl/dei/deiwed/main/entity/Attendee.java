package pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.AttendeeDto;

@Entity
@Table(name = "attendees")
public class Attendee {

	public enum ATTENDEE_TYPE {
		TEACHER, GRANTEE
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "ist_id", nullable = false)
	private String istId;

	@Column(name = "type", nullable = false)
	private ATTENDEE_TYPE type;

	// TODO: maybe add more fields? ...or maybe not? what makes sense here?

	protected Attendee() {
	}

	public Attendee(String name, String istId, ATTENDEE_TYPE type) {
		this.name = name;
		this.istId = istId;
		this.type = type;
	}

	public Attendee(AttendeeDto attendeeDto) {
		this(attendeeDto.getName(), attendeeDto.getIstId(),
				ATTENDEE_TYPE.valueOf(attendeeDto.getType().toUpperCase()));
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIstId() {
		return this.istId;
	}

	public void setIstId(String istId) {
		this.istId = istId;
	}

	public ATTENDEE_TYPE getType() {
		return this.type;
	}

	public void setType(ATTENDEE_TYPE type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format(
				"Attendee[id=%d, name='%s', istId='%s', type='%s']",
				id, name, istId, type);
	}
}
