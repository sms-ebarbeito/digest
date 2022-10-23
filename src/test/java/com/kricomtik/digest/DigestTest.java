package com.kricomtik.digest;

import com.kricomtik.streams.DigestInputStream;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DigestTest {
    private static final Logger LOGGER = LogManager.getLogger(DigestTest.class);

    @Test
    public void testSha1Digest_inputStream_digest_direct_consuming_and_digest_same_time() throws Exception {
        String text = "This is the way";
        Digest digest = new Digest();
        InputStream byteArrayInputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = new DigestInputStream(byteArrayInputStream, digest);

        //Using digest with DigestInputStream
        digest.withAlgorithm(Algorithm.SHA1)
                .withInputStream(inputStream)
                .initialize();

        int ch;
        //While consuming the inputStream, it feeds the MessageDigest to calculate the Hash
        while( (ch = inputStream.read()) != -1) {
            LOGGER.info("Consuming....");
            System.out.print((char)ch);
        }

        inputStream.close();

        //We are sure at this point that the MessageDigest has all inputStream data to calculate Hash.
        String hash = digest.calculateHash();
        Assert.assertEquals("wrjsFXaJIKygzfkcEF1einyxzvM=", hash); //Hash for This is the way

    }

    @Test
    public void testSha1Digest () throws Exception {
        String text = "This is the way";
        InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));

        Digest digest = new Digest()
                .withAlgorithm(Algorithm.SHA1)
                .withInputStream(inputStream)
                .initialize();

        //At this point HASH is calculated from a non consumed InputStream, this hash is for ""
        String hash;
        try {
            hash = digest.calculateHash();
            Assert.assertTrue(false);
        } catch (IOException e) {
            Assert.assertTrue(true);
        }

        digest.reset();
        digest.consumeAll();

        //digest.consume, force to read all inputStream and now the hash is correctly calculated
        hash = digest.calculateHash();
        Assert.assertEquals(hash, "wrjsFXaJIKygzfkcEF1einyxzvM=");


    }
}
