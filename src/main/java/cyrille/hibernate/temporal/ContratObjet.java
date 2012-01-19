/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cyrille.hibernate.temporal;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table(name = "contrat_objet")
public class ContratObjet implements Comparable<ContratObjet> {

    public enum CauseEffictivite {
        MODIFICATION_OBJET_PERE, MODIFICATION_OBJET_FILS
    }

    @Id
    @GeneratedValue
    protected Long id;;

    @ManyToOne
    @JoinColumn(name = "contrat_id", nullable = false)
    protected Contrat contrat;

    @ManyToOne
    @JoinColumn(name = "objet_id", nullable = false)
    protected Objet objet;

    @Basic
    @Column(name = "date_debut_effictivite")
    protected Timestamp dateDebutEffictivite;

    @Basic
    @Column(name = "cause_effictivite")
    protected CauseEffictivite causeEffictivite;

    public ContratObjet() {
        super();
    }

    /**
     * @param contrat
     * @param objet
     * @param dateDebutEffictivite
     * @param causeEffictivite
     */
    public ContratObjet(Contrat contrat, Objet objet, Timestamp dateDebutEffictivite, CauseEffictivite causeEffictivite) {
        super();
        this.contrat = contrat;
        this.objet = objet;
        this.dateDebutEffictivite = dateDebutEffictivite;
        this.causeEffictivite = causeEffictivite;
    }

    public int compareTo(ContratObjet other) {
        return new CompareToBuilder().append(this.contrat, other.contrat).append(this.dateDebutEffictivite,
                other.dateDebutEffictivite).toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ContratObjet other = (ContratObjet) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public Timestamp getDateDebutEffictivite() {
        return dateDebutEffictivite;
    }

    public Long getId() {
        return id;
    }

    public Objet getObjet() {
        return objet;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public void setDateDebutEffictivite(Timestamp dateDebutEffictivite) {
        this.dateDebutEffictivite = dateDebutEffictivite;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setObjet(Objet objet) {
        this.objet = objet;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id)
                .append("dateDebutEffictivite", this.dateDebutEffictivite).append("causeEffictivite",
                        this.causeEffictivite).append(this.contrat).append(this.objet).toString();
    }
}
