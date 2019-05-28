//Tomas Venere
import java.net.*; 
import java.io.*; 
  
public class Server 
{ 
    public Socket socket = null; //initialize socket and input stream 
    public ServerSocket server = null; 
    public DataInputStream in = null; 
    public DataOutputStream out = null; 
    //out = socket.getOutputStream();
    //BufferedReader br = new BufferedReader(new InputStreamReader(in));
    public Server(int port) 
    { 

        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server started"); 
            
            System.out.println("Waiting to start this Adventure"); 
  
            Socket socket;
            while((socket = server.accept()) != null) 
            {
                final Thread thread = new Thread(new Tuna(socket));
                thread.start();
            }

        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 
  //------------------------------------MAIN---------------------------
    public static void main(String args[]) 
    {   
        Server server = new Server(5094); 
    } 
} 
