package newlang4;

public class ENDNode extends Node {
	Environment env;
	LexicalAnalyzer lex;
	LexicalUnit END;
	Node expr;

	static Node isMatch(Environment env,LexicalUnit unit){
		if(unit.getType() == LexicalType.END){
			new ENDNode(env);
		}
		return null;
	}
	public ENDNode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse(){
		return true;
	}
	@Override
	public String toString(){
		return "END;";
	}
}
