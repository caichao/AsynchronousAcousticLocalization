package hust.cc.asynchronousacousticlocalization.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hust.cc.asynchronousacousticlocalization.R;
import hust.cc.asynchronousacousticlocalization.utils.FlagVar;

public class SettingActivity extends AppCompatActivity{

    @BindView(R.id.mic_group)
    RadioGroup micGroup;
    @BindView(R.id.max_avg_bar)
    SeekBar maxAvgRatioBar;
    @BindView(R.id.mar_ratio_txt)
    TextView marRatioText;
    @BindView(R.id.ratio_bar)
    SeekBar ratioBar;
    @BindView(R.id.radio_txt)
    TextView ratioText;
    @BindView(R.id.preamble_detect_group)
    RadioGroup preambleDetectionTypeGroup;
    @BindView(R.id.mic_down)
    RadioButton micDownButton;
    @BindView(R.id.mic_up)
    RadioButton micUpButton;
    @BindView(R.id.detect1)
    RadioButton detectButton1;
    @BindView(R.id.detect2)
    RadioButton detectButton2;
    @BindView(R.id.detect3)
    RadioButton detectButton3;
    @BindView(R.id.detect4)
    RadioButton detectButton4;
    @BindView(R.id.reset_settings)
    Button resetButton;
    @BindView(R.id.speed_offset)
    EditText offsetEdit;
    @BindView(R.id.setting_linear)
    LinearLayout settingLinear;

    private int micUsed;
    private int preambleDetectionType;
    private int maxAvgRatio;
    private int ratio;
    private int speedOffset;
    private SharedPreferences sharedPreferences;
    private String runningSettingsStr = "RUNNING_SETTINGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_running_setting);
        ButterKnife.bind(this);
        initSettings();

    }

    public void initSettings(){
        micGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(R.id.mic_up == i){
                    micUsed = FlagVar.MIC_UP;
                }else if(R.id.mic_down == i){
                    micUsed = FlagVar.MIC_DOWN;
                }
                saveSettings();
            }
        });
        preambleDetectionTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.detect1:{
                        preambleDetectionType = FlagVar.DETECT_TYPE1;
                        break;
                    }
                    case R.id.detect2:{
                        preambleDetectionType = FlagVar.DETECT_TYPE2;
                        break;
                    }
                    case R.id.detect3:{
                        preambleDetectionType = FlagVar.DETECT_TYPE3;
                        break;
                    }
                    case R.id.detect4:{
                        preambleDetectionType = FlagVar.DETECT_TYPE4;
                        break;
                    }
                }
                saveSettings();
            }
        });

        maxAvgRatioBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                maxAvgRatio = i;
                marRatioText.setText(Integer.toString(maxAvgRatio));
                saveSettings();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ratioBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ratio = i;
                ratioText.setText(Integer.toString(ratio));
                saveSettings();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                micUsed = FlagVar.micUsed;
                preambleDetectionType = FlagVar.preambleDetectionType;
                maxAvgRatio = (int)(FlagVar.maxAvgRatioThreshold*10);
                ratio = (int)(FlagVar.ratioThreshold*10);
                speedOffset = FlagVar.speedOffset;

                loadSettings();
            }
        });

        offsetEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int val = Integer.parseInt(offsetEdit.getText().toString());
                if(hasFocus == false){
                    speedOffset = val;
                }
                saveSettings();

            }
        });

        settingLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                settingLinear.setFocusable(true);
                settingLinear.setFocusableInTouchMode(true);
                settingLinear.requestFocus();
                InputMethodManager imm = (InputMethodManager) SettingActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return false;

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(runningSettingsStr, Context.MODE_PRIVATE);
        micUsed = sharedPreferences.getInt("micUsed",FlagVar.micUsed);
        preambleDetectionType = sharedPreferences.getInt("preambleDetectionType",FlagVar.preambleDetectionType);
        maxAvgRatio = sharedPreferences.getInt("maxAvgRatio",(int)(FlagVar.maxAvgRatioThreshold*10));
        ratio = sharedPreferences.getInt("ratio",(int)(FlagVar.ratioThreshold*10));
        speedOffset = sharedPreferences.getInt("speedOffset",FlagVar.speedOffset);
        loadSettings();
    }

    private void saveSettings(){
        SharedPreferences setting = getSharedPreferences(runningSettingsStr,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("micUsed",micUsed);
        editor.putInt("preambleDetectionType",preambleDetectionType);
        editor.putInt("maxAvgRatio",maxAvgRatio);
        editor.putInt("ratio",ratio);
        editor.putInt("speedOffset",speedOffset);
        editor.apply();
    }

    private void loadSettings(){
        maxAvgRatioBar.setProgress(maxAvgRatio);
        marRatioText.setText(Integer.toString(maxAvgRatio));
        ratioBar.setProgress(ratio);
        ratioText.setText(Integer.toString(ratio));
        switch (micUsed){
            case FlagVar.MIC_UP:{
                micUpButton.setChecked(true);
                break;
            }
            case FlagVar.MIC_DOWN:{
                micDownButton.setChecked(true);
                break;
            }
        }

        switch (preambleDetectionType){
            case FlagVar.DETECT_TYPE1:{
                detectButton1.setChecked(true);
                break;
            }
            case FlagVar.DETECT_TYPE2:{
                detectButton2.setChecked(true);
                break;
            }
            case FlagVar.DETECT_TYPE3:{
                detectButton3.setChecked(true);
                break;
            }
            case FlagVar.DETECT_TYPE4:{
                detectButton4.setChecked(true);
                break;
            }
        }
        offsetEdit.setText(""+speedOffset);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent();
            intent.putExtra("result", "test");
            intent.putExtra("micUsed",micUsed);
            intent.putExtra("preambleDetectionType",preambleDetectionType);
            intent.putExtra("maxAvgRatio",maxAvgRatio);
            intent.putExtra("ratio",ratio);
            intent.putExtra("speedOffset",speedOffset);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
