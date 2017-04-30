package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by huanghe on 30/04/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tarik.triggerwordsv1.R;

import java.util.ArrayList;

public class AddWordUi extends AppCompatActivity implements WordRecyclerAdapter.onClickItemListener,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        View.OnCreateContextMenuListener{
    private SqliteAdapter sqliteAdapter;
    private RecyclerView recyclerView;
    private WordRecyclerAdapter wordRecyclerAdapter;
    private EditText wordNameEdit;
    private ImageView addWordButton;
    private EditText searchNameEdit;
    private ImageView searchWordBtn;
    private RadioGroup mainRadioGrp;
    private RadioButton latestRadioBtn;
    private RadioButton difficultyRadioBtn;
    private RadioButton alphabeticalRadioBtn;
    private TextView resultCount;
    private ToggleButton orderToggleBtn;
    private ArrayList<Word> wordList;
    private PopupMenu popupMenu;
    private String wordNameForPic;
    private int wordPositionForPic;
    private static final int SELECTED_PICTURE = 1;
    private static final int  REQUEST_PERMISSION = 2;
    private Button gobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word_ui);
        init();
    }

    private void initDB() {
        wordList.addAll(sqliteAdapter.getAllWords());
        wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
        recyclerView.setAdapter(wordRecyclerAdapter);
        wordRecyclerAdapter.setOnClickItemListener(this);
        Log.d("initDB", "initDB " + wordList.size());
        setResultsFoundCounter(wordList.size());
    }

    private void initRankingDB(String rankingOption) {
        if (!wordList.isEmpty()) {
            wordList.clear();
            wordList.addAll(sqliteAdapter.rankBy(rankingOption));
            wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
            wordRecyclerAdapter.setOnClickItemListener(this);
            recyclerView.setAdapter(wordRecyclerAdapter);
            setResultsFoundCounter(wordList.size());
        }
    }

    private void initOrderingDB(String rankingOption, String orderingOption) {
        if (!wordList.isEmpty()) {
            wordList.clear();
            wordList.addAll(sqliteAdapter.orderBy(rankingOption, orderingOption));
            wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
            wordRecyclerAdapter.setOnClickItemListener(this);
            recyclerView.setAdapter(wordRecyclerAdapter);
            setResultsFoundCounter(wordList.size());
        }
    }

    private void initSearchDB(String searchWordName) {
        wordList.clear();
        wordList.add(sqliteAdapter.searchWord(searchWordName));
        wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
        wordRecyclerAdapter.setOnClickItemListener(this);
        recyclerView.setAdapter(wordRecyclerAdapter);
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.wordRecyclerView);
        wordList = new ArrayList<Word>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordNameEdit = (EditText) findViewById(R.id.wordNameEditView);
        wordNameEdit.setHint(Html.fromHtml("<i>" + "Add word to list.." + "</i>"));
        wordNameEdit.setSelectAllOnFocus(true);
        addWordButton = (ImageView) findViewById(R.id.addWordButton);
        addWordButton.setOnClickListener(this);
        searchNameEdit = (EditText) findViewById(R.id.searchNameEditView);
        searchNameEdit.setHint(Html.fromHtml("<i>" + "Search word from list.." + "</i>"));
        searchNameEdit.setSelectAllOnFocus(true);
        searchWordBtn = (ImageView) findViewById(R.id.searchWordButton);
        searchWordBtn.setOnClickListener(this);
        mainRadioGrp = (RadioGroup) findViewById(R.id.mainRadioGroupView);
        latestRadioBtn = (RadioButton) findViewById(R.id.latestRadioButton);
        latestRadioBtn.setOnClickListener(this);
        difficultyRadioBtn = (RadioButton) findViewById(R.id.difficultyRadioButton);
        difficultyRadioBtn.setOnClickListener(this);
        alphabeticalRadioBtn = (RadioButton) findViewById(R.id.alphabeticalRadioButton);
        alphabeticalRadioBtn.setOnClickListener(this);
        resultCount = (TextView) findViewById(R.id.resultCountView);
        orderToggleBtn = (ToggleButton) findViewById(R.id.orderToggleButton);
        orderToggleBtn.setOnCheckedChangeListener(this);
        findViewById(R.id.mainLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        gobtn = (Button) findViewById(R.id.wtfButton);
        gobtn.setOnClickListener(this);

        sqliteAdapter = new SqliteAdapter(this);
        if (sqliteAdapter.getRowCount() != 0) {
            initDB();
        }
    }

    private void addNewWord() {
        String newWordName = wordNameEdit.getText().toString().trim();
        if (newWordName.length() != 0) {
            if (sqliteAdapter.compareName(newWordName))
                alertDialog("That word is already in the list.");
            else {
                Word newWord = new  Word(newWordName, "a", 0);
                long exitId = sqliteAdapter.insertData(newWord);
                if (exitId < 0)
                    alertDialog("Error: Could not add that word");
                else {
                    Toast.makeText(AddWordUi.this, "Word: " + newWordName + " added", Toast.LENGTH_SHORT).show();
                    wordRecyclerAdapter.addingWord(newWord, 0);
                    recyclerView.smoothScrollToPosition(0);
                    setResultsFoundCounter(sqliteAdapter.getRowCount());;
                }
            }
        }
        else {
            alertDialog("Please type in a word first");
        }
    }

    private void searchWord() {
        String searchWordName = searchNameEdit.getText().toString().trim();
        if (searchWordName.length() != 0) {
            if (!sqliteAdapter.compareName(searchWordName))
                alertDialog("That word is not in the list.");
            else {
                initSearchDB(searchWordName);
            }
        }
        else {
            alertDialog("Please type in a word first");
        }
    }

    public void editWord(String oldWordName, String newWordName, int position) {
        Word oldWord = sqliteAdapter.searchWord(oldWordName);
        Word newWord = new  Word(newWordName, oldWord.getImageUrl(), oldWord.getPoints());
        long exitId = sqliteAdapter.editWordFromDB(oldWord, newWord);
        if (exitId < 0)
            alertDialog("Error: Could not edit that word");
        else {
            Toast.makeText(AddWordUi.this, "Word: " + oldWordName + " edited to " + newWordName, Toast.LENGTH_SHORT).show();
            wordRecyclerAdapter.editingWord(oldWord, newWord ,position);
        }
    }

    public void setupEditWord(String oldWordName, String inputWordName, int position) {
        //Toast.makeText(this, "edit clicked on" + inputWordName, Toast.LENGTH_SHORT).show();
        if (sqliteAdapter.compareName(inputWordName)) {
            alertDialog("This new word is already in the list.");
            //Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();

        }
        else {
            editWord(oldWordName, inputWordName, position);
            //Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
        }

    }

    public void editDialog(final String oldWordName, final int position) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.edit_word_dialog, null);

        myAlert.setView(v)
                .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editWordTxt = (EditText) v.findViewById(R.id.editWordView);
                        editWordTxt.setSelectAllOnFocus(true);
                        String inputWordName = editWordTxt.getText().toString().trim();
                        if (inputWordName.length() != 0)
                            setupEditWord(oldWordName, inputWordName, position);
                        else {
                            alertDialog("Word field cannot be empty");
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }

    public void deleteWord(String inputWordName, int position) {
        //Toast.makeText(this, "delete clicked on" + inputWordName, Toast.LENGTH_SHORT).show();
        long exitId = sqliteAdapter.deleteWordFromDB(inputWordName);
        if (exitId < 0)
            alertDialog("Error: Could not delete that word");
        else {
            Toast.makeText(AddWordUi.this, "Word: " + inputWordName + " deleted", Toast.LENGTH_SHORT).show();
            wordRecyclerAdapter.removingWord(inputWordName, position);
            setResultsFoundCounter(sqliteAdapter.getRowCount());
        }
    }

    public void askSDAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                changePicture();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                changePicture();
            } else {
                Toast.makeText(this, "Permission to Read/Write from external storage denied", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void changePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String filePath = null;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    String [] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(projection[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                }
                break;
        }
        replacePictureUrl(filePath);
    }

    public void replacePictureUrl(String newImageUrl) {
        String inputWordName = getWordNameForPic();
        Word word = sqliteAdapter.searchWord(inputWordName);
        int position = getWordPositionForPic();
        if (newImageUrl != null) {
            long exitId = sqliteAdapter.updateWordImage(word, newImageUrl);
            if (exitId < 0) {
                alertDialog("Error: Could not increase that word's points");
            } else {
                wordRecyclerAdapter.settingImage(word, newImageUrl, position);
            }
        }
    }

    @Override
    public void increaseWordPoints(String currentWordName, int position) {
        //Toast.makeText(this, "Like clicked for " + currentWordName, Toast.LENGTH_SHORT).show();
        Word word = sqliteAdapter.searchWord(currentWordName);
        int oldPoints = word.getPoints();
        if (oldPoints < 10) {
            //Toast.makeText(this, "points for " + currentWordName + " = " + oldPoints, Toast.LENGTH_SHORT).show();
            long exitId = sqliteAdapter.updateWordPoints(word, (oldPoints + 1));
            if (exitId < 0)
                alertDialog("Error: Could not increase that word's points");
            else
                wordRecyclerAdapter.increasePoints(word, position);
        }
        else {
            alertDialog("Cannot increase points to more than 10");
        }
    }

    @Override
    public void decreaseWordPoints(String currentWordName, int position) {
        //sToast.makeText(this, "Like clicked for " + currentWordName, Toast.LENGTH_SHORT).show();
        Word word = sqliteAdapter.searchWord(currentWordName);
        int oldPoints = word.getPoints();
        if (oldPoints > 0) {
            //Toast.makeText(this, "points for " + currentWordName + " = " + oldPoints, Toast.LENGTH_SHORT).show();
            long exitId = sqliteAdapter.updateWordPoints(word, (oldPoints - 1));
            if (exitId < 0)
                alertDialog("Error: Could not decrease that word's points");
            else
                wordRecyclerAdapter.decreasePoints(word, position);
        }
        else {
            alertDialog("Cannot decrease points to less than 0");
        }
    }

    @Override
    public void contextMenuHandler(Context context, TextView contextMenuView, final String currentWordName, final int position) {
        popupMenu = new PopupMenu(context, contextMenuView);
        popupMenu.inflate(R.menu.word_context_menu);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.editItem:
                        editDialog(currentWordName, position);
                        break;

                    case R.id.deleteItem:
                        deleteWord(currentWordName, position);
                        break;

                    case R.id.changePicture:
                        setWordNameForPic(currentWordName);
                        setWordPositionForPic(position);
                        askSDAccessPermission();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onStop() {
        if (popupMenu != null) { popupMenu.dismiss(); popupMenu = null; }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (popupMenu != null) { popupMenu.dismiss(); popupMenu = null; }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (popupMenu != null) { popupMenu.dismiss(); popupMenu = null; }
        super.onDestroy();
    }

    private void setWordNameForPic(String currentWordName) {
        this.wordNameForPic = currentWordName;
    }

    private void setWordPositionForPic(int currentWordPosition) {
        this.wordPositionForPic = currentWordPosition;
    }

    private String getWordNameForPic() {
        return this.wordNameForPic;
    }

    private int getWordPositionForPic() {
        return this.wordPositionForPic;
    }

    private void setResultsFoundCounter(int wordListSize) {
        resultCount.setText(Integer.toString(wordListSize));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addWordButton:
                addNewWord();
                break;

            case R.id.searchWordButton:
                searchWord();
                break;

            case R.id.latestRadioButton:
                initRankingDB("latest");
                break;

            case R.id.difficultyRadioButton:
                initRankingDB("difficulty");
                break;

            case R.id.alphabeticalRadioButton:
                initRankingDB("alphabet");
                break;

            case R.id.wtfButton:
                startActivity(new Intent(this, InteractiveSession.class));
                break;
        }
    }

    private void rankOrdering(String orderingOption) {
        switch (mainRadioGrp.getCheckedRadioButtonId()) {
            case R.id.latestRadioButton:
                initOrderingDB("latest", orderingOption);
                break;

            case R.id.difficultyRadioButton:
                initOrderingDB("difficulty", orderingOption);
                break;

            case R.id.alphabeticalRadioButton:
                initOrderingDB("alphabet", orderingOption);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            rankOrdering("DESC");
        else
            rankOrdering("ASC");
    }

    public void alertDialog(String errorMessage) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }


}

