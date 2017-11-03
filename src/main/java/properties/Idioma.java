package properties;

import java.io.IOException;
import java.util.Properties;

/**
 * The Class Idioma.
 */
public final class Idioma extends Properties {

    private static final long serialVersionUID = 1L;

    private static Idioma idioma;

    /**
     * Instantiates a new idioma.
     *
     * @param idioma
     *            the idioma
     */
    private Idioma(final String idioma) {
        try {
            this.load(getClass().getResourceAsStream(idioma));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the idioma espaniol.
     */
    public static void setIdiomaEspaniol() {
        idioma = new Idioma("spanish.properties");
    }

    /**
     * Sets the idioma ingles.
     */
    public static void setIdiomaIngles() {
        idioma = new Idioma("english.properties");
    }

    /**
     * Gets the idioma.
     *
     * @return the idioma
     */
    public static Idioma getIdioma() {
        if (idioma == null) {
            setIdiomaEspaniol();
        }
        return idioma;
    }
}