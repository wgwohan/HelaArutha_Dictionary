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
import android.widget.ImageView;


public class AboutFragment extends Fragment {
    ImageView btnGmail;
    ImageView btnFb;
    ImageView btnlinkedIn;
    ImageView btnWeb;
    public AboutFragment() {
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

        btnFb = (ImageView) view.findViewById(R.id.btnFb);
        btnGmail = (ImageView) view.findViewById(R.id.btnGmail);
        btnlinkedIn = (ImageView) view.findViewById(R.id.btnlinkedIn);
        btnWeb = (ImageView) view.findViewById(R.id.btnWeb);

        btnFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.facebook.com/wohanpresents/");
            }
        });

        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentg = new Intent(Intent.ACTION_SENDTO);
                final Intent chooserg;
                intentg.putExtra(Intent.EXTRA_EMAIL,new String[]{"wgwchamara@gmail.com"});
                intentg.putExtra(Intent.EXTRA_SUBJECT, "Hela Arutha App");
                intentg.setData(Uri.parse("mailto:"));
                chooserg = Intent.createChooser(intentg,"Send email");
                startActivity(chooserg);
            }
        });

        btnlinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://www.linkedin.com/in/wohanchamara/");
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("https://wohanchamara.blogspot.com");
            }
        });
    }

    private void goToUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


}