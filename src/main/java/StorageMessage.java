import java.io.Serializable;

/**
 * Created by tjamir on 7/4/17.
 */
public class StorageMessage implements Serializable{


    private String fileName;

    private byte[] data;

    private boolean put;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isPut() {
        return put;
    }

    public void setPut(boolean put) {
        this.put = put;
    }
}
