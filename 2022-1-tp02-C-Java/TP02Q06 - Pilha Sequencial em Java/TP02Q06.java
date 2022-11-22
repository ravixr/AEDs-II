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

class Movie {
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

	public static String sequentialMovieSearch(Movie[] movies, String movieName) {
		String ans = "NAO";
		for(int i = 0; i < movies.length; i++) {
			++numComp;
			if(movies[i].name.matches(movieName)) ans = "SIM";
		}
		return ans;
	}

	public static void movieQuicksort(Movie[] m, int left, int right) {
		Movie temp;
        int i = left, j = right;
        String pivot = m[(left+right)/2].name;
        while (i <= j) {
            while (m[i].name.compareTo(pivot) < 0 && i < right) { i++; numComp+=2; }
            while (m[j].name.compareTo(pivot) > 0 && j > left) { j--; numComp+=2; }
            if (i <= j) {
				temp = m[i];
				m[i] = m[j];
				m[j] = temp;
                i++;
                j--;
            }
			numComp+=2;
        }
        if (left < j) { numComp++; movieQuicksort(m, left, j); }
        if (i < right) { numComp++; movieQuicksort(m, i, right); }
    }

	public static String binaryMovieSearch(Movie[] m, String movieName) {
		String ans = "NAO";
		int mid, low = 0, high = m.length - 1;
		while(low <= high) {
			numComp+=2;
			mid = low + (high - low) / 2;
			if (movieName.compareToIgnoreCase(m[mid].name) > 0) {
				low = mid + 1;
			} else if (movieName.compareToIgnoreCase(m[mid].name) < 0) {
				numComp++;
				high = mid - 1;
			} else {
				ans = "SIM";
				break;
			}
		}
		return ans;
	}
}

class MovieList {
	public Movie[] movies;
	public int n;

	public MovieList(){}

	public MovieList (int length) {
		this.movies = new Movie[length];
		this.n = 0;
	}

	public void insertAtStart (Movie m) throws Exception{
		if (n >= movies.length) throw new IndexOutOfBoundsException("Index " + n + " is out of bounds!");

		for(int i = n; i > 0; i--) {
			movies[i] = movies[i-1];
		}
   		movies[0] = m;
		n++;
	}

	public void insert (Movie m, int pos) {
		if (n >= movies.length) throw new IndexOutOfBoundsException("Index " + n + " is out of bounds!");
		if (pos < 0 || pos > n) throw new IndexOutOfBoundsException("Index " + pos + " is out of bounds!");

		for(int i = n; i > pos; i--){
			movies[i] = movies[i-1];
		}
		movies[pos] = m;
		n++;
	}

	public void insertAtEnd (Movie m) {
		if (n >= movies.length) throw new IndexOutOfBoundsException("Index " + n + " is out of bounds!");
		movies[n++] = m;
	}

	public Movie removeFromStart() {
		if (n == 0) throw new IndexOutOfBoundsException("List is empty!");
   
		Movie m = movies[0];
		n--;
		for(int i = 0; i < n; i++){
			movies[i] = movies[i+1];
		}
		return m;
	}

	public Movie remove(int pos) {
		if (n == 0) throw new IndexOutOfBoundsException("List is empty!");
		if (pos < 0 || pos >= n) throw new IndexOutOfBoundsException("Index " + pos + " is out of bounds!");
		Movie m = movies[pos];
		n--;
		for(int i = pos; i < n; i++){
			movies[i] = movies[i+1];
		}
		return m;
	}

	public Movie removeFromEnd() {
		if (n == 0) throw new IndexOutOfBoundsException("List is empty!");
		return movies[--n];
	}
}

class MovieStack {
	public Movie[] movies;
	public int n;

	public MovieStack(){}

	public MovieStack (int length) {
		this.movies = new Movie[length];
		this.n = 0;
	}

	public void push(Movie m) {
		if (n >= movies.length) throw new IndexOutOfBoundsException("Stack is full!");
		movies[n++] = m;
	}

	public Movie pop() {
		if (n == 0) throw new IndexOutOfBoundsException("Stack is empty!");
		return movies[--n];
	}
}

class TP02Q06 {

	public static boolean isFim(String s){
		return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M');
	}
   
	public static void main (String[] args) throws IOException {
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
		MovieStack mStack = new MovieStack(100);
		for(int i = 0; i < inputLength[0]; i++) {
			mStack.push(Movie.readMovie(fileNames[i]));
		}

		inputLength[1] = Integer.parseInt(br.readLine());
		String[] input = new String[100];		
		for(int i = 0; i < inputLength[1]; i++)
			input[i] = br.readLine();
	
		for(int i = 0; i < inputLength[1]; i++) {
			if (input[i].startsWith("I"))
				try {
					mStack.push(Movie.readMovie(input[i].substring(2)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			else
				try {
					MyIO.println("(R) " + mStack.pop().getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
		}

		for(int i = 0; i < mStack.n; i++) {
			MyIO.print("[" + i + "] ");
			Movie.printMovie(mStack.movies[i]);
		}
		
	}
}