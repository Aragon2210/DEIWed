package pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions;

public enum ErrorMessage {

	NO_SUCH_ATTENDEE("Não existe nenhum participante com o ID %s", 1001),
	ATTENDEE_NAME_NOT_VALID("O nome de participante especificado não é válido.", 1002);

	private final String label;
	private final int code;

	ErrorMessage(String label, int code) {
		this.label = label;
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}

	public int getCode() {
		return this.code;
	}
}
