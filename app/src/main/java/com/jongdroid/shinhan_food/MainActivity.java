package com.jongdroid.shinhan_food;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import android.widget.Toast;


public class MainActivity extends Activity {

    DatePicker mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDate = findViewById(R.id.datePicker);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        //getSupportActionBar().setTitle("ACTIONBAR");
        //액션바 배경색 변경
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //액션바 숨기기
        //hideActionBar();


        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {

                        // TODO Auto-generated method stub


                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.

                        Toast.makeText(MainActivity.this, "테스트입니다", Toast.LENGTH_LONG).show();


                    }

                });
    }
}