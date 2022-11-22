import java.util.Random;

class TP01Q04 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static String randomLetterChange (String s, Random r){
      char chToBeChanged = (char)('a' + (Math.abs (r.nextInt()) % 26)), chToReplace = (char)('a' + (Math.abs (r.nextInt()) % 26));
      String newStr = "";
      /*Loop para passar os caracteres da string original para uma nova string 
      alterando aqueles que forem iguais ao primeiro numero gerado pelo segundo*/
      for (int i =  0; i < s.length(); i++) {
         if (s.charAt(i) == chToBeChanged)
            newStr += chToReplace;
         else newStr += s.charAt(i);
      }
      return newStr;
   }

   public static void main (String[] args){
      String[] entrada = new String[1000];
      int numEntrada = 0;
      //Classe random deve estar fora da funcao pois esta e chamada varias vezes mudando o seu valor
      Random r = new Random();
      r.setSeed(4);
      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (isFim(entrada[numEntrada++]) == false);
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada; i++){
         MyIO.println(randomLetterChange(entrada[i], r));
      }
   }
}
