package thread;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MemoryObserver implements Runnable {
    

    @Override
    public void run() {
        boolean interrupted = false;
        Runtime runtime = Runtime.getRuntime();
        long oldUserdMemory = runtime.maxMemory() - runtime.freeMemory();
        while (!interrupted) {
            try {
                
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String date = sdf.format(calendar.getTime());
                
                long totalMemory = runtime.totalMemory();
                long maxMemory = runtime.maxMemory();
                long freeMemory = runtime.freeMemory();
                long usedMemory = maxMemory - freeMemory;
                long upUsedMeomory = usedMemory - oldUserdMemory;
//                System.out.printf("%s\tTotal:%d,\tMax:%d,\tFree:%d,\tUsed:%d,\tUp:%d%n",
//                       date,  totalMemory, maxMemory, freeMemory, usedMemory, upUsedMeomory);
                System.out.println(usedMemory);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("MemoryObserver is interrrupted.");
                interrupted = true;
            }
        }
    }
}
