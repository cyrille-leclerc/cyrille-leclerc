package cyrille.sample;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@Table(name = "person")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "age", nullable = true)
    Integer age;

    @Basic
    @XStreamAlias("age-as-object")
    int ageAsPrimitive;

    @Basic
    BigInteger amount;

    @Basic
    @XStreamAlias("birth-date")
    @Column(name = "birth_date", nullable = true)
    Date birthDate;

    @Basic
    @Column(name = "first_name", nullable = false, length = 255)
    @XStreamAlias("first-name")
    String firstName;

    @Id
    @GeneratedValue
    Long id;

    @Basic
    @Column(name = "last_name", nullable = false, length = 255)
    @XStreamAlias("last-name")
    String lastName;

    public Person() {
        super();
    }

    public Person(String firstName, String lastName, Date birthDate, int ageAsPrimitive, Integer age, BigInteger amount) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.ageAsPrimitive = ageAsPrimitive;
        this.age = age;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;

        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    public Integer getAge() {
        return this.age;
    }

    public int getAgeAsPrimitive() {
        return this.ageAsPrimitive;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Long getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    public void setAge(Integer ageAsObject) {
        this.age = ageAsObject;
    }

    public void setAgeAsPrimitive(int age) {
        this.ageAsPrimitive = age;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("lastName", this.lastName).append("firstName", this.firstName).append("age", this.age)
                .append("ageAsPrimitive", this.ageAsPrimitive).append("birthDate", this.birthDate).toString();
    }
}
