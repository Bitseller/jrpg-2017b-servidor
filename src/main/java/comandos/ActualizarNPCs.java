package comandos;

/**
 * The Class ActualizarNPCs.
 */
public class ActualizarNPCs extends ComandosServer {

    @Override
    public void ejecutar() {

        // no esta esto todavia pero creo q lo vamo a usar
        /*
         * escuchaCliente.setPaqueteDeNPC((PaqueteDeNPC) gson.fromJson(cadenaLeida,
         * PaqueteDeNPC.class));
         * Servidor.getNPCs().remove(escuchaCliente.getPaquetePersonaje().getId());
         * Servidor.getNPCs().put(escuchaCliente.getPaqueteDeNPC().getId(),
         * escuchaCliente.getPaqueteDeNPC());
         * for(EscuchaCliente conectado : Servidor.getClientesConectados()) { try {
         * conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteDeNPC(
         * ) )); } catch (IOException e) {
         * Servidor.log.append("Falló al intentar enviar paquetePersonaje a:" +
         * conectado.getPaqueteDeNPC().getId() + "\n"); } }
         */
    }
}
