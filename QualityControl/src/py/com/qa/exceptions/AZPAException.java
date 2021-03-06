package py.com.qa.exceptions;

public class AZPAException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Code code;
	private String userMessage;

	public AZPAException(Code code, String message) {
		super(message);
		this.code = code;
	}

	public AZPAException(Code code, String message, String userMessage) {
		super(message);
		this.userMessage = userMessage;
		this.code = code;
	}

	public AZPAException(Code code) {
		this.code = code;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}

	/**
	 * @param userMessage
	 *            the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public Type getType() {
		return code.getType();
	}

	public Code getCode() {
		return code;
	}
}