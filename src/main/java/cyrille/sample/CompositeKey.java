package cyrille.sample;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class CompositeKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic
    Long id1;

    @Basic
    Long id2;

    public Long getId1() {
        return this.id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return this.id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id1).append(this.id2).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompositeKey other = (CompositeKey) obj;

        return new EqualsBuilder().append(this.id1, other.id1).append(this.id2, other.id2).isEquals();
    }

}
