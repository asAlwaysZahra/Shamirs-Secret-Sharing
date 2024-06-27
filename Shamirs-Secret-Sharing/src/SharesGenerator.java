import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SharesGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the secret (s): ");
        BigInteger secret = new BigInteger(scanner.nextLine());

        System.out.print("Enter a prime number (p): ");
        BigInteger prime = new BigInteger(scanner.nextLine());

        System.out.print("Enter the number of shares (n): ");
        int n = scanner.nextInt();

        System.out.print("Enter the threshold (t): ");
        int t = scanner.nextInt();

        List<Share> shares = generateShares(n, t, secret, prime);
        for (Share share : shares) {
            System.out.println(share);
        }
    }

    private static List<BigInteger> generateCoefficients(int k, BigInteger secret, BigInteger prime) {
        List<BigInteger> coefficients = new ArrayList<>();
        coefficients.add(secret); // a0

        SecureRandom random = new SecureRandom();

        for (int i = 1; i < k; i++)
            coefficients.add(new BigInteger(prime.bitLength(), random).mod(prime));

        return coefficients;
    }

    private static BigInteger evaluatePolynomial(List<BigInteger> coefficients, BigInteger x, BigInteger prime) {
        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < coefficients.size(); i++) {
            BigInteger term = coefficients.get(i).multiply(x.pow(i)).mod(prime);
            result = result.add(term).mod(prime);
        }

        return result;
    }

    public static List<Share> generateShares(int n, int k, BigInteger secret, BigInteger prime) {
        List<BigInteger> coefficients = generateCoefficients(k, secret, prime);
        List<Share> shares = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            BigInteger x = BigInteger.valueOf(i);
            BigInteger y = evaluatePolynomial(coefficients, x, prime);
            shares.add(new Share(x, y));
        }
        return shares;
    }

    public static class Share {
        private final BigInteger x;
        private final BigInteger y;

        public Share(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }

        public BigInteger getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Share{ x=" + x + ", y=" + y + " }";
        }
    }
}
