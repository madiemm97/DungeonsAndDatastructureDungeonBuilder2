package com.example.awesomefat.dungeonsanddatastructuredungeonbuilder;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class NewRoomActivity extends AppCompatActivity
{
    private Button northButton, southButton, eastButton, westButton;
    private Button currentSelectedButton = null;
    private EditText roomNameET, roomDescriptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room);

        this.northButton = (Button)this.findViewById(R.id.northButton);
        this.southButton = (Button)this.findViewById(R.id.southButton);
        this.eastButton = (Button)this.findViewById(R.id.eastButton);
        this.westButton = (Button)this.findViewById(R.id.westButton);

        this.roomNameET = (EditText)this.findViewById(R.id.roomNameET);
        this.roomDescriptionET = (EditText)this.findViewById(R.id.roomDescriptionET);

        this.hideExitButtons();
    }

    public void onSaveButtonPressed(View v)
    {
        String roomName = this.roomNameET.getText().toString();
        String roomDescription = this.roomDescriptionET.getText().toString();

        if(roomName.length() == 0 || roomDescription.length() == 0 || this.currentSelectedButton == null)
        {
            Toast.makeText(this, "Fill Everything Out!!!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Room r = new Room(roomName, roomDescription);
            Core.theDungeon.addRoom(r);
            int newRoomPos = Core.theDungeon.findIndexOfRoom(r);

            Exit e = new Exit(newRoomPos, Core.p.currentRoom_index);
            String currentDirection = this.currentSelectedButton.getText().toString().toLowerCase();
            Core.p.getCurrentRoom().addExit(currentDirection, e );

            r.addExit(this.getInverseDirection(currentDirection), e);
            Core.firstScreen.fillInterface(Core.p.getCurrentRoom());
            this.finish();
        }
    }

    private String getInverseDirection(String direction)
    {
        String[] directions = {"north", "south", "east", "west"};
        String[] inverseDirections = {"south", "north", "west", "east"};
        for(int i = 0; i < directions.length; i++)
        {
            if(directions[i].equals(direction))
            {
                return inverseDirections[i];
            }
        }
        return "oh no!!!!!!";
    }

    public void exitButtonPressed(View v)
    {
        Button theButton = (Button)v;
        this.currentSelectedButton = theButton;
        this.northButton.setTextColor(getResources().getColor(R.color.black));
        this.southButton.setTextColor(getResources().getColor(R.color.black));
        this.eastButton.setTextColor(getResources().getColor(R.color.black));
        this.westButton.setTextColor(getResources().getColor(R.color.black));
        theButton.setTextColor(getResources().getColor(R.color.red));
    }

    private void hideExitButtons()
    {
        Map<String, Exit> currentExits = Core.p.getCurrentRoom().exits;
        for(String key : currentExits.keySet())
        {
            if(key.equals("north"))
            {
                this.northButton.setVisibility(View.INVISIBLE);
            }
            else if(key.equals("south"))
            {
                this.southButton.setVisibility(View.INVISIBLE);
            }
            else if(key.equals("east"))
            {
                this.eastButton.setVisibility(View.INVISIBLE);
            }
            else if(key.equals("west"))
            {
                this.westButton.setVisibility(View.INVISIBLE);
            }
        }
    }
}
