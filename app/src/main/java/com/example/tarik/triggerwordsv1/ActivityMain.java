package com.example.tarik.triggerwordsv1;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * Created by huanghe on 1/04/2017.
 */

public class ActivityMain extends ListActivity {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
    FloatingActionButton fab,fab_1,fab_2;
    Animation FabOpen,FabClose,FabRclockwise,FabRanticlockwise;
    boolean isOpen = false;
    private DiaryDbAdapter mDbHelper;
    private Cursor mDiaryCursor;
    private Button button2;
    ImageButton imageButton7;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);
        mDbHelper = new DiaryDbAdapter(this);
        mDbHelper.open();
        renderListView();



        registerForContextMenu(getListView());

        this.getListView().setLongClickable(true);



        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab_1 = (FloatingActionButton) findViewById(R.id.fab_1);
        final FloatingActionButton fab_2 = (FloatingActionButton) findViewById(R.id.fab_2);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRclockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabRanticlockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    fab_1.startAnimation(FabClose);
                    fab_2.startAnimation(FabClose);
                    fab.startAnimation(FabRanticlockwise);
                    fab_1.setClickable(false);
                    fab_2.setClickable(false);
                    isOpen=false;

                }
                else{
                    fab_1.startAnimation(FabOpen);
                    fab_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentLoadNewActivity = new Intent(ActivityMain.this, SendCalendar.class);
                            startActivity(intentLoadNewActivity);

                        }
                    });
                    fab_2.startAnimation(FabOpen);
                    fab.startAnimation(FabRclockwise);
                    fab_1.setClickable(true);
                    fab_2.setClickable(true);
                    isOpen=true;


                }

            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(ActivityMain.this, ActivityDiaryEdit.class);
                startActivity(intentLoadNewActivity);

            }
        });
        }



    private void renderListView() {
        mDiaryCursor = mDbHelper.getAllNotes();
        startManagingCursor(mDiaryCursor);
        String[] from = new String[] { DiaryDbAdapter.KEY_TITLE,
                DiaryDbAdapter.KEY_CREATED };
        int[] to = new int[] { R.id.delete, R.id.created };
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
                R.layout.diary_row, mDiaryCursor, from, to);
        setListAdapter(notes);


    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, INSERT_ID, 0, R.string.menu_insert);

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createDiary();
                return true;

        }
        return super.onMenuItemSelected(featureId, item);
    }




    private void createDiary() {
        Intent i = new Intent(this, ActivityDiaryEdit.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete); //notes can be deleted here


    }



    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteDiary(info.id);
                renderListView();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mDiaryCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, ActivityDiaryEdit.class);
        i.putExtra(DiaryDbAdapter.KEY_ROWID, id);
        i.putExtra(DiaryDbAdapter.KEY_TITLE, c.getString(c
                .getColumnIndexOrThrow(DiaryDbAdapter.KEY_TITLE)));
        i.putExtra(DiaryDbAdapter.KEY_BODY, c.getString(c
                .getColumnIndexOrThrow(DiaryDbAdapter.KEY_BODY)));
        startActivityForResult(i, ACTIVITY_EDIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        renderListView();
    }


}