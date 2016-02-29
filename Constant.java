package newlang4;

public class Constant extends Node {
	Value value;
	public Constant(Value value){
		this.value = value;
	}
	public String toString(){
		return value.getSValue();
	}
	public Value getValue(){
		return value;
	}
}