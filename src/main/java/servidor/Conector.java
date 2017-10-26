package servidor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

/**
 * The Class Conector.
 */
public class Conector {

    private String url = "primeraBase.bd";
    private Connection connect;

    private static final int COLUMNA_1 = 1;
    private static final int COLUMNA_2 = 2;
    private static final int COLUMNA_3 = 3;
    private static final int COLUMNA_4 = 4;
    private static final int COLUMNA_5 = 5;
    private static final int COLUMNA_6 = 6;
    private static final int COLUMNA_7 = 7;
    private static final int COLUMNA_8 = 8;
    private static final int COLUMNA_9 = 9;
    private static final int COLUMNA_10 = 10;
    private static final int COLUMNA_11 = 11;
    private static final int COLUMNA_12 = 12;
    private static final int COLUMNA_13 = 13;
    private static final int COLUMNA_14 = 14;

    /**
     * Connect.
     */
    public void connect() {
        try {
            Servidor.log.append("Estableciendo conexión con la base de datos..." + System.lineSeparator());
            connect = DriverManager.getConnection("jdbc:sqlite:" + url);
            Servidor.log.append("Conexión con la base de datos establecida con éxito." + System.lineSeparator());
        } catch (SQLException ex) {
            Servidor.log.append("Fallo al intentar establecer la conexión con la base de datos. " + ex.getMessage()
                    + System.lineSeparator());
        }
    }

    /**
     * Close.
     */
    public void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Servidor.log.append("Error al intentar cerrar la conexión con la base de datos." + System.lineSeparator());
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Registrar usuario.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean registrarUsuario(final PaqueteUsuario user) {
        ResultSet result = null;
        try {
            PreparedStatement st1 = connect.prepareStatement("SELECT * FROM registro WHERE usuario= ? ");
            st1.setString(1, user.getUsername());
            result = st1.executeQuery();

            if (!result.next()) {

                PreparedStatement st = connect
                        .prepareStatement("INSERT INTO registro (usuario, password, idPersonaje) VALUES (?,?,?)");
                st.setString(COLUMNA_1, user.getUsername());
                st.setString(COLUMNA_2, user.getPassword());
                st.setInt(COLUMNA_3, user.getIdPj());
                st.execute();
                Servidor.log.append("El usuario " + user.getUsername() + " se ha registrado." + System.lineSeparator());
                return true;
            } else {
                Servidor.log.append(
                        "El usuario " + user.getUsername() + " ya se encuentra en uso." + System.lineSeparator());
                return false;
            }
        } catch (SQLException ex) {
            Servidor.log.append("Eror al intentar registrar el usuario " + user.getUsername() + System.lineSeparator());
            System.err.println(ex.getMessage());
            return false;
        }

    }

    /**
     * Registrar personaje.
     *
     * @param paquetePersonaje the paquete personaje
     * @param paqueteUsuario the paquete usuario
     * @return true, if successful
     */
    public boolean registrarPersonaje(final PaquetePersonaje paquetePersonaje, final PaqueteUsuario paqueteUsuario) {

        try {

            // Registro al personaje en la base de datos
            PreparedStatement stRegistrarPersonaje = connect.prepareStatement(
                    "INSERT INTO personaje (idInventario, idMochila,casta,raza,fuerza,destreza,inteligencia,saludTope,energiaTope,nombre,experiencia,nivel,idAlianza,puntosSkill) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            stRegistrarPersonaje.setInt(COLUMNA_1, -1);
            stRegistrarPersonaje.setInt(COLUMNA_2, -1);
            stRegistrarPersonaje.setString(COLUMNA_3, paquetePersonaje.getCasta());
            stRegistrarPersonaje.setString(COLUMNA_4, paquetePersonaje.getRaza());
            stRegistrarPersonaje.setInt(COLUMNA_5, paquetePersonaje.getFuerza());
            stRegistrarPersonaje.setInt(COLUMNA_6, paquetePersonaje.getDestreza());
            stRegistrarPersonaje.setInt(COLUMNA_7, paquetePersonaje.getInteligencia());
            stRegistrarPersonaje.setInt(COLUMNA_8, paquetePersonaje.getSaludTope());
            stRegistrarPersonaje.setInt(COLUMNA_9, paquetePersonaje.getEnergiaTope());
            stRegistrarPersonaje.setString(COLUMNA_10, paquetePersonaje.getNombre());
            stRegistrarPersonaje.setInt(COLUMNA_11, 0);
            stRegistrarPersonaje.setInt(COLUMNA_12, 1);
            stRegistrarPersonaje.setInt(COLUMNA_13, -1);
            stRegistrarPersonaje.setInt(COLUMNA_14, paquetePersonaje.getPuntosSkill()); // Comienzan
            // con
            // los
            // puntos
            // para
            // asignar.
            stRegistrarPersonaje.execute();

            // Recupero la última key generada
            ResultSet rs = stRegistrarPersonaje.getGeneratedKeys();
            if (rs != null && rs.next()) {

                // Obtengo el id
                int idPersonaje = rs.getInt(1);

                // Le asigno el id al paquete personaje que voy a devolver
                paquetePersonaje.setId(idPersonaje);

                // Le asigno el personaje al usuario
                PreparedStatement stAsignarPersonaje = connect
                        .prepareStatement("UPDATE registro SET idPersonaje=? WHERE usuario=? AND password=?");
                stAsignarPersonaje.setInt(COLUMNA_1, idPersonaje);
                stAsignarPersonaje.setString(COLUMNA_2, paqueteUsuario.getUsername());
                stAsignarPersonaje.setString(COLUMNA_3, paqueteUsuario.getPassword());
                stAsignarPersonaje.execute();

                // Por ultimo registro el inventario y la mochila
                if (this.registrarInventarioMochila(idPersonaje)) {
                    Servidor.log.append("El usuario " + paqueteUsuario.getUsername() + " ha creado el personaje "
                            + paquetePersonaje.getId() + System.lineSeparator());
                    return true;
                } else {
                    Servidor.log.append(
                            "Error al registrar la mochila y el inventario del usuario " + paqueteUsuario.getUsername()
                                    + " con el personaje" + paquetePersonaje.getId() + System.lineSeparator());
                    return false;
                }
            }
            return false;

        } catch (SQLException e) {
            Servidor.log.append(
                    "Error al intentar crear el personaje " + paquetePersonaje.getNombre() + System.lineSeparator());
            return false;
        }

    }

    /**
     * Registrar inventario mochila.
     *
     * @param idInventarioMochila the id inventario mochila
     * @return true, if successful
     */
    public boolean registrarInventarioMochila(final int idInventarioMochila) {
        try {
            // Preparo la consulta para el registro el inventario en la base de
            // datos
            PreparedStatement stRegistrarInventario = connect.prepareStatement(
                    "INSERT INTO inventario(idInventario,manos1,manos2,pie,cabeza,pecho,accesorio) VALUES (?,-1,-1,-1,-1,-1,-1)");
            stRegistrarInventario.setInt(1, idInventarioMochila);

            // Preparo la consulta para el registro la mochila en la base de
            // datos
            PreparedStatement stRegistrarMochila = connect.prepareStatement(
                    "INSERT INTO mochila(idMochila,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,item11,item12,item13,item14,item15,item16,item17,item18,item19,item20) VALUES(?,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1)");
            stRegistrarMochila.setInt(COLUMNA_1, idInventarioMochila);

            // Registro inventario y mochila
            stRegistrarInventario.execute();
            stRegistrarMochila.execute();

            // Le asigno el inventario y la mochila al personaje
            PreparedStatement stAsignarPersonaje = connect
                    .prepareStatement("UPDATE personaje SET idInventario=?, idMochila=? WHERE idPersonaje=?");
            stAsignarPersonaje.setInt(COLUMNA_1, idInventarioMochila);
            stAsignarPersonaje.setInt(COLUMNA_2, idInventarioMochila);
            stAsignarPersonaje.setInt(COLUMNA_3, idInventarioMochila);
            stAsignarPersonaje.execute();

            Servidor.log.append("Se ha registrado el inventario de " + idInventarioMochila + System.lineSeparator());
            return true;

        } catch (SQLException e) {
            Servidor.log.append("Error al registrar el inventario de " + idInventarioMochila + System.lineSeparator());
            return false;
        }
    }

    /**
     * Loguear usuario.
     *
     * @param user the user
     * @return true, if successful
     */
    public boolean loguearUsuario(final PaqueteUsuario user) {
        ResultSet result = null;
        try {
            // Busco usuario y contraseña
            PreparedStatement st = connect
                    .prepareStatement("SELECT * FROM registro WHERE usuario = ? AND password = ? ");
            st.setString(COLUMNA_1, user.getUsername());
            st.setString(COLUMNA_2, user.getPassword());
            result = st.executeQuery();

            // Si existe inicio sesion
            if (result.next()) {
                Servidor.log
                        .append("El usuario " + user.getUsername() + " ha iniciado sesión." + System.lineSeparator());
                return true;
            }

            // Si no existe informo y devuelvo false
            Servidor.log.append("El usuario " + user.getUsername()
                    + " ha realizado un intento fallido de inicio de sesión." + System.lineSeparator());
            return false;

        } catch (SQLException e) {
            Servidor.log
                    .append("El usuario " + user.getUsername() + " fallo al iniciar sesión." + System.lineSeparator());
            return false;
        }

    }

    /**
     * Actualizar personaje.
     *
     * @param paquetePersonaje the paquete personaje
     */
    public void actualizarPersonaje(final PaquetePersonaje paquetePersonaje) {
        /*
         * Se agrego una instruccion para que se actualizen los puntos de skill ya
         * que, previamente este dato no se actualizada y cargaba 3 puntos cada vez
         * que se abría la ventana de AsignarSkills. AVERIGUAR COMO DIFICAR
         * StActualizarPersonaje (?.
         */
        try {
            int i = 2;
            int j = 1;
            PreparedStatement stActualizarPersonaje = connect.prepareStatement(
                    "UPDATE personaje SET fuerza=?, destreza=?, inteligencia=?, saludTope=?, energiaTope=?, experiencia=?, nivel=?, puntosSkill=? "
                            + "  WHERE idPersonaje=?");

            stActualizarPersonaje.setInt(COLUMNA_1, paquetePersonaje.getFuerza());
            stActualizarPersonaje.setInt(COLUMNA_2, paquetePersonaje.getDestreza());
            stActualizarPersonaje.setInt(COLUMNA_3, paquetePersonaje.getInteligencia());
            stActualizarPersonaje.setInt(COLUMNA_4, paquetePersonaje.getSaludTope());
            stActualizarPersonaje.setInt(COLUMNA_5, paquetePersonaje.getEnergiaTope());
            stActualizarPersonaje.setInt(COLUMNA_6, paquetePersonaje.getExperiencia());
            stActualizarPersonaje.setInt(COLUMNA_7, paquetePersonaje.getNivel());
            stActualizarPersonaje.setInt(COLUMNA_8, paquetePersonaje.getPuntosSkill());
            stActualizarPersonaje.setInt(COLUMNA_9, paquetePersonaje.getId());
            stActualizarPersonaje.executeUpdate();

            PreparedStatement stDameItemsID = connect.prepareStatement("SELECT * FROM mochila WHERE idMochila = ?");
            stDameItemsID.setInt(1, paquetePersonaje.getId());
            ResultSet resultadoItemsID = stDameItemsID.executeQuery();
            PreparedStatement stDatosItem = connect.prepareStatement("SELECT * FROM item WHERE idItem = ?");
            ResultSet resultadoDatoItem = null;
            paquetePersonaje.eliminarItems();

            while (j <= 9) {
                if (resultadoItemsID.getInt(i) != -1) {
                    stDatosItem.setInt(1, resultadoItemsID.getInt(i));
                    resultadoDatoItem = stDatosItem.executeQuery();

                    paquetePersonaje.anadirItem(resultadoDatoItem.getInt("idItem"),
                            resultadoDatoItem.getString("nombre"), resultadoDatoItem.getInt("wereable"),
                            resultadoDatoItem.getInt("bonusSalud"), resultadoDatoItem.getInt("bonusEnergia"),
                            resultadoDatoItem.getInt("bonusFuerza"), resultadoDatoItem.getInt("bonusDestreza"),
                            resultadoDatoItem.getInt("bonusInteligencia"), resultadoDatoItem.getString("foto"),
                            resultadoDatoItem.getString("fotoEquipado"));
                }
                i++;
                j++;
            }
            Servidor.log.append("El personaje " + paquetePersonaje.getNombre() + " se ha actualizado con éxito."
                    + System.lineSeparator());
        } catch (SQLException e) {
            Servidor.log.append("Fallo al intentar actualizar el personaje " + paquetePersonaje.getNombre()
                    + System.lineSeparator());
        }

    }

    /**
     * Gets the personaje.
     *
     * @param user the user
     * @return the personaje
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public PaquetePersonaje getPersonaje(final PaqueteUsuario user) throws IOException {
        ResultSet result = null;
        ResultSet resultadoItemsID = null;
        ResultSet resultadoDatoItem = null;
        int i = 2;
        int j = 0;
        try {
            // Selecciono el personaje de ese usuario
            PreparedStatement st = connect.prepareStatement("SELECT * FROM registro WHERE usuario = ?");
            st.setString(1, user.getUsername());
            result = st.executeQuery();

            // Obtengo el id
            int idPersonaje = result.getInt("idPersonaje");

            // Selecciono los datos del personaje
            PreparedStatement stSeleccionarPersonaje = connect
                    .prepareStatement("SELECT * FROM personaje WHERE idPersonaje = ?");
            stSeleccionarPersonaje.setInt(1, idPersonaje);
            result = stSeleccionarPersonaje.executeQuery();
            // Traigo los id de los items correspondientes a mi personaje
            PreparedStatement stDameItemsID = connect.prepareStatement("SELECT * FROM mochila WHERE idMochila = ?");
            stDameItemsID.setInt(1, idPersonaje);
            resultadoItemsID = stDameItemsID.executeQuery();
            // Traigo los datos del item
            PreparedStatement stDatosItem = connect.prepareStatement("SELECT * FROM item WHERE idItem = ?");

            // Obtengo los atributos del personaje
            PaquetePersonaje personaje = new PaquetePersonaje();
            personaje.setId(idPersonaje);
            personaje.setRaza(result.getString("raza"));
            personaje.setCasta(result.getString("casta"));
            personaje.setFuerza(result.getInt("fuerza"));
            personaje.setInteligencia(result.getInt("inteligencia"));
            personaje.setDestreza(result.getInt("destreza"));
            personaje.setEnergiaTope(result.getInt("energiaTope"));
            personaje.setSaludTope(result.getInt("saludTope"));
            personaje.setNombre(result.getString("nombre"));
            personaje.setExperiencia(result.getInt("experiencia"));
            personaje.setNivel(result.getInt("nivel"));
            personaje.setPuntosSkill(result.getInt("puntosSkill"));
            // Se debe agregar a la Base de Datos.

            while (j <= 9) {
                if (resultadoItemsID.getInt(i) != -1) {
                    stDatosItem.setInt(1, resultadoItemsID.getInt(i));
                    resultadoDatoItem = stDatosItem.executeQuery();
                    personaje.anadirItem(resultadoDatoItem.getInt("idItem"), resultadoDatoItem.getString("nombre"),
                            resultadoDatoItem.getInt("wereable"), resultadoDatoItem.getInt("bonusSalud"),
                            resultadoDatoItem.getInt("bonusEnergia"), resultadoDatoItem.getInt("bonusFuerza"),
                            resultadoDatoItem.getInt("bonusDestreza"), resultadoDatoItem.getInt("bonusInteligencia"),
                            resultadoDatoItem.getString("foto"), resultadoDatoItem.getString("fotoEquipado"));
                }
                i++;
                j++;
            }

            // Devuelvo el paquete personaje con sus datos
            return personaje;

        } catch (SQLException ex) {
            Servidor.log
                    .append("Fallo al intentar recuperar el personaje " + user.getUsername() + System.lineSeparator());
            Servidor.log.append(ex.getMessage() + System.lineSeparator());
        }

        return new PaquetePersonaje();
    }

    /**
     * Gets the usuario.
     *
     * @param usuario the usuario
     * @return the usuario
     */
    public PaqueteUsuario getUsuario(final String usuario) {
        ResultSet result = null;
        PreparedStatement st;

        try {
            st = connect.prepareStatement("SELECT * FROM registro WHERE usuario = ?");
            st.setString(1, usuario);
            result = st.executeQuery();

            String password = result.getString("password");
            int idPersonaje = result.getInt("idPersonaje");

            PaqueteUsuario paqueteUsuario = new PaqueteUsuario();
            paqueteUsuario.setUsername(usuario);
            paqueteUsuario.setPassword(password);
            paqueteUsuario.setIdPj(idPersonaje);

            return paqueteUsuario;
        } catch (SQLException e) {
            Servidor.log.append("Fallo al intentar recuperar el usuario " + usuario + System.lineSeparator());
            Servidor.log.append(e.getMessage() + System.lineSeparator());
        }

        return new PaqueteUsuario();
    }

    /**
     * Actualizar inventario.
     *
     * @param paquetePersonaje the paquete personaje
     */
    public void actualizarInventario(final PaquetePersonaje paquetePersonaje) {
        int i = 0;
        PreparedStatement stActualizarMochila;
        try {
            stActualizarMochila = connect.prepareStatement(
                    "UPDATE mochila SET item1=? ,item2=? ,item3=? ,item4=? ,item5=? ,item6=? ,item7=? ,item8=? ,item9=? "
                            + ",item10=? ,item11=? ,item12=? ,item13=? ,item14=? ,item15=? ,item16=? ,item17=? ,item18=? ,item19=? ,item20=? WHERE idMochila=?");
            while (i < paquetePersonaje.getCantItems()) {
                stActualizarMochila.setInt(i + 1, paquetePersonaje.getItemID(i));
                i++;
            }
            for (int j = paquetePersonaje.getCantItems(); j < 20; j++) {
                stActualizarMochila.setInt(j + 1, -1);
            }
            stActualizarMochila.setInt(21, paquetePersonaje.getId());
            stActualizarMochila.executeUpdate();

        } catch (SQLException e) {
        }
    }

    /**
     * Actualizar inventario.
     *
     * @param idPersonaje the id personaje
     */
    public void actualizarInventario(final int idPersonaje) {
        int i = 0;
        PaquetePersonaje paquetePersonaje = Servidor.getPersonajesConectados().get(idPersonaje);
        PreparedStatement stActualizarMochila;
        try {
            stActualizarMochila = connect.prepareStatement(
                    "UPDATE mochila SET item1=? ,item2=? ,item3=? ,item4=? ,item5=? ,item6=? ,item7=? ,item8=? ,item9=? "
                            + ",item10=? ,item11=? ,item12=? ,item13=? ,item14=? ,item15=? ,item16=? ,item17=? ,item18=? ,item19=? ,item20=? WHERE idMochila=?");
            while (i < paquetePersonaje.getCantItems()) {
                stActualizarMochila.setInt(i + 1, paquetePersonaje.getItemID(i));
                i++;
            }
            if (paquetePersonaje.getCantItems() < 9) {
                int itemGanado = new Random().nextInt(29);
                itemGanado += 1;
                stActualizarMochila.setInt(paquetePersonaje.getCantItems() + 1, itemGanado);
                for (int j = paquetePersonaje.getCantItems() + 2; j < 20; j++) {
                    stActualizarMochila.setInt(j, -1);
                }
            } else {
                for (int j = paquetePersonaje.getCantItems() + 1; j < 20; j++) {
                    stActualizarMochila.setInt(j, -1);
                }
            }
            stActualizarMochila.setInt(21, paquetePersonaje.getId());
            stActualizarMochila.executeUpdate();

        } catch (SQLException e) {
            Servidor.log.append("Falló al intentar actualizar inventario de" + idPersonaje + "\n");
        }
    }

    /**
     * Actualizar personaje subio nivel.
     *
     * @param paquetePersonaje the paquete personaje
     */
    public void actualizarPersonajeSubioNivel(final PaquetePersonaje paquetePersonaje) {
        try {
            PreparedStatement stActualizarPersonaje = connect.prepareStatement(
                    "UPDATE personaje SET fuerza=?, destreza=?, inteligencia=?, saludTope=?, energiaTope=?, experiencia=?, nivel=?, puntosSkill=? "
                            + "  WHERE idPersonaje=?");

            stActualizarPersonaje.setInt(COLUMNA_1, paquetePersonaje.getFuerza());
            stActualizarPersonaje.setInt(COLUMNA_2, paquetePersonaje.getDestreza());
            stActualizarPersonaje.setInt(COLUMNA_3, paquetePersonaje.getInteligencia());
            stActualizarPersonaje.setInt(COLUMNA_4, paquetePersonaje.getSaludTope());
            stActualizarPersonaje.setInt(COLUMNA_5, paquetePersonaje.getEnergiaTope());
            stActualizarPersonaje.setInt(COLUMNA_6, paquetePersonaje.getExperiencia());
            stActualizarPersonaje.setInt(COLUMNA_7, paquetePersonaje.getNivel());
            stActualizarPersonaje.setInt(COLUMNA_8, paquetePersonaje.getPuntosSkill());
            stActualizarPersonaje.setInt(COLUMNA_9, paquetePersonaje.getId());
            stActualizarPersonaje.executeUpdate();

            Servidor.log.append("El personaje " + paquetePersonaje.getNombre() + " se ha actualizado con éxito."
                    + System.lineSeparator());
        } catch (SQLException e) {
            Servidor.log.append("Fallo al intentar actualizar el personaje " + paquetePersonaje.getNombre()
                    + System.lineSeparator());
        }
    }
}
