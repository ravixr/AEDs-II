class Cell {
    int value;
    Cell top;
    Cell bottom;
    Cell left;
    Cell right;

    public Cell() {
        this.value = 0;
        this.top = null;
        this.bottom = null;
        this.left = null;
        this.right = null;
    }

    public Cell(int value) {
        this.value = value;
        this.top = null;
        this.bottom = null;
        this.left = null;
        this.right = null;
    }
}

class LinkedSquareIntMatrix {
    private Cell start;
    int height;
    int width;
    int n;

    public LinkedSquareIntMatrix() {
        this.start = new Cell();
        this.height = 0;
        this.width = 0;
        this.n = 0;
    }

    public LinkedSquareIntMatrix(int w, int h) throws Exception{
        start = new Cell();
        width = 0;
        for (Cell i = start; width < w; i = i.right, width++) {
            if (width < w - 1) {
                i.right = new Cell();
                i.right.left = i;
            }
            height = 1;
            for (Cell j = i; height < h; j = j.bottom, height++) {
                Cell tmp = new Cell();
                j.bottom = tmp;
                tmp.top = j;
                if (j.left != null) {
                    tmp.left = j.left.bottom;
                    j.left.bottom.right = tmp;
                }
            }
        }
    }

    public boolean isEmpty() {
        return height == 0 && width == 0;
    }

    public int size() {
        return (width * height);
    }

    // Returns the cell at the x, y coordinate
    public Cell cellAt(int x, int y) throws Exception {
        if (isEmpty()) throw new Exception("Matrix is Empty");
        if (x < 0 || y < 0 || x > width || y > height) 
            throw new Exception("'x' or 'y' values must be in between 0 and height or width");
        Cell c = start;
        for(int i = 0; i < x; c = c.right, i++);
        for(int j = 0; j < y; c = c.bottom, j++);
        return c;
    }

    public void add(int data) throws Exception {
        if (n == size()) throw new Exception("Matrix is Full");
        // Convert the position of a cell from an array perspective to the parameters x, y 
        cellAt(n % width, n / width).value = data;
        n++;
    }

    public void printMainDiagonal() throws Exception {
        if (isEmpty()) throw new Exception("Matrix is Empty");
        for(int i = 0, j = 0; i < width; i++, j++)
            MyIO.print(cellAt(i, j).value + " ");
        MyIO.println("");
    }

    public void printAntiDiagonal() throws Exception {
        if (isEmpty()) throw new Exception("Matrix is Empty");
        for(int i = width - 1, j = 0; i >= 0; i--, j++)
            MyIO.print(cellAt(i, j).value + " ");
        MyIO.println("");
    }

    public void print() throws Exception {
        if (isEmpty()) throw new Exception("Matrix is Empty");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                MyIO.print(cellAt(j, i).value + " ");
            }
            MyIO.println("");
        }
    }

    public boolean equalSize(LinkedSquareIntMatrix m) {
        return height == m.height && width == m.width;
    }

    public LinkedSquareIntMatrix sum(LinkedSquareIntMatrix m) throws Exception {
        LinkedSquareIntMatrix aux = new LinkedSquareIntMatrix(width, height);
        if (isEmpty()) throw new Exception("Matrix is Empty");
        if(!equalSize(m)) throw new Exception("Matrixes must have the same size");
        // loop through both matrixes and add the sum of each position into the new matrix 
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                aux.add(m.cellAt(j, i).value + cellAt(j, i).value);
            }
        }
        return aux;
    }

    public LinkedSquareIntMatrix multiply(LinkedSquareIntMatrix m) throws Exception {
        LinkedSquareIntMatrix aux = new LinkedSquareIntMatrix(width, height);
        if (isEmpty()) throw new Exception("Matrix is Empty");
        if(!equalSize(m)) throw new Exception("Matrixes must have the same size");
        Cell c1 = start;
        for(int h = 0; h < width; c1 = c1.bottom, h++) { // Goes to start of next row
            Cell c2 = m.start;
            for (int i = 0; i < height; c2 = c2.right, i++) { // Goes to start of next column
                int tmp = 0;
                for (int j = 0; j < width; j++) { // Loop of the proper multiplication
                    tmp += c1.value * c2.value;
                    if (j < width - 1) { // Goes to next in the row or column
                        c1 = c1.right; c2 = c2.bottom;
                    }
                }
                for (int k = 1; k < height; c1 = c1.left, c2 = c2.top, k++); // Shift reference back to start of row or column
                aux.add(tmp); // Append value in new matrix
            }
        }
        return aux;
    }
}


class TP03Q16 {
    public static void main (String[] args) throws Exception {
        System.setProperty("file.encoding","UTF-8");
        MyIO.setCharset("UTF-8");
        int count = MyIO.readInt();
        for (int i = 0; i < count; i++) {
            LinkedSquareIntMatrix m1 = new LinkedSquareIntMatrix(MyIO.readInt(), MyIO.readInt());
            for (int j = 0; j < m1.size(); j++) m1.add(MyIO.readInt());
            LinkedSquareIntMatrix m2 = new LinkedSquareIntMatrix(MyIO.readInt(), MyIO.readInt());
            for (int j = 0; j < m2.size(); j++) m2.add(MyIO.readInt());
            m1.printMainDiagonal();
            m1.printAntiDiagonal();
            m1.sum(m2).print();
            m1.multiply(m2).print();
        }
    }
}
