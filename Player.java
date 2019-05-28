public class Player 
{
    public String name;
    //byte[] flags = new byte[8];
    char flags;
    //byte[] attack = new byte[16];
    int attack;
    //byte[] defense = new byte[16];
    int defense;
    //byte[] regen = snew byte[16];
    int regen;
    //byte[] health = new byte[16];
    int health;
    //byte[] gold = new byte[16];w
    int gold;
    //byte[] currentRoomNumber = new byte[16];
    int currentRoomNumber;
    //byte[] descriptionLength = new byte[16];
    int descriptionLength;
    //byte[] playerDescription = new byte[32];
    public String playerDescription;

    public Player(String name, char flags, int attack, int defense, int regen, int health, int gold, int currentRoomNumber, int descriptionLength, String playerDescription)
    {
        this.name=name;
        this.flags=flags;
        this.attack=attack;
        this.defense=defense;
        this.regen=regen;
        this.health=health;
        this.gold=gold;
        this.currentRoomNumber=currentRoomNumber;
        this.descriptionLength=descriptionLength;
        this.playerDescription=playerDescription;
    }

        public String getName() 
        { 
            return name; 
        } 
        public void setName(String name) 
        {
            this.name = name;
        }


        public int getFlags() 
        { 
            return flags; 
        } 
        public void setFlags(char flags) 
        {
            this.flags = flags;
        }


        public int getAttack() 
        { 
            return attack; 
        } 
        public void setAttack(int attack) 
        {
            this.attack = attack;
        }


        public int getDefense() 
        { 
            return defense; 
        } 
        public void setDefense(int defense) 
        {
            this.defense = defense;
        }

        public int getRegen() 
        { 
            return regen; 
        } 
        public void setRegen(int regen) 
        {
            this.regen = regen;
        }

        public int getHealth() 
        { 
            return health; 
        } 
        public void setHealth(int health) 
        {
            this.health = health;
        }

        public int getGold() 
        { 
            return gold; 
        } 
        public void setGold(int gold) 
        {
            this.gold = gold;
        }

        public int getCurrentRoomNumber() 
        { 
            return currentRoomNumber; 
        } 
        public void setCurrentRoomNumber(int currentRoomNumber) 
        {
            this.currentRoomNumber = currentRoomNumber;
        }

        public int getDescriptionLength() 
        { 
            return descriptionLength; 
        } 
        public void setDescriptionLength(int descriptionLength) 
        {
            this.descriptionLength = descriptionLength;
        }

        public String getPlayerDescription() 
        { 
            return playerDescription; 
        } 
        public void setPlayerDescription(String playerDescription) 
        {
            this.playerDescription = playerDescription;
        }

        //is player description a String?
        //Is it short or Short, int or int?
    
}
