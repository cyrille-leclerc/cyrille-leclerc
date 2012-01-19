/*
 * Created on Mar 29, 2006
 */
package cyrille.sample.person;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
@Entity
@Table(name = "person")
public class Person implements Comparable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Basic
    @Column(name = "last_name")
    String lastName;

    @Basic
    @Column(name = "first_name")
    String firstName;

    @Version
    @Column(name = "version")
    int version;

    @Column(name = "last_modification_date")
    Timestamp lastModificationDate;

    @Basic
    @Column(name = "last_modification_user")
    String lastModificationUser;

    @Transient
    Address address = new Address();

    @Transient
    List<Account> accounts = new ArrayList<Account>();

    @Basic
    @Column(name = "comment")
    String comment;

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Person() {
        super();
    }

    /**
     * @param lastName
     * @param firstName
     * @param address
     */
    public Person(String lastName, String firstName, Address address) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Person other = (Person) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", this.id).append("lastName", this.lastName).append("version", this.version).toString();
    }

    public int compareTo(Object o) {
        Person other = (Person) o;
        return new CompareToBuilder().append(this.lastName, other.lastName).append(this.firstName, other.firstName).toComparison();
    }

    public void add(Account account) {
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getLastModificationDate() {
        return this.lastModificationDate;
    }

    public void setLastModificationDate(Timestamp lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getLastModificationUser() {
        return this.lastModificationUser;
    }

    public void setLastModificationUser(String lastModificationUser) {
        this.lastModificationUser = lastModificationUser;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
