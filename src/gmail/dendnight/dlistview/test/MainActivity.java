package gmail.dendnight.dlistview.test;

import gmail.dendnight.dlistview.R;
import gmail.dendnight.dlistview.main.DListView;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DListView listView = (DListView) findViewById(R.id.list);

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("²âÊÔÊý¾Ý" + (i + 1));
		}
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
