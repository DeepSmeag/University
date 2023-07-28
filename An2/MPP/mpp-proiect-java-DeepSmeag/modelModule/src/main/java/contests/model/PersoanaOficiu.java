package contests.model;

import contests.model.entity.Entitate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name = "PersoanaOficiu.findPersoanaOficiu", query = "SELECT p FROM PersoanaOficiu p WHERE p.username = :username")
@Table(name = "persoanaoficiu")
public class PersoanaOficiu extends Entitate<Integer> implements Serializable {
    //    o persoanaOficiu este asociata unui singur oficiu
//    pot fi mai multe persoaneOficiu asociate unui oficiu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    public PersoanaOficiu() {
        super(null);
    }
    public PersoanaOficiu(Integer id, String username, String password) {
        super(id);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
