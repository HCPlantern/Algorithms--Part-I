import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int size;
    // with two top and bottom virtual site
    private WeightedQuickUnionUF uf;
    // with one top virtual site
    private WeightedQuickUnionUF uf2;
    // number of open sites
    private int openSitesNum;
    // record whether open
    private boolean[] isOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.size = n;
        this.openSitesNum = 0;
        this.isOpen = new boolean[size * size + 1];
        this.uf = new WeightedQuickUnionUF(size * size + 2);
        this.uf2 = new WeightedQuickUnionUF(size * size + 1);

    }

    // begin from (1, 1) -> 1
    private int calIndex(int x, int y) {
        return ((x - 1) * size + y);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        assert (1 <= row && row <= size) && (1 <= col && col <= size);
        int currIndex = calIndex(row, col);
        if (isOpen[currIndex]) return;
        isOpen[currIndex] = true;
        openSitesNum += 1;
        if (row == 1) { // union the top virtual site
            union(currIndex, 0);
        }
        else {
            if (isOpen(row - 1, col)) {
                union(currIndex, calIndex(row - 1, col));
            }
        }

        if (row == size) { // union the bottom virtual site only uf
            uf.union(currIndex, size * size + 1);
        }
        else {
            if (isOpen(row + 1, col)) {
                union(currIndex, calIndex(row + 1, col));
            }
        }

        if ((col > 1) && isOpen(row, col - 1)) {
            union(currIndex, calIndex(row, col - 1));
        }
        if ((col < size) && isOpen(row, col + 1)) {
            union(currIndex, calIndex(row, col + 1));
        }
    }

    private void union(int index1, int index2) {
        uf.union(index1, index2);
        uf2.union(index1, index2);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return isOpen[calIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int currIndex = calIndex(row, col);
        return (uf.find(0) == uf.find(currIndex)) && (uf2.find(0) == uf2.find(currIndex));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(size * size + 1);
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 3);
        System.out.println(percolation.percolates());
    }
}
