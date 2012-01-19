package cyrille.lang.ref;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 * 
 */
public class ReferencedObject {
    private String lastName;

    private String firstName;

    private byte[] data;

    /**
     * Returns the firstName.
     * 
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Returns the lastName.
     * 
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Sets the firstName.
     * 
     * @param firstName
     *            The firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the lastName.
     * 
     * @param lastName
     *            The lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the data.
     * 
     * @return byte[]
     */
    public byte[] getData() {
        return this.data;
    }

    /**
     * Sets the data.
     * 
     * @param data
     *            The data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }

}
