package servidor;

import properties.Idioma;

public final class MensajesLog {
	
    public static String usuarioDuplicado(String usuario){
    	return Idioma.getIdioma().getProperty("ERR_REG_USR_DUPLICADO")+" "+usuario;
    }
	public static String usuarioRegistradoExitosamente(String usuario) {
    	return Idioma.getIdioma().getProperty("OK_REG_USR")+" "+usuario;
	}
	public static String usuarioErrorGeneralAlRegistrar(String usuario) {
    	return Idioma.getIdioma().getProperty("ERR_REG_USR_GRL")+" "+usuario;
	}
	public static String estableciendoConexion() {
    	return Idioma.getIdioma().getProperty("CON_DB_ESTABLECIENDO");
	}
	public static String conexionEstablecida() {
    	return Idioma.getIdioma().getProperty("CON_DB_ESTABLECIDA");
	}
	public static String errorEstablecimientoConexionDB() {
    	return Idioma.getIdioma().getProperty("CON_DB_ERR_ESTABLECER");
	}
	public static String errorCerrarConexion() {
    	return Idioma.getIdioma().getProperty("CON_DB_ERR_ESTABLECER");
	}
	public static String personajeErrorGeneralAlRegistrar() {
    	return Idioma.getIdioma().getProperty("ERR_REG_Personaje");
	}
	public static String personajeActualizadoExitosamente(String nombre) {
    	return Idioma.getIdioma().getProperty("OK_UPD_PERSONAJE")+" "+nombre;
	}
	public static String personajeErrorGeneralAlActualizar(String nombre) {
    	return Idioma.getIdioma().getProperty("ERR_UPD_Personaje")+" "+nombre;
	}
	public static String errorAlIntentarRecuperarElUsuario(String usuario) {
    	return Idioma.getIdioma().getProperty("ERR_RECUP_USR")+" "+usuario;
	}
	public static String inventarioErrorGeneralAlActualizar(String nombre) {
    	return Idioma.getIdioma().getProperty("ERR_GRAL_UPD_USR")+" "+nombre;
	}
	public static String ingresoExitoso(String nombre) {
    	return Idioma.getIdioma().getProperty("OK_LOGIN")+" "+nombre;
	}
	public static String ingresoFallido(String nombre) {
    	return Idioma.getIdioma().getProperty("ERR_LOGIN")+" "+nombre;
	}
	public static String personajeRegistradoExitosamente(String nombre) {
    	return Idioma.getIdioma().getProperty("OK_REG_PERSONAJE")+" "+nombre;
	}
	public static String errorAlIntentarRecuperarElPJ(String nombre) {
    	return Idioma.getIdioma().getProperty("ERR_RECUP_PJ")+" "+nombre;
	}
}
