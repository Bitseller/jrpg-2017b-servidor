package comandos;

import java.io.IOException;

import mensajeria.PaqueteAtacar;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * The Class Atacar.
 */
public class Atacar extends ComandosServer {

    @Override
    public void ejecutar() {
        escuchaCliente.setPaqueteAtacar(getGson().fromJson(getCadenaLeida(), PaqueteAtacar.class));
        for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
            if (conectado.getIdPersonaje() == escuchaCliente.getPaqueteAtacar().getIdEnemigo()) {
                try {
                    conectado.getSalida().writeObject(getGson().toJson(escuchaCliente.getPaqueteAtacar()));
                } catch (IOException e) {
                    Servidor.log.append(
                            "Falló al intentar enviar ataque a:" + conectado.getPaquetePersonaje().getId() + "\n");
                }
            }
        }

    }

}
