public class BookSelf {
    int W;
    int[][] books;
    int[][] dp;
/*
 Vijay kumar swarnkar
 This a test message
 */

    int solve(int i, int width, int height) {
        if (width < 0) return Integer.MAX_VALUE;
        if (i == books.length) return height;
        if (dp[width][height] != -1) return dp[width][height];
        int x = solve(i + 1, width - books[i][0], Math.max(height, books[i][1]));
        int y = height + solve(i + 1, W - books[i][0], books[i][0]);
        return Math.min(x, y);
    }

    public int minHeightShelves(int[][] books, int shelfWidth) {
        this.W = shelfWidth;
        this.books = books;
        this.dp = new int[1001][1001];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return solve(0, W, 0);
    }

    public static void main(String[] args) {
        System.out.println(new BookSelf().minHeightShelves(new int[][]{
                {1, 1}, {2, 3}, {2, 3}, {1, 1}, {1, 1}, {1, 1}, {1, 2}
        }, 4));
    }
}


class LowerBound {
    int lessThanOrEqual(int[] arr, int val) {
        return lessThanOrEqual(arr, val, 0, arr.length-1);
    }

    int lessThanOrEqual(int[] arr, int val, int st, int ed) {
        if(st > ed){
            return ed;
        }
        int mid = (st+ed)/2;
        if(arr[mid] <= val){
            return lessThanOrEqual(arr, val, mid+1, ed);
        } else {
            return lessThanOrEqual(arr, val, st, mid-1);
        }
    }

    int greaterThanOrEqual(int[] arr, int val) {
        return greaterThanOrEqual(arr, val, 0, arr.length-1);
    }

    int greaterThanOrEqual(int[] arr, int val, int st, int ed) {
        if(st > ed){
            return ed+1;
        }
        int mid = (st+ed)/2;
        if(arr[mid] <= val){
            return greaterThanOrEqual(arr, val, mid+1, ed);
        } else {
            return greaterThanOrEqual(arr, val, st, mid-1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new LowerBound().lessThanOrEqual(new int[]{1, 3, 5, 7, 7, 10, 20}, 8));
//        System.out.println(new LowerBound().greaterThanOrEqual(new int[]{1, 3, 5, 7, 10, 20}, 8));
    }
}

//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 1));
//        list.add(Arrays.asList(0, 0, 0, 1, 0, 1, 0, 1, 0, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 1, 0, 0, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 1, 0, 0, 0, 0, 0));
//        list.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 1, 0, 0));
//        list.add(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0, 0, 0));