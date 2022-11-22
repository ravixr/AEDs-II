class Palindromo {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static String isPalindrome (String s){
      String resp = "SIM";
      if (s.length() == 0) resp = "NAO";
      else {
         for (int i = 0, j = s.length() - 1; i < (s.length() / 2); i++, j--)
            if (s.charAt(i) != s.charAt(j)) {
               resp = "NAO";
               break;
            }
      }
      return resp;
   }

   public static void main (String[] args){
      String[] entrada = new String[1000];
      int numEntrada = 0;

      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (isFim(entrada[numEntrada++]) == false);
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada; i++){
         MyIO.println(isPalindrome(entrada[i]));
      }
   }
}
