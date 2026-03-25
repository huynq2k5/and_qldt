package vn.huy.quanlydaotao.ui.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import vn.huy.quanlydaotao.R;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageButton btnBack = findViewById(R.id.btnBackQuiz);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        ImageButton btnClose = findViewById(R.id.btnCloseQuiz);
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> finish());
        }
    }
}