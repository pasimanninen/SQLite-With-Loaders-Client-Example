package fi.ptm.sqlitewithloadersclientexample;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

/**
 * Created by pasi on 27/09/15.
 */
public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get list view
        listView = (ListView) findViewById(R.id.listView);
        // show highscores
        showHighscores();
    }

    // show hs in listview
    private void showHighscores() {
        // Fields from the database (projection)
        String[] from = new String[] { "name", "score" };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.name, R.id.score };

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, null, from, to, 0);
        listView.setAdapter(adapter);
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                "_id",
                "name",
                "score"};
        CursorLoader cursorLoader = new CursorLoader(
                this,
                Uri.parse("content://fi.ptm.sqlitewithloadersexample.contentprovider/highscores"),
                projection, null, null, "score DESC"); // highest score first
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // new data is available, use it
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

}
