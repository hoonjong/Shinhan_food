package com.jongdroid.shinhan_food;


import android.os.Bundle;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.DatePicker;

import android.widget.Toast;


public class MainActivity extends Activity {

    DatePicker mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDate = findViewById(R.id.datePicker);




        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {

                        // TODO Auto-generated method stub


                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.

                        Toast.makeText(MainActivity.this, "테스트입니다" ,Toast.LENGTH_LONG).show();



                    }

                });
    }
}