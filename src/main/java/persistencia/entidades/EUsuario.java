package persistencia.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Class EUsuario.
 */
@Entity(name = "usuario")
@Table(name = "usuario")
public class EUsuario {
    @Id
    @Column(name = "usuario")
    private String userName;
    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonaje")
    private EPersonaje personaje;

    /**
     * Instantiates a new e usuario.
     */
    public EUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Gets the user name.
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     *
     * @param userName
     *            the userName to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
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
        this.personaje.setUsuario(this);
    }

}
