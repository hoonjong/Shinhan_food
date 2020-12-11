package com.jongdroid.shinhan_food;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.Toast;


public class MainActivity extends Activity {

    DatePicker mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDate = findViewById(R.id.datePicker);


        //버튼 설정 및 이벤트 처리
        Button button = findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mCount 통해서 버튼 클릭 횟수를 저장
                mCount++;
                //버튼 4번 클릭시 다음 이벤트를 실행
                if (mCount == 4) {
                    Toast.makeText(getApplicationContext(), "관리자 페이지로 넘어갑니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                    startActivity(intent);
                } else if (mCount == 3) {
                    Toast.makeText(getApplicationContext(), "3번 클릭하셨습니다.", Toast.LENGTH_SHORT).show();
                } else if (mCount > 4) {
                    mCount = 1;
                }
            }
        });


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

                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                        Toast.makeText(MainActivity.this, "테스트입니다", Toast.LENGTH_LONG).show();
                    }
                });
    }
}