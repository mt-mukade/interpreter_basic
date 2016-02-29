package newlang4;

public class WHILENode extends Node {
	Environment env;
	LexicalAnalyzer lex;
	LexicalUnit WHILE;
	Node expr;
	Node cond;
	Node body;
	Node stmt_list;

	public static Node isMatch(Environment env,LexicalUnit first){
		if(first.getType() != LexicalType.WHILE) return null;
		return new WHILENode(env);
	}
	private WHILENode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	public boolean Parse(){
		WHILE = lex.get();
		LexicalUnit lu = lex.get();

		lu = lex.get();
		cond = CondNode.isMatch(env,lu);
		lex.unget(lu);
		if(cond == null) return false;
		if(cond.Parse() == false) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.NL){
			return false;
		}

		lu = lex.get();
		stmt_list = StatementList.isMatch(env, lu);
		lex.unget(lu);
		if(stmt_list == null) return false;
		if(stmt_list.Parse() == false) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.WEND){
			return false;
		}
		lu = lex.get();
		if(lu.getType() == LexicalType.NL){
			return true;
		}
		return false;
	}
	public String toString(){
		return WHILE.getValue().getSValue() + expr.toString();
	}
}
