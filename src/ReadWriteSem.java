import java.util.concurrent.Semaphore;

public class ReadWriteSem {
	private Semaphore readAccess;
	private Semaphore writeAccess;
	
	public ReadWriteSem(int permits) {
		readAccess = new Semaphore(permits);
		writeAccess = new Semaphore(1);
	}
	
	public void readRequest() {
		try {
			readAccess.acquire();
		} catch (Exception e) {
			//Do dar do dar day
		}
	}
	
	public void readDone() {
		try {
			readAccess.release();
		} catch (Exception e) {
			//Do dar do dar day
		}		
	}
}
