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
public class Mochila {
    @Id
    @Column(name = "idPersonaje")
    private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "idPersonaje")
    private Personaje personaje;

    @ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "mochila")
    private Set<Personaje> personajes = new HashSet<Personaje>();

    /**
     * Instantiates a new e mochila.
     */
    public Mochila() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new e mochila.
     *
     * @param personaje
     *            the personaje
     */
    public Mochila(final Personaje personaje) {
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
     * Gets the personajes.
     *
     * @return the profesores
     */
    public Set<Personaje> getPersonajes() {
        return personajes;
    }

    /**
     * Sets the personaje.
     *
     * @param profesores
     *            the profesores to set
     */
    public void setPersonaje(final Set<Personaje> profesores) {
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
