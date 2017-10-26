package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase que nos permite acceder a las propiedades de comunicacion. Se aplica el
 * patron singleton por las siguientes motivos. .Prentendemos encapsular el
 * acceso al archivos de propiedaes cada vez que queremos conocer algun valor de
 * alguna propiedad por ejemplo el puerto .Por otro lado consideramos que no se
 * debe estar accediendo constantemente al archivo en disco, accedemos una vez y
 * conservamos sus valores en memoria, y al controlar la creacion de instancias
 * aseguramos tener una sola copia de ellos. .Tambien con singleton nos evitamos
 * ensuciar el codigo que necesita acceder a las propiedades instanciando esta
 * clase.
 */
public final class PropiedadesComunicacion {
    private static final int MAX_PORT = 65535;

    private final String RUTA_PROPIEDADES = "properties/comunicacion.properties";

    private final String KEY_PORT = "PORT";

    private static PropiedadesComunicacion propiedades = null;

    private int puerto;

    /**
     * Gets the puerto servidor.
     *
     * @return the puerto servidor
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static int getPuertoServidor() throws IOException {
        getInstancia();
        return propiedades.puerto;
    }

    /**
     * Crea la unica instancia de esta clase
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private static void getInstancia() throws IOException {
        if (propiedades == null) {
            propiedades = new PropiedadesComunicacion();
        }
    }

    /**
     * Instantiates a new propiedades comunicacion.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private PropiedadesComunicacion() throws IOException {
        try {

            Properties propiedades = new Properties();
            InputStream stream = ClassLoader.getSystemResourceAsStream(RUTA_PROPIEDADES);
            propiedades.load(stream);

            puerto = Integer.parseInt(propiedades.getProperty(KEY_PORT));

            stream.close();

            stream.close();
            if (!validarPuerto(puerto)) {
                throw new IOException("Error de direccion ip del servidor");
            }
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Valida el puerto.
     *
     * @param puerto
     *            el puerto
     * @return true, if successful
     */
    public static boolean validarPuerto(final int puerto) {
        try {
            if (puerto > 0 && puerto < MAX_PORT) {
                return true;
            }

        } catch (Exception ex) {
            return false;
        }
        return false;
    }
}
