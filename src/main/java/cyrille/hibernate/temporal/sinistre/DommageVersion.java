package cyrille.hibernate.temporal.sinistre;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table(name = "dommage_version")
public class DommageVersion implements Comparable<DommageVersion> {

    @Basic
    @Column(name = "date_situation", nullable = false)
    protected Timestamp dateSituation;

    @Basic
    @Column(name = "label_dommage")
    protected String labelDommage;

    @Basic
    @Column(name = "description")
    protected String description;

    @Id
    @GeneratedValue
    protected Long id;

    public DommageVersion() {
        super();
    }

    public DommageVersion(String labelDommage, String description) {
        super();
        this.labelDommage = labelDommage;
        this.description = description;
    }

    /**
     * Comparaison sur {@link Dommage#getNumeroDommage()} et {@link #getDateSituation()}.
     */
    public int compareTo(DommageVersion other) {
        return new CompareToBuilder().append(this.dateSituation, other.dateSituation).toComparison();
    }

    public DommageVersion copy() {
        DommageVersion copy = new DommageVersion(this.labelDommage, this.description);
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof DommageVersion)) {
            return false;
        }
        final DommageVersion other = (DommageVersion) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    public Timestamp getDateSituation() {
        return dateSituation;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public void setDateSituation(Timestamp dateModificationDommage) {
        this.dateSituation = dateModificationDommage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("dateSituation", this.dateSituation).append("labelDommage",
                this.labelDommage).append("description", this.description).toString();
    }

    public String getLabelDommage() {
        return labelDommage;
    }

    public void setLabelDommage(String labelDommage) {
        this.labelDommage = labelDommage;
    }
}
