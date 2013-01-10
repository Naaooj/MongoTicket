package fr.free.naoj.mongoticket.shared.exception;
 
import fr.free.naoj.mongoticket.shared.Constant.ExceptionCode;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class InternalException extends Exception {

	private static final long serialVersionUID = -6294183858888115805L;

	private ExceptionCode exceptionCode;
	
	public InternalException() {
		this(null);
	}
	
	public InternalException(ExceptionCode exceptionCode) {
		this(exceptionCode, null);
	}
	
	public InternalException(ExceptionCode exceptionCode, Throwable cause) {
		super(cause);
		this.exceptionCode = exceptionCode;
	}

	public ExceptionCode getExceptionCode() {
		return exceptionCode;
	}
}
