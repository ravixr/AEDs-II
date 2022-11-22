import java.util.Scanner;

class TP01Q05 {

   public static String solve(String booleanExp) {
      int varCount = booleanExp.charAt(0) - '0';
      int[] vars = new int[varCount];

      for (int i = 0; i < varCount; i++)
         vars[i] = booleanExp.charAt((i * 2) + 2) - '0';

      booleanExp = booleanExp.substring((varCount + 1) * 2);

      while (booleanExp.contains("(")) {
         int i = booleanExp.length() - 1;

         while (booleanExp.charAt(i) != '(') i--;

         if (booleanExp.charAt(i - 1) == 'd') 
            booleanExp = solveAnd(booleanExp, vars, i - 3);
         else if (booleanExp.charAt(i - 1) == 'r') 
            booleanExp = solveOr(booleanExp, vars, i - 2);
         else if (booleanExp.charAt(i - 1) == 't') 
            booleanExp = solveNot(booleanExp, vars, i - 3);
      }

      return booleanExp;
   }

   public static String solveNot(String booleanExp, int[] vars, int i) {
      String aux = booleanExp.substring(i, i + 6);
      char c = aux.charAt(4);
      if (c > '1') aux = "" + (vars[c - 'A'] == 1 ? 0 : 1);
      else aux = "" + ((c - '0') == 1 ? 0 : 1);
      String s1 = booleanExp.substring(0, i),
      s2 = booleanExp.substring(i + 6);
      return (s1 + aux + s2);
   }

   public static String solveOr(String booleanExp, int[] vars, int i) {
      int j = booleanExp.indexOf(")", i) + 1;
      String aux = booleanExp.substring(i, j);
      int n = aux.length();
      for (int k = 0; k < vars.length; k++) {
         if (aux.contains("1") || (aux.contains("" + (char)('A' + k)) && vars[k] == 1)) {
            aux = "1";
            k = vars.length;
         }
      }
      if (aux.length() > 1) aux = "0";
      return booleanExp.substring(0, i) + aux + booleanExp.substring(i + n);
   }

   public static String solveAnd(String booleanExp, int[] vars, int i) {
      int j = booleanExp.indexOf(")", i) + 1;
      String aux = booleanExp.substring(i, j);
      int n = aux.length();
      for (int k = 0; k < vars.length; k++) {
         if (aux.contains("0") || (aux.contains("" + (char)('A' + k)) && vars[k] == 0)) {
            aux = "0";
            k = vars.length;
         }
      }
      if (aux.length() > 1) aux = "1";
      return booleanExp.substring(0, i) + aux + booleanExp.substring(i + n);
   }

   public static void main (String[] args){
      Scanner sc = new Scanner(System.in);
      String[] entrada = new String[1000];
      int numEntrada = 0;

      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = sc.nextLine();
      } while (entrada[numEntrada++].charAt(0) != '0');
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada; i++){
         System.out.println(solve(entrada[i]));
      }
      sc.close();
   }
}
