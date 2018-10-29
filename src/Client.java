import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Client implements Runnable{
	private String clientName;
	private MyFileServer fileServer;
	
	public Client(String clientName, MyFileServer fileServer) {
		this.clientName = clientName;
		this.fileServer = fileServer;
	}
	
	public void run() {		
		int k = 0;
		while(k < 6){
			//get list of files = fileStore<String>
			Set<String> availableFiles = fileServer.availableFiles();
			String[] filesArray = availableFiles.toArray(new String[availableFiles.size()]);
			String fileName = filesArray[ThreadLocalRandom.current().nextInt(0, filesArray.length)]; //Random file from file store
			//Choose between read and write
			Mode mode;
			boolean choice = ThreadLocalRandom.current().nextBoolean();
			if(choice) {
				mode = Mode.READWRITEABLE;
			} else {
				mode = Mode.READABLE;
			}
			//random boolean for read/write Mode
			System.out.println(clientName + " choosing to get get file " + fileName + " with " + mode + " mode");
			
			//open(fileName, Mode) -> returns File object
			Optional<File> openFile = fileServer.open(fileName, mode);
		
			//content = File.read
			//if write: call File.write(content + clientName)
			String fileContents = openFile.get().read(); //how to get File out of Optional

			if(mode == Mode.READWRITEABLE){
				openFile.get().write(fileContents + clientName);
				System.out.println(clientName + " writing to file " + fileName + ": " + openFile.get().read());
			} else if(mode == Mode.READABLE) {
				System.out.println(clientName + " reading file " + fileName + ": " + fileContents);				
			}
			//close save file
			fileServer.close(openFile.get());
			try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(0, 1000));
			} catch (Exception e) {
				
			}		
		}
		k++;
	}
	
	public static void main(String[] args) {
		MyFileServer fileServer = new MyFileServer();
		fileServer.create("test1", "I like bananas and stuff");
		//fileServer.create("one1351", "Bananas and stuff like me");
		//Make a for loop?
		for (int i = 0; i < 2; i++) {
			Client client = new Client("Client" + i, fileServer);
			Thread thread = new Thread(client);
			thread.start();
		}
	}
}