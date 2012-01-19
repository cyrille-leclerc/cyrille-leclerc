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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Contrat d'assurance.
 * 
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table(name = "contrat")
public class Contrat implements Comparable<Contrat> {

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "atemporal_id", nullable = false)
    protected long atemporalId;

    @Basic
    @Column(name = "date_modification_contrat", nullable = false)
    protected Timestamp dateModificationContrat;

    @Basic
    @Column(name = "description")
    protected String description;

    @OneToMany
    @JoinColumn(name = "contrat_id")
    protected List<ContratObjet> contratObjets = new ArrayList<ContratObjet>();

    public Contrat() {
        super();
    }

    /**
     * @param atemporalId
     * @param description
     */
    public Contrat(long atemporalId, String description) {
        super();
        this.atemporalId = atemporalId;
        this.dateModificationContrat = new Timestamp(System.currentTimeMillis());
        this.description = description;
    }

    public int compareTo(Contrat other) {
        return new CompareToBuilder().append(this.atemporalId, other.atemporalId).append(this.dateModificationContrat,
                other.atemporalId).toComparison();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Contrat other = (Contrat) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public long getAtemporalId() {
        return atemporalId;
    }

    public List<ContratObjet> getContratObjets() {
        return contratObjets;
    }

    public Timestamp getDateModificationContrat() {
        return dateModificationContrat;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    public void setAtemporalId(long atemporalId) {
        this.atemporalId = atemporalId;
    }

    public void setContratObjets(List<ContratObjet> contratObjets) {
        this.contratObjets = contratObjets;
    }

    public void setDateModificationContrat(Timestamp lastModificationDate) {
        this.dateModificationContrat = lastModificationDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("atemporalId", this.atemporalId).append(
                "dateModificationContrat", this.dateModificationContrat).toString();
    }
}
