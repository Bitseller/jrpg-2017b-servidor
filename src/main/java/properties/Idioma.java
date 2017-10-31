package properties;
import java.io.IOException;
import java.util.Properties;

public class Idioma extends Properties{
    
    private static final long serialVersionUID = 1L;
    
    private static Idioma idioma;
    
    private Idioma(String idioma)
    {
        try {
			this.load(getClass().getResourceAsStream(idioma));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void setIdiomaEspaniol() {
       idioma = new Idioma("spanish.properties");
    }

    public static void setIdiomaIngles() {
       idioma = new Idioma("english.properties");
    }
    
    public static Idioma getIdioma() {
    	if(idioma == null)
    		setIdiomaEspaniol();
    	return idioma;
    }
}