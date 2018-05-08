package com.example.awesomefat.dungeonsanddatastructuredungeonbuilder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewNPCActivity extends AppCompatActivity
{

    private EditText npcNameET;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_npc);

        this.npcNameET = (EditText)this.findViewById(R.id.npcNameET);
    }

    public void saveButtonPressed(View v)
    {
        if(this.npcNameET.getText().toString().length() == 0)
        {
            Toast.makeText(this, "Enter Everything!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            NPC temp = new NPC(this.npcNameET.getText().toString(), Core.p.currentRoom_index);
            Room theRoom = Core.theDungeon.findRoomWithIndex(Core.p.currentRoom_index);
            theRoom.addNPC(temp);
            Core.firstScreen.fillInterface(Core.p.getCurrentRoom());
            this.finish();
        }
    }
}
