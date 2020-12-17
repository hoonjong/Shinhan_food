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

import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    DatePicker mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDate = findViewById(R.id.datePicker);
        View1 = findViewById(R.id.tv1);


        Intent intent = getIntent();
        String receiveStr = intent.getExtras().getString("Text1");// 전달한 값을 받을 때
        View1 = findViewById(R.id.tv1);
        View1.setText(receiveStr);


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
                    //Toast.makeText(MainActivity.this, "테스트입니다", Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    String receiveStr = intent.getExtras().getString("Text1");// 전달한 값을 받을 때
                    View1 = findViewById(R.id.tv1);
                    View1.setText(receiveStr);
                }
            });

    } //oncreate 끝

    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //or switch문도 이용가능^^
        if (id == R.id.action_chat) {
            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("plain/text");
            // email setting 배열로 해서 복수 발송 가능
            String[] address = {"qkr7627@gmail.com"};
            email.putExtra(Intent.EXTRA_EMAIL, address);
            email.putExtra(Intent.EXTRA_SUBJECT, "개발자님 문의합니다.");  //Gmail 제목 고정
            email.putExtra(Intent.EXTRA_TEXT, "내용\n"); // 내용 고정
            startActivity(email);
            return true;
        }
        if (id == R.id.action_share) {
            Intent msg = new Intent(Intent.ACTION_SEND);
            msg.addCategory(Intent.CATEGORY_DEFAULT);
            msg.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.jongdroid.shinhan_food");
            msg.putExtra(Intent.EXTRA_TITLE, "제목");
            msg.setType("text/plain");
            startActivity(Intent.createChooser(msg, "앱을 선택해 주세요"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected의 끝
}

