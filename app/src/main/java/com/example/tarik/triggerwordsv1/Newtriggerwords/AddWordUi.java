package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by Tarik on 32/04/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.tarik.triggerwordsv1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddWordUi extends AppCompatActivity implements WordRecyclerAdapter.onClickItemListener,
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        View.OnCreateContextMenuListener{
    public SqliteAdapter sqliteAdapter;
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
    private Button deleteAllWordsBtn;
    private ToggleButton orderToggleBtn;
    private Button gobtn;
    private ImageView helpBtn;
    private ArrayList<Word> wordList;
    private PopupMenu popupMenu;
    private String wordNameForPic;
    private int wordPositionForPic;
    private static final int SELECTED_PICTURE = 1;
    private static final int  REQUEST_PERMISSION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_word_ui);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar14);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        if (sqliteAdapter.getRowCount() > 0) {
            wordList.clear();
            initList();
        }
        else {
            initListDialog();
        }
    }

    private void init() {
        initWidgets();
        wordList = new ArrayList<Word>();
        sqliteAdapter = new SqliteAdapter(this);
        delayer();
        int tableRowCount = sqliteAdapter.getRowCount();


        if (tableRowCount > 0) {
            wordList.clear();
            initList();
        }
        else {
            initListDialog();
        }
    }

    private ArrayList<String> initListWithTriggerWords() {
        ArrayList<String> givenTriggerWordsList = new ArrayList<String>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("addtriggerwords.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                givenTriggerWordsList.add(mLine);
            }
        } catch (IOException e) {
            Log.d("Error trigger words DB:", "Could not load trigger words from assets");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d("Error trigger words DB:", "Could not load trigger words from assets");
                }
            }
        }
        return givenTriggerWordsList;
    }

    public void delayer() {
        try {
            Thread.sleep(800);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void initWidgets() {
        recyclerView = (RecyclerView) findViewById(R.id.wordRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wordNameEdit = (EditText) findViewById(R.id.wordNameEditView);
        wordNameEdit.setHint(Html.fromHtml("<i>" + "Type word here to add to list.." + "</i>"));
        wordNameEdit.setSelectAllOnFocus(true);

        addWordButton = (ImageView) findViewById(R.id.addWordButton);
        addWordButton.setOnClickListener(this);

        searchNameEdit = (EditText) findViewById(R.id.searchNameEditView);
        searchNameEdit.setHint(Html.fromHtml("<i>" + "Type word here to search from list.." + "</i>"));
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

        deleteAllWordsBtn = (Button)findViewById(R.id.deleteAllButton);
        deleteAllWordsBtn.setOnClickListener(this);

        orderToggleBtn = (ToggleButton) findViewById(R.id.orderToggleButton);
        orderToggleBtn.setOnCheckedChangeListener(this);

        gobtn = (Button) findViewById(R.id.wtfButton);
        gobtn.setOnClickListener(this);

        helpBtn = (ImageView) findViewById(R.id.helpButton);
        helpBtn.setOnClickListener(this);

        findViewById(R.id.mainLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return true;
            }
        });
    }

    protected void initList() {
        wordList.addAll(sqliteAdapter.getAllWords());
        delayer();
        wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
        recyclerView.setAdapter(wordRecyclerAdapter);
        wordRecyclerAdapter.setOnClickItemListener(this);
        //Log.d("initDB", "initDB " + wordList.size());
        setResultsFoundCounter(wordList.size());
    }

    private void initRankingList(String rankingOption) {
        if (!wordList.isEmpty()) {
            wordList.clear();
            wordList.addAll(sqliteAdapter.rankBy(rankingOption));
            delayer();
            wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
            wordRecyclerAdapter.setOnClickItemListener(this);
            recyclerView.setAdapter(wordRecyclerAdapter);
            setResultsFoundCounter(wordList.size());
        }
    }

    private void initOrderingList(String rankingOption, String orderingOption) {
        if (!wordList.isEmpty()) {
            wordList.clear();
            wordList.addAll(sqliteAdapter.orderBy(rankingOption, orderingOption));
            delayer();
            wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
            wordRecyclerAdapter.setOnClickItemListener(this);
            recyclerView.setAdapter(wordRecyclerAdapter);
            setResultsFoundCounter(wordList.size());
        }
    }

    private void initSearchList(String searchWordName) {
        wordList.clear();
        wordList.add(sqliteAdapter.searchWord(searchWordName));
        delayer();
        wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
        wordRecyclerAdapter.setOnClickItemListener(this);
        recyclerView.setAdapter(wordRecyclerAdapter);
    }



    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void addNewWord() {
        String newWordName = wordNameEdit.getText().toString().trim();
        int tableRowCount = sqliteAdapter.getRowCount();
        if (newWordName.length() != 0) {
            newWordName = newWordName.toLowerCase();
            if (tableRowCount > 0) {
                if (sqliteAdapter.compareName(newWordName))
                    alertDialog("That word is already in the list.");
                else {
                    Word newWord = new Word(newWordName, "a", 0);
                    long exitId = sqliteAdapter.insertData(newWord);
                    if (exitId < 0)
                        alertDialog("Error: Could not add that word");
                    else {
                        Toast.makeText(AddWordUi.this, "Word: " + newWordName + " added", Toast.LENGTH_SHORT).show();
                        wordRecyclerAdapter.addingWord(newWord, 0);
                        recyclerView.smoothScrollToPosition(0);
                        setResultsFoundCounter(tableRowCount + 1);

                    }
                }
            }
            else {
                Word newWord = new Word(newWordName, "a", 0);
                long exitId = sqliteAdapter.insertData(newWord);
                if (exitId < 0)
                    alertDialog("Error: Could not add that word");
                else {
                    Toast.makeText(AddWordUi.this, "Word: " + newWordName + " added", Toast.LENGTH_SHORT).show();
                    wordList.clear();
                    wordList.add(newWord);
                    wordRecyclerAdapter = new WordRecyclerAdapter(this, wordList);
                    wordRecyclerAdapter.addingWord(newWord, 0);
                    latestRadioBtn.performClick();
                    recyclerView.smoothScrollToPosition(0);
                    setResultsFoundCounter(1);
                    ;
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
            if (sqliteAdapter.getRowCount() > 0) {
                if (!sqliteAdapter.compareName(searchWordName)) {
                    alertDialog("That word is not in the list.");
                    //setResultsFoundCounter(0);
                }
                else {
                    initSearchList(searchWordName);
                    setResultsFoundCounter(1);
                }
            }
            else {
                alertDialog("No words are present in the list.");
            }
        }
        else {
            alertDialog("Please type in a word first");
        }
    }
    public void performSearch(){
        searchNameEdit.clearFocus();
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchNameEdit.getWindowToken(), 0);
        searchWord();


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

    public void initListDialog() {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.init_list_dialog, null);
        TextView messageText = (TextView) v.findViewById(R.id.messageTextView);
        messageText.setText("Do you wish to add a given list of words?");
        myAlert.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> givenTriggerWordsList = new ArrayList<String>();
                        givenTriggerWordsList.addAll(initListWithTriggerWords());
                        //Log.d("triggerwordslist size", ": " + givenTriggerWordsList.size());
                        for (String currentWord : givenTriggerWordsList) {
                            sqliteAdapter.insertData(new Word(currentWord, "a", 0));
                        }
                        initList();

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

    public void deleteOneWordDialog(final String currentWordName, final int position) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.init_list_dialog, null);
        TextView messageText = (TextView) v.findViewById(R.id.messageTextView);
        messageText.setText("Confirm deleting this word?");
        myAlert.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideKeyboard();
                        deleteWord(currentWordName, position);
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

    public void deleteAllWordsDialog() {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.init_list_dialog, null);
        TextView messageText = (TextView) v.findViewById(R.id.messageTextView);
        messageText.setText("Confirm deleting all words?");
        myAlert.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideKeyboard();
                        deleteAllWords();
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

    public void deletePictureDialog(final String imageUrl) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.init_list_dialog, null);
        TextView messageText = (TextView) v.findViewById(R.id.messageTextView);
        messageText.setText("Confirm deleting this picture?");
        myAlert.setView(v)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hideKeyboard();
                        replacePictureUrl(imageUrl);
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

    public void deleteAllWords() {
        //Toast.makeText(this, "delete All clicked on", Toast.LENGTH_SHORT).show();
        sqliteAdapter.deleteAllWordsFromDB();
        if (sqliteAdapter.getRowCount() == 0) {
            Toast.makeText(this, "All words have been deleted.", Toast.LENGTH_SHORT).show();
            wordList.clear();
            wordRecyclerAdapter.removingAllWords();
            setResultsFoundCounter(0);

        }
        else
        {
            Toast.makeText(this, "Error: Could not delete all words.", Toast.LENGTH_SHORT).show();
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
        if (word != null) {
            int position = getWordPositionForPic();
            if (newImageUrl != null) {
                long exitId = sqliteAdapter.updateWordImage(word, newImageUrl);
                if (exitId < 0) {
                    alertDialog("Error: Could not change/delete that word's image");
                } else {
                    wordRecyclerAdapter.settingImage(word, newImageUrl, position);
                }
            }
        }
    }

    @Override
    public void increaseWordPoints(String currentWordName, int position) {
        //Toast.makeText(this, "Like clicked for " + currentWordName, Toast.LENGTH_SHORT).show();
        Word word = sqliteAdapter.searchWord(currentWordName);
        int oldPoints = 0;
        if (word != null) {
            oldPoints = word.getPoints();
            if (oldPoints < 10) {
                //Toast.makeText(this, "points for " + currentWordName + " = " + oldPoints, Toast.LENGTH_SHORT).show();
                long exitId = sqliteAdapter.updateWordPoints(word, (oldPoints + 1));
                if (exitId < 0)
                    alertDialog("Error: Could not increase that word's points");
                else
                    wordRecyclerAdapter.increasePoints(word, position);
            } else {
                alertDialog("Cannot increase points to more than 10");
            }
        }
    }

    @Override
    public void decreaseWordPoints(String currentWordName, int position) {
        //sToast.makeText(this, "Like clicked for " + currentWordName, Toast.LENGTH_SHORT).show();
        Word word = sqliteAdapter.searchWord(currentWordName);
        int oldPoints = 0;
        if (word != null) {
            oldPoints = word.getPoints();
            if (oldPoints > 0) {
                //Toast.makeText(this, "points for " + currentWordName + " = " + oldPoints, Toast.LENGTH_SHORT).show();
                long exitId = sqliteAdapter.updateWordPoints(word, (oldPoints - 1));
                if (exitId < 0)
                    alertDialog("Error: Could not decrease that word's points");
                else
                    wordRecyclerAdapter.decreasePoints(word, position);
            } else {
                alertDialog("Cannot decrease points to less than 0");
            }
        }
    }

    @Override
    public void contextMenuHandler(Context context, TextView contextMenuView, Word currentWord, final int position) {
        if (currentWord != null) {
            final String currentWordName = currentWord.getWordName();
            final String wordImageUrl = currentWord.getImageUrl();
            popupMenu = new PopupMenu(context, contextMenuView);
            popupMenu.inflate(R.menu.word_context_menu);
            popupMenu.show();
            MenuItem deletePicButton = popupMenu.getMenu().findItem(R.id.deletePicture);
            if (wordList.size() > 0) {
                for (Word word : wordList) {
                    if (currentWordName.equals(word.getWordName())) {
                        if (word.getImageUrl().equals("a")) {
                            deletePicButton.setEnabled(false);
                            break;
                        }
                    }
                }
            }
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.editItem:
                            editDialog(currentWordName, position);
                            hideKeyboard();
                            break;

                        case R.id.deleteItem:
                            deleteOneWordDialog(currentWordName, position);
                            break;

                        case R.id.changePicture:
                            setWordNameForPic(currentWordName);
                            setWordPositionForPic(position);
                            hideKeyboard();
                            askSDAccessPermission();
                            break;

                        case R.id.deletePicture:
                            if (!wordImageUrl.equals("a")) {
                                setWordNameForPic(currentWordName);
                                setWordPositionForPic(position);
                                deletePictureDialog("a");
                            } else {
                                Toast.makeText(AddWordUi.this, "No image there to delete", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void imageClickHandler(String imageUrl) {
        Intent intent = new Intent(this, ZoomPic.class);
        intent.putExtra("ImageUrl", imageUrl);
        startActivity(intent);
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
                hideKeyboard();
                addNewWord();
                break;

            case R.id.searchWordButton:
                hideKeyboard();
                searchWord();
                break;

            case R.id.deleteAllButton:
                if (wordList.size() != 0)
                    deleteAllWordsDialog();
                else
                    Toast.makeText(this, "The List is already empty", Toast.LENGTH_SHORT).show();
                break;

            case R.id.latestRadioButton:
                initRankingList("latest");
                break;

            case R.id.difficultyRadioButton:
                initRankingList("difficulty");
                break;

            case R.id.alphabeticalRadioButton:
                initRankingList("alphabet");
                break;

            case R.id.wtfButton:
                orderToggleBtn.performClick();
                hideKeyboard();
                if (wordList.size() >= 2) {
                    startActivity(new Intent(this, InteractiveSession.class));
                }
                else {
                    Toast.makeText(this, "Please add at least 2 words to the list first.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.helpButton:
                hideKeyboard();
                helpDialog();
        }
    }

    private void rankOrdering(String orderingOption) {
        switch (mainRadioGrp.getCheckedRadioButtonId()) {
            case R.id.latestRadioButton:
                initOrderingList("latest", orderingOption);
                break;

            case R.id.difficultyRadioButton:
                initOrderingList("difficulty", orderingOption);
                break;

            case R.id.alphabeticalRadioButton:
                initOrderingList("alphabet", orderingOption);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            hideKeyboard();
            rankOrdering("DESC");
        }
        else {
            hideKeyboard();
            rankOrdering("ASC");
        }
    }

    public void helpDialog() {
        String message = "1. Start Blackboard --> Accesses the blackboard activity\n" +
                         "2. (+) button --> Adds the typed word to the list\n" +
                         "3. Search button --> Searches for the typed word from the list\n" +
                         "4. Words in the list can be ordered by latest entry, difficulty or alphabetical order\n" +
                         "5. Edit button --> opens menu to change/delete a word, or to change/delete the picture of a word\n" +
                         "6. Like/Dislike --> increases/decreases points of a word. More Likes mean word is easier\n" +
                         "7. The rating bar indicates whether a word is Easy(green), Normal(Yellow) or Tough(Red)\n" +
                         "8. Click on a picture to fully view it.";
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
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

