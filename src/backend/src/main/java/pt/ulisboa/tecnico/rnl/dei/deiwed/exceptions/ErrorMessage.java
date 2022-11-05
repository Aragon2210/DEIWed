package pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions;

public enum ErrorMessage {

	NO_SUCH_ATTENDEE("Não existe nenhum participante com o ID %s", 1001),
	ATTENDEE_NAME_NOT_VALID("O nome de participante especificado não é válido", 1002),

	NO_SUCH_SESSION("Não existe nenhuma sessão com o ID %s", 1003),
	SESSION_DATE_NOT_WEDNESDAY("A data da sessão deveria ser numa quarta-feira", 1004),

	ATTENDEE_ALREADY_ENROLLED_SESSION("O participante com ID %s já está inscrito na sessao com ID %s", 1005),
	ATTENDEE_NOT_IN_SESSION("O participante com ID %s nao pode ser removido da sessao com ID %s pois nao esta inscrito", 1006);

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
