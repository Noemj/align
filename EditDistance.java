public class EditDistance {

  private static String m;
  private static int M;
  private static String n;
  private static int N;

  public static void main(String[] args) {
    if(args.length < 2) {
      System.err.println("Please provide two strings.");
      System.exit(0);
    }
    initializeVariables(args);
    int[][] opt = compute(calculatePenalty(initialize(new int[M + 1][N + 1])));
    System.out.println("The edit distance is: " + opt[0][0]);
    recoverAlignment(opt);
  }

  private static void initializeVariables(String[] args) {
    m = args[0];
    M = m.length();
    n = args[1];
    N = n.length();
  }

  //aligning an empty string with another string of length k requires inserting k gaps for a total cost of 2k. 
  //we can therefore set opt[M][i] = 2(N-i) and opt[j][N] = 2(M-j).
  private static int[][] initialize(int[][] opt) {
    for(int i = 0; i < N; i++) {
      opt[M][i] = 2 * (N - i);
    }
    for(int j = 0; j < M; j++) {
      opt[j][N] = 2 * (M - j);
    }
    return opt;
  }

  //the cost of mismatch is 1 while the cost of match is 0
  private static int[][] calculatePenalty(int[][] opt) {
    for(int i = M - 1; i >= 0; i--) {
      for(int j = N - 1; j >= 0; j--) {
        opt[i][j] = penalty(m.charAt(i), n.charAt(j));
      }
    }
    return opt;
  }

  //opt[i][j] denotes the edit distance of m[i..M] and n[j..N], where M is the length of m and N the length of n.
  //for the first pair of characters in an optimal alignment of m[i..M] with n[j..N], the optimal alignment has three possibilities:
  //m[i] is matched with n[j]. the penalty is either 0 or 1, and m[i+1..M] still has to be aligned with n[j+1..N].
  //m[i] is matched with a gap. the penalty is 2, and m[i+1..M] still has to be aligned with n[j..N].
  //n[j] is matched with a gap. the penalty is 2, and m[i..M] still has to be aligned with n[j+1..N].
  //all the resulting subproblems are sequence alignment problems on suffixes of the original inputs, and we can calculate
  //opt[i][j] by taking the minimum of opt[i+1][j+1] + 0/1, opt[i+1][j] + 2 and opt[i][j+1] + 2.
  private static int[][] compute(int[][] opt) {
    for(int i = M - 1; i >= 0; i--) {
      for(int j = N - 1; j >= 0; j--) {
        opt[i][j] = min(opt[i + 1][j + 1] + (m.charAt(i) == n.charAt(j) ? 0 : 1),
          opt[i + 1][j] + 2, opt[i][j + 1] + 2);
      }
    }
    return opt;
  }

  //to recover the optimal alignment, retrace the steps of the dynamic algorithm backwards
  private static void recoverAlignment(int[][] opt) {
    int i = 0; int j = 0;
    while(i < M || j < N) {
      if (j == N){
        System.out.println(m.charAt(i) + " " + '-' + " " + '2');
        i++;
      } else if (i == M){
        System.out.println('-' + " " + n.charAt(j) + " " + '2');
        j++;
      } else if(opt[i][j] == opt[i + 1][j + 1] && m.charAt(i) == n.charAt(j)) {
        System.out.println(m.charAt(i) + " " + n.charAt(j) + " " + '0');
        i++; j++;
      } else if(opt[i][j] == opt[i + 1][j + 1] + 1) {
        System.out.println(m.charAt(i) + " " + n.charAt(j) + " " + '1');
        i++; j++;
      } else if(opt[i][j] == opt[i + 1][j] + 2) {
        System.out.println(m.charAt(i) + " " + '-' + " " + '2');
        i++;
      } else if(opt[i][j] == opt[i][j + 1] + 2) {
        System.out.println('-' + " " + n.charAt(j) + " " + '2');
        j++;
      }
    }
  }

  private static int penalty(char a, char b) {
    if(a == b) {
      return 0;
    }
    return 1;
  }

  private static int min(int a, int b, int c) {
    return Math.min(Math.min(a, b), c);
  }
}
