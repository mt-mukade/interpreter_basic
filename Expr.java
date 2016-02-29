package newlang4;

import java.util.EnumSet;
import java.util.Stack;

public class Expr extends Node {
	LexicalAnalyzer lex;
	Node expr;
	Node body;

	Stack<Node> operandstack = new Stack<Node>();
	Stack<LexicalType> operatorstack = new Stack<LexicalType>();
	public static EnumSet<LexicalType> firstset = null;
	protected static EnumSet<LexicalType> exprset = null;

	public static Node isMatch(Environment env, LexicalUnit first) {
		if (firstset == null){
			firstset = EnumSet.of(LexicalType.NAME);
			firstset.add(LexicalType.INTVAL);
			firstset.add(LexicalType.DOUBLEVAL);
			firstset.add(LexicalType.LITERAL);
			firstset.add(LexicalType.SUB);
			firstset.add(LexicalType.LP);
		}
		if (exprset == null){
			exprset = EnumSet.of(LexicalType.NAME);
			exprset.add(LexicalType.INTVAL);
			exprset.add(LexicalType.DOUBLEVAL);
			exprset.add(LexicalType.LITERAL);
			exprset.add(LexicalType.LP);
			exprset.add(LexicalType.RP);
			exprset.add(LexicalType.ADD);
			exprset.add(LexicalType.SUB);
			exprset.add(LexicalType.MUL);
			exprset.add(LexicalType.DIV);
		}
		if(firstset.contains(first.getType())){
			return new Expr(env,first);
		}
		return null;
	}
	private Expr(Environment env,LexicalUnit first){
		this.env = env;
		lex = env.getInput();
	}
	@Override
	public boolean Parse(){

		if(!getOperand()){
			return false;
		}
		while(true){
			LexicalUnit lu = lex.get();
			if(lu == null){
				break;
			}
			if(!exprset.contains(lu.getType()) || lu.getType()==LexicalType.RP){
				lex.unget(lu);
				break;
			}
			LexicalType type = lu.getType();
			operatorstack.push(type);

			if(!getOperand()){
				return false;
			}
			if(type == LexicalType.MUL || type == LexicalType.DIV){
				reuse();
			}
		}
		while(!operatorstack.isEmpty()){
			reuse();
		}
		return true;
	}
	public void reuse(){
		Node second = operandstack.pop();
		Node first = operandstack.pop();
		LexicalType opr = operatorstack.pop();

		operandstack.push(new Operation(first,second,opr));
	}
	public boolean getOperand(){
		LexicalUnit lu = lex.get();

		Node variable = Variable.isMatch(env,lu);
		if(variable != null){
			operandstack.push(variable);
		}else{
			operandstack.push(new Constant(lu.getValue()));
		}
		return true;
	}
	@Override
	public String toString(){
		return operandstack.peek().toString();
	}
	@Override
	public Value getValue() {
		return operandstack.peek().getValue();
	}
}
	}
