package com.blogspot.wohanchamara.helaarutha;

import static com.blogspot.wohanchamara.helaarutha.MyDatabaseHelper.COLUMN_MEAN;
import static com.blogspot.wohanchamara.helaarutha.MyDatabaseHelper.COLUMN_NAME;
import static com.blogspot.wohanchamara.helaarutha.MyDatabaseHelper.TABLE_NAME;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class DictionaryFragment extends Fragment implements RecyclerViewClickInterface{

    private FragmentListner listner;
    ListView dicList;
    ArrayAdapter<String> arrayAdapter;

    MyDatabaseHelper myDB;
    ArrayList<String> allWordsList = new ArrayList<>();
    ArrayList<String> allMeaningsList = new ArrayList<>();
    ArrayList<String> idsList = new ArrayList<>();
    ArrayList<String> wordsList = new ArrayList<>();
    ArrayList<String> meaningsList = new ArrayList<>();
    CustomAdapter customAdapter;

    SendDataInterface sendDataInterface;

    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDB = new MyDatabaseHelper(getContext());
//        getAllWords();
    }

    void getAllWords() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(requireContext(), "There is no Data", Toast.LENGTH_SHORT).show();
        } else while (cursor.moveToNext()) {
            /*idList.add(cursor.getString(0));
            wordList.add(cursor.getString(1));
            meanList.add(cursor.getString(2));*/
            allWordsList.add(cursor.getString(1));
            allMeaningsList.add(cursor.getString(2));
        }
        wordsList.addAll(allWordsList);
        meaningsList.addAll(allMeaningsList);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        getAllWords();
        initWordSearching(view);
    }

    private void initWordSearching(View fragmentView){

        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        customAdapter = new CustomAdapter(idsList, wordsList, meaningsList, this);
        recyclerView.setAdapter(customAdapter);

        HandlerThread thread = new HandlerThread("dictionary_thread");
        thread.start();
        Handler searchWordsHandler = new Handler(thread.getLooper());
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        EditText searchBar = requireActivity().findViewById(R.id.edit_search);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String word = s.toString().trim().toLowerCase();
                wordsList.clear();
                meaningsList.clear();

                if (word.isEmpty()) {
                    wordsList.addAll(allWordsList);
                    meaningsList.addAll(allMeaningsList);
                    refreshData(mainThreadHandler);
                } else { // search words
                    searchWordsHandler.postDelayed(new Runnable() {
                        @SuppressLint("Range")
                        @Override
                        public void run() {
                            Cursor cursor = myDB.query(TABLE_NAME, new String[]{COLUMN_NAME, COLUMN_MEAN},
                                    COLUMN_NAME + " LIKE ?", new String[]{word + "%"}, null, null,
                                    "LENGTH(" + COLUMN_NAME + ")", "20");
                            if (cursor.moveToFirst()) {
                                do {
                                    wordsList.add(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                                    meaningsList.add(cursor.getString(cursor.getColumnIndex(COLUMN_MEAN)));
                                } while (cursor.moveToNext());
                            }
                            refreshData(mainThreadHandler);
                        }
                    }, 30);
                }
            }
        });
    }

    private void refreshData(Handler mainThreadHandler) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                customAdapter.notifyDataSetChanged();
            }
        });
    }

    public void filterValue(String value) {
        //adapter.getFilter().filter(value);
        int size = arrayAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (arrayAdapter.getItem(i).startsWith(value)) {
                dicList.setSelection(i);
                break;
            }
        }
    }

    public interface SendDataInterface{
        public void sendData(String word, String mean);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try {
            sendDataInterface=(SendDataInterface) activity;
        } catch (RuntimeException a){
            throw new RuntimeException(activity.toString()+"Must implement method");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    public void setOnFragmentListener(FragmentListner listner) {
        this.listner = listner;
    }

    @Override
    public void onItemClick(int position) {
        String word = wordsList.get(position);
        String mean = meaningsList.get(position);
        //Toast.makeText(getContext(), word, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), mean, Toast.LENGTH_LONG).show();

        sendDataInterface.sendData(word,mean);
    }
}