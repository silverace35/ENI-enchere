package fr.eni.enchere.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class Logger {
	//private static Logger instance;
	private final static String LOG_NAME = "log.txt";
	
	private Logger() {
	}
	
//	public static Logger getInstance() {
//		if (instance == null) {
//			return new Logger();
//		} else {
//			return instance;
//		}
//	}
	
	/** Constuction d'un msg de log à partir des paramètres et inscription dans le fichier. Création du fichier si ce dernier n'existe pas.
	 * @param appPath
	 * @param location
	 * @param userId
	 * @param action
	 * @param articleId
	 * @param str
	 */
	public static void log(String appPath, String location, Integer userId, String action, Integer articleId, String... str) {
		// Construction du message
		StringBuilder sb = new StringBuilder();
		sb.append(LocalDateTime.now().toString());
		sb.append(" | Controller : ").append(location);
		sb.append(" | userId : ").append(userId);
		sb.append(action==""?"":" | "+action);
		if(articleId!=null) {
			sb.append(" | articleId : ").append(articleId);
		}
		for(String s: str) {
			sb.append(" | "+s);
		}
		sb.append("\n");
		String msg = sb.toString();
		System.out.println(msg);
		// Définition du chemin du fichier
		Path path = Paths.get(appPath,LOG_NAME);
		File log = new File(path.toString());
			try {
				
				// Création du fichier
				log.createNewFile();
				
				// Inscription du message dans le log
				Files.write(path, msg.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	

}
