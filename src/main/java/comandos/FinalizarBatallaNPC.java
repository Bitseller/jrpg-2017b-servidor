package comandos;

import java.io.IOException;

import estados.Estado;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteMovimiento;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * The Class FinalizarBatallaNPC.
 */
public class FinalizarBatallaNPC extends ComandosServer {

    private static final int RANDOM_HASTA = 500;
    private static final int RANDOM_X_DESDE = 100;
    private static final int RANDOM_Y_DESDE = 10;
    private static final float POS_Y_ALEATORIA = RANDOM_Y_DESDE + ((float) Math.random() * RANDOM_HASTA);
    private static final float POS_X_ALEATORIA = RANDOM_X_DESDE + ((float) Math.random() * RANDOM_HASTA);

    @Override
    public void ejecutar() {

        PaqueteFinalizarBatalla paqueteFinalizarBatalla = gson.fromJson(cadenaLeida, PaqueteFinalizarBatalla.class);
        paqueteFinalizarBatalla.setComando(FINALIZARBATALLA);
        escuchaCliente.setPaqueteFinalizarBatalla(paqueteFinalizarBatalla);

        Servidor.getConector().actualizarInventario(paqueteFinalizarBatalla.getId());
        Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteFinalizarBatalla().getId())
                .setEstado(Estado.estadoJuego);

        if (paqueteFinalizarBatalla.getGanadorBatalla() < 0) { // gano el personaje
            Servidor.getUbicacionNPCs().remove(paqueteFinalizarBatalla.getIdEnemigo());
            // PaqueteDeNPC newNPC;
            // new NPC =

            // PaqueteDeNPC newNPC = new PaqueteDeNPC(
            // paqueteFinalizarBatalla.getIdEnemigo() );
            PaqueteMovimiento newPosicion = new PaqueteMovimiento(paqueteFinalizarBatalla.getIdEnemigo(),
                    POS_X_ALEATORIA, POS_Y_ALEATORIA);

            Servidor.getUbicacionNPCs().put(paqueteFinalizarBatalla.getIdEnemigo(), newPosicion);

        }

        for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
            if (conectado.getIdPersonaje() == escuchaCliente.getPaqueteFinalizarBatalla().getId()) {
                try {
                    conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteFinalizarBatalla()));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Servidor.log.append("Falló al intentar enviar finalizarBatalla a:"
                            + conectado.getPaquetePersonaje().getId() + "\n");
                }
            }
        }

        synchronized (Servidor.atencionMovimientos) {
            Servidor.atencionMovimientos.notify();
        }

    }

}
