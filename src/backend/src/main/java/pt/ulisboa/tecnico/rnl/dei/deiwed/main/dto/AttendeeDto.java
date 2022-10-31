package pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto;

import java.io.Serializable;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Attendee;

// Data Transfer Object, to communicate with frontend
public class AttendeeDto implements Serializable {
	private long id;
	private String name;
	private String istId;
	private String type;

	public AttendeeDto() {
	}

	public AttendeeDto(long id, String name, String istId, String type) {
		this.id = id;
		this.name = name;
		this.istId = istId;
		this.type = type;
	}

	public AttendeeDto(Attendee attendee) {
		this(attendee.getId(), attendee.getName(), attendee.getIstId(),
				attendee.getType().toString());
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("AttendeeDto[id=%d, name='%s', istId='%s', type='%s']", id, name, istId, type);
	}
}
