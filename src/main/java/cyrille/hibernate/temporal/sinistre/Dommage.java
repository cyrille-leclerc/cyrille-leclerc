package cyrille.hibernate.temporal.sinistre;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
 * Dommage avec versionnage par pattern <a href="http://martinfowler.com/ap2/temporalObject.html">Temporal Object</a>
 * </p>
 * 
 * @author <a href="mailto:cleclerc@xebia.fr">Cyrille Le Clerc</a>
 */
@Entity
@Table(name = "dommage")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_dommage")
public abstract class Dommage implements Comparable<Dommage> {

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "last_dommage_version_id", nullable = false)
    protected DommageVersion lastDommageVersion;

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "code_type_dommage")
    protected String codeTypeDommage;

    @ManyToOne
    @JoinColumn(name = "sinistre_id", nullable = false)
    protected Sinistre sinistre;

    @Transient
    protected DommageVersion dommageWorkingCopy;

    public Dommage() {
        super();
    }

    public Dommage(String codeTypeDommage) {
        super();
        this.codeTypeDommage = codeTypeDommage;
    }

    /**
     * Comparaison sur {@link #getId()}.
     */
    public int compareTo(Dommage other) {
        return new CompareToBuilder().append(this.id, other.id).toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (false == (obj instanceof Dommage)) {
            return false;
        }
        final Dommage other = (Dommage) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    public String getCodeTypeDommage() {
        return codeTypeDommage;
    }

    protected DommageVersion getCurrentDommageVersion() {
        return this.lastDommageVersion;
    }

    public Timestamp getDateSituation() {
        return getCurrentDommageVersion().getDateSituation();
    }

    public String getDescription() {
        return getCurrentDommageVersion().getDescription();
    }

    public synchronized DommageVersion getDommageWorkingCopy() {

        if (this.dommageWorkingCopy == null) {

            DommageVersion currentDommageVersion = getCurrentDommageVersion();
            DommageVersion newDommageWorkingCopy;
            if (currentDommageVersion == null) {
                newDommageWorkingCopy = new DommageVersion();
            } else {
                newDommageWorkingCopy = currentDommageVersion.copy();
            }

            newDommageWorkingCopy.setDateSituation(new Timestamp(System.currentTimeMillis()));
            // add forward linking here

            // no exception during working copy initialization, associate
            this.lastDommageVersion = newDommageWorkingCopy;
            this.dommageWorkingCopy = newDommageWorkingCopy;
        }
        return dommageWorkingCopy;
    }

    public Long getId() {
        return id;
    }

    public String getLabelDommage() {
        return getDommageWorkingCopy().getLabelDommage();
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public void setCodeTypeDommage(String numeroDeclaration) {
        this.codeTypeDommage = numeroDeclaration;
    }

    public void setDescription(String description) {
        getDommageWorkingCopy().setDescription(description);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabelDommage(String labelDommage) {
        getDommageWorkingCopy().setLabelDommage(labelDommage);
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("codeTypeDommage", this.codeTypeDommage).append(
                "lastDommageVersion", this.lastDommageVersion).append("sinistre", this.sinistre).toString();
    }
}
