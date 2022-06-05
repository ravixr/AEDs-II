class TP01Q05 {

   public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }

   public static String idk (String s){
      int n = s.charAt(0) - '0';
      int[] val = new int[n], idx = new int[n];
      for (int i = 0, j = 2; i < n; i++, j+=2) {
         val[i] = s.charAt(j) - '0';
      }
      int start = (n + 1) * 2; // para saber em qual posicao comeca a expressao
      s = s.substring(start);
      for (int i = 0, j = 0; i < s.length(); i++) {
         if (s.charAt(i) == 'A') {
            s = s.substring(0, i) + val[0] + s.substring(i + 1);
            idx[0] = i;
         } else if (s.charAt(i) == 'B') {
            s = s.substring(0, i) + val[1] + s.substring(i + 1);
            idx[1] = i;
         } else if (s.charAt(i) == 'C') {
            s = s.substring(0, i) + val[2] + s.substring(i + 1);
            idx[2] = i;
         }
      }
      boolean flag = true;
      while (flag)
         switch (s.charAt((idx[i] - 2))) {
            case 't':
               if (val[i] == 1) val[i] = 0;
               else val[i] = 1;
               s = s.substring(0, idx[i] - 4) + val[i] + s.substring(idx[i] + 2);
               for (int j = i; j < idx.length; j++) if (i == j) idx[j] -= 4; else idx[j] -= 5;
               break;
               //s += " " + idx[1];
            case 'd':
               if (val[i] == 0) {
                  s = "0";
                  flag = false;
                  break;
               } else if ((s.charAt((idx[i] + 4)) - '0') == 0) {
                  s = "0";
                  flag = false;
                  break;
               } else {
                  s = "1";
                  flag = false;
                  break;
               }
               //+3
            case 'r':
               //+2
         }
      return s;
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
         MyIO.println(idk(entrada[i]));
      }
   }
}
