package persistencia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Class EInventario.
 */
@Entity(name = "inventario")
@Table(name = "inventario")
public class EInventario {

    @Id
    @Column(name = "idPersonaje")
    private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "idPersonaje")
    private EPersonaje personaje;

    @ManyToOne
    @JoinColumn(name = "manos1")
    private EItem mano1;

    @ManyToOne
    @JoinColumn(name = "manos2")
    private EItem mano2;

    @ManyToOne
    @JoinColumn(name = "pie")
    private EItem pie;

    @ManyToOne
    @JoinColumn(name = "cabeza")
    private EItem cabeza;

    @ManyToOne
    @JoinColumn(name = "pecho")
    private EItem pecho;

    @ManyToOne
    @JoinColumn(name = "accesorio")
    private EItem accesorio;

    /**
     * Instantiates a new e inventario.
     */
    public EInventario() {
        super();

    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the personaje.
     *
     * @return the personaje
     */
    public EPersonaje getPersonaje() {
        return personaje;
    }

    /**
     * Sets the personaje.
     *
     * @param personaje
     *            the personaje to set
     */
    public void setPersonaje(final EPersonaje personaje) {
        this.personaje = personaje;
    }

    /**
     * Gets the mano 1.
     *
     * @return the mano1
     */
    public EItem getMano1() {
        return mano1;
    }

    /**
     * Sets the mano 1.
     *
     * @param mano1
     *            the mano1 to set
     */
    public void setMano1(final EItem mano1) {
        this.mano1 = mano1;
    }

    /**
     * Gets the mano 2.
     *
     * @return the mano2
     */
    public EItem getMano2() {
        return mano2;
    }

    /**
     * Sets the mano 2.
     *
     * @param mano2
     *            the mano2 to set
     */
    public void setMano2(final EItem mano2) {
        this.mano2 = mano2;
    }

    /**
     * Gets the pie.
     *
     * @return the pie
     */
    public EItem getPie() {
        return pie;
    }

    /**
     * Sets the pie.
     *
     * @param pie
     *            the pie to set
     */
    public void setPie(final EItem pie) {
        this.pie = pie;
    }

    /**
     * Gets the cabeza.
     *
     * @return the cabeza
     */
    public EItem getCabeza() {
        return cabeza;
    }

    /**
     * Sets the cabeza.
     *
     * @param cabeza
     *            the cabeza to set
     */
    public void setCabeza(final EItem cabeza) {
        this.cabeza = cabeza;
    }

    /**
     * Gets the pecho.
     *
     * @return the pecho
     */
    public EItem getPecho() {
        return pecho;
    }

    /**
     * Sets the pecho.
     *
     * @param pecho
     *            the pecho to set
     */
    public void setPecho(final EItem pecho) {
        this.pecho = pecho;
    }

    /**
     * Gets the accesorio.
     *
     * @return the accesorio
     */
    public EItem getAccesorio() {
        return accesorio;
    }

    /**
     * Sets the accesorio.
     *
     * @param accesorio
     *            the accesorio to set
     */
    public void setAccesorio(final EItem accesorio) {
        this.accesorio = accesorio;
    }

}
