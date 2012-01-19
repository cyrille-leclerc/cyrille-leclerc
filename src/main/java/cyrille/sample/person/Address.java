/*
 * Created on Mar 29, 2006
 */
package cyrille.sample.person;

/**
 * 
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class Address {

    private String street;

    private String city;

    private String zipCode;

    public Address() {
        super();
    }

    /**
     * @param street
     * @param city
     * @param zipCode
     */
    public Address(String street, String city, String zipCode) {
        super();
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @hibernate.property column = "city"
     */
    public String getCity() {
        return this.city;
    }

    /**
     * @hibernate.property column="street" length="255"
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * @hibernate.property column = "zipCode"
     */
    public String getZipCode() {
        return this.zipCode;
    }

}
