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
@Table(name = "sinistre_version")
public class SinistreVersion implements Comparable<SinistreVersion> {

    @Basic
    @Column(name = "date_situation", nullable = false)
    protected Timestamp dateSituation;

    @Basic
    @Column(name = "description")
    protected String description;

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "code_assureur")
    protected String codeAssureur;

    public SinistreVersion() {
        super();
    }

    public SinistreVersion(String codeAssureur, String description) {
        super();
        this.codeAssureur = codeAssureur;
        this.description = description;
    }

    /**
     * Comparaison sur {@link Sinistre#getNumeroSinistre()} et {@link #getDateSituation()}.
     */
    public int compareTo(SinistreVersion other) {
        return new CompareToBuilder().append(this.dateSituation, other.dateSituation).toComparison();
    }

    public SinistreVersion copy() {
        SinistreVersion copy = new SinistreVersion(this.codeAssureur, this.description);
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof SinistreVersion)) {
            return false;
        }
        final SinistreVersion other = (SinistreVersion) obj;

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

    public void setDateSituation(Timestamp dateModificationSinistre) {
        this.dateSituation = dateModificationSinistre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("dateSituation", this.dateSituation).append("codeAssureur",
                this.codeAssureur).append("description", this.description).toString();
    }

    public String getCodeAssureur() {
        return codeAssureur;
    }

    public void setCodeAssureur(String codeAssureur) {
        this.codeAssureur = codeAssureur;
    }
}
