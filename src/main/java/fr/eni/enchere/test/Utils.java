package fr.eni.enchere.test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
