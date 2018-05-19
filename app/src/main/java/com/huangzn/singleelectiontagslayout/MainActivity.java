package com.huangzn.singleelectiontagslayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.huangzn.tagslayout.SingleElectionTagsLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SingleElectionTagsLayout tagsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagsLayout = findViewById(R.id.category_tags);
        tagsLayout.setTags(mockData());
        tagsLayout.setOnItemClickListener(new SingleElectionTagsLayout.OnItemClickListener() {
            @Override
            public void onClick(int position, String tagText) {
                Toast.makeText(getApplicationContext(), "clicked item position: " + position + ", tagText: " + tagText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> mockData() {
        List<String> data = new ArrayList<>();
        data.add("All");
        data.add("sample1");
        data.add("sample2");
        data.add("sample3");
        data.add("sample4");
        data.add("sample5");
        data.add("sample6");
        return data;
    }
}
