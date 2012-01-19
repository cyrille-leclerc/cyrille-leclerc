package cyrille.io;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class SerializationUtils {

    private final static Logger LOGGER = Logger.getLogger(SerializationUtils.class.getName());

    private static class CountingOutputStreamWrapper extends OutputStream {

        int length;

        OutputStream outputStream;

        public CountingOutputStreamWrapper(OutputStream outputStream) {
            super();
            this.outputStream = outputStream;
        }

        @Override
        public void close() throws IOException {
            this.outputStream.close();
            this.length = 0;
        }

        @Override
        public void flush() throws IOException {
            this.outputStream.flush();
            this.length = 0;
        }

        @Override
        public String toString() {
            return "length=" + this.length + ":" + this.outputStream.toString();
        }

        @Override
        public void write(byte[] b) throws IOException {
            this.length += b.length;
            this.outputStream.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            this.length += len;
            this.outputStream.write(b, off, len);
        }

        @Override
        public void write(int b) throws IOException {
            this.length++;
            this.outputStream.write(b);
        }

        public int getLength() {
            return this.length;
        }

    }

    private static class NullOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            // do nothing
        }
    }

    /**
     * Private contstructor for util class
     */
    private SerializationUtils() {
    }

    /**
     * Figures out the length of the serialized version of the given object
     * 
     * @param object
     *            object
     * @return size in bytes
     * @throws IOException
     */
    public static int getObjectSerializedSize(Object object) {
        if (object == null) {
            return -1;
        }
        int result;
        try {
            CountingOutputStreamWrapper out = new CountingOutputStreamWrapper(new NullOutputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(object);
            result = out.getLength();

            return result;
        } catch (NotSerializableException e) {
            LOGGER.warn("NotSerializableException in getObjectSerializedSize return -1 instead of raising an exception: " + e);
            result = -1;
        } catch (StackOverflowError e) {
            LOGGER.warn("StackOverflowError in getObjectSerializedSize return -1 instead of raising an exception: " + e);
            result = -1;
        } catch (Exception e) {
            LOGGER.warn("Exception in getObjectSerializedSize return -1 instead of raising an exception: " + e, e);
            result = -1;
        }
        return result;
    }
}
