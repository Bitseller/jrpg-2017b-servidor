package comandos;

import mensajeria.PaqueteMovimiento;
import servidor.Servidor;

/**
 * The Class Movimiento.
 */
public class Movimiento extends ComandosServer {

    @Override
    public void ejecutar() {
        escuchaCliente.setPaqueteMovimiento((gson.fromJson(cadenaLeida, PaqueteMovimiento.class)));

        escuchaCliente.setPaqueteMovimiento((gson.fromJson(cadenaLeida, PaqueteMovimiento.class)));

        Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
                .setPosX(escuchaCliente.getPaqueteMovimiento().getPosX());
        Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
                .setPosY(escuchaCliente.getPaqueteMovimiento().getPosY());
        Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
                .setDireccion(escuchaCliente.getPaqueteMovimiento().getDireccion());
        Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
                .setFrame(escuchaCliente.getPaqueteMovimiento().getFrame());

        synchronized (Servidor.atencionMovimientos) {
            Servidor.atencionMovimientos.notify();
        }

        // if(juego.getNPCs() != null){
        // NPCs = new HashMap(juego.getNPCs());
        /* boolean esPelea = false;
         Map<Integer, PaqueteMovimiento> ubicacionNPCs; ubicacionNPCs = new HashMap(
         Servidor.getUbicacionNPCs() ); Iterator<Integer> it =
         ubicacionNPCs.keySet().iterator(); int key; PaqueteMovimiento actual;
         while (it.hasNext()) { key = it.next(); actual = ubicacionNPCs.get(key); if
         (actual != null) { if( actual.getPosX() -
         escuchaCliente.getPaqueteMovimiento().getPosX() < 50 && actual.getPosX() -
         escuchaCliente.getPaqueteMovimiento().getPosX() > -50 && actual.getPosY() -
         escuchaCliente.getPaqueteMovimiento().getPosY() < 50 && actual.getPosY() -
         escuchaCliente.getPaqueteMovimiento().getPosY() > -50){ // iniciar pelea
         //mandarcomando(INICIARPELEA);
         // Servidor.setUbicacionPersonajes(
         (Servidor.getUbicacionPersonajes()).remove(
         escuchaCliente.getPaqueteMovimiento().getIdPersonaje() ) );
         Servidor.log.append("Se Peleaaaaaaaaaaaaa" + actual.getPosX() + "," +
         actual.getPosY() + "       " +
         escuchaCliente.getPaqueteMovimiento().getPosX() + "," +
         escuchaCliente.getPaqueteMovimiento().getPosY() + System.lineSeparator());
         esPelea = true; break; } } } if(!esPelea) {
         Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().
         getIdPersonaje()).setPosX(escuchaCliente.getPaqueteMovimiento().getPosX());
         Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().
         getIdPersonaje()).setPosY(escuchaCliente.getPaqueteMovimiento().getPosY());
         Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().
         getIdPersonaje()).setDireccion(escuchaCliente.getPaqueteMovimiento().
         getDireccion());
         Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().
         getIdPersonaje()).setFrame(escuchaCliente.getPaqueteMovimiento().getFrame());
         }
         synchronized(Servidor.atencionMovimientos){
         Servidor.atencionMovimientos.notify(); }*/
    }

}
