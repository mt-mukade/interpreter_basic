package newlang4;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
	PushbackReader in;
	Map<String, LexicalUnit> reserved;
	Map<String, LexicalUnit> opr;

    Stack<LexicalUnit> stack = new Stack<LexicalUnit>();

	public LexicalAnalyzerImpl(InputStream is) {
		Reader r = new InputStreamReader(is);
		in = new PushbackReader(r);

		reserved = new HashMap<String, LexicalUnit>();
		opr = new HashMap<String, LexicalUnit>();
		reserved.put("LOOP", new LexicalUnit(LexicalType.LOOP));
		reserved.put("IF", new LexicalUnit(LexicalType.IF));
		reserved.put("THEN", new LexicalUnit(LexicalType.THEN));
		reserved.put("ELSE", new LexicalUnit(LexicalType.ELSE));
		reserved.put("ELSEIF", new LexicalUnit(LexicalType.ELSEIF));
		reserved.put("ENDIF", new LexicalUnit(LexicalType.ENDIF));
		reserved.put("FOR", new LexicalUnit(LexicalType.FOR));
		reserved.put("NEXT", new LexicalUnit(LexicalType.NEXT));
		reserved.put("FUNC", new LexicalUnit(LexicalType.FUNC));
		reserved.put("FORALL", new LexicalUnit(LexicalType.FORALL));
		reserved.put("DIM", new LexicalUnit(LexicalType.DIM));
		reserved.put("AS", new LexicalUnit(LexicalType.AS));
		reserved.put("END", new LexicalUnit(LexicalType.END));
		reserved.put("WHILE", new LexicalUnit(LexicalType.WHILE));
		reserved.put("DO", new LexicalUnit(LexicalType.DO));
		reserved.put("UNTIL", new LexicalUnit(LexicalType.UNTIL));
		reserved.put("TO", new LexicalUnit(LexicalType.TO));
		reserved.put("WEND", new LexicalUnit(LexicalType.WEND));

		opr.put("=", new LexicalUnit(LexicalType.EQ));
		opr.put("<", new LexicalUnit(LexicalType.LT));
		opr.put(">", new LexicalUnit(LexicalType.GT));
		opr.put("<=", new LexicalUnit(LexicalType.LE));
		opr.put(">=", new LexicalUnit(LexicalType.GE));
		opr.put("<>", new LexicalUnit(LexicalType.NE));
		opr.put("+", new LexicalUnit(LexicalType.ADD));
		opr.put("-", new LexicalUnit(LexicalType.SUB));
		opr.put("*", new LexicalUnit(LexicalType.MUL));
		opr.put("/", new LexicalUnit(LexicalType.DIV));
		opr.put(")", new LexicalUnit(LexicalType.RP));
		opr.put("(", new LexicalUnit(LexicalType.LP));
		opr.put(",", new LexicalUnit(LexicalType.COMMA));
		opr.put("\n", new LexicalUnit(LexicalType.NL));
	}

	@Override
	public LexicalUnit get() {
		if(!stack.empty()){
			return stack.pop();
		}
		while (true) {
			char c = getChar();

			if (c == (char)-1)
				return new LexicalUnit(LexicalType.EOF);

			if (c == ' ' || c == '\t' || c == '\r'){
				continue;
			}
			ungetChar(c);

			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				return getString();
			}
			if (c >= '0' && c <= '9') {
				return getNumber();
			}
			if (c == '"') {
				c = getChar();
				return getLiteral();
			}
			if (opr.containsKey(String.valueOf(c))) {
				return getOperator();
			}
		}
	}

	private LexicalUnit getString() {
		String buffer = "";
		while (true) {
			char c = getChar();
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c >= '0'
					&& c <= '9') {
				buffer += c;
			} else {
				ungetChar(c);
				break;
			}
		}

		if (reserved.containsKey(buffer))
			return reserved.get(buffer);
		return new LexicalUnit(LexicalType.NAME, new ValueImpl(buffer));
	}

	private LexicalUnit getNumber() {

		String buffer = "";
		boolean dot = false;
		while (true) {
			char c = getChar();
			if (c >= '0' && c <= '9') {
				buffer += c;
			} else if (c == '.' && !dot) {
				buffer += c;
				dot = true;
			} else {
				ungetChar(c);
				break;
			}
		}

		if (dot) {
			return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(
					Double.parseDouble(buffer)));
		} else {
			return new LexicalUnit(LexicalType.INTVAL, new ValueImpl(
					Integer.parseInt(buffer)));
		}
	}

	private LexicalUnit getLiteral() {
		String buffer = "";
		while (true) {
			char ci = getChar();
			if (ci == '"') {
				return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(buffer));
			}
			buffer += ci;
		}
	}

	private LexicalUnit getOperator() {
		String buffer = "";
		while (true) {
			char c = getChar();
			if (opr.containsKey(String.valueOf(c))) {
				buffer += c;
			} else {
				ungetChar(c);
				break;
			}
		}
		if (opr.containsKey(buffer)) {
			return opr.get(buffer);
		}
		return null;
	}

	private char getChar() {
		if (in == null)
			return (char) -1;
		try {
			int ci = in.read();
			if (ci < 0) {
				in.close();
				in = null;
				return (char) -1;
			}
			return (char) ci;
		} catch (Exception e) {
			in = null;
			return (char) -1;
		}
	}

	private void ungetChar(char c) {
		try {
			in.unread((int) c);
		} catch (IOException e) {
		}catch(Exception e){
		}
	}

	@Override
	public boolean expect(LexicalType type) {
		return false;
	}

	@Override
	public void unget(LexicalUnit token) {
		stack.push(token);
	}
}
