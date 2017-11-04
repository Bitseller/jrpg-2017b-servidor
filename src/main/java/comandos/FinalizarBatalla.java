package comandos;

import java.io.IOException;

import estados.Estado;
import mensajeria.PaqueteFinalizarBatalla;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * The Class FinalizarBatalla.
 */
public class FinalizarBatalla extends ComandosServer {

    @Override
    public void ejecutar() {

        PaqueteFinalizarBatalla paqueteFinalizarBatalla = gson.fromJson(cadenaLeida, PaqueteFinalizarBatalla.class);
        escuchaCliente.setPaqueteFinalizarBatalla(paqueteFinalizarBatalla);
        Servidor.getConector().actualizarMochila(paqueteFinalizarBatalla.getGanadorBatalla());
        Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteFinalizarBatalla().getId())
                .setEstado(Estado.getEstadoJuego());
        Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteFinalizarBatalla().getIdEnemigo())
                .setEstado(Estado.getEstadoJuego());
        for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
            if (conectado.getIdPersonaje() == escuchaCliente.getPaqueteFinalizarBatalla().getIdEnemigo()) {
                try {
                    conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteFinalizarBatalla()));
                } catch (IOException e) {
                    Servidor.log.append("Falló al intentar enviar finalizarBatalla a:"
                            + conectado.getPaquetePersonaje().getId() + "\n");
                }
            }
        }

        synchronized (Servidor.atencionConexiones) {
            Servidor.atencionConexiones.notify();
        }

    }

}
