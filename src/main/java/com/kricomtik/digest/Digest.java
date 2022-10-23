package com.kricomtik.digest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Digest {
    private static final Logger LOGGER = LogManager.getLogger(Digest.class);

    private InputStream input;
    private Algorithm alg = Algorithm.SHA1;
    private MessageDigest md;
    private byte[] hash;

    public Digest withInputStream(InputStream input) {
        this.input = input;
        return this;
    }

    public Digest withAlgorithm(Algorithm alg) {
        this.alg = alg;
        return this;
    }

    public Digest initialize() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance(alg.getAlg());
        return this;
    }

    public String calculateHash() throws IOException {
        if (hash == null) {
            if (input.available() != 0) {
                throw new IOException("Not Complete digesting the message, read all inputStream before calculate.");
            }
            hash = new byte[20];
            hash = md.digest();
            LOGGER.info("Calculated Hash (" + alg.getAlg() + ") : " + Base64.getEncoder().encodeToString(hash));
        }
        return Base64.getEncoder().encodeToString(hash);
    }

    public void consume(byte ch ) {
        if (ch != -1) {
//            System.out.print(" [" + (int)ch + "]" + (char)ch );
            md.update(ch);
        }
    }

    public void reset() throws IOException {
        this.hash = null;
        if (input.markSupported()) {
            LOGGER.info("Trying to reset inputStream");
            input.reset();
        }
    }

    public void consumeAll() throws IOException {
        int ch;
        while( (ch = input.read()) != -1) {
            LOGGER.info("Consuming....");
            md.update((byte) ch);
        }
        input.close();
    }

}
