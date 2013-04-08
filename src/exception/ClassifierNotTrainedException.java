package exception;

public class ClassifierNotTrainedException extends Exception {
	public ClassifierNotTrainedException(){
		super();
	}
	public ClassifierNotTrainedException(String message){
		super(message);
	}
}
