package com.kovalenko.notetoself;

import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private boolean mSound;
    private int mAnimOption;

    public static final int FAST = 0;
    public static final int SLOW = 1;
    public static final int NONE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPrefs = getSharedPreferences("Note to self", MODE_PRIVATE);
        mEditor = mPrefs.edit();

        mSound = mPrefs.getBoolean("sound", true);
        CheckBox checkBoxSound = (CheckBox) findViewById(R.id.checkBoxSound);

        if (mSound) {
            checkBoxSound.setChecked(true);
        } else {
            checkBoxSound.setChecked(false);
        }

        checkBoxSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSound = !mSound;
                mEditor.putBoolean("sound", mSound);
            }
        });

        mAnimOption = mPrefs.getInt("anim option", FAST);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        switch (mAnimOption) {
            case FAST:
                radioGroup.check(R.id.radioFast);
                break;
            case SLOW:
                radioGroup.check(R.id.radioSlow);
                break;
            case NONE:
                radioGroup.check(R.id.radioNone);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb = radioGroup.findViewById(i);

                if (null != rb && i > -1) {
                    switch (rb.getId()){
                        case R.id.radioFast:
                            mAnimOption = FAST;
                            break;
                        case R.id.radioSlow:
                            mAnimOption = SLOW;
                            break;
                        case R.id.radioNone:
                            mAnimOption = NONE;
                            break;
                    }

                    mEditor.putInt("anim option", mAnimOption);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        mEditor.commit();
    }
}
