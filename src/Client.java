public class Client implements Runnable{
	private String clientName;
	
	public Client(String clientName) {
		this.clientName = clientName;
	}
	
	public void run() {
		//get list of files = fileStore<String>
		//random number generator for choosing file
		//fileStore[random]
		//random boolean for read/write Mode
		System.out.println(clientName + " going to get file + filename");
		//open(fileName, Mode)
	}
}