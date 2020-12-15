package com.jongdroid.shinhan_food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        final EditText Text1;
        DatePicker mDate;
        Button btn1;
        Text1 = findViewById(R.id.et1);
        mDate = findViewById(R.id.datePicker);
        btn1 = findViewById(R.id.btn1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InputActivity.this, MainActivity.class);
                intent.putExtra("Text1", Text1.getText().toString());// 이 메서드를 통해 데이터를 전달합니다.
                startActivity(intent);
            }
        });


        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {

                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {

                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                        Intent intent = new Intent(InputActivity.this, MainActivity.class);
                        intent.putExtra("Text1", Text1.getText().toString());// 이 메서드를 통해 데이터를 전달합니다.
                        startActivity(intent);
                        //Edittext Text 지우기
                        Text1.setText(null);
                    }
                });


        }


    }



