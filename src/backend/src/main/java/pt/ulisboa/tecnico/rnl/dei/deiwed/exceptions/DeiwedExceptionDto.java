package pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DeiwedExceptionDto {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private int code;

	public DeiwedExceptionDto(DeiwedException e) {
		this.timestamp = LocalDateTime.now();
		this.message = e.getMessage();
		this.code = e.getCode();
	}

	public DeiwedExceptionDto(Throwable e) {
		this.timestamp = LocalDateTime.now();
		this.message = "Unexpected error";
		this.debugMessage = e.getLocalizedMessage();
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public String getDebugMessage() {
		return this.debugMessage;
	}

	public int getCode() {
		return this.code;
	}

}
