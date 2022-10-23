# digest
## Digest - hash calculator from inputStream

Proof of concept. Easy way to calculate hash from a inputStream and feed MessageDigest while consume it.

```java
InputStream is = new DigestInputStream(byteArrayInputStream, digest);

Digest digest = new Digest()
    .withAlgorithm(Algorithm.SHA1)
    .withInputStream(is)
    .initialize();

int ch;
//While consuming the inputStream, it feeds the MessageDigest to calculate the Hash
while( (ch = inputStream.read()) != -1) {
    System.out.print((char)ch);
}

inputStream.close();

//calculate hash
String hash = digest.calculateHash();
```