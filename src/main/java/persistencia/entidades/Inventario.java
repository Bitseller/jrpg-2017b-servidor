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
public class Inventario {

    @Id
    @Column(name = "idPersonaje")
    private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "idPersonaje")
    private Personaje personaje;

    @ManyToOne
    @JoinColumn(name = "manos1")
    private Item mano1;

    @ManyToOne
    @JoinColumn(name = "manos2")
    private Item mano2;

    @ManyToOne
    @JoinColumn(name = "pie")
    private Item pie;

    @ManyToOne
    @JoinColumn(name = "cabeza")
    private Item cabeza;

    @ManyToOne
    @JoinColumn(name = "pecho")
    private Item pecho;

    @ManyToOne
    @JoinColumn(name = "accesorio")
    private Item accesorio;

    /**
     * Instantiates a new e inventario.
     */
    public Inventario() {
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
    public Personaje getPersonaje() {
        return personaje;
    }

    /**
     * Sets the personaje.
     *
     * @param personaje
     *            the personaje to set
     */
    public void setPersonaje(final Personaje personaje) {
        this.personaje = personaje;
    }

    /**
     * Gets the mano 1.
     *
     * @return the mano1
     */
    public Item getMano1() {
        return mano1;
    }

    /**
     * Sets the mano 1.
     *
     * @param mano1
     *            the mano1 to set
     */
    public void setMano1(final Item mano1) {
        this.mano1 = mano1;
    }

    /**
     * Gets the mano 2.
     *
     * @return the mano2
     */
    public Item getMano2() {
        return mano2;
    }

    /**
     * Sets the mano 2.
     *
     * @param mano2
     *            the mano2 to set
     */
    public void setMano2(final Item mano2) {
        this.mano2 = mano2;
    }

    /**
     * Gets the pie.
     *
     * @return the pie
     */
    public Item getPie() {
        return pie;
    }

    /**
     * Sets the pie.
     *
     * @param pie
     *            the pie to set
     */
    public void setPie(final Item pie) {
        this.pie = pie;
    }

    /**
     * Gets the cabeza.
     *
     * @return the cabeza
     */
    public Item getCabeza() {
        return cabeza;
    }

    /**
     * Sets the cabeza.
     *
     * @param cabeza
     *            the cabeza to set
     */
    public void setCabeza(final Item cabeza) {
        this.cabeza = cabeza;
    }

    /**
     * Gets the pecho.
     *
     * @return the pecho
     */
    public Item getPecho() {
        return pecho;
    }

    /**
     * Sets the pecho.
     *
     * @param pecho
     *            the pecho to set
     */
    public void setPecho(final Item pecho) {
        this.pecho = pecho;
    }

    /**
     * Gets the accesorio.
     *
     * @return the accesorio
     */
    public Item getAccesorio() {
        return accesorio;
    }

    /**
     * Sets the accesorio.
     *
     * @param accesorio
     *            the accesorio to set
     */
    public void setAccesorio(final Item accesorio) {
        this.accesorio = accesorio;
    }

}
