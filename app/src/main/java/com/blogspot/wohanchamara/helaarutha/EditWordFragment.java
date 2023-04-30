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
import android.widget.TextView;
import android.widget.Toast;


public class EditWordFragment extends Fragment {

    TextView wrongWord, wrongWordMean;
    EditText editWord, editWordMean;
    Button btnSend;

    public EditWordFragment() {
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

        wrongWord = view.findViewById(R.id.wrongWord);
        editWord = view.findViewById(R.id.editWord);
        wrongWordMean = view.findViewById(R.id.wrongWordMean);
        editWordMean = view.findViewById(R.id.editWordMean);
        btnSend = view.findViewById(R.id.btnSend);

        String word, mean;

        Bundle bundle = getArguments();
        if (bundle.getString("WRONGWORD")==null || bundle.getString("WRONGMEAN")==null){
            word = "My Word";
            mean = "My Mean";
        } else {
            word = bundle.getString("WRONGWORD");
            mean = bundle.getString("WRONGMEAN");
        }

        wrongWord.setText(word);
        wrongWordMean.setText(mean);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editWord.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "කරුණාකර නිවැරදි වචනය ඇතුලත් කරන්න.", Toast.LENGTH_SHORT).show();
                } else if (editWordMean.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "කරුණාකර වචනයේ නිවැරදි අර්ථය ඇතුලත් කරන්න.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    final Intent chooserg;
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"wgwchamara@gmail.com"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Edit Word in Hela Arutha App");
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "වැරදි වචනය: "+wrongWord.getText().toString()+
                                    "\nනිවැරදි විය යුතු ආකාරය: "+editWord.getText().toString()+
                                    "\n\nවැරදි අර්ථය: "+wrongWordMean.getText().toString()+
                                    "\nනිවැරදි විය යුතු ආකාරය: "+editWordMean.getText().toString());
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
        return inflater.inflate(R.layout.fragment_edit_word, container, false);

    }
}