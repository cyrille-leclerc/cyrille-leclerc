package cyrille.hibernate.temporal.sinistre;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
/*
 * @Table(name="dommage_corporel")
 */
@DiscriminatorValue(value = "dommage_corporel")
public class DommageCorporel extends Dommage {

    @Transient
    protected DommageCorporelVersion dommageCorporelworkingCopy;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "last_dommage_corporel_vers_id", nullable = false)
    protected DommageCorporelVersion lastDommageCorporelVersion;

    public DommageCorporel() {
        super();
    }

    public DommageCorporel(String codeTypeDommage) {
        super(codeTypeDommage);
    }

    public String getCodeNatureMaladie() {
        return getCurrentDommageCorporelVersion().getCodeNatureMaladie();
    }

    protected DommageCorporelVersion getCurrentDommageCorporelVersion() {
        return this.lastDommageCorporelVersion;
    }

    public synchronized DommageCorporelVersion getDommageCorporelVersionWorkingCopy() {

        if (this.dommageCorporelworkingCopy == null) {

            DommageCorporelVersion currentDommageCorporelVersion = getCurrentDommageCorporelVersion();
            DommageCorporelVersion newWorkingCopy;
            if (currentDommageCorporelVersion == null) {
                newWorkingCopy = new DommageCorporelVersion();
            } else {
                newWorkingCopy = currentDommageCorporelVersion.copy();
            }

            newWorkingCopy.setDateSituation(new Timestamp(System.currentTimeMillis()));
            // add forward linking here

            // no exception during working copy initialization, associate
            this.lastDommageCorporelVersion = newWorkingCopy;
            this.dommageCorporelworkingCopy = newWorkingCopy;
        }
        return dommageCorporelworkingCopy;
    }

    public void setCodeNatureMaladie(String codeNatureMaladie) {
        getDommageCorporelVersionWorkingCopy().setCodeNatureMaladie(codeNatureMaladie);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("codeTypeDommage", this.codeTypeDommage).append(
                "lastDommageVersion", this.lastDommageVersion).append("sinistre", this.sinistre).toString();
    }

}
