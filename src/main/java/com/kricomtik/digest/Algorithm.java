package com.kricomtik.digest;

public enum Algorithm {

    SHA1("SHA-1"),
    MD5("MD5"),
    SHA256("SHA-256");

    private String alg;
    private Algorithm(String alg) {
        this.alg = alg;
    }

    public String getAlg() {
        return alg;
    }
}
