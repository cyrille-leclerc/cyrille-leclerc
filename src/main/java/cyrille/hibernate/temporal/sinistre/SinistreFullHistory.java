package cyrille.hibernate.temporal.sinistre;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * <p>
 * Sinistre avec versionnage par pattern <a
 * href="http://martinfowler.com/ap2/temporalObject.html">Temporal Object</a>
 * </p>
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
@Entity
@org.hibernate.annotations.Entity(mutable = true)
@Table(name = "sinistre")
public class SinistreFullHistory implements Comparable<SinistreFullHistory> {

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "numero_declaration")
    protected String numeroDeclaration;

    @Basic
    @Column(name = "numero_sinistre")
    protected String numeroSinistre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "last_sinistre_id", nullable = false)
    protected SinistreVersion lastSinistreVersion;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "sinistre_id")
    @Sort(type = SortType.NATURAL)
    protected SortedSet<SinistreVersion> sinistreVersions = new TreeSet<SinistreVersion>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sinistre")
    protected List<Evenement> evenements = new ArrayList<Evenement>();

    transient protected SinistreVersion workingCopy;

    public SinistreFullHistory() {
        super();
    }

    public SinistreFullHistory(String numeroSinistre, String numeroDeclaration) {
        super();
        this.numeroDeclaration = numeroDeclaration;
        this.numeroSinistre = numeroSinistre;
    }

    /**
     * Comparaison sur {@link #getNumeroSinistre()}.
     */
    public int compareTo(SinistreFullHistory other) {
        return new CompareToBuilder().append(this.numeroSinistre, other.numeroSinistre).toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof SinistreFullHistory)) {
            return false;
        }
        final SinistreFullHistory other = (SinistreFullHistory) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    public String getCodeAssureur() {
        return getCurrentSinistreVersion().getCodeAssureur();
    }

    protected SinistreVersion getCurrentSinistreVersion() {
        return getSinistreVersion(new Timestamp(System.currentTimeMillis()));
    }

    public Timestamp getDateSituation() {
        return getCurrentSinistreVersion().getDateSituation();
    }

    public String getDescription() {
        return getCurrentSinistreVersion().getDescription();
    }

    public String getDescription(Timestamp dateEffectivite) {
        return getSinistreVersion(dateEffectivite).getDescription();
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

    /**
     * <p>
     * Retourne le {@link SinistreVersion} effectif à la date donnee.
     * </p>
     * <p>
     * Regle : <code>
     * sinistreVersion.dateSituation <= dateEffectivite && 
     * (sinistreVersion.nextSituation.dateSituation < dateEffectivite || 
     * sinistreVersion.nextSituation == null) 
     * </code>
     * </p>
     */
    protected SinistreVersion getSinistreVersion(Timestamp dateEffectivite) {
        SinistreVersion result = null;
        for (SinistreVersion sinistreVersion : this.sinistreVersions) {
            if (dateEffectivite.getTime() >= sinistreVersion.getDateSituation().getTime()) {
                result = sinistreVersion;
            }
        }
        return result;
    }

    public SortedSet<SinistreVersion> getSinistreVersions() {
        return sinistreVersions;
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
            this.sinistreVersions.add(newWorkingCopy);
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumeroDeclaration(String numeroDeclaration) {
        this.numeroDeclaration = numeroDeclaration;
    }

    public void setNumeroSinistre(String numeroSinistre) {
        this.numeroSinistre = numeroSinistre;
    }

    public void setSinistreVersions(SortedSet<SinistreVersion> sinistreVersions) {
        this.sinistreVersions = sinistreVersions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("numeroSinistre", this.numeroSinistre).append("numeroDeclaration",
                this.numeroDeclaration).toString();
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }
}
