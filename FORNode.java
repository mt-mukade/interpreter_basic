package newlang4;

public class FORNode extends Node {
	Environment env;
	LexicalAnalyzer lex;
	LexicalUnit FOR;
	Node stmt_list;
	Node expr;

	static Node isMatch(Environment env,LexicalUnit unit){
		if(unit.getType() == LexicalType.FOR){
			new FORNode(env);
		}
		return null;
	}
	public FORNode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse(){
		FOR = lex.get();
		LexicalUnit lu = lex.get();

		lu = lex.get();
		if(lu.getType() == LexicalType.NAME) {
			if(lu.getType() == LexicalType.EQ)
				expr = Expr.isMatch(env,lu);
			lex.unget(lu);
			if(expr == null) return false;
			if(expr.Parse() == false) return false;
		}

		lu = lex.get();
		if(lu.getType() != LexicalType.TO) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.INTVAL) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.NL) return false;

		lu = lex.get();
		stmt_list = StatementList.isMatch(env, lu);
		lex.unget(lu);
		if(stmt_list == null) return false;
		if(stmt_list.Parse() == false) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.NEXT) return false;

		lu = lex.get();
		if(lu.getType() == LexicalType.NAME) return true;

		return false;
	}
	public String toString(){
		return FOR.getValue().getSValue() + expr.toString();
	}
}
