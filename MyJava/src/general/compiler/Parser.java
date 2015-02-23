package general.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 		OO PARSER AND BYTE-CODE GENERATOR FOR TINY PL

 Grammar for TinyPL (using EBNF notation) is as follows:

 program ->  decls stmts end
 decls   ->  int idlist ;
 idlist  ->  id { , id } 
 stmts   ->  stmt [ stmts ]
 cmpdstmt->  '{' stmts '}'
 stmt    ->  assign | cond | loop
 assign  ->  id = expr ;
 cond    ->  if '(' rexp ')' cmpdstmt [ else cmpdstmt ]
 loop    ->  while '(' rexp ')' cmpdstmt  
 rexp    ->  expr (< | > | =) expr
 expr    ->  term   [ (+ | -) expr ]
 term    ->  factor [ (* | /) term ]
 factor  ->  int_lit | id | '(' expr ')'

 Lexical:   id is a single character; 
 int_lit is an unsigned integer;
 equality operator is =, not ==

 Sample Program: Factorial

 int n, i, f;
 n = 4;
 i = 1;
 f = 1;
 while (i < n) {
 i = i + 1;
 f= f * i;
 }
 end

 Sample Program:  GCD

 int x, y;
 x = 121;
 y = 132;
 while (x != y) {
 if (x > y) 
 { x = x - y; }
 else { y = y - x; }
 }
 end

 */

public class Parser {
	public static void main(String[] args) {
		System.out.println("Enter program and terminate with 'end'!\n");
		//Lexer.debug = false;
		Lexer.lex();
		new Program();
		Code.output();

	}
}

class Program {
	public Program() {
		new Decls();
		new Stmts();
		assert Lexer.nextToken == Token.KEY_END;
		Code.append(ByteCode.end());
	}
}

/**
 * Declarations
 * 
 * @author yazhoucao
 * 
 */
class Decls {
	/**
	 * form: int id1, id2, id3
	 */
	public Decls() {
		assert Lexer.nextToken == Token.KEY_INT;
		new Idlist();
	}
}

/**
 * Id list
 * 
 * @author yazhoucao
 * 
 */
class Idlist {
	public Idlist() {
		do {
			Lexer.lex(); // pass int or comma
			assert Lexer.nextToken == Token.ID;
			IdTable.getTable().add(Lexer.ident);
			Lexer.lex(); // pass id
		} while (Lexer.nextToken == Token.COMMA);
		assert Lexer.nextToken == Token.SEMICOLON;
		Lexer.lex();
	}
}

class Stmt {
	Assign assign;
	Loop loop;
	Cond cond;

	public Stmt() {
		int tk = Lexer.nextToken;
		switch (tk) {
		case Token.ID:
			assign = new Assign();
			assert Lexer.nextToken == Token.SEMICOLON;
			Lexer.lex();
			break;
		case Token.KEY_IF:
			cond = new Cond();
			break;
		case Token.KEY_WHILE:
			loop = new Loop();
			break;
		default:
			break;
		}
	}
}

class Stmts {
	Stmt stmt;

	public Stmts() {
		stmt = new Stmt();

		// iterative instead of recursion
		while (isStmt()) {
			stmt = new Stmt();
		}
	}

	private boolean isStmt() {
		int tk = Lexer.nextToken;
		return tk == Token.ID || tk == Token.KEY_IF || tk == Token.KEY_WHILE;
	}
}

class Assign {
	Expr expr;
	int idnum;

	public Assign() {
		assert Lexer.nextToken == Token.ID;
		Lexer.lex(); // skip id
		idnum = IdTable.getTable().get(Lexer.ident);

		assert Lexer.nextToken == Token.ASSIGN_OP;
		Lexer.lex(); // skip '='

		expr = new Expr();

		Code.append(ByteCode.istore(idnum));
	}
}

class Cond {
	Rexpr rexpr;
	Cmpdstmt cmpdstmt;

	public Cond() {
		assert Lexer.nextToken == Token.KEY_IF;
		Lexer.lex(); // skip if

		assert Lexer.nextToken == Token.LEFT_PAREN;
		Lexer.lex(); // skip '('

		rexpr = new Rexpr();

		String cmpStmt = ByteCode.icmp(rexpr.op);
		int cmpIdx = Code.append("");

		assert Lexer.nextToken == Token.RIGHT_PAREN;
		Lexer.lex(); // skip ')'
		cmpdstmt = new Cmpdstmt();

		int skipIfPos = ByteCode.getBytesCount();

		if (Lexer.nextToken == Token.KEY_ELSE) {
			String gotoStmt = ByteCode.gotoFuturePos();
			int gotoIdx = Code.append("");
			skipIfPos = ByteCode.getBytesCount(); // update skip if position

			Lexer.lex(); // skip else
			cmpdstmt = new Cmpdstmt();

			int currPos = ByteCode.getBytesCount();
			Code.set(gotoStmt + currPos, gotoIdx);
		}
		Code.set(cmpStmt + skipIfPos, cmpIdx);
	}
}

class Loop {
	Rexpr rexpr;
	Cmpdstmt cmpdstmt;

	public Loop() {
		assert Lexer.nextToken == Token.KEY_WHILE;
		Lexer.lex(); // skip while
		assert Lexer.nextToken == Token.LEFT_PAREN;
		Lexer.lex(); // skip '('

		int whileBeginPos = ByteCode.getBytesCount();
		rexpr = new Rexpr();

		// reserve a slot for 'if', later we still have to change it
		int index = Code.append("");
		String cmpStmt = ByteCode.icmp(rexpr.op);

		assert Lexer.nextToken == Token.RIGHT_PAREN;
		Lexer.lex(); // skip ')'

		cmpdstmt = new Cmpdstmt();
		// end while
		// append 'go back to beginning of while'
		Code.append(ByteCode.gotoPos(whileBeginPos));

		int currPos = ByteCode.getBytesCount();
		Code.set(cmpStmt + currPos, index);
	}
}

class Cmpdstmt {
	Stmts stmts;

	public Cmpdstmt() {
		assert Lexer.nextToken == Token.LEFT_BRACE;
		Lexer.lex(); // skip '{'

		stmts = new Stmts();

		assert Lexer.nextToken == Token.RIGHT_BRACE;
		Lexer.lex(); // skip '}'
	}
}

class Rexpr {
	Expr expr;
	char op;

	public Rexpr() {
		expr = new Expr();

		int tk = Lexer.nextToken;
		assert tk == Token.GREATER_OP || tk == Token.LESSER_OP
				|| tk == Token.NOT_EQ || tk == Token.ASSIGN_OP;
		op = Lexer.nextChar;
		Lexer.lex(); // skip op

		expr = new Expr();
	}
}

class Expr {
	Term t;
	Expr e;

	public Expr() {
		t = new Term();
		if (Lexer.nextToken == Token.ADD_OP || Lexer.nextToken == Token.SUB_OP) {
			char op = Lexer.nextChar;
			Lexer.lex(); // skip op
			e = new Expr();

			Code.append(ByteCode.icalc(op));
		}
	}
}

class Term {
	Factor factor;
	Term t;

	public Term() {
		factor = new Factor();
		if (Lexer.nextToken == Token.MULT_OP || Lexer.nextToken == Token.DIV_OP) {
			char op = Lexer.nextChar;
			Lexer.lex(); // skip op
			t = new Term();

			Code.append(ByteCode.icalc(op));
		}
	}
}

class Factor {
	Expr expr;
	int i;
	char var;

	public Factor() {
		if (Lexer.nextToken == Token.INT_LIT) {
			i = Lexer.intValue;
			Lexer.lex();
			Code.append(ByteCode.push(i));
		} else if (Lexer.nextToken == Token.ID) {
			var = Lexer.ident;
			Lexer.lex();
			Code.append(ByteCode.iload(var));
		} else if (Lexer.nextToken == Token.LEFT_PAREN) {
			Lexer.lex(); // skip over '('
			expr = new Expr();
			Lexer.lex(); // skip over ')'
		} else
			System.err.println("factor parsing error, token :"
					+ Token.toString(Lexer.nextToken));
	}
}

/**
 * Id table, which stores the variable name and variable index of the local
 * variable array, though the local variable array does not exist in the this
 * program, it's just a simulation
 * 
 * @author yazhoucao
 * 
 */
class IdTable {
	private static IdTable instance = new IdTable();
	private char[] ids; // it's a dynamic array
	private int size;

	private IdTable() {
		ids = new char[10];
		size = 0;
	}

	public static IdTable getTable() {
		return instance;
	}

	/**
	 * Add a new variable into Id table
	 */
	public void add(char ch) {
		if (size < ids.length)
			ids[size++] = ch;
		else
			ids = Arrays.copyOf(ids, size * 2); // expanding size
	}

	/**
	 * Get the index of an existing variable by its name
	 */
	public int get(char ch) {
		for (int i = 0; i < size; i++)
			if (ch == ids[i])
				return i;
		return -1;
	}
}

/**
 * Generate String of byte code by corresponding operations
 * 
 * @author yazhoucao
 *
 */
class ByteCode {
	private static int bytesCount = 0;

	public static int getBytesCount() {
		return bytesCount;
	}

	public static String icmp(char op) {
		String s = null;
		switch (op) {
		case '>':
			s = bytesCount + ": if_icmple ";
			break;
		case '<':
			s = bytesCount + ": if_icmpge ";
			break;
		case '!':
			s = bytesCount + ": if_icmpeq ";
			break;
		case '=':
			s = bytesCount + ": if_icmpne ";
			break;
		default:
			System.err.println("ByteCode icmp() err, unknown op:" + op);
			break;
		}
		bytesCount += 3;
		return s;
	}

	public static String gotoFuturePos() {
		String s = bytesCount + ": goto ";
		bytesCount += 3;
		return s;
	}

	public static String gotoPos(int pos) {
		String s = bytesCount + ": goto " + pos;
		bytesCount += 3;
		return s;
	}

	public static String icalc(char op) {
		String s = bytesCount + ": ";
		switch (op) {
		case '+':
			s += iadd();
			break;
		case '-':
			s += isub();
			break;
		case '*':
			s += imul();
			break;
		case '/':
			s += idiv();
			break;
		default:
			System.err.println("ByteCode icalc() err, unknown op:" + op);
			break;
		}
		bytesCount++;
		return s;
	}

	public static String iadd() {
		return "iadd";
	}

	public static String isub() {
		return "isub";
	}

	public static String imul() {
		return "imul";
	}

	public static String idiv() {
		return "idiv";
	}

	public static String istore(int idx) {
		String s = bytesCount + ": istore_" + idx;
		bytesCount++;
		return s;
	}

	public static String iload(int idx) {
		String s = bytesCount + ": iload_" + idx;
		bytesCount++;
		return s;
	}

	public static String iload(char id) {
		String s = bytesCount + ": iload_" + IdTable.getTable().get(id);
		bytesCount++;
		return s;
	}

	public static String push(int val) {
		String s;
		if (val >= 0 && val <= 5) {
			s = bytesCount + ": iconst_" + val;
			bytesCount++;
		} else if (val >= -128 && val < 128) {
			s = bytesCount + ": bipush " + val;
			bytesCount += 2;
		} else {
			s = bytesCount + ": sipush " + val;
			bytesCount += 3;
		}
		return s;
	}

	public static String end() {
		return bytesCount + ": return";
	}
}

/**
 * The abstraction of source code, just for output
 * 
 * @author yazhoucao
 *
 */
class Code {
	private static List<String> content = new ArrayList<String>();

	public static int append(String s) {
		// System.out.println(s);
		content.add(s + "\n");
		return content.size() - 1;
	}

	public static void set(String s, int index) {
		content.set(index, s + "\n");
	}

	public static void output() {
		// System.out.println("=================Output====================");
		for (String line : content)
			System.out.print(line);
	}
}
