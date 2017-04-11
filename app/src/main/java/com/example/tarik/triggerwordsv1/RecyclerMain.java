package com.example.tarik.triggerwordsv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.tarik.triggerwordsv1.triggerWords.FirebaseHelper;
import com.example.tarik.triggerwordsv1.triggerWords.WordActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerMain extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference mRef;
    private RecyclerView recyclerView;
    private WordListAdapter wordListAdapter;
    private FirebaseHelper helper;

    private Button startGame;
    private Button addWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_list);



        this.mRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://triggerwordsv1.firebaseio.com/");
        helper = new FirebaseHelper(mRef);
        recyclerView = (RecyclerView) findViewById(R.id.wordListRecyclerView);

        startGame = (Button) findViewById(R.id.startGameButton);
        addWord = (Button) findViewById(R.id.addWordButton);
        startGame.setOnClickListener(this);
        addWord.setOnClickListener(this);

        //wordListAdapter = new WordListAdapter(RecyclerMain.this, helper.fetchAllData());
        wordListAdapter = new WordListAdapter(RecyclerMain.this, getData());
        recyclerView.setAdapter(wordListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerMain.this));



    }


    public static List<Informationn> getData() {
        List<Informationn> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_accessibility_black_24dp,
                R.drawable.ic_bug_report_black_24dp,
                R.drawable.ic_fingerprint_black_24dp,
                R.drawable.ic_language_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_motorcycle_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_record_voice_over_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_perm_identity_black_24dp,
                R.drawable.ic_perm_identity_black_24dp};

        String[] titles = {"dummy1", "dummy", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7","dummy1", "dummy", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7","dummy1", "dummy", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7","dummy1", "dummy", "dummy3", "dummy4", "dummy5", "dummy6", "dummy7"};

        for (int counter = 0; counter < titles.length && counter < icons.length; counter++) {
            Informationn current = new Informationn();
            current.iconId = icons[counter];
            current.title = titles[counter];
            data.add(current);
        }
        return data;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addWordButton:
                startAddWord();
                 break;
            case R.id.startGameButton:

                startTriggerWordGame();
                 break;
        }
    }

    public void startTriggerWordGame() {
        Intent newIntent = new Intent(this, interactiveSession.class);
        startActivity(newIntent);
       // Intent newIntent = new Intent(this, DisplayAnswerActivity.class);
        //startActivity(newIntent);
    }

    public void startAddWord() {
        Intent newIntent = new Intent(this, WordActivity.class);
        startActivity(newIntent);
    }
}
