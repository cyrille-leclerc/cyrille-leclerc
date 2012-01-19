package cyrille.hibernate.temporal.sinistre;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>
 * Sinistre avec versionnage par pattern <a href="http://martinfowler.com/ap2/temporalObject.html">Temporal Object</a>
 * </p>
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
@Entity
@Table(name = "sinistre")
public class Sinistre implements Comparable<Sinistre> {

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "numero_declaration")
    protected String numeroDeclaration;

    @Basic
    @Column(name = "numero_sinistre")
    protected String numeroSinistre;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "last_sinistre_version_id", nullable = false)
    protected SinistreVersion lastSinistreVersion;

    @OneToMany(mappedBy = "sinistre", cascade = CascadeType.ALL)
    protected List<Evenement> evenements = new ArrayList<Evenement>();

    @OneToMany(mappedBy = "sinistre", cascade = CascadeType.ALL)
    protected List<Dommage> dommages = new ArrayList<Dommage>();

    @Transient
    protected SinistreVersion workingCopy;

    public Sinistre() {
        super();
    }

    public Sinistre(String numeroSinistre, String numeroDeclaration) {
        super();
        this.numeroDeclaration = numeroDeclaration;
        this.numeroSinistre = numeroSinistre;
    }

    /**
     * Comparaison sur {@link #getNumeroSinistre()}.
     */
    public int compareTo(Sinistre other) {
        return new CompareToBuilder().append(this.numeroSinistre, other.numeroSinistre).toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof Sinistre)) {
            return false;
        }
        final Sinistre other = (Sinistre) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    public String getCodeAssureur() {
        return getCurrentSinistreVersion().getCodeAssureur();
    }

    protected SinistreVersion getCurrentSinistreVersion() {
        return this.lastSinistreVersion;
    }

    public Timestamp getDateSituation() {
        return getCurrentSinistreVersion().getDateSituation();
    }

    public String getDescription() {
        return getCurrentSinistreVersion().getDescription();
    }

    public List<Dommage> getDommages() {
        return dommages;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroDeclaration() {
        return numeroDeclaration;
    }

    public String getNumeroSinistre() {
        return numeroSinistre;
    }

    public synchronized SinistreVersion getWorkingCopy() {

        if (this.workingCopy == null) {

            SinistreVersion currentSinistreVersion = getCurrentSinistreVersion();
            SinistreVersion newWorkingCopy;
            if (currentSinistreVersion == null) {
                newWorkingCopy = new SinistreVersion();
            } else {
                newWorkingCopy = currentSinistreVersion.copy();
            }

            newWorkingCopy.setDateSituation(new Timestamp(System.currentTimeMillis()));
            // add forward linking here

            // no exception during working copy initialization, associate
            this.lastSinistreVersion = newWorkingCopy;
            this.workingCopy = newWorkingCopy;
        }
        return workingCopy;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public void setCodeAssureur(String codeAssureur) {
        getWorkingCopy().setCodeAssureur(codeAssureur);
    }

    public void setDescription(String description) {
        getWorkingCopy().setDescription(description);
    }

    public void setDommages(List<Dommage> dommages) {
        this.dommages = dommages;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroDeclaration(String numeroDeclaration) {
        this.numeroDeclaration = numeroDeclaration;
    }

    public void setNumeroSinistre(String numeroSinistre) {
        this.numeroSinistre = numeroSinistre;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("numeroSinistre", this.numeroSinistre).append(
                "numeroDeclaration", this.numeroDeclaration).append("lastSinistreVersion", this.lastSinistreVersion)
                .toString();
    }
}
