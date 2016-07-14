package pengchang.service.front;

import java.io.Serializable;
import java.util.ArrayList;

public class WordList  extends ArrayList<OneWord> implements Serializable{

	private static final long serialVersionUID = 7458044131025062511L;
	
	public long tryTimestamp = 0; //唯一标识了一次尝试
	
	public int getAnsweredNumber(){
		int count = 0;
		for( OneWord oneWord : this){
			if( oneWord.answer.length() > 0 ){
				count++;
			}
		}
		return count;
	}

	
	public boolean has(int index) {
		for( OneWord oneWord : this){
			if( oneWord.wordId == index) 
				return true;
		}
		
		return false;
	}
	
}
