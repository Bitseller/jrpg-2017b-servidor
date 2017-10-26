package comandos;

import java.io.IOException;

import mensajeria.Comando;
import mensajeria.PaqueteDeNPCs;
import mensajeria.PaquetePersonaje;
import servidor.Servidor;

/**
 * The Class MostrarMapas.
 */
public class MostrarMapas extends ComandosServer {

    @Override
    public void ejecutar() {
        escuchaCliente.setPaquetePersonaje(gson.fromJson(cadenaLeida, PaquetePersonaje.class));
        Servidor.log.append(escuchaCliente.getSocket().getInetAddress().getHostAddress() + " ha elegido el mapa "
                + escuchaCliente.getPaquetePersonaje().getMapa() + System.lineSeparator());

        // carga los NPCs del Mapa
        try {

            PaqueteDeNPCs paqueteNPCs;
            paqueteNPCs = new PaqueteDeNPCs(Servidor.getNPCs());
            paqueteNPCs.setComando(Comando.ACTUALIZARNPCS);
            // escuchaCliente.setIdPersonaje(paquetePersonaje.getId());

            escuchaCliente.getSalida().writeObject(gson.toJson(paqueteNPCs));

        } catch (IOException e) {
            Servidor.log.append("Falló al intentar crear NPCs \n");
            e.printStackTrace();
        }

    }

}
