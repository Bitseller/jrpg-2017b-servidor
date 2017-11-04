package persistencia.entidades;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Class EMochila.
 */
@Entity(name = "mochila")
@Table(name = "mochila")
public class EMochila {
    @Id
    @Column(name = "idPersonaje")
    private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "idPersonaje")
    private EPersonaje personaje;

    @ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "mochila")
    private Set<EPersonaje> personajes = new HashSet<EPersonaje>();

    /**
     * Instantiates a new e mochila.
     */
    public EMochila() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new e mochila.
     *
     * @param personaje
     *            the personaje
     */
    public EMochila(final EPersonaje personaje) {
        super();
        this.personaje = personaje;
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
     * Gets the personajes.
     *
     * @return the profesores
     */
    public Set<EPersonaje> getPersonajes() {
        return personajes;
    }

    /**
     * Sets the personaje.
     *
     * @param profesores
     *            the profesores to set
     */
    public void setPersonaje(final Set<EPersonaje> profesores) {
        this.personajes = profesores;
    }

    /**
     * Sets the item.
     *
     * @param i
     *            the i
     * @param itemID
     *            the item ID
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NoSuchMethodException
     *             the no such method exception
     */
    public void setItem(final int i, final int itemID) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException {
        Method method = this.getClass().getMethod(("setItem" + itemID), int.class);
        method.invoke(this, i);
    }

}
