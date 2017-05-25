package pengchang.service.front;

import java.util.ArrayList;
import java.util.List;

import pengchang.database.models.PCEarnWord;

public class OneWord {

	
	public OneWord(PCEarnWord[] wordsArray, int index) {
		for( int i = 0 ; i < wordsArray.length ; i ++){
			if( wordsArray[i].getId() == index){
				//这个是对象词
				this.word = wordsArray[i].getEnglish();
				this.wordId = wordsArray[i].getId();
			}
			explanations.add(wordsArray[i].getChinese());
		}
		this.answer = "";
	}

	public String word;
	public int wordId;
	public List<String> explanations = new ArrayList<String>();
	public String answer;
}
