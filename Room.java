import java.net.*; 
import java.io.*; 

public class Room {

    //byte[] roomNumber = new byte[16];
    int roomNumber;
    public String roomName;
    //byte[] roomDescriptionLength = new byte[16];
    int roomDescriptionLength;
    //byte[] roomDescription = new byte[roomDescriptionLength];
    public String roomDescription;

    public Room(int roomNumber, String roomName, int roomDescriptionLength, String roomDescription)
    {
        this.roomNumber=roomNumber;
        this.roomName=roomName;
        this.roomDescriptionLength=roomDescriptionLength;
        this.roomDescription=roomDescription;
    }

    public int getRoomNumber() 
    { 
        return roomNumber; 
    } 
    public void setRoomNumber(int roomNumber) 
    {
        this.roomNumber = roomNumber;
    }


    public String getRoomName() 
    { 
        return roomName; 
    } 
    public void setRoomName(String roomName) 
    {
        this.roomName = roomName;
    }


    public int getRoomDescriptionLength() 
    { 
        return roomDescriptionLength; 
    } 
    public void setRoomDescriptionLength(int roomDescriptionLength) 
    {
        this.roomDescriptionLength = roomDescriptionLength;
    }

    public String getRoomDescription() 
    { 
        return roomDescription; 
    } 
    public void setRoomDescription(String roomDescription) 
    {
        this.roomDescription = roomDescription;
    }


}
