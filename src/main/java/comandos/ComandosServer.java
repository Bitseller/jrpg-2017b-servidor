package comandos;

import mensajeria.Comando;
import servidor.EscuchaCliente;

/**
 * The Class ComandosServer de donde heredan todos los comandos
 */
public abstract class ComandosServer extends Comando {
    protected EscuchaCliente escuchaCliente;

    /**
     * Sets the escucha cliente.
     *
     * @param escuchaCliente
     *            un Escuchador de clientes
     */
    public void setEscuchaCliente(EscuchaCliente escuchaCliente) {
        this.escuchaCliente = escuchaCliente;
    }

}
