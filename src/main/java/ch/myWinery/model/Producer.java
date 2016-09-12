package ch.myWinery.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
        @NamedQuery(name = Producer.FIND_ALL, query = "SELECT p FROM Producer p"),
        @NamedQuery(name = Producer.FIND_BY_ID, query = "SELECT p FROM Producer p WHERE p.id = :id")
})
public class Producer implements Serializable {
    private static final long serialVersionUID = 5765748705470422520L;

    public final static String FIND_ALL = "Producer.findAll";
    public final static String FIND_BY_ID = "Producer.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "producer_id")
    private int id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "{ch.myWinery.message.PRODUCER_NAME_EMPTY}")
    private String name;

    @Column(name = "origin", nullable = false)
    @NotNull(message = "{ch.myWinery.message.PRODUCER_ORIGIN_EMPTY}")
    private String origin;

    @OneToMany(mappedBy = "producer")
    private Set<Wine> wines;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the wines
     */
    public Set<Wine> getWines() {
        return wines;
    }

    /**
     * @param wines the wines to set
     */
    public void setWines(Set<Wine> wines) {
        this.wines = wines;
    }
}
