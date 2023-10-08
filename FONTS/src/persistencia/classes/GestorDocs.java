package persistencia.classes;

import domini.exceptions.JaExisteixException;
import domini.exceptions.NoExisteixException;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class GestorDocs extends GestorDisc{

		public GestorDocs(){}

		/**
		 * Llegeix el string de un arxiu donat per un path
		 *
		 * @param path representa el path del document del qual es vol conseguir el seu string
		 * @return Un String que representa el contingut del document que s'ha llegit
		 */
		public String llegirStringArxiu(String path){
				String line = null;
				StringBuilder stringBuilder = new StringBuilder();
				try {
						FileReader fileReader = new FileReader(path);
						BufferedReader bufferedReader = new BufferedReader(fileReader);
						while((line = bufferedReader.readLine()) != null) {
								stringBuilder.append(line + "\n");
						}
						bufferedReader.close();
				} catch(IOException ex) {
						System.out.println("Error reading file '" + path + "'");
				}
				return stringBuilder.toString();
		}

		/**
		 * Exporta un document del programa al sistema operatiu amb el nom final donat i el string final donat
		 *
		 * @param nomFinal representa el path on s'exportarà el fitxer
		 * @param res representa el string que contindrà aquest fitxer
		 */
		public void exportarDocument(String nomFinal, String res) throws JaExisteixException{
				File doc = new File(nomFinal);
				try{
						if(doc.createNewFile()){
								FileWriter fw = new FileWriter(doc.getAbsoluteFile());
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(res);
								bw.close();
						}
						else throw new JaExisteixException("Ja existeix un fitxer amb aquest nom");
				}
				catch (IOException ioe){
						ioe.printStackTrace();
				}
		}

		/**
		 * Elimina de disc el document amb el titol i el autor donat
		 *
		 * @param titol representa el titol del document que es vol eliminar de disc
		 * @param autor representa el autor del document que es vol eliminar de disc
		 */
		public void eliminarDoc(String titol, String autor){
				Path path = FileSystems.getDefault().getPath("src" + File.separator + "files" + File.separator + "docs" + File.separator + titol + "_" + autor);
				Path pathFrases = FileSystems.getDefault().getPath("src" + File.separator + "files" + File.separator + "docs" + File.separator + "continguts" + File.separator + "frases_" + titol + "_" + autor);
				Path pathContingut = FileSystems.getDefault().getPath("src" + File.separator + "files" + File.separator + "docs" + File.separator + "continguts" + File.separator + "contingut_" + titol + "_" + autor);
				try{
						Files.deleteIfExists(path);
						Files.deleteIfExists(pathFrases);
						Files.deleteIfExists(pathContingut);
				}
				catch (IOException x){
						System.err.println(x);
				}
		}
}
