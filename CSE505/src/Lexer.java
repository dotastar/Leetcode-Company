import java.io.*;

public class Lexer {
	
	static public char ch = ' ';
	static public char ident = ' ';
	static private Buffer buffer = new Buffer(new DataInputStream(System.in));
	static public int nextToken;
	static public char nextChar;
	static public int intValue;
	static public boolean debug = false;
	
	public static void main(String[] args) {
		while (Lexer.lex() != 20) {
			System.out.println("nextToken :" + Lexer.nextToken + "\t"
					+ Token.toString(Lexer.nextToken));
			System.out.println("ident :" + Lexer.ident);
			System.out.println("ch :" + Lexer.ch);
			System.out.println("nextChar :" + Lexer.nextChar);
			System.out.println("inValue :" + Lexer.intValue);
			System.out.println(System.lineSeparator());
		}
	}

	public static void printState(){
		System.out.println("Token: "+Token.toString(nextToken)+"  id:"+ident+"  ch: "+nextChar+"  nextRead:"+ch+"  intVal:"+intValue);
	}
	
	public static int lex() {
		while (Character.isWhitespace(ch))
			ch = buffer.getChar();
		if (Character.isLetter(ch)) {
			ident = Character.toLowerCase(ch);
			ch = buffer.getChar();
			if (ident == 'i' && ch == 'f') {
				ch = buffer.getChar();
				nextToken = Token.KEY_IF;
			} else if (ident == 'i' && ch == 'n') {
				ch = buffer.getChar(); // 't'
				ch = buffer.getChar(); // white space
				nextToken = Token.KEY_INT;
			} else if (ident == 'e' && ch == 'l') {
				ch = buffer.getChar(); // 's'
				ch = buffer.getChar(); // 'e'
				ch = buffer.getChar(); // white space
				nextToken = Token.KEY_ELSE;
			} else if (ident == 'e' && ch == 'n') {
				ch = buffer.getChar(); // 'd'
				ch = buffer.getChar(); // white space
				nextToken = Token.KEY_END;
			} else if (ident == 'w' && ch == 'h') {
				ch = buffer.getChar(); // 'i'
				ch = buffer.getChar(); // 'l'
				ch = buffer.getChar(); // 'e'
				ch = buffer.getChar(); // white space
				nextToken = Token.KEY_WHILE;
			} else
				nextToken = Token.ID;
		} else if (Character.isDigit(ch)) {
			nextToken = getNumToken(); // intValue would be set
		} else {
			nextChar = ch;
			switch (ch) {
			case ';':
				nextToken = Token.SEMICOLON;
				break;
			case ',':
				nextToken = Token.COMMA;
				break;
			case '+':
				nextToken = Token.ADD_OP;
				break;
			case '-':
				nextToken = Token.SUB_OP;
				break;
			case '*':
				nextToken = Token.MULT_OP;
				break;
			case '/':
				nextToken = Token.DIV_OP;
				break;
			case '=':
				nextToken = Token.ASSIGN_OP;
				break;
			case '<':
				nextToken = Token.LESSER_OP;
				break;
			case '>':
				nextToken = Token.GREATER_OP;
				break;
			case '!':
				ch = buffer.getChar(); // '='
				nextToken = Token.NOT_EQ;
				break;
			case '(':
				nextToken = Token.LEFT_PAREN;
				break;
			case ')':
				nextToken = Token.RIGHT_PAREN;
				break;
			case '{':
				nextToken = Token.LEFT_BRACE;
				break;
			case '}':
				nextToken = Token.RIGHT_BRACE;
				break;
			default:
				error("Illegal character " + ch);
				break;
			}
			ch = buffer.getChar();
		}
		if(debug)
			printState();
		return nextToken;
	} // lex

	private static int getNumToken() {
		int num = 0;
		do {
			num = num * 10 + Character.digit(ch, 10);
			ch = buffer.getChar();
		} while (Character.isDigit(ch));
		intValue = num;
		return Token.INT_LIT;
	}

	public int number() {
		return intValue;
	} // number

	public Character identifier() {
		return ident;
	} // letter

	public static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	} // error

}
