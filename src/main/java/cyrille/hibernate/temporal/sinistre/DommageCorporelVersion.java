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
@Table(name = "dommage_corporel_version")
public class DommageCorporelVersion implements Comparable<DommageCorporelVersion> {

    @Basic
    @Column(name = "code_nature_maladie")
    protected String codeNatureMaladie;

    @Basic
    @Column(name = "date_situation", nullable = false)
    protected Timestamp dateSituation;

    @Id
    @GeneratedValue
    protected Long id;

    public DommageCorporelVersion() {
        super();
    }

    public DommageCorporelVersion(String codeNatureMaladie) {
        super();
        this.codeNatureMaladie = codeNatureMaladie;
    }

    /**
     * Comparaison sur {@link DommageCorporel#getNumeroDommageCorporel()} et
     * {@link #getDateSituation()}.
     */
    public int compareTo(DommageCorporelVersion other) {
        return new CompareToBuilder().append(this.dateSituation, other.dateSituation).toComparison();
    }

    public DommageCorporelVersion copy() {
        DommageCorporelVersion copy = new DommageCorporelVersion(this.codeNatureMaladie);
        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof DommageCorporelVersion)) {
            return false;
        }
        final DommageCorporelVersion other = (DommageCorporelVersion) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    public String getCodeNatureMaladie() {
        return codeNatureMaladie;
    }

    public Timestamp getDateSituation() {
        return dateSituation;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public void setCodeNatureMaladie(String codeNatureMaladie) {
        this.codeNatureMaladie = codeNatureMaladie;
    }

    public void setDateSituation(Timestamp dateModificationDommageCorporel) {
        this.dateSituation = dateModificationDommageCorporel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("dateSituation", this.dateSituation).append("codeNatureMaladie",
                this.codeNatureMaladie).toString();
    }
}
