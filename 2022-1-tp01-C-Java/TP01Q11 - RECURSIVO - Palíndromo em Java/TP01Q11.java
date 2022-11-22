class TP01Q11 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

    public static String isPalindrome (String s){
        int i = 0, j = s.length() - 1; 
        if (s.length() <= 1){
            return "SIM";
        } else if (s.charAt(i) == s.charAt(j)) {
            return isPalindrome(s.substring(i+1, j));
        } else return "NAO";
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
        //int aux = (entrada[i].length() - 1);
        MyIO.println(isPalindrome(entrada[i]));
      }
   }
}