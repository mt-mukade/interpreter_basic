package newlang4;


public class NameNode extends Node {
	Environment env;
	LexicalAnalyzer lex;
	LexicalUnit name;
	Variable variable;
	Node expr;
	Node expr_list;

	static Node isMatch(Environment env,LexicalUnit unit){
		if(unit.getType() == LexicalType.NAME){
			return new NameNode(env);
		}
		return null;
	}
	public NameNode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse(){
		name = lex.get();
		LexicalUnit lu = lex.get();

		if(lu.getType() == LexicalType.EQ){
			variable = (Variable)Variable.isMatch(env, name);
			LexicalUnit right = lex.get();
			expr = Expr.isMatch(env, right);
			lex.unget(right);
			if(expr != null){
				return expr.Parse();
			}
		}
		else{
			expr_list = Expr_list.isMatch(env,lu);
			lex.unget(lu);
			if(expr_list != null){
				return expr_list.Parse();
			}
		}
		return false;
	}
	@Override
	public String toString(){
		if(expr != null){
			return name.getValue().getSValue() + "[" + expr.toString() + "];";
		}
		return name.getValue().getSValue() + "[" + expr_list.toString() + "];";
	}
	@Override
	public Value getValue(){
		if(env.library.containsKey(name.getValue().getSValue())){
			Function func = (Function)env.library.get(name.getValue().getSValue());
			func.invoke((Expr_list)expr_list);
		} else {
			variable.setValue(expr.getValue());
		}
		return null;
	}
}
