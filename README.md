# digest
## Digest - hash calculator from inputStream

```java
InputStream is = new DigestInputStream(byteArrayInputStream, digest);

Digest digest = new Digest()
    .withAlgorithm(Algorithm.SHA1)
    .withInputStream(is)
    .initialize();

int ch;
//While consuming the inputStream, it feeds the MessageDigest to calculate the Hash
while( (ch = inputStream.read()) != -1) {
LOGGER.info("Consuming....");
System.out.print((char)ch);
}

inputStream.close();

String hash = digest.calculateHash();
```