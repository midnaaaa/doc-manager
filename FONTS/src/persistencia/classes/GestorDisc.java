package persistencia.classes;

import domini.exceptions.NoExisteixException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public abstract class GestorDisc {

    /**
     * Guarda un objecte amb el path donat a la carpeta src/files/ + nomF
     *
     * @param o representa el objecte que es vol guardar a disc
     * @param nomF representa el path on es guardarà l'objecte a partir de src/files/
     */
    public void guardarObjecte(Object o, String nomF){
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        try {
            fileOutputStream = new FileOutputStream("src/files/"+nomF);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(o);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega un objecte del path donat
     *
     * @param nomF representa el path que es vol carregar
     * @return Un Object que representa el objecte carregat
     */
    public Object carregarObjecte(String nomF) throws IOException, NoExisteixException{
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        Object o = new Object();
        try {
            Files.createDirectories(Paths.get("src/files/docs/continguts"));
            fileInputStream = new FileInputStream("src/files/"+nomF);
            objectInputStream = new ObjectInputStream(fileInputStream);
            o = (Object) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            throw new NoExisteixException("No existeix el binari");
        }
        return o;
    }
}
