import java.io.*;
import java.net.*;

class TP01Q08 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }
    // Retirado de ExemploURL.java
       public static String getHtml(String endereco) {
      URL url;
      InputStream is = null;
      BufferedReader br;
      String resp = "", line;

      try {
         url = new URL(endereco);
         is = url.openStream();  // throws an IOException
         br = new BufferedReader(new InputStreamReader(is));

         while ((line = br.readLine()) != null) {
            resp += line + "\n";
         }
      } catch (MalformedURLException mue) {
         mue.printStackTrace();
      } catch (IOException ioe) {
         ioe.printStackTrace();
      } 

      try {
         is.close();
      } catch (IOException ioe) {
         // nothing to see here

      }

      return resp;
    }

    public static boolean isLowerCaseLetter (char c) {
        return (c >= 'a' && c <= 'z');
    }

    public static boolean isBrTag (String s, int i) {
        boolean isBrTag = false;
        if ((i + 3) > (s.length() - 1)) isBrTag = false;
        else if (s.charAt(i) == '<' && s.charAt(i + 1) == 'b' && 
        s.charAt(i + 2) == 'r' && s.charAt(i + 3) == '>') isBrTag = true;
        return isBrTag;
    }

    public static boolean isTableTag (String s, int i) {
        boolean isTableTag = false;
        if ((i + 6) > (s.length() - 1)) isTableTag = false;
        else if (s.charAt(i) == '<' && s.charAt(i + 1) == 't' && s.charAt(i + 2) == 'a' && 
        s.charAt(i + 3) == 'b' && s.charAt(i + 4) == 'l' && s.charAt(i + 5) == 'e' && s.charAt(i + 6) == '>') isTableTag = true;
        return isTableTag;
    }

    public static int[] countStuff(String s) {
        int[] count = new int[25];
        char[] vowels = {'a', 'e', 'i', 'o', 'u', 'á', 'é', 'í', 'ó', 'ú', 'à', 'è', 'ì', 'ò', 'ù', 'ã', 'õ', 'â', 'ê', 'î', 'ô', 'û'};
        for (int i = 0; i < s.length(); i++) {
            if (isBrTag(s, i)) { 
                count[23]++;
                i += 4;
            } else if (isTableTag(s, i)) {
                count[24]++;
                i += 7;
            } else {
                boolean isVowel = false;
                for (int j = 0; j < vowels.length; j++)
                    if (s.charAt(i) == vowels[j]) {
                        count[j]++;
                        isVowel = true;
                        break;
                    }
                if (!isVowel && (isLowerCaseLetter(s.charAt(i)))) count[22]++;
            }
        }
        return count;
    }

    public static String printMyOutput (String name, String url){
        String html = getHtml(url);
        int[] count = countStuff(html);
        String text = "a("+ count[0] +") e("+ count[1] +") i("+ count[2] +") o("+ count[3] +") u("+ count[4] +") á("+ count[5] +
        ") é("+ count[6] +") í("+ count[7] +") ó("+ count[8] +") ú("+ count[9] +") à("+ count[10] +") è("+ count[11] +") ì("+ count[12] +
        ") ò("+ count[13] +") ù("+ count[14] +") ã("+ count[15] +") õ("+ count[16] +") â("+ count[17] +") ê("+ count[18] +") î("+ count[19] +
        ") ô("+ count[20] +") û("+ count[21] +") consoante("+ count[22] +") <br>("+ count[23] +") <table>("+ count[24] +") " + name;
        return text;
    }

   public static void main (String[] args){
      String[] entrada = new String[1000];
      int numEntrada = 0;
      System.setProperty("file.encoding","UTF-8");
      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (isFim(entrada[numEntrada++]) == false);
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada; i += 2){
         System.out.println(printMyOutput(entrada[i], entrada[i + 1]));
      }
   }
}