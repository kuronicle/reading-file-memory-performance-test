package thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ByteArraySearch implements Runnable {

    private String filePath = "";

    @Override
    public void run() {
        
        File inputFile = new File(filePath);
        int fileSize = (int) inputFile.length();
        
        byte[] fileBytes = new byte[fileSize];
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(
                    new FileInputStream(inputFile));
            
            inputStream.read(fileBytes);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
        for(int i = 0; i < fileSize; i++) {
            byte b = fileBytes[i];
        }

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
