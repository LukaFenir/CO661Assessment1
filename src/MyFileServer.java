import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class MyFileServer implements FileServer {
	private HashMap<String,String> fileStore; //TODO set, list, map
	private HashMap<String,ReadWriteSem> locks;
	

	//TODO get a file searcher method
	
	public MyFileServer(){
		fileStore = new HashMap<String,String>(10);
		locks = new HashMap<String,ReadWriteSem>(10);
	}
	
	@Override
	public void create(String filename, String content) {
		fileStore.put(filename, content); //returns false if already exists, catch?
		locks.put(filename, new ReadWriteSem(6));
	}

	@Override
	public Optional<File> open(String filename, Mode mode) {
				//TODO how to say this is/isn't readable
				//return Optional.ofNullable(file);
		//if file exists: new File(fileStore[locataion].key,fileStore[locataion].value,mode)
		//return new file
			
		//semaphory shiz?
		if(!fileStore.containsKey(filename)){
			return Optional.empty();
		}
		try{
			locks.get(filename).readRequest();
			System.out.println(Thread.currentThread().getName() + " has the lock on " + filename);
		} catch (Exception e) {
			System.out.println(Thread.currentThread().getName() + " lock acquire went awry");
		}
		
		String openFile = fileStore.get(filename); //attempt to get??
		return Optional.of(new File(filename, openFile, mode));
		
	}

	@Override
	public void close(File file) {
		fileStore.put(file.filename(), file.read());
		locks.get(file.filename()).readDone();
		System.out.println(Thread.currentThread().getName() + " released lock on " + file.filename());

	}

	@Override
	public Mode fileStatus(String filename) {
		// TODO return File status
		
		return Mode.UNKNOWN;
	}

	@Override
	public Set<String> availableFiles() {
		return fileStore.keySet();
	}

}
