import java.util.concurrent.Semaphore;

public class ReadWriteSem {

	private Semaphore writeAccess;
	private Semaphore readCounterAccess;
	private int readCounter;
	private boolean writeFlag;
	
	public ReadWriteSem() {
		
		writeAccess = new Semaphore(1);
		readCounterAccess = new Semaphore(1);
		readCounter = 0;
		writeFlag = false;
	}
	
	public void readRequest() {
		try {
			if(readCounter == 0){
				writeAccess.acquire(); //Disable writes
				System.out.println(Thread.currentThread().getName() + " has first-read lock");	
			}
			readCounterAccess.acquire();
			readCounter++;
			readCounterAccess.release();
			
		} catch (Exception e) {
			//Do dar do dar day
		}
	}
	
	public void readDone() {
		try {
			readCounterAccess.acquire();
			readCounter--;
			readCounterAccess.release();
			if(readCounter == 0) {
				writeAccess.release(); //Hay no reader en esta program
			}
		} catch (Exception e) {
			//Do dar do dar day
		}		
	}
	
	public void writeRequest() {
		try {
			writeAccess.acquire();
			writeFlag = true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void writeDone() {
		writeAccess.release();
		writeFlag = false;
	}	
	
	public int getReadCounter(){
		return readCounter;
	}
	
	public int getWritePermits() {
		return writeAccess.availablePermits();
	}
}
