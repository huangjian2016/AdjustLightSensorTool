package com.example.huangjian.adjustlightsensortool;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    final String TAG ="adjustlightsensortool";
    TextView mTextViewShow;
    Button mButtonRecord,mButtonCalcaulate;
    EditText mEditText;
    TextView mTextViewHelp;
    float /*int*/[][] mLightSensorData = new float[2][2];
    static int mRecordFalg, mCheckRecordFlag;
    float mLightSensorCurveRatio ;
    float mLightSensorCurveRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adjustlightsensortool);
        mTextViewShow =(TextView)findViewById(R.id.mtextViewshow);
        mTextViewHelp = (TextView)findViewById(R.id.mHelpText);
        mEditText = (EditText)findViewById(R.id.mEditText);
        mButtonRecord = (Button)findViewById(R.id.mButton01);
        mButtonCalcaulate = (Button)findViewById(R.id.mButton02);

        mTextViewHelp.setText("操作说明\n\n①光源置为100nit，点击【记录】按钮\n②光源置为200nit，点击【记录】按钮\n" +
                "③点击【计算】按钮\n④显示环境光补偿系数等信息\n⑤");
        mTextViewHelp.setTextSize(20);

        mRecordFalg = 0;
        mCheckRecordFlag = 0;

        //EditText 中的默认光标位置到最后
        mEditText.setSelection(mEditText.getText().toString().length());
        mButtonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mRecordFalg % 2 == 0 ){
                    mLightSensorData[0][0] = Integer.parseInt(mEditText.getText().toString());
                    mLightSensorData[0][1] = 100; //调用获取lightsersor接口获取
                    Log.i(TAG,"Record lightsensor input 1111 data:"+mLightSensorData[0][0]+"input times:"+mRecordFalg);
                }else if(mRecordFalg % 2 == 1){
                    mLightSensorData[1][0] = Integer.parseInt(mEditText.getText().toString());
                    mLightSensorData[1][1] = 300; //调用获取lightsersor接口获取
                    Log.i(TAG,"Record lightsensor input 2222 data:"+mLightSensorData[1][0]+"input times:"+mRecordFalg);
                }
                mRecordFalg ++;
                mCheckRecordFlag++;

//                mLightSensorCurveRatio = (mLightSensorData[1][1] - mLightSensorData[0][0]) /(mLightSensorData[1][0]-mLightSensorData[0][0]);
//                mLightSensorCurveRange = mLightSensorData[1][1] - 2 * mLightSensorData[0][1];
//                Log.i(TAG,"3333 Calcaulate result:mLightSensorCurveRatio"+mLightSensorCurveRatio+"mLightSensorCurveRange"+mLightSensorCurveRange);

                Log.i(TAG,"have record times mRecordFalg:"+mRecordFalg+"mCheckRecordFlag:"+mCheckRecordFlag);
                Toast.makeText(getApplicationContext(),"第"+mRecordFalg+"次输入内容已经记录OK！请输入下一个光照量",Toast.LENGTH_LONG).show();
            }
        });


        mButtonCalcaulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckRecordFlag % 2 == 0) {
                    //ratio = y2-y1/x2-x1
                    mLightSensorCurveRatio = (mLightSensorData[1][1] - mLightSensorData[0][1]) /(mLightSensorData[1][0]-mLightSensorData[0][0]);
                    //range = y2- 2*y1
                    mLightSensorCurveRange = mLightSensorData[1][1] - 2 * mLightSensorData[0][1];
                    Log.i(TAG,"3333 Calcaulate result:mLightSensorCurveRatio"+mLightSensorCurveRatio+"mLightSensorCurveRange"+mLightSensorCurveRange);

                    mTextViewShow.setText("光照/光强接收量 记录1 :" + mLightSensorData[0][0] + "--" + mLightSensorData[0][1] + "\n\n"
                            + "光照/光强接收量 记录2 :" + mLightSensorData[1][0] + "--" + mLightSensorData[1][1] + "\n\n"
                            + "光照补偿系数：" + mLightSensorCurveRatio + "\n\n"
                            + "光照幅度补偿系数：" + mLightSensorCurveRange + "\n\n");

                    Log.i(TAG, "4444 计算OK" + "\n");
                } else {
                    mTextViewShow.setText("");//clear TextView Content
                    Toast.makeText(getApplicationContext(), "请连续记录两次光照量！！mCheckRecordFlag "+mCheckRecordFlag , Toast.LENGTH_LONG).show();
                }
            }

        });

    }

//    public void showToast(){
//        Toast toast = Toast.makeText(getApplicationContext(),"你好，输入内容已经记录OK！请输入下一个光照量",Toast.LENGTH_LONG).show();
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
