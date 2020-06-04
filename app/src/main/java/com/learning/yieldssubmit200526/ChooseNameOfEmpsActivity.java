package com.learning.yieldssubmit200526;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.learning.utils.BaseActivity;

public class ChooseNameOfEmpsActivity extends BaseActivity {
    private Button back_to_submit_Activity_btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_name_of_emps);
        this.back_to_submit_Activity_btn = (Button)super.findViewById(R.id.back_to_submit_Activity_btn);
        this.back_to_submit_Activity_btn.setOnClickListener(new backToSubmit_OnClickListenerImpl());
    }

    private class backToSubmit_OnClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ChooseNameOfEmpsActivity.this,SubmitYields.class);
            startActivity(intent);
            finish();
        }
    }
}
