class TP01Q06 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static boolean onlyVowel (String s) {
      boolean bl = true;
      if (s.length() == 0) bl = false;
      else
         // testa se há algum caractere diferente de uma vogal
         for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != 'a' && s.charAt(i) != 'e' && s.charAt(i) != 'i' && s.charAt(i) != 'o' && s.charAt(i) != 'u' &&
            s.charAt(i) != 'A' && s.charAt(i) != 'E' && s.charAt(i) != 'I' && s.charAt(i) != 'O' && s.charAt(i) != 'U') {
               bl = false;
               break;
            }
         }
      return bl;
   }

   public static boolean onlyCons (String s) {
      boolean bl = true;
      if (s.length() == 0) bl = false;
      else
         // testa se há algum caractere igual a uma vogal
         for (int i = 0; i < s.length(); i++) {
            if ((s.charAt(i) < 'a' || s.charAt(i) > 'z') && (s.charAt(i) < 'A' || s.charAt(i) > 'Z') || 
            (s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u' ||
            s.charAt(i) == 'A' || s.charAt(i) == 'E' || s.charAt(i) == 'I' || s.charAt(i) == 'O' || s.charAt(i) == 'U')) {
               bl = false;
               break;
            }
         }
      return bl;
   }

   public static boolean isInt (String s) {
      boolean bl = true;
      if (s.length() == 0) bl = false;
      else
         // testa se há algum caractere diferente de um dígito
         for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
               bl = false;
               break;
            }
         }
      return bl;
   }

   public static boolean isFloat (String s) {
      boolean bl = true;
      int dot = 0;
      if (s.length() == 0) bl = false;
      else {
         for (int i = 0; i < s.length(); i++) {
            // testa se há algum caractere diferente de um dígito
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
               // se sim testa se é uma virgula ou ponto
               if (s.charAt(i) == ',' || s.charAt(i) == '.') {
                  dot++;
               } else {
                  bl = false;
                  break;
               }
            }
         }
      }
      // se possuir apenas numeros porém mais de uma virgula e/ou ponto logo não é um número real
      if (bl && dot > 1) bl = false;
      return bl;
   }

   public static String printMyOutput (String s){
      String text;
      if (onlyVowel(s)) {
         text = "SIM NAO NAO NAO";
      } else if (onlyCons(s)) {
         text = "NAO SIM NAO NAO";
      } else if (isInt(s)) {
         if (isFloat(s)) text = "NAO NAO SIM SIM";
         else text = "NAO NAO SIM NAO";
      } else if (isFloat(s)) {
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
