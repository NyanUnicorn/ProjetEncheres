package fr.eni.encheres.dal;

public class DalException extends Exception {

	public static final String DAL_CANNOT_OPEN_CONECTION = "Ne  peut pas ouvrir connexion";
	public static final String DAL_NAMING_EXCEPTION = "Erreur de nommage";
	public static final String DAL_NO_IMPLEMENTATION = "Methode non implementé ou non implementable";
	public static final String DAL_ERROR_READING_DATA = "Methode non implementé ou non implementable";
	public static final String DAL_ERROR_WRITING_DATA = "Methode non implementé ou non implementable";
	
	
	
	public DalException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DalException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}
	public DalException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	public DalException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public DalException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
}
