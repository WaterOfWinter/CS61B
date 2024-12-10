import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private final WeightedQuickUnionUF uf;
    private final boolean[][] grid;
    private final int N;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        uf = new WeightedQuickUnionUF(N * N);
        this.N = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        grid[row][col] = true;
        if (row < N - 1) {
            if (grid[row + 1][col]) {
                uf.union((row)*N+col, (row+1)*N+col);
            }
        }
        if (col < N - 1) {
            if (grid[row][col + 1]) {
                uf.union((row)*N+col, (row)*N+col+1);
            }
        }
        if (row > 0) {
            if (grid[row - 1][col]) {
                uf.union((row)*N+col, (row-1)*N+col);
            }
        }
        if (col > 0) {
            if (grid[row][col - 1]) {
                uf.union((row)*N+col, (row)*N+col-1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (isOpen(row,col)) {
            if (row == 0)
                return true;
            for (int i = 0; i < N; i++) {
                if (uf.connected(row*N+col, i)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (uf.connected(i, j+N*(N-1))) {
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}

// 以下是AI提供：

//import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//
//public class Percolation {
//    private final WeightedQuickUnionUF uf;
//    private final WeightedQuickUnionUF fullUf; // 防止虚拟底部节点导致冗余连通
//    private final int topVirtual; // 顶部虚拟节点
//    private final int bottomVirtual; // 底部虚拟节点
//    private final boolean[][] grid; // 用于记录站点的打开状态
//    private final int n; // 网格大小
//    private int openSites; // 打开的站点数
//
//    // 构造函数
//    public Percolation(int n) {
//        if (n <= 0) {
//            throw new IllegalArgumentException("Grid size must be greater than 0");
//        }
//        this.n = n;
//        this.grid = new boolean[n][n];
//        this.openSites = 0;
//        this.topVirtual = n * n;
//        this.bottomVirtual = n * n + 1;
//
//        uf = new WeightedQuickUnionUF(n * n + 2);
//        fullUf = new WeightedQuickUnionUF(n * n + 1); // 不包含底部虚拟节点
//    }
//
//    // 将 (row, col) 转换为一维索引
//    private int toIndex(int row, int col) {
//        return row * n + col;
//    }
//
//    // 检查索引是否合法
//    private void validate(int row, int col) {
//        if (row < 0 || row >= n || col < 0 || col >= n) {
//            throw new IllegalArgumentException("Index out of bounds");
//        }
//    }
//
//    // 打开站点
//    public void open(int row, int col) {
//        validate(row, col);
//        if (isOpen(row, col)) {
//            return;
//        }
//        grid[row][col] = true;
//        openSites++;
//
//        int index = toIndex(row, col);
//
//        // 连接到相邻的打开站点
//        if (row > 0 && isOpen(row - 1, col)) {
//            uf.union(index, toIndex(row - 1, col));
//            fullUf.union(index, toIndex(row - 1, col));
//        }
//        if (row < n - 1 && isOpen(row + 1, col)) {
//            uf.union(index, toIndex(row + 1, col));
//            fullUf.union(index, toIndex(row + 1, col));
//        }
//        if (col > 0 && isOpen(row, col - 1)) {
//            uf.union(index, toIndex(row, col - 1));
//            fullUf.union(index, toIndex(row, col - 1));
//        }
//        if (col < n - 1 && isOpen(row, col + 1)) {
//            uf.union(index, toIndex(row, col + 1));
//            fullUf.union(index, toIndex(row, col + 1));
//        }
//
//        // 连接到虚拟节点
//        if (row == 0) {
//            uf.union(index, topVirtual);
//            fullUf.union(index, topVirtual);
//        }
//        if (row == n - 1) {
//            uf.union(index, bottomVirtual);
//        }
//    }
//
//    // 判断站点是否打开
//    public boolean isOpen(int row, int col) {
//        validate(row, col);
//        return grid[row][col];
//    }
//
//    // 判断站点是否与顶部连通
//    public boolean isFull(int row, int col) {
//        validate(row, col);
//        return isOpen(row, col) && fullUf.connected(toIndex(row, col), topVirtual);
//    }
//
//    // 返回打开的站点数
//    public int numberOfOpenSites() {
//        return openSites;
//    }
//
//    // 判断系统是否渗透
//    public boolean percolates() {
//        return uf.connected(topVirtual, bottomVirtual);
//    }
//}
//
