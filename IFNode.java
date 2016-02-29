package newlang4;

public class IFNode extends Node{
	Environment env;
	LexicalAnalyzer lex;
	Node cond;
	Node body;
	Node else_body;

	public static Node isMatch(Environment env, LexicalUnit first){
		if(first.getType() != LexicalType.IF) return null;
		return new IFNode(env);
	}
	private IFNode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse(){
		LexicalUnit lu = lex.get();

		cond = CondNode.isMatch(env,lu);
		lex.unget(lu);
		if(cond == null) return false;
		if(cond.Parse() == false) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.THEN) return false;

		lu = lex.get();
		if(lu.getType() != LexicalType.NL){
			body = Stmt.isMatch(env, lu);
			lex.unget(lu);
			if(body.Parse() != true) return false;

			lu = lex.get();
			if(lu.getType() != LexicalType.ELSE){
				if(lu.getType() == LexicalType.NL) return true;
				return false;
			}
			else {
				lu = lex.get();
				else_body = Stmt.isMatch(env, lu);
				lex.unget(lu);
				if(else_body == null || else_body.Parse() == false) return false;

				lu = lex.get();
				if(lu.getType() == LexicalType.NL){
					return true;
				}
				return false;
			}
		}
		return false;
	}
}
