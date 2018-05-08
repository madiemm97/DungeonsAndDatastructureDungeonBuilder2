package com.example.awesomefat.dungeonsanddatastructuredungeonbuilder;
import com.google.firebase.database.Exclude;

/**
 * Created by awesomefat on 3/29/18.
 */

public class NPC
{
    public String name;
    public int currentRoom_index;

    public NPC(){}

    public NPC(String name)
    {
        this.name = name;
        this.currentRoom_index = -1;
    }

    public NPC(String name, int currentRoom_index)
    {
        this.name = name;
        this.currentRoom_index = currentRoom_index;
    }

    public void display()
    {
        System.out.println(this.name);
    }

    public String getName() {
        return name;
    }

    @Exclude
    public Room getCurrentRoom()
    {
        return Core.theDungeon.rooms.get(this.currentRoom_index);
    }

    public void setCurrentRoomIndex(int currentRoom_index)
    {
        this.currentRoom_index = currentRoom_index;
    }
}
