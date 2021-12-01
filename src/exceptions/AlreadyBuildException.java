package exceptions;

public class AlreadyBuildException extends BuildingException {
	public AlreadyBuildException() {
		super();
	}
	public AlreadyBuildException(String s) {
		super(s);
	}

}
