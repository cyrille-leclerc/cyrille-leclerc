package cyrille.sample.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author <a href="mailto:cleclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class Product {

    private String name;

    private Date dateProperty;

    private Number numberProperty;

    private int intProperty;

    private Color color;

    private Size size;

    private List<SubProduct> subProducts = new ArrayList<SubProduct>();

    public Product() {
        super();
    }

    public Product(String name, Color color, Size size) {
        this();
        this.name = name;
        this.color = color;
        this.size = size;
        for (int i = 0; i < 10; i++) {
            this.subProducts.add(new SubProduct(name + "." + i));
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getDateProperty() {
        return this.dateProperty;
    }

    public void setDateProperty(Date dateProperty) {
        this.dateProperty = dateProperty;
    }

    public int getIntProperty() {
        return this.intProperty;
    }

    public void setIntProperty(int intProperty) {
        this.intProperty = intProperty;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getNumberProperty() {
        return this.numberProperty;
    }

    public List<SubProduct> getSubProducts() {
        return this.subProducts;
    }

    public void setSubProducts(List<SubProduct> subProducts) {
        this.subProducts = subProducts;
    }

    public void setNumberProperty(Number numberProperty) {
        this.numberProperty = numberProperty;
    }

    public Size getSize() {
        return this.size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

}
