import java.math.BigInteger;

public class Rational extends Number implements Comparable<Rational> {
    private BigInteger[] r = new BigInteger[2];

    public Rational() {
        this(BigInteger.ZERO, BigInteger.ONE);
    }

    public Rational(BigInteger numerator, BigInteger denominator) {
        if (denominator.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }

        BigInteger gcd = gcd(numerator, denominator);
        r[0] = numerator.divide(gcd);
        r[1] = denominator.divide(gcd);

        if (r[1].compareTo(BigInteger.ZERO) < 0) {
            r[0] = r[0].negate();
            r[1] = r[1].negate();
        }
    }

    private static BigInteger gcd(BigInteger n, BigInteger d) {
        n = n.abs();
        d = d.abs();

        while (!d.equals(BigInteger.ZERO)) {
            BigInteger temp = d;
            d = n.mod(d);
            n = temp;
        }

        return n;
    }

    public BigInteger getNumerator() {
        return r[0];
    }

    public BigInteger getDenominator() {
        return r[1];
    }

    public Rational add(Rational secondRational) {
        BigInteger n = r[0].multiply(secondRational.getDenominator())
                .add(r[1].multiply(secondRational.getNumerator()));
        BigInteger d = r[1].multiply(secondRational.getDenominator());
        return new Rational(n, d);
    }

    public Rational subtract(Rational secondRational) {
        BigInteger n = r[0].multiply(secondRational.getDenominator())
                .subtract(r[1].multiply(secondRational.getNumerator()));
        BigInteger d = r[1].multiply(secondRational.getDenominator());
        return new Rational(n, d);
    }

    public Rational multiply(Rational secondRational) {
        BigInteger n = r[0].multiply(secondRational.getNumerator());
        BigInteger d = r[1].multiply(secondRational.getDenominator());
        return new Rational(n, d);
    }

    public Rational divide(Rational secondRational) {
        BigInteger n = r[0].multiply(secondRational.getDenominator());
        BigInteger d = r[1].multiply(secondRational.getNumerator());
        return new Rational(n, d);
    }

    @Override
    public String toString() {
        if (r[1].equals(BigInteger.ONE))
            return r[0].toString();
        else
            return r[0] + "/" + r[1];
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Rational))
            return false;

        Rational rational = (Rational) other;
        return r[0].equals(rational.getNumerator()) && r[1].equals(rational.getDenominator());
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        return r[0].doubleValue() / r[1].doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public int compareTo(Rational other) {
        BigInteger n = subtract(other).getNumerator();
        return n.compareTo(BigInteger.ZERO);
    }
}
