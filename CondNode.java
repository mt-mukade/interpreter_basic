package newlang4;


public class CondNode extends Node{
	LexicalAnalyzer lex;
	Node expr_left;
	Node expr_right;
	LexicalType opr;

	public static Node isMatch(Environment env, LexicalUnit first) {

		if(first.getType() == LexicalType.LP ||
				first.getType() == LexicalType.NAME ||
				first.getType() == LexicalType.INTVAL ||
				first.getType() == LexicalType.DOUBLEVAL ||
				first.getType() == LexicalType.LITERAL ||
				first.getType() == LexicalType.SUB){
			return new CondNode(env);
		}
		return null;
	}
	private CondNode(Environment env){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse(){

		LexicalUnit lu = lex.get();
		expr_left = Expr.isMatch(env,lu);
		lex.unget(lu);
		if(expr_left == null) return false;
		if(expr_left.Parse() == false) return false;

		opr = lex.get().getType();
		if(opr != LexicalType.EQ && opr != LexicalType.GT && opr != LexicalType.LT
				&& opr != LexicalType.GE && opr != LexicalType.LE && opr != LexicalType.NE){
			return false;
		}
		lu = lex.get();
		expr_right = Expr.isMatch(env,lu);
		lex.unget(lu);
		if(expr_right == null) return false;
		if(expr_right.Parse() == false) return false;
		return true;
	}
	@Override
	public String toString(){
		String buffer = "[" + expr_right.toString() +":"+ expr_left.toString() + "]";
		switch(opr){
		case EQ :
			return "==" + buffer;
		case GT :
			return ">" + buffer;
		case LT :
			return "<" + buffer;
		case GE :
			return ">=" + buffer;
		case LE :
			return "<=" + buffer;
		case NE :
			return "!=" + buffer;
		default:
			return null;
		}
	}
	@Override
	public Value getValue(){
		switch(opr){
		case EQ :
			return new ValueImpl(expr_left.getValue().getIValue() == expr_right.getValue().getIValue());
		case GT :
			return new ValueImpl(expr_left.getValue().getIValue() > expr_right.getValue().getIValue());
		case LT :
			return new ValueImpl(expr_left.getValue().getIValue() < expr_right.getValue().getIValue());
		case GE :
			return new ValueImpl(expr_left.getValue().getIValue() >= expr_right.getValue().getIValue());
		case LE :
			return new ValueImpl(expr_left.getValue().getIValue() <= expr_right.getValue().getIValue());
		case NE :
			return new ValueImpl(expr_left.getValue().getIValue() != expr_right.getValue().getIValue());
		default:
			return null;
		}
	}
}

