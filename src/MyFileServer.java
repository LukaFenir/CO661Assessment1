import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class MyFileServer implements FileServer {
	HashMap<String,String> fileStore; //TODO set, list, map
	

	//TODO get a file searcher method
	
	public MyFileServer(){
		fileStore = new HashMap<String,String>(10);
	}
	
	@Override
	public void create(String filename, String content) {
		fileStore.put(filename, content); //returns false if already exists, catch?
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
		String openFile = fileStore.get(filename); //attempt to get??
		return Optional.of(new File(filename, openFile, mode));
		
	}

	@Override
	public void close(File file) {
		// TODO set mode to closed

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
