import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
	static int getOrd(char c) {
	    if (c >= '0' && c <= '9') return c-'0';
	    else if (c >= 'A' && c <= 'Z') return 10 + c-'A';
	    return 0;
	}
	static char getChar(BigInteger n) {
		int t = n.intValue();
	    if (t<10) return (char)('0' + t);
	    else return (char)('A'+ t-10);
	}
	
	static BigInteger XtoDec(String num, int x) {
	    BigInteger multiplier = new BigInteger("1");
	    BigInteger result = new BigInteger("0");
	    int i = num.length()-1;
	    while (i>=0) {
	        char c = num.charAt(i);
	        i--;
	        BigInteger repr = new BigInteger(Integer.toString(getOrd(c)));

	        repr = repr.multiply(multiplier);
	        result = result.add(repr);
	        multiplier = multiplier.multiply(new BigInteger(Integer.toString(x)));
	    }
	    return result;
	}
	
	static String DecToY(BigInteger num, int y) {
	    String res = "";
	    BigInteger Y = new BigInteger(Integer.toString(y));
	    do{
	    	BigInteger rem = num.mod(Y);
	        num = num.divide(Y);
	        res = getChar(rem) + res;
	    } while (num.compareTo(BigInteger.ZERO) > 0);
	    return res;
	}
	
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(new File("input.txt"));
		PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
		int a = sc.nextInt(),
			b = sc.nextInt();
		sc.nextLine();
		String s = sc.nextLine();
		
		BigInteger num = XtoDec(s, a);

		s = DecToY(num, b);
		
		pw.println(s);
		sc.close();
		pw.close();
	}
}
