import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class MyFileServer implements FileServer {
	private HashMap<String,String> fileStore; //TODO set, list, map
	private HashMap<String,ReadWriteSem> locks;
	
	
	public MyFileServer(){
		fileStore = new HashMap<String,String>(10);
		locks = new HashMap<String,ReadWriteSem>(10);
	}
	
	@Override
	public void create(String filename, String content) {
		fileStore.put(filename, content); //returns false if already exists, catch?
		locks.put(filename, new ReadWriteSem());
	}

	@Override
	public Optional<File> open(String filename, Mode mode) {
				//TODO how to say this is/isn't readable
				//return Optional.ofNullable(file);
		//if file exists: new File(fileStore[locataion].key,fileStore[locataion].value,mode)
		//return new file
			
		//return empty if filename doesn't exist3
		if(!fileStore.containsKey(filename) || mode == Mode.CLOSED || mode == Mode.UNKNOWN){
			return Optional.empty();
		}
		try{
			if(mode == Mode.READABLE) {
				locks.get(filename).readRequest(); // Will it block?			
			} else if(mode == Mode.READWRITEABLE) {
				locks.get(filename).writeRequest(); // Will it block?
				System.out.println(Thread.currentThread().getName() + " has write lock on " + filename);							
			}

		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " lock acquire went awry");
		}
		
		String acquiredFile = fileStore.get(filename); //attempt to get??
		return Optional.of(new File(filename, acquiredFile, mode));
		
	}

	@Override
	public void close(File file) {	
		
		if(locks.get(file.filename()).getReadCounter() > 0){//Read mode ==============================Deny line 97, persistent files
			locks.get(file.filename()).readDone();
		} else if(locks.get(file.filename()).getWritePermits() == 0) { //Write mode
			fileStore.put(file.filename(), file.read());
			locks.get(file.filename()).writeDone();
			System.out.println(Thread.currentThread().getName() + " released write lock on " + file.filename());
		}
	}

	@Override
	public Mode fileStatus(String filename) {
		// TODO return File status
		if(!locks.containsKey(filename)) {
			return Mode.UNKNOWN;
		}
		else if(locks.get(filename).getReadCounter() > 0) {
			return Mode.READABLE;
		}
		else if (locks.get(filename).getWritePermits() == 0) {
			return Mode.READWRITEABLE;
		}
		return Mode.CLOSED;
	}

	@Override
	public Set<String> availableFiles() {
		return fileStore.keySet();
	}

}
