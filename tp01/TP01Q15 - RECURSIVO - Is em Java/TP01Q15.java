class TP01Q15 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static boolean onlyVowel (String s, int i) {
      boolean bl = true;
      if (s.charAt(i) != 'a' && s.charAt(i) != 'e' && s.charAt(i) != 'i' && s.charAt(i) != 'o' && s.charAt(i) != 'u' &&
      s.charAt(i) != 'A' && s.charAt(i) != 'E' && s.charAt(i) != 'I' && s.charAt(i) != 'O' && s.charAt(i) != 'U') {
         bl = false;
      } else if (i < (s.length() - 1)) bl = onlyVowel (s, ++i);
      return bl;
   }

   public static boolean onlyCons (String s, int i) {
      boolean bl = true;
      if ((s.charAt(i) < 'a' || s.charAt(i) > 'z') && (s.charAt(i) < 'A' || s.charAt(i) > 'Z') || 
      (s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' ||
      s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')) {
         bl = false;
      } else if (i < (s.length() - 1)) bl = onlyCons(s, ++i);
      return bl;
   }

   public static boolean isInt (String s, int i) {
      boolean bl = true;
      if (s.charAt(i) < '0' || s.charAt(i) > '9') {
         bl = false;
      } else if (i < (s.length() - 1)) bl = isInt(s, ++i);
      return bl;
   }

   public static boolean isFloat (String s, int i, int dot) {
      boolean bl = true;
      if (s.charAt(i) < '0' || s.charAt(i) > '9') {
      // se sim testa se é uma virgula ou ponto
         if (s.charAt(i) == ',' || s.charAt(i) == '.') {
            dot++;
         } else {
            bl = false;
         }
      } if (bl && i < (s.length() - 1)) bl = isFloat(s, ++i, dot);
      // se possuir apenas numeros porém mais de uma virgula e/ou ponto logo não é um número real
      if (dot > 1) bl = false;
      return bl;
   }

   public static String printMyOutput (String s){
      String text;
      if (onlyVowel(s, 0)) {
         text = "SIM NAO NAO NAO";
      } else if (onlyCons(s, 0)) {
         text = "NAO SIM NAO NAO";
      } else if (isInt(s, 0)) {
         if (isFloat(s, 0, 0)) text = "NAO NAO SIM SIM";
         else text = "NAO NAO SIM NAO";
      } else if (isFloat(s, 0, 0)) {
         text = "NAO NAO NAO SIM";
      } else {
         text = "NAO NAO NAO NAO";
      }
      return text;
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
         MyIO.println(printMyOutput(entrada[i]));
      }
   }
}
