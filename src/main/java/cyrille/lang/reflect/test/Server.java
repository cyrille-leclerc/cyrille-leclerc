/*
 * Created on Jul 1, 2005
 */
package cyrille.lang.reflect.test;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Server {

    private String id;

    private String name;

    private WebContainer webContainer;

    /**
     * @param id
     * @param name
     */
    public Server(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebContainer getWebContainer() {
        return this.webContainer;
    }

    public void setWebContainer(WebContainer webContainer) {
        this.webContainer = webContainer;
    }
}
