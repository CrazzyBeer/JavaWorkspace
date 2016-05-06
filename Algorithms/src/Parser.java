import java.math.BigDecimal;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Parser {
    public static class Token {
	private boolean isNumber = false;
	private boolean isOperator = false;
	private boolean isLeftBracket = false;
	private boolean isRightBracket = false;
	private String token;

	public Token() {
	}

	public Token(String s, int is) {
	    this.token = s;
	    if (is == 0)
		isNumber = true;
	    else if (is == 1)
		isOperator = true;
	    else if (is == 2)
		isLeftBracket = true;
	    else if (is == 3)
		isRightBracket = true;
	}

	public String getToken() {
	    return token;
	}

	public boolean isNumber() {
	    return isNumber;
	}

	public boolean isOperator() {
	    return isOperator;
	}

	public boolean isLeftBracket() {
	    return isLeftBracket;
	}

	public boolean isRightBracket() {
	    return isRightBracket;
	}
    }

    private static int[] percedence;

    static {
	percedence = new int[200];
	percedence['+'] = 0;
	percedence['-'] = 0;
	percedence['*'] = 1;
	percedence['/'] = 1;
	percedence['^'] = 2;
    }

    private static boolean bigger(Token a, Token b) {
	char ac = a.getToken().charAt(0);
	char bc = b.getToken().charAt(0);
	int pa = percedence[ac], pb = percedence[bc];

	return ((ac != '^' && pa <= pb) || (ac == '^' && pa < pb));
    }

    private static boolean isNum(char c) {
	return (c >= '0' && c <= '9') || (c == '.');
    }

    private static boolean isOp(char c) {
	return ("+-*/^".indexOf(c) != -1);
    }

    private static boolean isLeftBracket(char c) {
	return (c == '(');
    }

    private static boolean isRightBracket(char c) {
	return (c == ')');
    }

    private static void check(String s) {
	if (s == null)
	    throw new NullPointerException();
	for (int i = 0; i < s.length(); i++) {
	    char c = s.charAt(i);
	    if (!isNum(c) && !isOp(c) && c != ' ' && !isLeftBracket(c) && !isRightBracket(c))
		throw new IllegalArgumentException();
	}
    }

    private static ArrayList<Token> tokenize(String s) {
	// Array of tokens
	ArrayList<Token> result = new ArrayList<>();

	// Main string
	StringBuilder ex = new StringBuilder(s);
	StringBuilder temp;
	while (ex.length() > 0) {
	    temp = new StringBuilder();
	    int number = 0;
	    char f = ex.charAt(0);
	    // Reads the number until it ends
	    if (isNum(f)) {
		number = 0;
		while (ex.length() > 0 && isNum(ex.charAt(0))) {
		    temp.append(ex.charAt(0));
		    ex.deleteCharAt(0);
		}
		// Reads the operation
	    } else if (isOp(f)) {
		temp.append(ex.charAt(0));
		ex.deleteCharAt(0);
		number = 1;
		// Negative number
		if (f == '-' && ex.length() > 0 && isNum(ex.charAt(0))) {
		    while (ex.length() > 0 && isNum(ex.charAt(0))) {
			temp.append(ex.charAt(0));
			ex.deleteCharAt(0);
		    }
		    number = 0;

		}

	    } else if (isLeftBracket(f)) {
		number = 2;
		temp.append('(');
		ex.deleteCharAt(0);
	    } else if (isRightBracket(f)) {
		number = 3;
		temp.append(')');
		ex.deleteCharAt(0);
	    } else {
		temp = null;
		ex.deleteCharAt(0);
		continue;
	    }
	    result.add(new Token(temp.toString(), number));
	    temp = null;
	}
	return result;
    }

    private static Queue<Token> translate(String s) {
	check(s);
	Stack<Token> operators = new Stack<>();
	Queue<Token> output = new Queue<>();
	ArrayList<Token> tokens = tokenize(s);
	System.out.println();
	while (!tokens.isEmpty()) {
	    Token t = tokens.get(0);
	    tokens.remove(0);
	    if (t.isNumber())
		output.enqueue(t);
	    else if (t.isOperator) {
		while (!operators.isEmpty() && operators.peek().isOperator() && bigger(t, operators.peek())) {
		    output.enqueue(operators.pop());
		}
		operators.push(t);
	    } else if (t.isLeftBracket()) {
		operators.push(t);
	    } else if (t.isRightBracket()) {
		while (!operators.peek().getToken().equals("(")) {
		    output.enqueue(operators.pop());
		}
		if (!operators.peek().getToken().equals("("))
		    throw new IllegalArgumentException();
		operators.pop();
	    }
	}
	while (!operators.isEmpty()) {
	    Token t = operators.pop();
	    if (t.isLeftBracket() || t.isRightBracket())
		throw new IllegalArgumentException();
	    output.enqueue(t);
	}

	return output;

    }

    public static BigDecimal eval(String s) {
	Queue<Token> output = translate(s);
	Stack<BigDecimal> numbers = new Stack<>();
	while (!output.isEmpty()) {

	    Token t = output.dequeue();
	    if (t.isNumber()) {
		numbers.push(new BigDecimal(t.getToken()));
	    }
	    if (t.isOperator()) {
		if (numbers.size() < 2)
		    throw new IllegalArgumentException();
		BigDecimal n2 = numbers.pop();
		BigDecimal n1 = numbers.pop();
		BigDecimal res = null;
		String op = t.getToken();
		if (op.equals("+"))
		    res = n1.add(n2);
		else if (op.equals("-"))
		    res = n1.subtract(n2);
		else if (op.equals("*"))
		    res = n1.multiply(n2);
		else if (op.equals("/"))
		    res = n1.divide(n2);
		else if (op.equals("^")) {
		    res = n1.pow(Integer.parseInt(n2.toString()));
		    if (n1.toString().length() > 5000)
			throw new ArithmeticException();
		}
		numbers.push(res);
		n1 = null;
		n2 = null;
	    }
	}
	return numbers.pop();
    }
}
