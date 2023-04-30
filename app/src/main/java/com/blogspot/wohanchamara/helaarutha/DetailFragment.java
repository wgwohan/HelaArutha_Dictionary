package com.blogspot.wohanchamara.helaarutha;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;



public class DetailFragment extends Fragment {

    private String value;
    private ImageButton btnEdit, btnVolume, btnBookmark;
    private TextView tvWord, tvWordTranslate;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment getNewInstance(String value){
        DetailFragment fragment = new DetailFragment();
        fragment.value = value;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        tvWord = view.findViewById(R.id.tvWord);
        tvWordTranslate = view.findViewById(R.id.tvWordTranslate);
        String word, mean;

        Bundle bundle = getArguments();
        if (bundle.getString("WORD")==null || bundle.getString("MEAN")==null){
            word = "My Word";
            mean = "My Mean";
        } else {
            word = bundle.getString("WORD");
            mean = bundle.getString("MEAN");
        }

        tvWord.setText(word);
        tvWordTranslate.setText(mean);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        /*tvWord = (TextView) view.findViewById(R.id.tvWord);
        tvWordTranslate = view.findViewById(R.id.tvWordTranslate);

        btnBookmark.setTag(0);

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (int)btnBookmark.getTag();
                if (i==0){
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_fill);
                    btnBookmark.setTag(1);
                } else if (i==1){
                    btnBookmark.setImageResource(R.drawable.ic_bookmark_border);
                    btnBookmark.setTag(0);
                }
            }
        });*/

        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("WRONGWORD", tvWord.getText().toString());
                bundle.putString("WRONGMEAN", tvWordTranslate.getText().toString());
                EditWordFragment editWordFragment = new EditWordFragment();
                editWordFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,editWordFragment).commit();
            }
        });
    }
}