package com.example.secretsharingproject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SecretFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the threshold (t): ");
        int t = scanner.nextInt();

        System.out.print("Enter a prime number (p): ");
        BigInteger p = new BigInteger(scanner.next());

        System.out.println("Enter shares (x, y): ");
        List<Share> sharesToReconstruct = new ArrayList<>();
        for (int i = 0; i < t; i++)
            sharesToReconstruct.add(new Share(scanner.nextBigInteger(), scanner.nextBigInteger()));

        BigInteger reconstructedSecret = reconstructSecret(sharesToReconstruct, p);
        System.out.println("Reconstructed secret: " + reconstructedSecret);
    }

    public static BigInteger reconstructSecret(List<Share> shares, BigInteger p) {
        BigInteger secret = BigInteger.ZERO;

        for (int i = 0; i < shares.size(); i++) {

            BigInteger x_i = shares.get(i).x;
            BigInteger pi = shares.get(i).y;

            for (int j = 0; j < shares.size(); j++) {
                if (i != j) {
                    BigInteger x_j = shares.get(j).x;
                    pi = pi.multiply(x_j.multiply((x_j.subtract(x_i)).modInverse(p)).mod(p));
                }
            }

            secret = secret.add(pi).mod(p);
        }

        return secret;
    }
}
