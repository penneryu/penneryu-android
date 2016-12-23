package com.penner.android;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.penner.android.base.BaseActivity;
import com.penner.android.util.LogUtils;
import com.penner.android.view.emoji.AndroidEmoji;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmojiActivity extends BaseActivity {

    @BindView(R.id.emoji_edit_name)
    EditText emojiEdit;
    @BindView(R.id.emoji_txt)
    TextView emojiText;
    @BindView(R.id.emoji_btn)
    Button emojiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);
        ButterKnife.bind(this);

        emojiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editable = emojiEdit.getText();
                int size = editable.length();
                StringBuilder StringBuilder = new StringBuilder(size);
                for (int i = 0; i < size; i++) {
                    StringBuilder.append(Character.toChars(0x1F44D));
                }
                String value = StringBuilder.toString();
                CharSequence result = AndroidEmoji.ensure(value, EmojiActivity.this);
                emojiText.setText(result);
                LogUtils.d("emoji", result.toString());
            }
        });
    }
}
