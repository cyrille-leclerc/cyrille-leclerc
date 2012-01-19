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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Objet sur lequel porte un contrat d'assurance.
 * 
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Table(name = "objet")
public class Objet {

    @Id
    @GeneratedValue
    protected Long id;

    @Basic
    @Column(name = "atemporal_id", nullable = true)
    protected long atemporalId;

    @Basic
    protected String description;

    public Objet() {
        super();
    }

    /**
     * @param atemporalId
     * @param description
     */
    public Objet(long atemporalId, String description) {
        super();
        this.atemporalId = atemporalId;
        this.description = description;
    }

    public long getAtemporalId() {
        return atemporalId;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setAtemporalId(long atemporalId) {
        this.atemporalId = atemporalId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
