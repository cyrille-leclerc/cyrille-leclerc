package cyrille.xstream;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;

public class XStreamizableObject {

    private XStream xstream;

    private Object object;

    public XStreamizableObject(Object object, XStream xstream) {
        super();
        this.object = object;
        this.xstream = xstream;
    }

    public void marshal(Writer writer) {
        this.xstream.toXML(this.object, writer);
    }

    @Override
    public String toString() {
        return this.xstream.toXML(this.object);
    }
}
