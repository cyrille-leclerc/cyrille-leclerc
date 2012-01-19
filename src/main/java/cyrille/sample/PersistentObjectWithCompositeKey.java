package cyrille.sample;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pertsistent_object_with_composite_key")
public class PersistentObjectWithCompositeKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    CompositeKey compositeKey;

    @Basic
    String name;

    public CompositeKey getCompositeKey() {
        return this.compositeKey;
    }

    public void setCompositeKey(CompositeKey compositeKey) {
        this.compositeKey = compositeKey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
