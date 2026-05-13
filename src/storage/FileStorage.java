package storage;


import java.io.*;

public class FileStorage {

    public void save(BankData data){
        try{
            FileOutputStream file_output = new FileOutputStream("bank_data.ser");
            ObjectOutputStream object_output = new ObjectOutputStream(file_output);
            object_output.writeObject(data);
            object_output.close();
            file_output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public BankData load(){
        try{
            FileInputStream file_input = new FileInputStream("bank_data.ser");
            ObjectInputStream object_input = new ObjectInputStream(file_input);
            BankData data = (BankData) object_input.readObject();
            object_input.close();
            file_input.close();
            return data;
        } catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
