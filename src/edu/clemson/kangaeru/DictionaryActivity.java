package edu.clemson.kangaeru;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class DictionaryActivity extends ListActivity{

	private DictionaryAdapter mDictionaryAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        mDictionaryAdapter = new DictionaryAdapter(this);
        mDictionaryAdapter.open();
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dictionary, menu);
        return true;
    }

    private void fillData() {
    	
        // Get all of the kanji from the database and create the item list
        Cursor c = mDictionaryAdapter.fetchAllEntries();
        startManagingCursor(c);

        String[] from = new String[] {	DictionaryAdapter.KEY_SQUIGGLE,
        								DictionaryAdapter.KEY_READINGS };
        int[] to = new int[] { R.id.listsquiggle, R.id.listreadings };
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
        		new SimpleCursorAdapter(this, R.layout.dictionary_list_entry, c, from, to);
        setListAdapter(notes);
        
        ListView lv = getListView();
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
        	  System.err.println(id);
              }
        });
    }
    
}
