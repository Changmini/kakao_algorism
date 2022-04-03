package dijkstra;

public class TaxiFare {
	static final int INF = 40000000;
	static int [][] Dist = new int[200][200];
	
	static void floyd(int n) {
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (Dist[i][k] + Dist[k][j] < Dist[i][j]) {
						Dist[i][j] = Dist[i][k] + Dist[k][j];
					}
				}
			}
		}
	}
	
	public static int solution(int n, int s, int a, int b, int[][] fares) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) Dist[i][j] = 0;
				else  Dist[i][j] = INF;
			}
		}
			
		for (int[] edge : fares) {
			Dist[edge[0]-1][edge[1]-1] = edge[2];
			Dist[edge[1]-1][edge[0]-1] = edge[2];
		}
			
		floyd(n);
		
		--s;
		--a;
		--b;
		
		int answer = INF;
		for (int k = 0; k < n; k++) {
			answer = Math.min(answer, Dist[s][k] + Dist[k][a] + Dist[k][b]);
		}
		return answer;
	}
	
	public static void main(String[] args) {
		int[] n = {6, 7, 6};
		int[] s = {4, 3, 4};
		int[] a = {6, 4, 5};
		int[] b = {2, 1, 6};
		int[][][] fares = {
				{
					{4,1,10},{3,5,24},{5,6,2},{3,1,41},{5,1,24}
					,{4,6,50},{2,4,66},{2,3,22},{1,6,25}
				}
				,{
					{5,7,9},{4,6,4},{3,6,1},{3,2,3},{2,1,6},
				}
				,{
					{2,6,6},{6,3,7},{4,6,7},{6,5,11},{2,5,12},{5,3,20}
					,{2,4,8},{4,3,9},
				}
		};
		int[] result = {82, 14, 18};
		
		for (int i = 0; i < 3; i++) {
			System.out.println( result[i] == solution(n[i], s[i], a[i], b[i], fares[i]) );
		}
		
		
	} // main
}
