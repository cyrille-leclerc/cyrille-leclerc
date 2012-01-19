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
@Table(name = "evenement_version")
public class EvenementVersion implements Comparable<EvenementVersion> {

    @Basic
    @Column(name = "date_situation", nullable = false)
    protected Timestamp dateSituation;

    @Basic
    @Column(name = "label_evenement")
    protected String labelEvenement;

    @Basic
    @Column(name = "description")
    protected String description;

    @Id
    @GeneratedValue
    protected Long id;

    public EvenementVersion() {
        super();
    }

    public EvenementVersion(String labelEvenement, String description) {
        super();
        this.labelEvenement = labelEvenement;
        this.description = description;
    }

    /**
     * Comparaison sur {@link Evenement#getNumeroEvenement()} et {@link #getDateSituation()}.
     */
    public int compareTo(EvenementVersion other) {
        return new CompareToBuilder().append(this.dateSituation, other.dateSituation).toComparison();
    }

    public EvenementVersion copy() {
        EvenementVersion copy = new EvenementVersion(this.labelEvenement, this.description);
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof EvenementVersion)) {
            return false;
        }
        final EvenementVersion other = (EvenementVersion) obj;

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

    public void setDateSituation(Timestamp dateModificationEvenement) {
        this.dateSituation = dateModificationEvenement;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("dateSituation", this.dateSituation).append("labelEvenement",
                this.labelEvenement).append("description", this.description).toString();
    }

    public String getLabelEvenement() {
        return labelEvenement;
    }

    public void setLabelEvenement(String labelEvenement) {
        this.labelEvenement = labelEvenement;
    }
}
