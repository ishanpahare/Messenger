package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import client.*;

public class ChatServer {
	static Vector clientSockets;
	static Vector LoginNames;
	
	ChatServer() throws IOException
	{
		ServerSocket server = new ServerSocket(1234);
		clientSockets = new Vector();
		LoginNames=new Vector();
		
		while(true)
		{
			Socket client=server.accept();
			AcceptClient acceptClient= new AcceptClient(client);
		}
	}
	
	public static void main(String[] args) throws IOException {
		ChatServer server= new ChatServer();
		
	}

class AcceptClient extends Thread{
	Socket ClientSocket;
	DataInputStream din;
	DataOutputStream dout;
	
	public AcceptClient(Socket client) throws IOException {
		ClientSocket =client;
		din= new DataInputStream(ClientSocket.getInputStream());
		dout=new DataOutputStream(ClientSocket.getOutputStream());
		
		String loginName=din.readUTF();
		LoginNames.add(loginName);
		clientSockets.add(ClientSocket);
		
		start();
		
		}
	public void run() {
		while(true) {
			
			try {
				String msgFromClient=din.readUTF();
				StringTokenizer st=new StringTokenizer(msgFromClient);
				String loginName=st.nextToken();
				String msgType=st.nextToken();
				
				int lo=-1;
				
				String msg="";
				
				while(st.hasMoreTokens())
				{
					msg=msg+" "+st.nextToken();
				}
				
				if(msgType.equals("LOGIN")) {
					for(int i=0;i<LoginNames.size();i++)
						{
							Socket pSocket=(Socket) clientSockets.elementAt(i);
							DataOutputStream pout= new DataOutputStream(pSocket.getOutputStream());
							pout.writeUTF(loginName+ " has logged in");
						}
				}
				else if(msgType.equals("LOGOUT")) {
					for(int i=0;i<LoginNames.size();i++)
						{
							if(loginName==LoginNames.elementAt(i));
								lo=i;
							Socket pSocket=(Socket) clientSockets.elementAt(i);
							DataOutputStream pout= new DataOutputStream(pSocket.getOutputStream());
							pout.writeUTF(loginName+ " has logged out");
						}
					if(lo>=0)
					{
						LoginNames.removeElementAt(lo);
						clientSockets.removeElementAt(lo);
					}
				}
				else {
						for(int i=0;i<LoginNames.size();i++)
							{
								Socket pSocket=(Socket) clientSockets.elementAt(i);
								DataOutputStream pout= new DataOutputStream(pSocket.getOutputStream());
								pout.writeUTF(loginName+ " : "+ msg);
							}
			}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	}
}