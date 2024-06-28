package com.example.secretsharingproject;

import java.math.BigInteger;

public class Share {
    public final BigInteger x;
    public final BigInteger y;

    public Share(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y;
    }
}