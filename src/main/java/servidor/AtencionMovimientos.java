package servidor;

import com.google.gson.Gson;

import estados.Estado;
import mensajeria.Comando;
import mensajeria.PaqueteDeMovimientos;

/**
 * The Class AtencionMovimientos.
 */
public class AtencionMovimientos extends Thread {

    private final Gson gson = new Gson();

    @Override
    public void run() {

        synchronized (this) {

            try {

                while (true) {

                    // Espero a que se conecte alguien
                    wait();

                    // Le reenvio la conexion a todos

                    for (EscuchaCliente conectado : Servidor.getClientesConectados()) {

                        if (conectado.getPaquetePersonaje().getEstado() == Estado.estadoJuego) {

                            // envio la ubicacion de todos los personajes
                            PaqueteDeMovimientos pdp = (PaqueteDeMovimientos) new PaqueteDeMovimientos(
                                    Servidor.getUbicacionPersonajes()).clone();
                            pdp.setComando(Comando.MOVIMIENTO);
                            synchronized (conectado) {
                                conectado.getSalida().writeObject(gson.toJson(pdp));
                            }

                            // envio la ubicacion de todos los NPCs
                            PaqueteDeMovimientos pdpN = (PaqueteDeMovimientos) new PaqueteDeMovimientos(
                                    Servidor.getUbicacionNPCs()).clone();
                            pdpN.setComando(Comando.MOVIMIENTONPCS);
                            synchronized (conectado) {
                                conectado.getSalida().writeObject(gson.toJson(pdpN));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Servidor.log.append("Falló al intentar enviar paqueteDeMovimientos \n");
            }
        }
    }
}
