package comandos;

import java.io.IOException;

import mensajeria.PaquetePersonaje;
import servidor.Servidor;

/**
 * The Class CrearPersonaje.
 */
public class CrearPersonaje extends ComandosServer {

    @Override
    public void ejecutar() {
        // Casteo el paquete personaje
        escuchaCliente.setPaquetePersonaje((getGson().fromJson(getCadenaLeida(), PaquetePersonaje.class)));
        // Guardo el personaje en ese usuario
        Servidor.getConector().registrarPersonaje(escuchaCliente.getPaquetePersonaje(),
                escuchaCliente.getPaqueteUsuario());
        try {
            PaquetePersonaje paquetePersonaje;
            paquetePersonaje = new PaquetePersonaje();
            paquetePersonaje = Servidor.getConector().getPersonaje(escuchaCliente.getPaqueteUsuario());
            escuchaCliente.setIdPersonaje(paquetePersonaje.getId());
            escuchaCliente.getPaquetePersonaje().setId(paquetePersonaje.getId());
            escuchaCliente.getSalida().writeObject(
                    getGson().toJson(escuchaCliente.getPaquetePersonaje(), escuchaCliente.getPaquetePersonaje().getClass()));
        } catch (IOException e1) {
            Servidor.log.append("Falló al intentar enviar personaje creado \n");
        }

    }

}
