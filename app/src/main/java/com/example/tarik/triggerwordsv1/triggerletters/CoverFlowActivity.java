package com.example.tarik.triggerwordsv1.triggerletters;

/**
 * Created by huanghe on 5/05/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

import com.example.tarik.triggerwordsv1.R;
import com.example.tarik.triggerwordsv1.ActionMenu.interactMenu;


public class CoverFlowActivity extends ActionBarActivity implements TextToSpeech.OnInitListener{

    private FeatureCoverFlow mCoverFlow;
    private TextSwitcher mTitle;
    // private ProgressBar progressBar;
    private letterAdapter mAdapter;
    private List<letter1> mData = new ArrayList<>(0);
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;
    private String readthing;
    private String readingSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverflow);

//        mData.add(new GameEntity(R.drawable.image_1, R.string.title1));
//        mData.add(new GameEntity(R.drawable.image_2, R.string.title2));
//        mData.add(new GameEntity(R.drawable.image_3, R.string.title3));
//        mData.add(new GameEntity(R.drawable.image_4, R.string.title4));

        mTitle = (TextSwitcher) findViewById(R.id.title);
        //   progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(CoverFlowActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        initLetters();


        LayoutInflater inflater = getLayoutInflater();

        final letterAdapter mAdapter = new letterAdapter(mData, inflater, this);


        //mAdapter = new letterAdapter(this);
        //mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);


        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readthing = mData.get(position).toString();
                if (readthing == null || readthing.trim().equals("")) {

                } else if (myTTS.isSpeaking()) {
                    myTTS.stop();
                } else {
                    speakWords(readthing);
                }
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {

                mTitle.setText(mData.get(position).getLetterup());
                readingSave = mData.get(position).getLetterup();
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar18);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
  @Override
   public void onBackPressed(){
    Intent setIntent = new Intent(this, interactMenu.class);
    startActivity(setIntent);

}


    private void initLetters() {
        letterAccess letterAccess = com.example.tarik.triggerwordsv1.triggerletters.letterAccess.getInstance(this);
        letterAccess.open();
        mData = letterAccess.getLettersAll();
    }

    //read word

    private void speakWords(String speech) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId");

        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, map);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            myTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                    //    progressBar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onDone(String utteranceId) {
                    CoverFlowActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //              progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }

                @Override
                public void onError(String utteranceId) {
                    //voice.setImageResource(R.drawable.voice);

                }
            });
            if (myTTS.isLanguageAvailable(Locale.ENGLISH) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.ENGLISH);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Reading story failed...", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(myTTS != null) {
            myTTS.shutdown();
        }
    }




}
