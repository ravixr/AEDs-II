import java.io.RandomAccessFile;

class TP01Q09 {

   /*public static boolean isFim(String s){
      return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
   }*/

   public static void main (String[] args) throws Exception {
      RandomAccessFile raf = new RandomAccessFile("my.out", "rw");
      int input = MyIO.readInt();
      long p = raf.getFilePointer();
      for (int i = 0; i < input; i++) {
         raf.seek(p + i * 8);
         raf.writeDouble(MyIO.readDouble());
      }
      raf.close();

      raf = new RandomAccessFile("my.out", "r");
      double output;
      for (int i = ((input - 1) * 8); i >= 0; i -= 8) {
         raf.seek(i);
         output = raf.readDouble();
         if (output - ((int) output) == 0) MyIO.println((int) output);
         else MyIO.println(output);
      }
      raf.close();
   }
}
