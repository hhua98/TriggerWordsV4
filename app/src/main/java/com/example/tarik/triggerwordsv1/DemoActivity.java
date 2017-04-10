package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 5/04/2017.
 */

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RunnableFuture;


public class DemoActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {
    private static final String TAG = "DemoActivity";

    private SlidingUpPanelLayout mLayout;
    private ListView listView;
    private TextView content;
    private ScrollView contentScoll;
    private ImageView upanddown;
    private ImageButton voice;
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;
    private String readthing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        //Add a list View
        this.listView = (ListView) findViewById(R.id.main);
        StoryAccess storyAccess = StoryAccess.getInstance(this);
        storyAccess.open();
        List<Story> stories = storyAccess.getStories();
        storyAccess.close();

        ArrayAdapter<Story> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, stories);
        this.listView.setAdapter(adapter);

        content = (TextView) findViewById(R.id.content);
        contentScoll = (ScrollView) findViewById(R.id.contentScroll);
        voice = (ImageButton) findViewById(R.id.voice);
        upanddown = (ImageView) findViewById(R.id.upanddown);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(DemoActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
                Story story = (Story) parent.getAdapter().getItem(position);
                content.setText(story.getContent());
                mLayout.setPanelState(PanelState.EXPANDED);
                readthing = story.getContent();
                TextView titleText = (TextView) findViewById(R.id.name);
                titleText.setText(story.getTitle());
                if (myTTS.isSpeaking()) {
                    myTTS.stop();
                    voice.setImageResource(R.drawable.voice);
                }

            }
        });



        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));



        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, PanelState previousState, PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
                if (newState.equals(PanelState.EXPANDED) || newState.equals(PanelState.ANCHORED))
                {
                    upanddown.setImageResource(R.drawable.down);
                } else {
                    upanddown.setImageResource(R.drawable.up);
                }
            }


        });
        mLayout.setFadeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });



        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);


        voice.setOnClickListener(voiceListener);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.demo, menu);
//        MenuItem item = menu.findItem(R.id.action_toggle);
//        if (mLayout != null) {
//            if (mLayout.getPanelState() == PanelState.HIDDEN) {
//                item.setTitle(R.string.action_show);
//            } else {
//                item.setTitle(R.string.action_hide);
//            }
//        }
//        return true;
//    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_toggle: {
//                if (mLayout != null) {
//                    if (mLayout.getPanelState() != PanelState.HIDDEN) {
//                        mLayout.setPanelState(PanelState.HIDDEN);
//                        item.setTitle(R.string.action_show);
//                    } else {
//                        mLayout.setPanelState(PanelState.COLLAPSED);
//                        item.setTitle(R.string.action_hide);
//                    }
//                }
//                return true;
//            }
//            case R.id.action_anchor: {
//                if (mLayout != null) {
//                    if (mLayout.getAnchorPoint() == 1.0f) {
//                        mLayout.setAnchorPoint(0.7f);
//                        mLayout.setPanelState(PanelState.ANCHORED);
//                        item.setTitle(R.string.action_anchor_disable);
//                    } else {
//                        mLayout.setAnchorPoint(1.0f);
//                        mLayout.setPanelState(PanelState.COLLAPSED);
//                        item.setTitle(R.string.action_anchor_enable);
//                    }
//                }
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //Implement voice ouput


    View.OnClickListener voiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.voice:
//                        if (readthing2 == null) {
//                            speakWords(readthing);
//                            voice.setImageResource(R.drawable.stop2);
//                            readthing2 = readthing;
//                        } else if (readthing == readthing2 && myTTS.isSpeaking()) {
//                            myTTS.stop();
//                            voice.setImageResource(R.drawable.voice);
//                        } else if (readthing == readthing2 && !myTTS.isSpeaking()) {
//                            speakWords(readthing);
//                            voice.setImageResource(R.drawable.stop2);
//                        } else if (readthing != readthing2 && myTTS.isSpeaking()) {
//                            myTTS.stop();
//                            voice.setImageResource(R.drawable.voice);
//                            readthing2 = readthing;
//                        }
                    if (readthing == null || readthing.trim().equals("")) {
                        voice.setImageResource(R.drawable.voice);
                    } else if (myTTS.isSpeaking()) {
                        myTTS.stop();
                        voice.setImageResource(R.drawable.voice);
                    } else {
                        speakWords(readthing);
                        voice.setImageResource(R.drawable.stop2);
                    }
                    break;
                default:
                    break;
            }
        }
    };


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

                }

                @Override
                public void onDone(String utteranceId) {
                    DemoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            voice.setImageResource(R.drawable.voice);
                        }
                    });

                }

                @Override
                public void onError(String utteranceId) {
                    voice.setImageResource(R.drawable.voice);

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






    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();

        }
    }



}

