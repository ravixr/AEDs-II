import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.DateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/*class Element {
	public Object element;
}*/

class Movie /*extends Element*/ {
    private String name;
    private String oTitle;
    private Date releaseDate;
    private int runtime;
    private String genre;
    private String oLang;
    private String status;
    private float budget;
    private String[] keywords;
    public static long numComp = 0;
    public static long numMov = 0;


    Movie() {
        this.name = ""; this.oTitle = ""; this.genre = ""; this.oLang = ""; this.status = "";
    }

    Movie(String name, String oTitle, Date releaseDate, int runtime,
          String genre, String oLang, String status, float budget, String[] keywords) {
        this.name = name; this.oTitle = oTitle; this.releaseDate = releaseDate;
        this.runtime = runtime; this.genre = genre; this.oLang = oLang; this.status = status;
        this.budget = budget; this.keywords = keywords;
    }

    public String getName() {
        return this.name;
    }

    public String getOTitle() {
        return this.oTitle;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public String getOLang() {
        return this.oLang;
    }

    public String getStatus() {
        return this.status;
    }

    public float getBudget() {
        return this.budget;
    }

    public String[] getKeywords() {
        return this.keywords;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOTitle(String oTitle) {
        this.oTitle = oTitle;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setOLang(String oLang) {
        this.oLang = oLang;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public Movie clone(){
        Movie clone = new Movie();
        clone.name = this.name;
        return clone;
    }

    public static void printMovie(Movie m) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        MyIO.print(m.getName());
        MyIO.print(" " + m.getOTitle());
        MyIO.print(" " + df.format(m.getReleaseDate()));
        MyIO.print(" " + m.getRuntime());
        MyIO.print(" " + m.getGenre());
        MyIO.print(" " + m.getOLang());
        MyIO.print(" " + m.getStatus());
        MyIO.print(" " + m.getBudget());
        MyIO.print(" [");
        String[] aux = m.getKeywords();
        if (aux != null && aux.length > 0) {
            for (int i = 0; i < aux.length - 1; i++)
                MyIO.print(aux[i] + ", ");
            MyIO.print(aux[aux.length - 1]);
        }
        MyIO.println("]");
    }

    public static String removeTags(String s) {
        String aux = "";
        boolean isTag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '<') isTag = true;
            else if (s.charAt(i) == '>') isTag = false;
            if (!isTag && s.charAt(i) != '<' && s.charAt(i) != '>') aux += s.charAt(i);
        }
        return aux;
    }

    public static Date stringToDate(String s) {
        SimpleDateFormat aux = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = aux.parse(s.substring(0, 10));
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public static String removeHtmlEntities(String s) {
        String aux = "";
        boolean isHtmlEntity = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '&') isHtmlEntity = true;
            else if (s.charAt(i) == ';') isHtmlEntity = false;
            if (!isHtmlEntity && s.charAt(i) != '&' && s.charAt(i) != ';') aux += s.charAt(i);
        }
        return aux;
    }

    public static int stringToMin(String s) {
        int result = 0;
        String[] aux = s.split(" ");
        if (aux.length > 1) {
            result += 60 * Integer.parseInt(aux[0].substring(0, (aux[0].length() - 1)));
            result += Integer.parseInt(aux[1].substring(0, (aux[1].length() - 1)));
        } else result += Integer.parseInt(aux[0].substring(0, (aux[0].length() - 1)));
        return result;
    }

    public static String removeDataName(String s, String dataName) {
        return s.substring(dataName.length());
    }

    public static float moneyToFloat(String s) {
        String aux = "";
        float f;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) != '$' && s.charAt(i) != ',') aux += s.charAt(i);
        try {
            f = Float.parseFloat(aux);
        } catch (NumberFormatException e) {
            f = 0.00f;
        }
        return f;
    }

    public static String[] parseKeywords(BufferedReader br) throws IOException{
        String[] keywords = null;
        br.readLine();
        if (!(new String(new String(removeTags(br.readLine())).trim()).equals("Nenhuma palavra-chave foi adicionada."))) {
            String line = "";
            for (int i = 0; i < 2; i++) line = br.readLine();
            String[] aux = new String[30];
            int count = 0;
            while (!line.contains("</ul>")) {
                if (line.contains("<li>")) {
                    aux[count] = removeTags(line).trim();
                    if (aux[count] != "") count++;
                }
                line = br.readLine();
            }
            keywords = new String[count];
            for (int i = 0; i < count; i++) keywords[i] = aux[i];
        }
        return keywords;
    }

    public static Movie readMovie(String fileName) throws IOException{
        String path;
        if (System.getProperty("os.name").contains("Linux") &&
                !System.getProperty("os.version").contains("WSL")) path = "/tmp/filmes/";
        else path = "tmp/filmes/";
        FileReader fr = new FileReader(path + fileName);
        BufferedReader br = new BufferedReader(fr);
        Movie m = new Movie();
        String line;
        while((line = br.readLine()) != null) {
            if (line.contains("h2 class")) {
                m.setName((removeTags(br.readLine())).trim());
                m.setOTitle(m.getName());
            } else if (line.contains("\"release\"")) {
                m.setReleaseDate(stringToDate((br.readLine()).trim()));
            } else if (line.contains("\"genres\"")) {
                br.readLine();
                m.setGenre(removeHtmlEntities((removeTags(br.readLine())).trim()));
            } else if (line.contains("\"runtime\"")) {
                br.readLine();
                m.setRuntime(stringToMin((br.readLine()).trim()));
            } else if (line.contains("Título original")) {
                m.setOTitle((removeDataName((removeTags(line)).trim(), "Título original")).trim());
            } else if (line.contains("Situação")) {
                m.setStatus((removeDataName((removeTags(line)).trim(), "Situação")).trim());
            } else if (line.contains("Idioma original")) {
                m.setOLang((removeDataName((removeTags(line)).trim(), "Idioma original")).trim());
            } else if (line.contains("Orçamento")) {
                m.setBudget(moneyToFloat((removeDataName((removeTags(line)).trim(), "Orçamento")).trim()));
            } else if (line.contains("Palavras-chave")) {
                m.setKeywords(parseKeywords(br));
            }
        }
        fr.close();
        return m;
    }
}

class Cell {
    public Object element;
    public Cell next;
    public Cell prev;

    public Cell() {
        this.element = null;
        this.next = null;
        this.prev = null;
    }

    public Cell(Object el) {
        this.element = el;
        this.next = null;
        this.prev = null;
    }
}

class DoublyLinkedList {
    private Cell first;
    private Cell last;
    int size;

    public DoublyLinkedList() {
        first = new Cell();
        last = first;
        size = 0;
    }

    public Cell getFirst() {
        return first;
    }

    public Cell getLast() {
        return last;
    }
    public Cell cellAt(int x) throws Exception {
        if (x < 0 || x > size) throw new Exception("Invalid index specified");
        Cell c = first;
        for(int i = 0; i < x; i++, c = c.next);
        return c;
    }

    public void insertAtStart(Object obj) {
    if (size == 0) {
        first = new Cell(obj);
        last = first;
    } else {
        Cell tmp = first;
        first = new Cell(obj);
        first.next = tmp;
        tmp.prev = first;
        tmp = null;
    }
    size++;
    }


    /**
     * Insere um elemento na ultima posicao da lista.
     * @param x int elemento a ser inserido.
     */
    public void insertAtEnd(Object obj) {
        if (size == 0) {
            last = new Cell(obj);
            first = last;
        } else {
            Cell tmp = new Cell(obj);
            tmp.prev = last;
            last.next = tmp;
            last = tmp;
            tmp = null;
        }
        size++;
    }


    /**
     * Remove um elemento da primeira posicao da lista.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista nao contiver elementos.
     */
    public Object removeFromStart() throws Exception {
        Object result = null;
        if (size == 0) {
            result = first.element;
            last = first = null;
        } else {
            result = first.element;
            first = first.next;
            first.prev = null;
        }
        size--;
        return result;
    }


    /**
     * Remove um elemento da ultima posicao da lista.
     * @return resp int elemento a ser removido.
     * @throws Exception Se a lista nao contiver elementos.
     */
    public Object removeFromEnd() throws Exception {
        Object result = null;
        if (size == 0) {
            result = last.element;
            last = first = null;
        } else {
            result = last.element;
            last = last.prev;
            last.next = null;
        }
        size--;
        return result;
    }


    /**
     * Insere um elemento em uma posicao especifica considerando que o
     * primeiro elemento valido esta na posicao 0.
     * @param x int elemento a ser inserido.
     * @param pos int posicao da insercao.
     * @throws Exception Se <code>posicao</code> invalida.
     */
    public void insert(Object obj, int pos) throws Exception {
        if(pos < 0 || pos > size){
            throw new Exception("Invalid index specified");
        } else if (pos == 0){
            insertAtStart(obj);
        } else if (pos == size){
            insertAtEnd(obj);
        } else {
            Cell c = cellAt(pos);
            Cell tmp = new Cell(obj);
            tmp.prev = c.prev;
            tmp.next = c;
            c.prev = tmp;
            tmp = c = null;
            size++;
        }
    }


    /**
     * Remove um elemento de uma posicao especifica da lista
     * considerando que o primeiro elemento valido esta na posicao 0.
     * @param posicao Meio da remocao.
     * @return resp int elemento a ser removido.
     * @throws Exception Se <code>posicao</code> invalida.
     */
    public Object remove(int pos) throws Exception {
        Object result;

        if (first == last){
            throw new Exception("List is empty");
        } else if(pos < 0 || pos >= size){
            throw new Exception("Invalid index specified");
        } else if (pos == 0){
            result = removeFromStart();
        } else if (pos == size){
            result = removeFromEnd();
        } else {
            Cell c = cellAt(pos);
            result = c.element;
            c.prev.next = c.next;
            c.next.prev = c.prev;
            c = null;
            size--;
        }
        return result;
    }
}

class TP03Q13 {

    /*
     * A quicksort string comparison algorithm that receives a DublyLinkedList instance,
     * casts its elements to the Movie class, and compares by the member variable "status".
     */
    public static void mListQuicksortByStatus(DoublyLinkedList mList, int left, int right) throws Exception {
        Cell temp;
        int i = left, j = right;
        String pivot = Movie.class.cast(mList.cellAt((left+right)/2).element).getStatus();
        while (i <= j) {
            while (i < right && Movie.class.cast(mList.cellAt(i).element).getStatus().compareTo(pivot) < 0) { i++; Movie.numComp+=2; }
            while (j > left && Movie.class.cast(mList.cellAt(j).element).getStatus().compareTo(pivot) > 0) { j--; Movie.numComp+=2; }
            if (i <= j) {
                //Only Swaps if movie's name at positon j is lexicography smaller than movie's name at positon i;
                if (Movie.class.cast(mList.cellAt(i).element).getName().compareTo(
                        Movie.class.cast(mList.cellAt(j).element).getName()) > 0) {
                    temp = mList.cellAt(i);
                    mList.insert(mList.cellAt(j).element, i);
                    mList.insert(temp.element, j);
                    Movie.numMov+=3;
                }
                i++;
                j--;
            }
            Movie.numComp+=2;
        }
        if (left < j) { Movie.numComp++; mListQuicksortByStatus(mList, left, j); }
        if (i < right) { Movie.numComp++; mListQuicksortByStatus(mList, i, right); }
    }

    public static void printLogFile(String fileName, long numComp, long numMov, long duration) throws Exception {
        PrintWriter pw = new PrintWriter(fileName, "UTF-8");
        pw.println("751441\t" + numComp + "\t" + numMov + "\t" + duration + "ns\t");
        pw.close();
    }

    public static boolean isFim(String s){
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
    }

    public static void main (String[] args) throws Exception {
        System.setProperty("file.encoding","UTF-8");
        MyIO.setCharset("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        String[] fileNames = new String[1000];
        int[] inputLength = new int[2];
        //Leitura da entrada padrao
        do {
            fileNames[inputLength[0]] = br.readLine();
        } while (isFim(fileNames[inputLength[0]++]) == false);
        inputLength[0]--;   //Desconsiderar ultima linha contendo a palavra FIM
        //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
        DoublyLinkedList mList = new DoublyLinkedList();
        for(int i = 0; i < inputLength[0]; i++){
            mList.insertAtEnd(Movie.readMovie(fileNames[i]));
            //Movie.printMovie(mList.movies[i]);
        }
        long duration = System.nanoTime();
        mListQuicksortByStatus(mList, 0, mList.size - 1);
        duration = (System.nanoTime() - duration);
        int i = 0;
        for(Cell c = mList.getFirst(); i < inputLength[0]; c = c.next, i++){
            Movie.printMovie(Movie.class.cast(c.element));
        }
        printLogFile("751441_quicksort2.txt", Movie.numComp, Movie.numMov, duration);

    }
}
