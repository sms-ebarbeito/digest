package com.kricomtik.streams;

import com.kricomtik.digest.Digest;

import java.io.IOException;
import java.io.InputStream;

public class DigestInputStream extends InputStream {

    protected byte buf[];
    protected int pos;
    protected int mark = 0;
    protected int count;

    protected InputStream stream;
    protected Digest digest;


    public DigestInputStream(InputStream stream, Digest digest) {
        this.digest = digest;
        this.stream = stream;
        this.pos = 0;
    }

    public DigestInputStream(byte buf[], Digest digest) {
        this.digest = digest;
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }


    public DigestInputStream(byte buf[], int offset, int length, Digest digest) {
        this.digest = digest;
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.mark = offset;
    }

    public synchronized int read() throws IOException {
        int read;
        if (stream != null) {
            read = stream.read();
        } else {
            read = (pos < count) ? (buf[pos++] & 0xff) : -1;
        }
        digest.consume((byte) read);
        return read;
    }

    public synchronized int read(byte b[], int off, int len) throws IOException {
        throw new IOException("Not implemented yet!");
//        if (stream != null) {
//            return stream.read(b, off, len);
//        }
//        if (b == null) {
//            throw new NullPointerException();
//        } else if (off < 0 || len < 0 || len > b.length - off) {
//            throw new IndexOutOfBoundsException();
//        }
//
//        if (pos >= count) {
//            return -1;
//        }
//
//        int avail = count - pos;
//        if (len > avail) {
//            len = avail;
//        }
//        if (len <= 0) {
//            return 0;
//        }
//        System.arraycopy(buf, pos, b, off, len);
//        pos += len;
//        return len;
    }

    public synchronized long skip(long n) throws IOException {
        throw new IOException("Not implemented yet!");
//        if (stream != null) {
//            return stream.skip(n);
//        }
//        long k = count - pos;
//        if (n < k) {
//            k = n < 0 ? 0 : n;
//        }
//
//        pos += k;
//        return k;
    }

    public synchronized int available() throws IOException {
        if (stream != null) {
            return stream.available();
        }
        return count - pos;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readAheadLimit)  {
        mark = pos;
    }

    public synchronized void reset() throws IOException {
        //pos = mark;
        throw new IOException("Reset NOT SUPPORTED!!");
    }

    public void close() throws IOException {
        if (stream != null) {
            stream.close();
        }
        //From byte array, nothing to close
    }

}
