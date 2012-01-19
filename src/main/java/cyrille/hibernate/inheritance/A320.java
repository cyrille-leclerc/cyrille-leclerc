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
package cyrille.hibernate.inheritance;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
@Entity
@DiscriminatorValue(value = "A320")
@Table(name = "plane_a320")
public class A320 extends Plane {

    @Basic
    @Column(name = "a320_specific_field")
    protected String a320SpecificField;

    public A320() {
        super();
    }

    public A320(String name, String a320SpecificField) {
        super(name);
        this.a320SpecificField = a320SpecificField;
    }

    public String getA320SpecificField() {
        return a320SpecificField;
    }

    public void setA320SpecificField(String specificField) {
        a320SpecificField = specificField;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("a320SpecificField",
                this.a320SpecificField).toString();
    }
}
