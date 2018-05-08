package com.example.awesomefat.dungeonsanddatastructuredungeonbuilder;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    private TextView nameTV;
    private TextView descriptionTV;
    private ViewGroup alsoHereViewGroup;
    private Button northButton;
    private Button southButton;
    private Button westButton;
    private Button eastButton;
    private Button saveButton;

    private Dungeon csDept;
    private MainActivity mainActivityInstancePointer;
    private boolean isPlayerAdded = false;
    private boolean isNPCAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Core.firstScreen = this;
        this.mainActivityInstancePointer = this;

        this.saveButton = (Button)this.findViewById(R.id.saveButton);
        this.saveButton.setVisibility(View.INVISIBLE);
        this.nameTV = (TextView)this.findViewById(R.id.nameTV);
        this.descriptionTV = (TextView)this.findViewById(R.id.descriptionTV);
        this.alsoHereViewGroup = (ViewGroup)this.findViewById(R.id.alsoHereViewGroup);
        this.northButton = (Button)this.findViewById(R.id.northButton);
        this.southButton = (Button)this.findViewById(R.id.southButton);
        this.eastButton = (Button)this.findViewById(R.id.eastButton);
        this.westButton = (Button)this.findViewById(R.id.westButton);

        Core.p = new Player("Mike");
        this.buildDungeon();
        //this.csDept.addPlayer(p);
        //this.fillInterface(p.getCurrentRoom());
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        System.out.println("********DSDSDFDSFDSF");
    }

    public void onExitButtonClicked(View v)
    {
        Button b = (Button)v;
        Core.p.getCurrentRoom().takeExit(b.getText().toString().toLowerCase());
        this.fillInterface(Core.p.getCurrentRoom());
    }

    private void buildDungeon()
    {
/*
        Room s120 = new Room("S120", "S120 Classroom");
        Room csHallway = new Room("CS Hallway", "The CS Hallway");
        this.csDept = new Dungeon("CS Department", csHallway);
        this.csDept.addRoom(s120);
        Core.theDungeon = this.csDept;

        //Linking rooms through exits
        Exit s120_csHallway = new Exit(0,1);

        //Exit s120_csHallway = new Exit(s120, csHallway);
        s120.addExit("north", s120_csHallway);
        csHallway.addExit("south", s120_csHallway);

        NPC monster = new NPC("Locklair");
        s120.addNPC(monster);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dungeonRef = database.getReference("dungeons");
        DatabaseReference tempDungeon = dungeonRef.push();
        tempDungeon.setValue(this.csDept);
*/


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dungeonRef = database.getReference("dungeons");
        dungeonRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                DataSnapshot theDungeon = dataSnapshot.getChildren().iterator().next();
                Core.currentDungeonKey = theDungeon.getKey();
                mainActivityInstancePointer.csDept = theDungeon.getValue(Dungeon.class);
                Core.theDungeon = mainActivityInstancePointer.csDept;
                if(!mainActivityInstancePointer.isPlayerAdded)
                {
                    if(Core.p.currentRoom_index != -1)
                    {
                        Core.theDungeon.addPlayer(Core.p, Core.p.currentRoom_index);
                    }
                    else
                    {
                        mainActivityInstancePointer.csDept.addPlayer(Core.p);
                    }
                    mainActivityInstancePointer.isPlayerAdded = !mainActivityInstancePointer.isPlayerAdded;

                }

                if(!mainActivityInstancePointer.isNPCAdded)
                {
                    mainActivityInstancePointer.csDept.addNPC;
                    mainActivityInstancePointer.isNPCAdded = !mainActivityInstancePointer.isNPCAdded;
                }


                mainActivityInstancePointer.fillInterface(Core.p.getCurrentRoom());
                mainActivityInstancePointer.saveButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    public void saveButtonPressed(View v)
    {
        this.isPlayerAdded = false;
        this.
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dungeonRef = database.getReference("dungeons");
        DatabaseReference tempDungeon = dungeonRef.child(Core.currentDungeonKey);
        tempDungeon.setValue(Core.theDungeon);
    }

    public void addNPCButtonPressed(View v)
    {
        Intent i = new Intent(this, NewNPCActivity.class);
        this.startActivity(i);
    }

    public void addExitButtonPressed(View v)
    {
        Intent i = new Intent(this, NewRoomActivity.class);
        this.startActivity(i);
    }

    public void fillInterface(Room r)
    {
        //fill interface for S120
        this.nameTV.setText(r.getName());
        this.descriptionTV.setText(r.getDescription());

        //fill the also here view group
        this.alsoHereViewGroup.removeAllViews();
        TextView playersLabel = new TextView(this);
        playersLabel.setText("Players:");
        playersLabel.setTypeface(null, Typeface.BOLD);
        this.alsoHereViewGroup.addView(playersLabel);
        TextView temp;
        for(Player player : r.getPlayers())
        {
            temp = new TextView(this);
            temp.setText("     " + player.getName());
            this.alsoHereViewGroup.addView(temp);
        }

        TextView npcLabel = new TextView(this);
        npcLabel.setText("NPCs:");
        npcLabel.setTypeface(null, Typeface.BOLD);
        this.alsoHereViewGroup.addView(npcLabel);
        for(NPC npc : r.getNpcs())
        {
            temp = new TextView(this);
            temp.setText("     " + npc.getName());
            this.alsoHereViewGroup.addView(temp);
        }

        this.northButton.setVisibility(View.INVISIBLE);
        this.southButton.setVisibility(View.INVISIBLE);
        this.eastButton.setVisibility(View.INVISIBLE);
        this.westButton.setVisibility(View.INVISIBLE);

        //hide the appropriate buttons:
        if(r.getExits().containsKey("north"))
        {
            this.northButton.setVisibility(View.VISIBLE);
        }
        if(r.getExits().containsKey("south"))
        {
            this.southButton.setVisibility(View.VISIBLE);
        }
        if(r.getExits().containsKey("east"))
        {
            this.eastButton.setVisibility(View.VISIBLE);
        }
        if(r.getExits().containsKey("west"))
        {
            this.westButton.setVisibility(View.VISIBLE);
        }
    }
}