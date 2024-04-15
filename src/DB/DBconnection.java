package DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DBconnection {
    private static DBconnection connection = null;
    private final String storageFile = "DBStudent.dat";
    public static DBconnection getInstance()
    {
    	if(connection == null)
    	{
    		connection = new DBconnection();
    	}
    	return connection;
    }
    
    public void writeFile(Object obj)
    {
         try {
			ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream(storageFile));
			objectOutput.writeObject(obj);
			objectOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    }
    public Object readFile() throws ClassNotFoundException
    {
    	try (ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(storageFile));) {
			return objectInput.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
}
