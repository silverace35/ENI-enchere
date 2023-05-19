package fr.eni.enchere.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.servlet.http.Part;

public class Utils {
	 public static String sha256(String mdp) throws NoSuchAlgorithmException {
        // salt statique, connu uniquement du serveur, pour davantage de sécurité. à mettre plutôt en variable d'instance normalement
        final String ENI_SALT = "Z_K_T";
        String entree = ENI_SALT + mdp;
        
        // choix de l'algorithme de hachage
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        
        // on procède au hachage
        byte[] hashResultat = digest.digest(entree.getBytes(StandardCharsets.UTF_8));
        
        // on transforme le tableau d'octect, en une chaine hexadémicale
        StringBuilder chaineResultat = new StringBuilder(2 * hashResultat.length);
        for (int i = 0; i < hashResultat.length; i++) {
            // 0xff est en binaire 11111111. laisse uniquement intact la valeur des 8 derniers bits, et ignore le reste.
            String hex = Integer.toHexString(0xff & hashResultat[i]);
            if(hex.length() == 1)
                // on ajoute un charactère 0, pour toujours avoir deux digits, même si la valeur hexa pouvait s'écrire sur un seul
                chaineResultat.append('0');
            chaineResultat.append(hex);
        }
        
        // on retourne la chaine résultat, exemple : e509591cd337c083bf5dc75e737ab9d13f3e22503a0aac4808e458b780351a6f (64 caractères)
        return chaineResultat.toString();
    }
	 
	 /**
		 * Sauvegarder le fichier image
		 * @param appPath
		 * @param part
		 * @return
		 * @throws IOException
		 */
		public static String saveFile(String directory, String appPath, Part part) throws IOException {
	        appPath = appPath.replace('\\', '/');
	 
	        // The directory to save uploaded file
	        String fullSavePath = null;
	        if (appPath.endsWith("/")) {
	            fullSavePath = appPath + directory;
	        } else {
	            fullSavePath = appPath + "/" + directory;
	        }
	        
	        // Creates the save directory if it does not exists
	        File fileSaveDir = new File(fullSavePath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdir();
	            System.out.println("Created the dir");
	        }
	        String filePath=null;

	        String fileName = extractFileName(part);
	        System.out.println(fileName);
	        String[] fn = fileName.split("(\\.)");
	        fileName = fn[0];
	        String ext = fn[(fn.length-1)];
	        if(!ext.isEmpty()) {
	        	//generate a unique file name
	        	UUID uuid = UUID.randomUUID();
	        	fileName = fileName + "_" + uuid.toString() + "." + ext ;
	        	if (fileName != null && fileName.length() > 0) {
	        		filePath = fullSavePath + File.separator + fileName;
	        		System.out.println("Write attachment to file: " + filePath);
	        		// Write to file
	        		part.write(filePath);
	        	}
	        }
	        return fileName;
		}
		
		/**
		 * extraire le nom du fichier provenant du client
		 * @param part
		 * @return
		 */
	    public static String extractFileName(Part part) {
	        String contentDisp = part.getHeader("content-disposition");
	        String[] items = contentDisp.split(";");
	        for (String s : items) {
	            if (s.trim().startsWith("filename")) {
	                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
	                clientFileName = clientFileName.replace("\\", "/");
	                int i = clientFileName.lastIndexOf('/');
	                return clientFileName.substring(i + 1);
	            }
	        }
	        return null;
	    }

}
