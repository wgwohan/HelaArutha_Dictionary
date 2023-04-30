package com.blogspot.wohanchamara.helaarutha;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewFragment extends Fragment {

    EditText newWord, newWordMean;
    Button btnSend;

    public AddNewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        newWord = view.findViewById(R.id.newWord);
        newWordMean = view.findViewById(R.id.newWordMean);
        btnSend = view.findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newWord.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "කරුණාකර වචනය ඇතුලත් කරන්න.", Toast.LENGTH_SHORT).show();
                } else if (newWordMean.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "කරුණාකර වචනයේ අර්ථය ඇතුලත් කරන්න.", Toast.LENGTH_SHORT).show();
                } else {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                final Intent chooserg;
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"wgwchamara@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Add new Word to Hela Arutha App");
                intent.putExtra(Intent.EXTRA_TEXT, "වචනය: "+newWord.getText().toString()+"\n\n"+"අර්ථය: "+newWordMean.getText().toString());
                //intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"));
                chooserg = Intent.createChooser(intent,"Send email");
                startActivity(chooserg);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }
}