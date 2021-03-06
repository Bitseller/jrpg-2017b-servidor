package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import comandos.ComandosServer;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaqueteAtacar;
import mensajeria.PaqueteBatalla;
import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteDeNPC;
import mensajeria.PaqueteDeNPCs;
import mensajeria.PaqueteDePersonajes;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

/**
 * The Class EscuchaCliente.
 */
public class EscuchaCliente extends Thread {

    private final Socket socket;
    private final ObjectInputStream entrada;
    private final ObjectOutputStream salida;
    private int idPersonaje;
    private final Gson gson = new Gson();

    private PaquetePersonaje paquetePersonaje;
    private PaqueteDeNPC paqueteDeNPC;
    private PaqueteMovimiento paqueteMovimiento;
    private PaqueteBatalla paqueteBatalla;
    private PaqueteAtacar paqueteAtacar;
    private PaqueteFinalizarBatalla paqueteFinalizarBatalla;
    private PaqueteUsuario paqueteUsuario;
    private PaqueteDeMovimientos paqueteDeMovimiento;
    private PaqueteDePersonajes paqueteDePersonajes;
    private PaqueteDeNPCs paqueteDeNPCs;

    /**
     * Instantiates a new escucha cliente.
     *
     * @param ip the ip
     * @param socket the socket
     * @param entrada the entrada
     * @param salida the salida
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public EscuchaCliente(final String ip, final Socket socket, final ObjectInputStream entrada,
        final ObjectOutputStream salida) throws IOException {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        paquetePersonaje = new PaquetePersonaje();
    }

    @Override
    public void run() {
        try {
            ComandosServer comand;
            Paquete paquete;
            paqueteUsuario = new PaqueteUsuario();

            String cadenaLeida = (String) entrada.readObject();

            paquete = gson.fromJson(cadenaLeida, Paquete.class);
            while (paquete.getComando() != Comando.DESCONECTAR) {
                // entra aca cada vez q alguien le pide algo al server,
                // sino se queda esperando andes de entrar al while
                // si alguien le pide desconectarse sale del loop

                comand = (ComandosServer) paquete.getObjeto(Comando.NOMBREPAQUETE);
                comand.setCadena(cadenaLeida);
                comand.setEscuchaCliente(this);
                comand.ejecutar();

                cadenaLeida = (String) entrada.readObject(); // vuelve a leer para entrar en el while

                paquete = gson.fromJson(cadenaLeida, Paquete.class);
            }

            // se desconecto alguien
            entrada.close();
            salida.close();
            socket.close();

            // borra ese personaje
            Servidor.getPersonajesConectados().remove(paquetePersonaje.getId());
            Servidor.getUbicacionPersonajes().remove(paquetePersonaje.getId());
            Servidor.getClientesConectados().remove(this);

            // le avisa a todos los otros q ese usuario se fue
            for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
                paqueteDePersonajes = new PaqueteDePersonajes(Servidor.getPersonajesConectados());
                paqueteDePersonajes.setComando(Comando.CONEXION);
                conectado.salida.writeObject(gson.toJson(paqueteDePersonajes, PaqueteDePersonajes.class));
            }

            Servidor.log.append(paquete.getIp() + " se ha desconectado." + System.lineSeparator());

        } catch (IOException | ClassNotFoundException e) {
            Servidor.log.append("Error de conexion: " + e.getMessage() + System.lineSeparator());
        }
    }

    /**
     * Gets the socket.
     *
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Gets the entrada.
     *
     * @return the entrada
     */
    public ObjectInputStream getEntrada() {
        return entrada;
    }

    /**
     * Gets the salida.
     *
     * @return the salida
     */
    public ObjectOutputStream getSalida() {
        return salida;
    }

    /**
     * Gets the paquete personaje.
     *
     * @return the paquete personaje
     */
    public PaquetePersonaje getPaquetePersonaje() {
        return paquetePersonaje;
    }

    /**
     * Gets the paquete de NPcs.
     *
     * @return the paquete de NPcs
     */
    public PaqueteDeNPCs getPaqueteDeNPCs() {
        return paqueteDeNPCs;
    }

    /**
     * Sets the paquete de NPcs.
     *
     * @param paqueteDeNPCs the new paquete de NPcs
     */
    public void setPaqueteDeNPCs(final PaqueteDeNPCs paqueteDeNPCs) {
        this.paqueteDeNPCs = paqueteDeNPCs;
    }

    /**
     * Gets the id personaje.
     *
     * @return the id personaje
     */
    public int getIdPersonaje() {
        return idPersonaje;
    }

    /**
     * Gets the paquete movimiento.
     *
     * @return the paquete movimiento
     */
    public PaqueteMovimiento getPaqueteMovimiento() {
        return paqueteMovimiento;
    }

    /**
     * Sets the paquete movimiento.
     *
     * @param paqueteMovimiento the new paquete movimiento
     */
    public void setPaqueteMovimiento(final PaqueteMovimiento paqueteMovimiento) {
        this.paqueteMovimiento = paqueteMovimiento;
    }

    /**
     * Gets the paquete batalla.
     *
     * @return the paquete batalla
     */
    public PaqueteBatalla getPaqueteBatalla() {
        return paqueteBatalla;
    }

    /**
     * Sets the paquete batalla.
     *
     * @param paqueteBatalla the new paquete batalla
     */
    public void setPaqueteBatalla(final PaqueteBatalla paqueteBatalla) {
        this.paqueteBatalla = paqueteBatalla;
    }

    /**
     * Gets the paquete atacar.
     *
     * @return the paquete atacar
     */
    public PaqueteAtacar getPaqueteAtacar() {
        return paqueteAtacar;
    }

    /**
     * Sets the paquete atacar.
     *
     * @param paqueteAtacar the paquete atacar
     */
    public void setPaqueteAtacar(final PaqueteAtacar paqueteAtacar) {
        this.paqueteAtacar = paqueteAtacar;
    }

    /**
     * Gets the paquete finalizar batalla.
     *
     * @return the paquete finalizar batalla
     */
    public PaqueteFinalizarBatalla getPaqueteFinalizarBatalla() {
        return paqueteFinalizarBatalla;
    }

    /**
     * Sets the paquete finalizar batalla.
     *
     * @param paqueteFinalizarBatalla the new paquete finalizar batalla
     */
    public void setPaqueteFinalizarBatalla(final PaqueteFinalizarBatalla paqueteFinalizarBatalla) {
        this.paqueteFinalizarBatalla = paqueteFinalizarBatalla;
    }

    /**
     * Gets the paquete de movimiento.
     *
     * @return the paquete de movimiento
     */
    public PaqueteDeMovimientos getPaqueteDeMovimiento() {
        return paqueteDeMovimiento;
    }

    /**
     * Sets the paquete de movimiento.
     *
     * @param paqueteDeMovimiento the new paquete de movimiento
     */
    public void setPaqueteDeMovimiento(final PaqueteDeMovimientos paqueteDeMovimiento) {
        this.paqueteDeMovimiento = paqueteDeMovimiento;
    }

    /**
     * Gets the paquete de personajes.
     *
     * @return the paquete de personajes
     */
    public PaqueteDePersonajes getPaqueteDePersonajes() {
        return paqueteDePersonajes;
    }

    /**
     * Sets the paquete de personajes.
     *
     * @param paqueteDePersonajes the new paquete de personajes
     */
    public void setPaqueteDePersonajes(final PaqueteDePersonajes paqueteDePersonajes) {
        this.paqueteDePersonajes = paqueteDePersonajes;
    }

    /**
     * Sets the id personaje.
     *
     * @param idPersonaje the new id personaje
     */
    public void setIdPersonaje(final int idPersonaje) {
        this.idPersonaje = idPersonaje;
    }

    /**
     * Sets the paquete personaje.
     *
     * @param paquetePersonaje the new paquete personaje
     */
    public void setPaquetePersonaje(final PaquetePersonaje paquetePersonaje) {
        this.paquetePersonaje = paquetePersonaje;
    }

    /**
     * Gets the paquete usuario.
     *
     * @return the paquete usuario
     */
    public PaqueteUsuario getPaqueteUsuario() {
        return paqueteUsuario;
    }

    /**
     * Sets the paquete usuario.
     *
     * @param paqueteUsuario the new paquete usuario
     */
    public void setPaqueteUsuario(final PaqueteUsuario paqueteUsuario) {
        this.paqueteUsuario = paqueteUsuario;
    }

    /**
     * Gets the paquete de NPC.
     *
     * @return the paquete de NPC
     */
    public PaqueteDeNPC getPaqueteDeNPC() {
        return paqueteDeNPC;
    }

    /**
     * Sets the paquete de NPC.
     *
     * @param paqueteDeNPC the new paquete de NPC
     */
    public void setPaqueteDeNPC(final PaqueteDeNPC paqueteDeNPC) {
        this.paqueteDeNPC = paqueteDeNPC;
    }

}
