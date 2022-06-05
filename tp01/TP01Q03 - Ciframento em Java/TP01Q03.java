class TP01Q03 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static String caesarCipher (String s){
      String cipher = "";
      int shift = 3, aux;
      //Loop que popula a nova string com o caractere de três posições a frente do caractere da string atual
      for (int i = 0; i < s.length(); i++) {
         aux = /*(int)*/ s.charAt(i) + shift;
         cipher += (char) aux;
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
         MyIO.println(caesarCipher(entrada[i]));
      }
   }
}