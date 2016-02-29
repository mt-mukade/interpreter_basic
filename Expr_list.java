package newlang4;

import java.util.ArrayList;
import java.util.List;

public class Expr_list extends Node {
	LexicalAnalyzer lex;
	List<Node> expr_list = new ArrayList<Node>();
	Node expr;

	public static Node isMatch(Environment env, LexicalUnit first) {
		if(first.getType() == LexicalType.LP ||
				first.getType() == LexicalType.NAME ||
				first.getType() == LexicalType.INTVAL ||
				first.getType() == LexicalType.DOUBLEVAL ||
				first.getType() == LexicalType.LITERAL ||
				first.getType() == LexicalType.SUB){
			return new Expr_list(env);
		}
		return null;
	}
	private Expr_list(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse() {
		LexicalUnit lu;
		while(true){
			lu = lex.get();
			expr = Expr.isMatch(env,lu);
			lex.unget(lu);
			if(expr == null){
				return false;
			}
			if(expr.Parse() == false) {
				return false;
			}
			expr_list.add(expr);
			lu = lex.get();
			if(lu.getType() != LexicalType.COMMA){
				lex.unget(lu);
				return true;
			}
		}
	}
	public List<Node> getList() {
		return expr_list;
	}
	@Override
	public String toString(){
		String buffer = "";
		for(Node node_toString: expr_list){
			buffer += node_toString.toString();
		}
		return buffer;
	}
}
