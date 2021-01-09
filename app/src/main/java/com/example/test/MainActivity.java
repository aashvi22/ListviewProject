package com.example.test;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /* Todo
     *   1. fix position error
     *   2. serialization of Marvel
     *   3. make UI REALLY nice - move text away from list, make heights of listview items same*/

    ListView listView;

    TextView textView;


    ArrayList<Marvel> chars = new ArrayList<>();

    public static final String key = "ldkjfdlsafl";
    public static final String keyString = "jdksafjakls;f";
    public static final String key3 = "asdfgasf";



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(key, chars);
        outState.putString(keyString, textView.getText().toString());
        //outState.putSerializable(key3, list);
    }
    /*protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        saveInstanceState.putSerializable(key, chars);
        saveInstanceState.putString(keyString, textView.getText().toString());

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) { //you must update adapter if it doesn't work
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.id_listView);

        textView = findViewById(R.id.textView);

        chars.add(new Marvel("Spiderman", "Peter Parker", "A science nerd at school and a friendly neighborhood Spiderman for the world. Newest member of the Avengers", R.drawable.spiderman));
        chars.add(new Marvel("Iron Man", "Tony Stark", "An innovative playboy billionaire who created a flying weapon suit. Member of the Avengers", R.drawable.ironman));
        chars.add(new Marvel("Hulk", "Bruce Banner","Big green guy with 17 PhDs. Member of the Avengers",R.drawable.hulk));
        chars.add(new Marvel("Thor", "Thor", "God of Thunder with a cool Australian accent. Member of the Avengers",R.drawable.thor));
        chars.add(new Marvel("Captain America", "Steve Rogers", "A patriotic superhero from World War 2. Member of the Avengers",R.drawable.captainamerica));
        chars.add(new Marvel("Doctor Strange", "Steven Strange", "Once a surgeon, he now uses magic and the time stone to manipulate reality.",R.drawable.doctorstrange));
        chars.add(new Marvel("Captain Marvel", "Carol Danvers", "The most powerful superhero",R.drawable.captainmarvel));
        chars.add(new Marvel("Black Widow", "Natasha Romanoff", "A Russian ballerina assassin",R.drawable.blackwidow));
        chars.add(new Marvel("Black Panther", "King D'Challa", "King of Wakanda and owner of the coolest suit",R.drawable.blackpanther));
        chars.add(new Marvel("GROOT!", "Groot", "I am groot.",R.drawable.groot));



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("clicked", "clicked on " + position);
                System.out.println("clicked on " + position);

                textView.setText(chars.get(position).getDescription());


            }
        });

        if(savedInstanceState != null){ //problem - list not displaying new list of chars
            chars = (ArrayList<Marvel>)savedInstanceState.getSerializable(key);
            textView.setText(savedInstanceState.getString(keyString));
        }

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.adapter_custom, chars);
        listView.setAdapter(customAdapter);


    }
    public class CustomAdapter extends ArrayAdapter{
        Context context;
        int xmlResource;
        List<Marvel> list;
        public CustomAdapter(@NonNull Context context, int resource, @NonNull List objects) {
            super(context, resource, objects);

            this.context = context;
            xmlResource = resource;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(xmlResource, null);
            final TextView textViewLabel = adapterView.findViewById(R.id.id_adapterLabel);
            Button remove = adapterView.findViewById(R.id.id_adapterRemove);
            ImageView imageView = adapterView.findViewById(R.id.id_adapterImage);
            TextView name = adapterView.findViewById(R.id.id_name);


            textViewLabel.setText(list.get(position).getCharacter());
            name.setText(list.get(position).getName());
            imageView.setImageResource(list.get(position).getImageId());


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    chars = (ArrayList<Marvel>) list;
                    notifyDataSetChanged();
                }
            });


            return adapterView;
        }

    }


    public class Marvel implements Serializable {
        String character;
        String name;
        String description;
        int imageID;

        public Marvel(String ch, String nm, String des, int id){
            character = ch;
            name = nm;
            description = des;
            imageID = id;
        }

        public String getCharacter(){
            return character;
        }
        public String getDescription(){
            return description;
        }
        public String getName(){
            return name;
        }
        public int getImageId(){
            return imageID;
        }

    }

}

