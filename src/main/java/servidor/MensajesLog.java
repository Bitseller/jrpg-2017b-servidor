package servidor;

import properties.Idioma;

/**
 * The Class MensajesLog.
 */
public final class MensajesLog {

    /**
     * Instantiates a new mensajes log.
     */
    private MensajesLog() {

    }

    /**
     * Usuario duplicado.
     *
     * @param usuario
     *            the usuario
     * @return the string
     */
    public static String usuarioDuplicado(final String usuario) {
        return Idioma.getIdioma().getProperty("ERR_REG_USR_DUPLICADO") + " " + usuario;
    }

    /**
     * Usuario registrado exitosamente.
     *
     * @param usuario
     *            the usuario
     * @return the string
     */
    public static String usuarioRegistradoExitosamente(final String usuario) {
        return Idioma.getIdioma().getProperty("OK_REG_USR") + " " + usuario;
    }

    /**
     * Usuario error general al registrar.
     *
     * @param usuario
     *            the usuario
     * @return the string
     */
    public static String usuarioErrorGeneralAlRegistrar(final String usuario) {
        return Idioma.getIdioma().getProperty("ERR_REG_USR_GRL") + " " + usuario;
    }

    /**
     * Estableciendo conexion.
     *
     * @return the string
     */
    public static String estableciendoConexion() {
        return Idioma.getIdioma().getProperty("CON_DB_ESTABLECIENDO");
    }

    /**
     * Conexion establecida.
     *
     * @return the string
     */
    public static String conexionEstablecida() {
        return Idioma.getIdioma().getProperty("CON_DB_ESTABLECIDA");
    }

    /**
     * Error establecimiento conexion DB.
     *
     * @return the string
     */
    public static String errorEstablecimientoConexionDB() {
        return Idioma.getIdioma().getProperty("CON_DB_ERR_ESTABLECER");
    }

    /**
     * Error cerrar conexion.
     *
     * @return the string
     */
    public static String errorCerrarConexion() {
        return Idioma.getIdioma().getProperty("CON_DB_ERR_ESTABLECER");
    }

    /**
     * Personaje error general al registrar.
     *
     * @return the string
     */
    public static String personajeErrorGeneralAlRegistrar() {
        return Idioma.getIdioma().getProperty("ERR_REG_Personaje");
    }

    /**
     * Personaje actualizado exitosamente.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String personajeActualizadoExitosamente(final String nombre) {
        return Idioma.getIdioma().getProperty("OK_UPD_PERSONAJE") + " " + nombre;
    }

    /**
     * Personaje error general al actualizar.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String personajeErrorGeneralAlActualizar(final String nombre) {
        return Idioma.getIdioma().getProperty("ERR_UPD_Personaje") + " " + nombre;
    }

    /**
     * Error al intentar recuperar el usuario.
     *
     * @param usuario
     *            the usuario
     * @return the string
     */
    public static String errorAlIntentarRecuperarElUsuario(final String usuario) {
        return Idioma.getIdioma().getProperty("ERR_RECUP_USR") + " " + usuario;
    }

    /**
     * Inventario error general al actualizar.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String inventarioErrorGeneralAlActualizar(final String nombre) {
        return Idioma.getIdioma().getProperty("ERR_GRAL_UPD_USR") + " " + nombre;
    }

    /**
     * Ingreso exitoso.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String ingresoExitoso(final String nombre) {
        return Idioma.getIdioma().getProperty("OK_LOGIN") + " " + nombre;
    }

    /**
     * Ingreso fallido.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String ingresoFallido(final String nombre) {
        return Idioma.getIdioma().getProperty("ERR_LOGIN") + " " + nombre;
    }

    /**
     * Personaje registrado exitosamente.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String personajeRegistradoExitosamente(final String nombre) {
        return Idioma.getIdioma().getProperty("OK_REG_PERSONAJE") + " " + nombre;
    }

    /**
     * Error al intentar recuperar el PJ.
     *
     * @param nombre
     *            the nombre
     * @return the string
     */
    public static String errorAlIntentarRecuperarElPJ(final String nombre) {
        return Idioma.getIdioma().getProperty("ERR_RECUP_PJ") + " " + nombre;
    }
}
