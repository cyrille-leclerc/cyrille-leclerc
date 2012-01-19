package cyrille.hibernate.temporal.sinistre;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Evenement avec versionnage par pattern <a
 * href="http://martinfowler.com/ap2/temporalObject.html">Temporal Object</a>
 * </p>
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
@Entity
@Table(name = "evenement")
public class Evenement implements Comparable<Evenement> {

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "last_evenement_version_id", nullable = false)
    protected EvenementVersion lastEvenementVersion;

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "numero_fonctionnel")
    protected String numeroFonctionnel;

    @ManyToOne
    @JoinColumn(name = "sinistre_id", nullable = false)
    protected Sinistre sinistre;

    @Transient
    protected EvenementVersion workingCopy;

    public Evenement() {
        super();
    }

    public Evenement(String numeroFonctionnel) {
        super();
        this.numeroFonctionnel = numeroFonctionnel;
    }

    /**
     * Comparaison sur {@link #getId()}.
     */
    public int compareTo(Evenement other) {
        return new CompareToBuilder().append(this.id, other.id).toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof Evenement)) {
            return false;
        }
        final Evenement other = (Evenement) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    protected EvenementVersion getCurrentEvenementVersion() {
        return this.lastEvenementVersion;
    }

    public Timestamp getDateSituation() {
        return getCurrentEvenementVersion().getDateSituation();
    }

    public String getDescription() {
        return getCurrentEvenementVersion().getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getNumeroFonctionnel() {
        return numeroFonctionnel;
    }

    public synchronized EvenementVersion getWorkingCopy() {

        if (this.workingCopy == null) {

            EvenementVersion currentEvenementVersion = getCurrentEvenementVersion();
            EvenementVersion newWorkingCopy;
            if (currentEvenementVersion == null) {
                newWorkingCopy = new EvenementVersion();
            } else {
                newWorkingCopy = currentEvenementVersion.copy();
            }

            newWorkingCopy.setDateSituation(new Timestamp(System.currentTimeMillis()));
            // add forward linking here

            // no exception during working copy initialization, associate
            this.lastEvenementVersion = newWorkingCopy;
            this.workingCopy = newWorkingCopy;
        }
        return workingCopy;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public void setDescription(String description) {
        getWorkingCopy().setDescription(description);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabelEvenement(String labelEvenement) {
        getWorkingCopy().setLabelEvenement(labelEvenement);
    }

    public void setNumeroFonctionnel(String numeroDeclaration) {
        this.numeroFonctionnel = numeroDeclaration;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("numeroFonctionnel", this.numeroFonctionnel).append(
                "lastEvenementVersion", this.lastEvenementVersion).append("sinistre", this.sinistre).toString();
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }
}
