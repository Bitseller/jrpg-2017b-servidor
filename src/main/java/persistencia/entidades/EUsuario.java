package persistencia.entidades;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity (name="usuario")
@Table (name="usuario")
public class EUsuario {
	@Id
	@Column (name="usuario")
	private String userName;
	@Column (name="password")
	private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="idPersonaje")
	private EPersonaje personaje;

	public EUsuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
		this.personaje.setUsuario(this);
	}

}
