package com.example.tarik.triggerwordsv1.diary;

/**
 * Created by huanghe on 5/04/2017.
 */
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;


import com.example.tarik.triggerwordsv1.CustomOnItemSelectedListener;
import com.example.tarik.triggerwordsv1.DateTime2.SublimePickerFragment;
import com.example.tarik.triggerwordsv1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class SendCalendar extends AppCompatActivity {

    private static final String TAG = "SendCalendar";
    private Spinner calendarIdSpinner;
    private Hashtable<String,String> calendarIdTable;
    private final int INVALID_VAL = -1;

    // Launches SublimePicker
    ImageView ivLaunchPicker;

    CheckBox checkBox;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    EditText editText;
    ArrayList<String> list ;
    ImageButton mbutton7;


    String description ="";
    TextView tvYear, tvMonth, tvDay, tvHour,
            tvMinute,
            tvStartDate, tvEndDate;
    RelativeLayout rlDateTimeRecurrenceInfo;
    LinearLayout llDateHolder, llDateRangeHolder;

    // Chosen values
    SelectedDate mSelectedDate;
    int mHour, mMinute;


    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {
            rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            mSelectedDate = selectedDate;
            mHour = hourOfDay;
            mMinute = minute;

            updateInfoView();


        }
    };


    //Button button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);


        ivLaunchPicker = (ImageView) findViewById(R.id.ivLaunchPicker);
        llDateHolder = (LinearLayout) findViewById(R.id.llDateHolder);
        llDateRangeHolder = (LinearLayout) findViewById(R.id.llDateRangeHolder);
        tvYear = ((TextView) findViewById(R.id.tvYear));
        tvMonth = ((TextView) findViewById(R.id.tvMonth));
        tvDay = ((TextView) findViewById(R.id.tvDay));

        tvStartDate = ((TextView) findViewById(R.id.tvStartDate));
        tvEndDate = ((TextView) findViewById(R.id.tvEndDate));

        tvHour = ((TextView) findViewById(R.id.tvHour));
        tvMinute = ((TextView) findViewById(R.id.tvMinute));
        rlDateTimeRecurrenceInfo
                = (RelativeLayout) findViewById(R.id.rlDateTimeRecurrenceInfo);
        ivLaunchPicker.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        ivLaunchPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DialogFragment to host SublimePicker
                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);

                // Options


                // Valid options
                Bundle bundle = new Bundle();

                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");
            }
        });
        dealWithSavedInstanceState(savedInstanceState);





        mbutton7 = (ImageButton)findViewById(R.id.imageButton7);
        calendarIdSpinner = (Spinner) findViewById(R.id.calendarid_spinner);


        addListenerButton();
        editText = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);


        list = new ArrayList<String>();

        calendarIdSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        if (CalendarHelper.haveCalendarReadWritePermissions(this))
        {
            //Load calendars
            calendarIdTable = CalendarHelper.listCalendarId(this);

            updateCalendarIdSpinner();

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar13);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void dealWithSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {


        } else {

            if (savedInstanceState.getBoolean(SS_INFO_VIEW_VISIBILITY)) {
                int startYear = savedInstanceState.getInt(SS_START_YEAR);

               if (startYear != INVALID_VAL) {
                    Calendar startCal = Calendar.getInstance();
                    startCal.set(startYear, savedInstanceState.getInt(SS_START_MONTH),
                            savedInstanceState.getInt(SS_START_DAY));

                    Calendar endCal = Calendar.getInstance();
                    endCal.set(savedInstanceState.getInt(SS_END_YEAR),
                            savedInstanceState.getInt(SS_END_MONTH),
                            savedInstanceState.getInt(SS_END_DAY));
                    mSelectedDate = new SelectedDate(startCal, endCal);
                }

                mHour = savedInstanceState.getInt(SS_HOUR);
                mMinute = savedInstanceState.getInt(SS_MINUTE);

                updateInfoView();
            }


            // Set callback
            SublimePickerFragment restoredFragment = (SublimePickerFragment)
                    getSupportFragmentManager().findFragmentByTag("SUBLIME_PICKER");
            if (restoredFragment != null) {
                restoredFragment.setCallback(mFragmentCallback);
            }
        }
    }

    private void updateInfoView() {
        if (mSelectedDate != null) {
            if (mSelectedDate.getType() == SelectedDate.Type.SINGLE) {
                llDateRangeHolder.setVisibility(View.GONE);
                llDateHolder.setVisibility(View.VISIBLE);

                tvYear.setText(applyBoldStyle("YEAR: ")
                        .append(String.valueOf(mSelectedDate.getStartDate().get(Calendar.YEAR))));
                tvMonth.setText(applyBoldStyle("MONTH: ")
                        .append(String.valueOf(mSelectedDate.getStartDate().get(Calendar.MONTH)+1)));
                tvDay.setText(applyBoldStyle("DAY: ")
                        .append(String.valueOf(mSelectedDate.getStartDate().get(Calendar.DAY_OF_MONTH))));
            }
            /*else if (mSelectedDate.getType() == SelectedDate.Type.RANGE) {
                llDateHolder.setVisibility(View.GONE);
                llDateRangeHolder.setVisibility(View.VISIBLE);

                tvStartDate.setText(applyBoldStyle("START: ")
                        .append(DateFormat.getDateInstance().format(mSelectedDate.getStartDate().getTime())));
                tvEndDate.setText(applyBoldStyle("END: ")
                        .append(DateFormat.getDateInstance().format(mSelectedDate.getEndDate().getTime())));
            }*/
        }

        tvHour.setText(applyBoldStyle("HOUR: ").append(String.valueOf(mHour)));
        tvMinute.setText(applyBoldStyle("MINUTE: ").append(String.valueOf(mMinute)));

        rlDateTimeRecurrenceInfo.setVisibility(View.VISIBLE);
    }

    // Applies a StyleSpan to the supplied text
    private SpannableStringBuilder applyBoldStyle(String text) {
        SpannableStringBuilder ss = new SpannableStringBuilder(text);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    // Keys for saving state

    final String SS_START_YEAR = "saved.state.start.year";
    final String SS_START_MONTH = "saved.state.start.month";
    final String SS_START_DAY = "saved.state.start.day";
    final String SS_END_YEAR = "saved.state.end.year";
   final String SS_END_MONTH = "saved.state.end.month";
    final String SS_END_DAY = "saved.state.end.day";
    final String SS_HOUR = "saved.state.hour";
    final String SS_MINUTE = "saved.state.minute";
    final String SS_INFO_VIEW_VISIBILITY = "saved.state.info.view.visibility";


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        int startYear = mSelectedDate != null ? mSelectedDate.getStartDate().get(Calendar.YEAR) : INVALID_VAL;
        int startMonth = mSelectedDate != null ? mSelectedDate.getStartDate().get(Calendar.MONTH) : INVALID_VAL;
        int startDayOfMonth = mSelectedDate != null ? mSelectedDate.getStartDate().get(Calendar.DAY_OF_MONTH) : INVALID_VAL;

       int endYear = mSelectedDate != null ? mSelectedDate.getEndDate().get(Calendar.YEAR) : INVALID_VAL;
        int endMonth = mSelectedDate != null ? mSelectedDate.getEndDate().get(Calendar.MONTH) : INVALID_VAL;
       int endDayOfMonth = mSelectedDate != null ? mSelectedDate.getEndDate().get(Calendar.DAY_OF_MONTH) : INVALID_VAL;

        // Save data
        outState.putInt(SS_START_YEAR, startYear);
        outState.putInt(SS_START_MONTH, startMonth);
        outState.putInt(SS_START_DAY, startDayOfMonth);
        outState.putInt(SS_END_YEAR, endYear);
        outState.putInt(SS_END_MONTH, endMonth);
        outState.putInt(SS_END_DAY, endDayOfMonth);
        outState.putInt(SS_HOUR, mHour);
        outState.putInt(SS_MINUTE, mMinute);

        outState.putBoolean(SS_INFO_VIEW_VISIBILITY,
                rlDateTimeRecurrenceInfo.getVisibility() == View.VISIBLE);




        super.onSaveInstanceState(outState);
    }

    public void addListenerButton(){



        mbutton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (String str : list) {

                    description += str + ",";
                }

                if (CalendarHelper.haveCalendarReadWritePermissions(SendCalendar.this))
                {
                    try {
                        addNewEvent();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    }
                    if (checkBox2.isChecked()) {
                        checkBox2.setChecked(false);
                    }
                    if (checkBox3.isChecked()) {
                        checkBox3.setChecked(false);
                    }
                    if (checkBox4.isChecked()) {
                        checkBox4.setChecked(false);
                    }
                    list.clear();

                    description = "";



                }
                else
                {
                    CalendarHelper.requestCalendarReadWritePermission(SendCalendar.this);
                }
            }
        });
    }





    public void onCheckboxClicked(View view) {

        //boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkBox:
               list.add(checkBox.getText().toString());


                break;
            case R.id.checkBox2:
               list.add(checkBox2.getText().toString());

                break;

            case R.id.checkBox3:
                list.add(checkBox3.getText().toString());

                break;
            case R.id.checkBox4:
                list.add(checkBox4.getText().toString());

                break;
        }
    }

    private void updateCalendarIdSpinner()
    {
        if (calendarIdTable==null)
        {
            return;
        }

        List<String> list = new ArrayList<String>();

        Enumeration e = calendarIdTable.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            list.add(key);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calendarIdSpinner.setAdapter(dataAdapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode==CalendarHelper.CALENDARHELPER_PERMISSION_REQUEST_CODE)
        {
            if (CalendarHelper.haveCalendarReadWritePermissions(this))
            {
                Toast.makeText(this, (String)"Have Calendar Read/Write Permission.",
                        Toast.LENGTH_LONG).show();

            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void addNewEvent() throws ParseException {
        if (calendarIdTable==null)
        {
            Toast.makeText(this, (String)"No calendars found. Please ensure at least one google account has been added.",
                    Toast.LENGTH_LONG).show();
            //Load calendars
            calendarIdTable = CalendarHelper.listCalendarId(this);

            updateCalendarIdSpinner();

            return;
        }


        int startYear = mSelectedDate != null ? mSelectedDate.getStartDate().get(Calendar.YEAR) : INVALID_VAL;
        int startMonth = mSelectedDate != null ? mSelectedDate.getStartDate().get(Calendar.MONTH) : INVALID_VAL;
        int startDayOfMonth = mSelectedDate != null ? mSelectedDate.getStartDate().get(Calendar.DAY_OF_MONTH) : INVALID_VAL;




        final long oneHour = 1000 * 60 * 60;







        String dates = String.valueOf(startMonth+1)+"-"+String.valueOf(startDayOfMonth)+"-"+String.valueOf(startYear)+" "+String.valueOf(mHour)+":"+String.valueOf(mMinute)+":"+"00"+".999";
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.S");
        Date myDate = fmt.parse(dates);

        long now = (new Date()).getTime();

        long yyy = myDate.getTime() + oneHour;
        long xxx = myDate.getTime();
        Log.d("jhg", "hjg" + now);
        Log.d("qwe", "hjg" + xxx);



        String calendarString = calendarIdSpinner.getSelectedItem().toString();
        String title = editText.getText().toString();



        int calendar_id = Integer.parseInt(calendarIdTable.get(calendarString));

        CalendarHelper.MakeNewCalendarEntry(this, title, description, "Somewhere",xxx,yyy,false,true,calendar_id,3);

    }




}