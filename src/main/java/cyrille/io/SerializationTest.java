package cyrille.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

public class SerializationTest extends TestCase {
    public static class CountingOutputStreamWrapper extends OutputStream {

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

    public static class NullOutputStream extends OutputStream {

        @Override
        public void write(int b) throws IOException {
            // do nothing
        }

    }

    public void testString() throws Exception {
        String message = "this is my string";
        CountingOutputStreamWrapper out = new CountingOutputStreamWrapper(new NullOutputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(message);
        System.out.println();
        System.out.println("message length:" + out.getLength());
    }

    public void testArrayOfStrings() throws Exception {
        String[] messages = new String[] { "this is my string", "another string", "a third string" };
        CountingOutputStreamWrapper out = new CountingOutputStreamWrapper(new NullOutputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(messages);
        System.out.println();
        System.out.println("messages length:" + out.getLength());
    }
}
