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

@Entity(name="mochila")
@Table (name="mochila")
public class EMochila {
	@Id
	@Column (name="idPersonaje")
	private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name="idPersonaje")
	private EPersonaje personaje;

    @ManyToMany(cascade = {CascadeType.ALL},mappedBy="mochila")
    private Set<EPersonaje> personajes=new HashSet<EPersonaje>();
    
	public EMochila() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EMochila(EPersonaje personaje) {
		super();
		this.personaje = personaje;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the personaje
	 */
	public EPersonaje getPersonaje() {
		return personaje;
	}

	/**
	 * @param personaje the personaje to set
	 */
	public void setPersonaje(EPersonaje personaje) {
		this.personaje = personaje;
	}
	
	/**
	 * @return the profesores
	 */
	public Set<EPersonaje> getPersonajes() {
		return personajes;
	}

	/**
	 * @param profesores the profesores to set
	 */
	public void setPersonaje(Set<EPersonaje> profesores) {
		this.personajes = profesores;
	}

	public void setItem(int i, int itemID) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method method = this.getClass().getMethod(("setItem"+itemID), int.class);
		method.invoke(this, i);
	}
	
	
	
}
