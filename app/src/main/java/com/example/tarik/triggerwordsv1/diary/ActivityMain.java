package com.example.tarik.triggerwordsv1.diary;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;

import com.example.tarik.triggerwordsv1.R;

import static com.example.tarik.triggerwordsv1.R.id.toolbar;

/**
 * Created by huanghe on 1/04/2017.
 */

public class ActivityMain extends ListActivity{
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
        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(ActivityMain.this, ActivityDiaryEdit.class);
                startActivity(intentLoadNewActivity);
            }
        });
        final FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b);
        actionB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(ActivityMain.this, SendCalendar.class);
                startActivity(intentLoadNewActivity);
            }
        });


        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));

       /* findViewById(R.id.pink_icon).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityMain.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.setter);
        button.setSize(FloatingActionButton.SIZE_MINI);
        button.setColorNormalResId(R.color.pink);
        button.setColorPressedResId(R.color.pink_pressed);
        button.setIcon(R.drawable.ic_fab_star);
        button.setStrokeVisible(false);

        final View actionB = findViewById(R.id.action_b);

        FloatingActionButton actionC = new FloatingActionButton(getBaseContext());
        actionC.setTitle("Hide/Show Action above");
        actionC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        menuMultipleActions.addButton(actionC);

        final FloatingActionButton removeAction = (FloatingActionButton) findViewById(R.id.button_remove);
        removeAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
            }
        });

        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));
        ((FloatingActionButton) findViewById(R.id.setter_drawable)).setIconDrawable(drawable);

        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                actionA.setTitle("Action A clicked");
            }
        });

        // Test that FAMs containing FABs with visibility GONE do not cause crashes
        findViewById(R.id.button_gone).setVisibility(View.GONE);

        final FloatingActionButton actionEnable = (FloatingActionButton) findViewById(R.id.action_enable);
        actionEnable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
            }
        });

        FloatingActionsMenu rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
        FloatingActionButton addedOnce = new FloatingActionButton(this);
        addedOnce.setTitle("Added once");
        rightLabels.addButton(addedOnce);

        FloatingActionButton addedTwice = new FloatingActionButton(this);
        addedTwice.setTitle("Added twice");
        rightLabels.addButton(addedTwice);
        rightLabels.removeButton(addedTwice);
        rightLabels.addButton(addedTwice);*/

        /*final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                    fab_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentLoadNewActivity = new Intent(ActivityMain.this, ActivityDiaryEdit.class);
                            startActivity(intentLoadNewActivity);

                        }
                    });

                    fab.startAnimation(FabRclockwise);
                    fab_1.setClickable(true);
                    fab_2.setClickable(true);
                    isOpen=true;


                }


            }
        });*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        toolbar.setNavigationIcon(R.drawable.ic_backarrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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