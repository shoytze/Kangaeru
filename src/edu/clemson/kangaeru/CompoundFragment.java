package edu.clemson.kangaeru;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class CompoundFragment extends AbstractFragment {

	private TextView remainder;
	private EditText answerInput;
	private TextView hint1, hint2;
	private String empty = "          ";
	private GuessEvaluator mAct;
	private InputMethodManager mMan;
	
	public interface GuessEvaluator{
		void updateImage(boolean success);
	}
	
	@Override 
	public void onAttach(Activity a){
		super.onAttach(a);
		mAct = (GuessEvaluator) a;
		mMan = (InputMethodManager) a.getSystemService(a.INPUT_METHOD_SERVICE);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			   Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.compoundfragment, container, false);
		v.setOnTouchListener(cursorSwapper);
		remainder = (TextView) v.findViewById(R.id.remainder);
		answerInput = (EditText) v.findViewById(R.id.answerInput);
		answerInput.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN &&
						keyCode == KeyEvent.KEYCODE_ENTER){
					if(list != null){
						if(list.getCount() > 0) //otherwise, there's a null exception
							guess();
					}
					mMan.hideSoftInputFromWindow(answerInput.getWindowToken(), 0);
					System.err.println("this IS happening, no?");
					return true;
				}
				return false;
			}
		});
		
		hint1 = (TextView) v.findViewById(R.id.prompt1);
		hint2 = (TextView) v.findViewById(R.id.prompt2);
		return v;
	}
	
	
	@Override
	protected void setStrings(){
		System.err.println("column #: " + list.getColumnCount());
		remainder.setText(list.getString(0).substring(1));
		hint1.setText(list.getString(1));
		hint2.setText(list.getString(2));
		answerInput.setGravity(Gravity.CENTER_HORIZONTAL);
		remainder.setGravity(Gravity.CENTER_HORIZONTAL);
	}
	
	@Override
	protected void nullStrings(){
		remainder.setText(empty);
		hint1.setText(empty);
		hint2.setText(empty);
	}
	
	private void guess(){
		String answer = answerInput.getText().toString() + remainder.getText();
		System.err.println("answer " + answer);
		mAct.updateImage(answer.equals(list.getString(0)));
	}

	@Override
	protected void swapSide() {
		//not really applicable here
	}


	@Override
	protected void updateDisplay() {
		setStrings();
	}

	
}
