package newlang4;

public class Operation extends Node{
	Node first;
	Node second;
	LexicalType operator;

	public Operation(Node first, Node second, LexicalType operator){
		this.first = first;
		this.second = second;
		this.operator = operator;
	}
	public String toString(){
		return operator.toString() + "[" + first.toString() + "," + second.toString() + "]";
	}
	public Value getValue(){
		if(first.getValue().getType() == ValueType.INTEGER){
			switch(operator){
			case ADD :
				return new ValueImpl(first.getValue().getIValue() + second.getValue().getIValue());
			case SUB :
				return new ValueImpl(first.getValue().getIValue() - second.getValue().getIValue());
			case MUL :
				return new ValueImpl(first.getValue().getIValue() * second.getValue().getIValue());
			case DIV :
				return new ValueImpl(first.getValue().getIValue() / second.getValue().getIValue());
			default : return null;
			}
		} else if(first.getValue().getType() == ValueType.STRING) {
			if(operator == LexicalType.ADD){
				return new ValueImpl(first.getValue().getSValue() + second.getValue().getSValue());
			}
		}
		return null;
	}
}
