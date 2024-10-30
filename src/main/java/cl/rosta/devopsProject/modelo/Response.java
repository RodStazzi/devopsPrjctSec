package cl.rosta.devopsProject.modelo;

public class Response {

	// Variables Privadas
	public String code;
	public String message;	

	// -------------------------------------------------------------------
	/*
	 * Constructor de la clase Response
	 */
	public Response() {
		this.code = "00";
		this.message = "OK";
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	/*
	 * Constructor de la clase Response
	 */
	public Response( String code, String message ) {
		this.code = code;
		this.message = message;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	/*
	 * Establece Respuesta Positiva
	 */
	public void setResponseOk() {
		this.code = "00";
		this.message = "OK";
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	/*
	 * Establece el mensaje de error
	 * @param Codigo del error
	 * @param Mensaje de error
	 */
	public void setResponseError( String code, String message ) {
		this.code = code;
		this.message = message;
	}
	// -------------------------------------------------------------------
	// -------------------------------------------------------------------
	@Override
	public String toString() {
		return "Response [code=" + code + ", message=" + message + "]";
	}
	// -------------------------------------------------------------------
	
}