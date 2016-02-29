package newlang4;

public class Program extends Node{
	Node stmt_list;

	public static Node isMatch(Environment env, LexicalUnit first){
		return new Program(env);
	}

	private Program(Environment env){
		this.env = env;
	}
	@Override
	public boolean Parse(){
		LexicalAnalyzer lex = env.getInput();
		LexicalUnit lu = lex.get();

		stmt_list = StatementList.isMatch(env, lu);
		lex.unget(lu);
		if(stmt_list != null){
			return stmt_list.Parse();
		}
		return false;
	}
	@Override
	public String toString(){
		return stmt_list.toString();
	}
	@Override
	public Value getValue(){
		stmt_list.getValue();
		return null;
	}
}
