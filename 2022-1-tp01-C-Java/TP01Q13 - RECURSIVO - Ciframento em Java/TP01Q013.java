class TP01Q013 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static String caesarCipher (String s, int i){
      String cipher = "";
      int shift = 3, aux;
      if (i < s.length()) {
         aux = s.charAt(i) + shift;
         cipher += (char) aux;
         cipher += caesarCipher(s, ++i);
      }
      return cipher;
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
         MyIO.println(caesarCipher(entrada[i], 0));
      }
   }
}