package newlang4;

import java.io.FileInputStream;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fin = null;
		LexicalAnalyzer lex;
		LexicalUnit		first;
		Environment		env;
		Node			expression;

		System.out.println("basic parser");
		try {
			fin = new FileInputStream("test.txt");
		}
		catch(Exception e) {
			System.out.println("file not found");
			System.exit(-1);
		}
		lex = new LexicalAnalyzerImpl(fin);
		env = new Environment(lex);

		first = lex.get();

		expression = Program.isMatch(env, first);
		lex.unget(first);
		if (expression != null && expression.Parse()) {
			System.out.println(expression.toString());
			expression.getValue();

		}
		else {
			System.out.println("syntax error");

			for(first = lex.get(); first.getType() != LexicalType.EOF;first = lex.get()){
				System.out.println(first);
			}
		}
	}
}
