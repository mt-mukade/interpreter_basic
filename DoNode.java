package newlang4;

public class DoNode extends Node{
	Environment env;
	LexicalAnalyzer lex;
	LexicalUnit DO;
	Node expr;
	Node cond;
	Node body;
	Node stmt_list;
	boolean flag;

	public static Node isMatch(Environment env,LexicalUnit first){
		if(first.getType() != LexicalType.DO) return null;
		return new DoNode(env);
	}
	private DoNode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	public boolean Parse(){
		LexicalUnit lu = lex.get();
		lu= lex.get();

		if(lu.getType() == LexicalType.WHILE || lu.getType() == LexicalType.UNTIL) {

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
			if(lu.getType() != LexicalType.LOOP){
				return false;
			}

		} else if(lu.getType() == LexicalType.NL) {
			lu = lex.get();
			stmt_list = StatementList.isMatch(env, lu);
			lex.unget(lu);
			if(stmt_list == null) return false;
			if(stmt_list.Parse() == false) return false;

			lu = lex.get();
			if(lu.getType() != LexicalType.LOOP){
				return false;
			}
			lu = lex.get();
			if(lu.getType() == LexicalType.WHILE || lu.getType() == LexicalType.UNTIL){

				lu = lex.get();
				cond = CondNode.isMatch(env,lu);
				lex.unget(lu);
				if(cond == null) return false;
				if(cond.Parse() == false) return false;
				return false;
			} else {
				return false;
			}
		}
		lu = lex.get();
		return LexicalType.NL == lu.getType();
	}
	@Override
	public String toString(){
		return "LOOP[" + cond.toString() + "[" +stmt_list.toString() + "]];";
	}
	@Override
	public Value getValue(){
		while(!cond.getValue().getBValue()){
			stmt_list.getValue();
		}
		return null;
	}
}
