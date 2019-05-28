import java.io.IOException;
import java.net.Socket;
//import java.util.UUID;
//import java.net.*; 
import java.io.*;

public class Tuna implements Runnable
{
	public DataInputStream in = null; 

	//player
	String pepe="Mensaje";
	Player player= new Player("Tom", '1', 90, 10, 0, 100, 0, 11, 17, "playerDescription");
	Player bot= new Player("Zelda", '1', 90, 10, 0, 100, 35, 3, 13, "IamHereToHelp");
	Player bot1= new Player("Bot", '1', 90, 10, 0, 100, 300, 4, 35, "I will help you to beat this trolls");
	Player bot3= new Player("Bot", '1', 90, 10, 0, 100, 0, 4, 14, "botDescription");
	Player bot4= new Player("Bot", '1', 90, 10, 0, 100, 0, 5, 14, "botDescription");
	Player bot5= new Player("Bot", '1', 90, 10, 0, 100, 0, 6, 14, "botDescription");
	Player bot6= new Player("Bot", '1', 90, 10, 0, 100, 0, 7, 14, "botDescription");
	Player bot7= new Player("Bot", '1', 90, 10, 0, 100, 0, 8, 14, "botDescription");
	Player bot8= new Player("Bot", '1', 90, 10, 0, 100, 0, 9, 14, "botDescription");
	Player bot9= new Player("Bot", '1', 90, 10, 0, 100, 0, 3, 19, "lobbyBotDescription");

	//monsters
    Player dragon= new Player("Dragon", '1', 10000, 10000, 10000, 100, 0, 10, 17, "DragonDescription");
    Player troll1= new Player("Troll", '1', 10, 10, 10, 100, 2500, 4, 30, "I will destroy you nasty human");
    Player troll2= new Player("Troll", '1', 10, 10, 10, 100, 2500, 4, 30, "I will destroy you nasty human");
    Player troll4= new Player("Troll", '1', 10, 10, 10, 100, 0, 4, 30, "I will destroy you nasty human");
    Player troll5= new Player("Troll", '1', 10, 10, 10, 100, 0, 5, 16, "TrollDescription");
    Player troll6= new Player("Troll", '1', 10, 10, 10, 100, 0, 6, 16, "TrollDescription");
    Player troll7= new Player("Troll", '1', 10, 10, 10, 100, 0, 7, 16, "TrollDescription");
    Player troll8= new Player("Troll", '1', 10, 10, 10, 100, 0, 8, 16, "TrollDescription");
    Player troll9= new Player("Troll", '1', 10, 10, 10, 100, 0, 9, 16, "TrollDescription");

    Room room1= new Room(1, "Dungeon1", 12, "NastyDungeon");
    Room room2= new Room(2, "Dungeon2", 12, "NastyDungeon.");
    Room lobby= new Room(3, "Lobby", 10, "QuietPlace");
    Room room4= new Room(4, "Dungeon4", 12, "NastyDungeon");
    Room room5= new Room(5, "Dungeon5", 12, "NastyDungeon");
    Room room6= new Room(6, "Dungeon6", 12, "NastyDungeon");
    Room room7= new Room(7, "Dungeon7", 12, "NastyDungeon");
    Room room8= new Room(8, "Dungeon8", 12, "NastyDungeon");
    Room room9= new Room(9, "Dungeon9", 12, "NastyDungeon");
    Room room10= new Room(10, "Dungeon10", 13, "DragonDungeon");
    Room room11= new Room(11, "Hell", 10, "GraveShard");

    private final Socket socket;

    public Tuna(final Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            System.out.println("Client accepted"); 

            final DataInputStream stream = new DataInputStream(socket.getInputStream()); //new input //stream.readByte(); //reads the byte
            final DataOutputStream streom = new DataOutputStream(socket.getOutputStream()); //new input //stream.readByte(); //reads the byte  

            //11
            System.out.println("Game Started");
            int initialPoints = 100;
            int statLimit = 50000;
            //DescLength
            String gameDescription = "The Isolated World of HakunaMatata: There are multiple options in this game and there is a 14 byte option that will give you the stats of you player.. Have fun and don't kill all the trolls, after all... They have feelings too. I will recomend you stay away from room 10, the mith said that a big dragon lives in that nasty place.";

            streom.writeByte((byte)11);
            streom.writeShort(Short.reverseBytes((short)initialPoints));
            streom.writeShort(Short.reverseBytes((short)statLimit));
            streom.writeShort(Short.reverseBytes((short)gameDescription.length()));
            streom.write(gameDescription.getBytes("UTF-8"));

            if(stream.readByte()==10)
            {
                System.out.println("Step 2: Creating player"); 
	            final byte[] name_bytes = new byte[32];
	            stream.read(name_bytes);
	            player.name= new String(name_bytes, "UTF-8"); 

	            stream.readByte(); //ignore flags
	            player.setFlags('L');//cheeting
	            player.attack=Short.reverseBytes(stream.readShort());
	            //player.setAttack(100);//cheating
	            player.defense=Short.reverseBytes(stream.readShort());
	            //player.setDefense(0);//cheeting
	            player.regen=Short.reverseBytes(stream.readShort());
	            //player.setRegen(0);//cheeting
	            Short.reverseBytes(stream.readShort()); //ignore health
	            Short.reverseBytes(stream.readShort()); //ignore gold
	            Short.reverseBytes(stream.readShort()); //ignore Room Number
	            player.descriptionLength=Short.reverseBytes(stream.readShort());

	            final byte[] desc_bytez = new byte[player.descriptionLength];
	            stream.read(desc_bytez);
            	player.playerDescription= new String(desc_bytez, "UTF-8"); 

            }            

            while((player.getAttack()+player.getDefense()+player.getRegen())>100)
            {
            	//7 4
            	streom.writeByte((byte)7);
            	streom.writeByte((byte)4);
            	final String message = "Invalid player.";
            	final byte[] error_message_bytes = message.getBytes("UTF-8");
            	streom.writeShort(Short.reverseBytes((short)message.length()));
            	streom.write(error_message_bytes);

            	if(stream.readByte()==10)
            	{
	                System.out.println("Step 2: Creating player"); 
		            final byte[] name_bytes = new byte[32];
		            stream.read(name_bytes);
		            player.name= new String(name_bytes, "UTF-8"); 

		            stream.readByte(); //ignore flags
		            player.setFlags('L');//cheeting
		            player.attack=Short.reverseBytes(stream.readShort());
		            //player.setAttack(100);//cheating
		            player.defense=Short.reverseBytes(stream.readShort());
		            //player.setDefense(0);//cheeting
		            player.regen=Short.reverseBytes(stream.readShort());
		            //player.setRegen(0);//cheeting
		            Short.reverseBytes(stream.readShort()); //ignore health
		            Short.reverseBytes(stream.readShort()); //ignore gold
		            Short.reverseBytes(stream.readShort()); //ignore Room Number
		            player.descriptionLength=Short.reverseBytes(stream.readShort());

		            final byte[] desc_bytez = new byte[player.descriptionLength];
		            stream.read(desc_bytez);
	            	player.playerDescription= new String(desc_bytez, "UTF-8"); 
            	}

            }

            System.out.println(player.getName());
            System.out.println(player.getFlags());
            System.out.println(player.getAttack());
            System.out.println(player.getDefense());
            System.out.println(player.getRegen());
            System.out.println(player.getHealth());
            System.out.println(player.getGold());
            System.out.println(player.getDescriptionLength());
            System.out.println(player.getPlayerDescription());

            //else{
            //8 Accept
        	streom.writeByte((byte)8);
        	streom.writeByte((byte)10); 
        	System.out.println("Valid Player Created");
            //}

            //*
			//10 stats
			streom.writeByte((byte)10);
            final byte[] nameBytes = player.name.getBytes("UTF-8");
            final int numNameByteZeros = 32 - nameBytes.length;
            streom.write(nameBytes);

            if(numNameByteZeros > 0) {
            	final byte[] zerosBuffer = new byte[numNameByteZeros];
            	streom.write(zerosBuffer);
            }

            streom.writeByte(player.getFlags());
            streom.writeShort(Short.reverseBytes((short)player.getAttack()));
            streom.writeShort(Short.reverseBytes((short)player.getDefense()));
            streom.writeShort(Short.reverseBytes((short)player.getRegen()));
            streom.writeShort(Short.reverseBytes((short)player.getHealth()));
            streom.writeShort(Short.reverseBytes((short)player.getGold()));
            streom.writeShort(Short.reverseBytes((short)player.getCurrentRoomNumber()));

            streom.writeShort(Short.reverseBytes((short)player.getDescriptionLength()));
            final byte[] descBytes = player.getPlayerDescription().getBytes("UTF-8");
            streom.write(descBytes);
            //*/
            
            //6 START GAME

            if(stream.readByte()==6){
            	System.out.println("Step 3: Putting player into lobby"); 
            	player.setCurrentRoomNumber(3);

            	//9 current room
				streom.writeByte((byte)9);

	            streom.writeShort(Short.reverseBytes((short)lobby.getRoomNumber()));

	            final byte[] bytessssss = lobby.getRoomName().getBytes("UTF-8");

	            final int numBytesssss = bytessssss.length;
	            final int zerossssss = 32 - numBytesssss;

	            streom.write(bytessssss);
	            if(zerossssss>0)
	            {
	                final byte[] extrasss = new byte[zerossssss];
	                streom.write(extrasss);
	            }

	            streom.writeShort(Short.reverseBytes((short)lobby.getRoomDescription().length()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytesss = lobby.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytesss);

	            System.out.println("9.Current Room: ");
	            System.out.println(lobby.getRoomName());
	            System.out.println(player.getCurrentRoomNumber());
	            System.out.println(lobby.getRoomDescriptionLength());
	            System.out.println(lobby.getRoomDescription());

				//10 stats
				streom.writeByte((byte)10);
				final byte[] bytesssss = player.getName().getBytes("UTF-8");
	            final int numBytessss = bytesssss.length;
	            final int zerosssss = 32 - numBytessss;

	            streom.write(bytesssss);
	            if(zerosssss>0)
	            {
	                final byte[] extrass = new byte[zerosssss];
	                streom.write(extrass);
	            }

	            streom.writeByte(player.getFlags());
	            streom.writeShort(Short.reverseBytes((short)player.getAttack()));
	            streom.writeShort(Short.reverseBytes((short)player.getDefense()));
	            streom.writeShort(Short.reverseBytes((short)player.getRegen()));
	            streom.writeShort(Short.reverseBytes((short)player.getHealth()));
	            streom.writeShort(Short.reverseBytes((short)player.getGold()));
	            streom.writeShort(Short.reverseBytes((short)player.getCurrentRoomNumber()));

	            streom.writeShort(Short.reverseBytes((short)player.getDescriptionLength()));
	            final byte[] descBytess = player.getPlayerDescription().getBytes("UTF-8");
	            streom.write(descBytess);
				
	            
	            //13 rooms around
/*
				streom.writeByte((byte)13);
				System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room1.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzz = room1.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzz = bytesssssszzz.length;
	            final int zerosssssszzz = 32 - numBytessssszzz;

	            streom.write(bytesssssszzz);
	            if(zerosssssszzz>0)
	            {
	                final byte[] extrassszzz = new byte[zerosssssszzz];
	                streom.write(extrassszzz);
	            }

	            streom.writeShort(Short.reverseBytes((short)room1.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzz = room1.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzz);
*/
	            //other rooms
	            streom.writeByte((byte)13);
	            System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room2.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzl = room2.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzl = bytesssssszzl.length;
	            final int zerosssssszzl = 32 - numBytessssszzl;

	            streom.write(bytesssssszzl);
	            if(zerosssssszzl>0)
	            {
	                final byte[] extrassszzl = new byte[zerosssssszzl];
	                streom.write(extrassszzl);
	            }

	            streom.writeShort(Short.reverseBytes((short)room2.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzl = room2.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzl);
        	//}
        	//else if(player.getCurrentRoomNumber()==2)
        	//{
        		/*
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)lobby.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzpso = lobby.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzpso = bytesssssszzpso.length;
	            final int zerosssssszzpso = 32 - numBytessssszzpso;

	            streom.write(bytesssssszzpso);
	            if(zerosssssszzpso>0)
	            {
	                final byte[] extrassszzpso = new byte[zerosssssszzpso];
	                streom.write(extrassszzpso);
	            }

	            streom.writeShort(Short.reverseBytes((short)lobby.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzpso = lobby.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzpso);
        	//}*/
        	//else if(player.getCurrentRoomNumber()==3)
        	//{
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room4.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzxp = room4.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzxp = bytesssssszzxp.length;
	            final int zerosssssszzxp = 32 - numBytessssszzxp;

	            streom.write(bytesssssszzxp);
	            if(zerosssssszzxp>0)
	            {
	                final byte[] extrassszzxp = new byte[zerosssssszzxp];
	                streom.write(extrassszzxp);
	            }

	            streom.writeShort(Short.reverseBytes((short)room4.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzxp = room4.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzxp);
        	//}
        	//else if(player.getCurrentRoomNumber()==4)
        	//{
        		streom.writeByte((byte)13);
				System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room5.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzpx = room5.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzpx = bytesssssszzpx.length;
	            final int zerosssssszzpx = 32 - numBytessssszzpx;

	            streom.write(bytesssssszzpx);
	            if(zerosssssszzpx>0)
	            {
	                final byte[] extrassszzpx = new byte[zerosssssszzpx];
	                streom.write(extrassszzpx);
	            }

	            streom.writeShort(Short.reverseBytes((short)room5.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzpx = room5.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzpx);                    	
        	//}
        	//else if(player.getCurrentRoomNumber()==5)
        	//{
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room6.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzp = room6.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzp = bytesssssszzp.length;
	            final int zerosssssszzp = 32 - numBytessssszzp;

	            streom.write(bytesssssszzp);
	            if(zerosssssszzp>0)
	            {
	                final byte[] extrassszzp = new byte[zerosssssszzp];
	                streom.write(extrassszzp);
	            }

	            streom.writeShort(Short.reverseBytes((short)room6.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzp = room6.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzp);   
        	//}
        	//else if(player.getCurrentRoomNumber()==6){
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room7.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzxx = room7.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzxx = bytesssssszzxx.length;
	            final int zerosssssszzxx = 32 - numBytessssszzxx;

	            streom.write(bytesssssszzxx);
	            if(zerosssssszzxx>0)
	            {
	                final byte[] extrassszzxx = new byte[zerosssssszzxx];
	                streom.write(extrassszzxx);
	            }

	            streom.writeShort(Short.reverseBytes((short)room7.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzxx = room7.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzxx);   
        	//}
        	//else if(player.getCurrentRoomNumber()==7){
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room8.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzx = room8.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzx = bytesssssszzx.length;
	            final int zerosssssszzx = 32 - numBytessssszzx;

	            streom.write(bytesssssszzx);
	            if(zerosssssszzx>0)
	            {
	                final byte[] extrassszzx = new byte[zerosssssszzx];
	                streom.write(extrassszzx);
	            }

	            streom.writeShort(Short.reverseBytes((short)room8.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzx = room8.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzx);   
        	//}
        	//else if(player.getCurrentRoomNumber()==8){
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room9.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzzz = room9.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzzz = bytesssssszzzz.length;
	            final int zerosssssszzzz = 32 - numBytessssszzzz;

	            streom.write(bytesssssszzzz);
	            if(zerosssssszzzz>0)
	            {
	                final byte[] extrassszzzz = new byte[zerosssssszzzz];
	                streom.write(extrassszzzz);
	            }

	            streom.writeShort(Short.reverseBytes((short)room9.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzzz = room9.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzzz); 
        	//}
        	//else if(player.getCurrentRoomNumber()==9)
        	//{
        		streom.writeByte((byte)13);
        		System.out.println("Rooms around you: ");
	            streom.writeShort(Short.reverseBytes((short)room10.getRoomNumber()));
	            //streom.writeUTF(lobby.getRoomName());//fucked
	            final byte[] bytesssssszzzn = room10.getRoomName().getBytes("UTF-8");

	            final int numBytessssszzzn = bytesssssszzzn.length;
	            final int zerosssssszzzn = 32 - numBytessssszzzn;

	            streom.write(bytesssssszzzn);
	            if(zerosssssszzzn>0)
	            {
	                final byte[] extrassszzzn = new byte[zerosssssszzzn];
	                streom.write(extrassszzzn);
	            }

	            streom.writeShort(Short.reverseBytes((short)room10.getRoomDescriptionLength()));
	            //streom.writeUTF(lobby.getRoomDescription());//fucked
	            final byte[] descBytessszzzn = room10.getRoomDescription().getBytes("UTF-8");
	            streom.write(descBytessszzzn);


	            System.out.println("13.Rooms around you: ");
	            System.out.println(room4.getRoomName());



            }

            System.out.println("Waiting for types: "); 

            while(true)
            { 
                try
                {   
                    byte code = stream.readByte();                
                    //line = in.readUTF(); 
                    //System.out.println(line); 
                    if(code==1){        
                        System.out.println("1.Message: "); 
                        int messageLength = Short.reverseBytes(stream.readShort()); //reads the message lenght//messageLength = 1024;
                        byte[] recipient = new byte[32]; //reads the recipient
                        byte[] sender = new byte[32]; //reads the sender

                        stream.read(recipient);
                        stream.read(sender);

                        byte[] message_content = new byte[messageLength];
                        stream.read(message_content);
                        //final String message= new String(message_content, 1024);
                    }

                    else if(code==2){
                    	//---------------------------------************************** DO I NEED CLASS 13?
                        System.out.println("2.Changing room: ");
                        short x;
                        x=Short.reverseBytes(stream.readShort());
                        //player.currentRoomNumber=Short.reverseBytes(stream.readShort());
                        player.setCurrentRoomNumber(x);
                        if(player.currentRoomNumber==bot.currentRoomNumber)
                        {
                        	streom.writeByte((byte)10);
							final byte[] bytesssssza = bot.getName().getBytes("UTF-8");
				            final int numBytessssza = bytesssssza.length;
				            final int zerosssssza = 32 - numBytessssza;

				            streom.write(bytesssssza);
				            if(zerosssssza>0)
				            {
				                final byte[] extrassza = new byte[zerosssssza];
				                streom.write(extrassza);
				            }

				            streom.writeByte(bot.getFlags());
				            streom.writeShort(Short.reverseBytes((short)bot.getAttack()));
				            streom.writeShort(Short.reverseBytes((short)bot.getDefense()));
				            streom.writeShort(Short.reverseBytes((short)bot.getRegen()));
				            streom.writeShort(Short.reverseBytes((short)bot.getHealth()));
				            streom.writeShort(Short.reverseBytes((short)bot.getGold()));
				            streom.writeShort(Short.reverseBytes((short)bot.getCurrentRoomNumber()));

				            streom.writeShort(Short.reverseBytes((short)bot.getDescriptionLength()));
				            final byte[] descBytesszal = bot.getPlayerDescription().getBytes("UTF-8");
				            streom.write(descBytesszal);
                        }
                        else if(player.currentRoomNumber==bot1.currentRoomNumber)
                        {
                        	streom.writeByte((byte)10);
							final byte[] bytessssszap = bot1.getName().getBytes("UTF-8");
				            final int numBytesssszap = bytessssszap.length;
				            final int zerossssszap = 32 - numBytesssszap;

				            streom.write(bytessssszap);
				            if(zerossssszap>0)
				            {
				                final byte[] extrasszap = new byte[zerossssszap];
				                streom.write(extrasszap);
				            }

				            streom.writeByte(bot1.getFlags());
				            streom.writeShort(Short.reverseBytes((short)bot1.getAttack()));
				            streom.writeShort(Short.reverseBytes((short)bot1.getDefense()));
				            streom.writeShort(Short.reverseBytes((short)bot1.getRegen()));
				            streom.writeShort(Short.reverseBytes((short)bot1.getHealth()));
				            streom.writeShort(Short.reverseBytes((short)bot1.getGold()));
				            streom.writeShort(Short.reverseBytes((short)bot1.getCurrentRoomNumber()));

				            streom.writeShort(Short.reverseBytes((short)bot1.getDescriptionLength()));
				            final byte[] descBytesszaa = bot1.getPlayerDescription().getBytes("UTF-8");
				            streom.write(descBytesszaa);

				            //troll
				            streom.writeByte((byte)10);
							final byte[] bytessssszapp = troll1.getName().getBytes("UTF-8");
				            final int numBytesssszapp = bytessssszapp.length;
				            final int zerossssszapp = 32 - numBytesssszapp;

				            streom.write(bytessssszapp);
				            if(zerossssszapp>0)
				            {
				                final byte[] extrasszapp = new byte[zerossssszapp];
				                streom.write(extrasszapp);
				            }

				            streom.writeByte(troll1.getFlags());
				            streom.writeShort(Short.reverseBytes((short)troll1.getAttack()));
				            streom.writeShort(Short.reverseBytes((short)troll1.getDefense()));
				            streom.writeShort(Short.reverseBytes((short)troll1.getRegen()));
				            streom.writeShort(Short.reverseBytes((short)troll1.getHealth()));
				            streom.writeShort(Short.reverseBytes((short)troll1.getGold()));
				            streom.writeShort(Short.reverseBytes((short)troll1.getCurrentRoomNumber()));

				            streom.writeShort(Short.reverseBytes((short)troll1.getDescriptionLength()));
				            final byte[] descBytesszap = troll1.getPlayerDescription().getBytes("UTF-8");
				            streom.write(descBytesszap);
                        }

                    }

                    else if(code==3){
                            System.out.println("3.Starting battle: ");
                      	if(player.currentRoomNumber==10){
                            System.out.println("Epic battle but the Dragon killed you :("); //own console?
                            //player.currentRoomNumber=3;
                            player.setCurrentRoomNumber(3);
                            System.out.println("You ran away to the lobby!");
                        }
                        else if(player.currentRoomNumber==3){
                      		System.out.println("Lobby is a safe zone");
                      	}
                      	else if(player.currentRoomNumber==1){
            				if(troll1.getFlags()=='1')
            				{
            					System.out.println("Epic battle! Troll is dead and it was sent to hell");
                      			troll1.setFlags('0');
                      			troll1.setCurrentRoomNumber(11);  
                      		}
                      		else{
            					System.out.println("Troll is already dead :(");
            				}
                      		//

                      	}
                      	else if(player.currentRoomNumber==2){
            				if(troll2.getFlags()=='1')
            				{
            					System.out.println("Epic battle! Troll is dead and it was sent to hell");
                      			troll2.setFlags('0');
                      			troll2.setCurrentRoomNumber(11);
            				}
            				else{
            					System.out.println("Troll is already dead :(");
            				}
                      	}
                      	else if(player.currentRoomNumber==4){
            				if(troll4.getFlags()=='1')
            				{
            					System.out.println("Epic battle! Troll is dead and it was sent to hell");
                      			troll4.setFlags('0');
                      			troll4.setCurrentRoomNumber(11);
            				}
            				else{
            					System.out.println("Troll is already dead :(");
            				}
                      	}
                      	else if(player.currentRoomNumber==5){
            				if(troll5.getFlags()=='1')
            				{
            					System.out.println("Epic battle! Troll is dead and it was sent to hell");
                      			troll5.setFlags('0');
                      			troll5.setCurrentRoomNumber(11);
            				}
            				else{
            					System.out.println("Troll is already dead :(");
            				}
                      	}
                      	else if(player.currentRoomNumber==6){
            				if(troll6.getFlags()=='1')
            				{
            					System.out.println("Epic battle! Troll is dead and it was sent to hell");
                      			troll6.setFlags('0');
                      			troll6.setCurrentRoomNumber(11);
            				}
            				else{
            					System.out.println("Troll is already dead :(");
            				}
                      	}
                      	else if(player.currentRoomNumber==7){
            				if(troll7.getFlags()=='1')
            				{
            					System.out.println("Epic battle! Troll is dead and it was sent to hell");
                      			troll7.setFlags('0');
                      			troll7.setCurrentRoomNumber(11);
            				}
            				else{
            					System.out.println("Troll is already dead :(");
            				}
                      	}
                      	else if(player.currentRoomNumber==8){
                      		troll8.setFlags('0');
                      		troll8.setCurrentRoomNumber(11);
                      		System.out.println("Epic battle! Troll is dead but somehow another one came from somewhere");
                      	}
                      	else if(player.currentRoomNumber==9){
                      		troll9.setFlags('0');
                      		troll9.setCurrentRoomNumber(11);
                      		System.out.println("Epic battle! Troll is dead but somehow another one came from somewhere");
                      	}

                        System.out.println("Battle ended...");
                    }
                    else if(code==4){
                        System.out.println("4.Not pvp on this server sorry.");
                        //String pluto="No pvp on this server sorry";
                        //streom.write(0);
                    }
                    else if(code==5)
                    {
                    	System.out.println("5.Loot.");
                        if(player.getCurrentRoomNumber()==10)
                        {
                        	System.out.println("Dragon is not dead");
                        }
                    	else if(player.getCurrentRoomNumber()==3){
                    		System.out.println("Lobby is a safe zone");
                        }
                        else{
                        	player.setGold((player.getGold()+100));
                        }       
                        
                    }
                    //if(stream.readByte()==6){
                        //Accept message only
                    //}
                    else if(code==7){
                        //Not needed? --------------------********************************
                        System.out.println("Function 7");
                    	streom.writeByte((byte)7);
		            	streom.writeByte((byte)4);
		            	final String message = "Invalid player.";
		            	final byte[] error_message_bytess = message.getBytes("UTF-8");
		            	streom.writeShort(Short.reverseBytes((short)message.length()));
		            	streom.write(error_message_bytess);
                    }
                    else if(code==8){
                    	char valid = 'T';
                    	streom.writeByte(valid);
                    	System.out.println("Valid message");

                        //--------*********** Do I need to read unsigened all the time?
                        //System.out.println("8.Action accepted");
                    }
                    else if(code==9){
                        System.out.println("Current Room info:");
			            streom.writeShort(Short.reverseBytes((short)lobby.getRoomNumber()));
			            //streom.writeUTF(lobby.getRoomName());//fucked
			            final byte[] bytessssssz = lobby.getRoomName().getBytes("UTF-8");

			            final int numBytesssssz = bytessssssz.length;
			            final int zerossssssz = 32 - numBytesssssz;

			            streom.write(bytessssssz);
			            if(zerossssssz>0)
			            {
			                final byte[] extrasssz = new byte[zerossssssz];
			                streom.write(extrasssz);
			            }

			            streom.writeShort(Short.reverseBytes((short)lobby.getRoomDescriptionLength()));
			            //streom.writeUTF(lobby.getRoomDescription());//fucked
			            final byte[] descBytesssz = lobby.getRoomDescription().getBytes("UTF-8");
			            streom.write(descBytesssz);
                    }
                    else if(stream.readByte()==10){
			            final byte[] bytessssszz = player.getName().getBytes("UTF-8");
			            final int numBytessszs = bytessssszz.length;
			            final int zerossssszz = 32 - numBytessszs;

			            streom.write(bytessssszz);
			            if(zerossssszz>0)
			            {
			                final byte[] extraszs = new byte[zerossssszz];
			                streom.write(extraszs);
			            }

			            streom.writeByte(player.getFlags());
			            streom.writeShort(Short.reverseBytes((short)player.getAttack()));
			            streom.writeShort(Short.reverseBytes((short)player.getDefense()));
			            streom.writeShort(Short.reverseBytes((short)player.getRegen()));
			            streom.writeShort(Short.reverseBytes((short)player.getHealth()));
			            streom.writeShort(Short.reverseBytes((short)player.getGold()));
			            streom.writeShort(Short.reverseBytes((short)player.getCurrentRoomNumber()));

			            streom.writeShort(Short.reverseBytes((short)player.getDescriptionLength()));
			            final byte[] descByteszs = player.getPlayerDescription().getBytes("UTF-8");
			            streom.write(descByteszs);


			            System.out.println(player.getName());
			            System.out.println(player.getFlags());
			            System.out.println(player.getAttack());
			            System.out.println(player.getDefense());
			            System.out.println(player.getRegen());
			            System.out.println(player.getHealth());
			            System.out.println(player.getGold());
			            System.out.println(player.getDescriptionLength());
			            System.out.println(player.getPlayerDescription());
                    }


                    //if(stream.readByte()==11){


                        //streom.write(gameDescription);//---------------------------_**********WHY I CANT WRITE A STRING

                    //}
                    else if(code==12){
                        System.out.println("Function 12");
                        System.out.println("Closing connection");
                        socket.close(); 
            			in.close(); 
            			//Thread().close();
            			//thread.close();
            			//System.exit(0);
                        break;
                    }
                    else if(code==13){


                    }
                    else if(code==14)
                    {
			            System.out.println(player.getName());
			            System.out.println(player.getFlags());
			            System.out.println(player.getAttack());
			            System.out.println(player.getDefense());
			            System.out.println(player.getRegen());
			            System.out.println(player.getHealth());
			            System.out.println(player.getGold());
			            System.out.println(player.getDescriptionLength());
			            System.out.println(player.getPlayerDescription());
                    }

  
                }
                catch(IOException i) 
                { 
                    System.out.println(i); 
                    break;
                } 
            } 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 

    } 

}

//check last test y el anterior plus quizzes mas email
//hacer chat para la app
//empezar lo del chato

//Check functions
//Make more players available
//switch flags to 1 and 2
