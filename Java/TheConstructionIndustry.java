package construction;

import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class TheConstructionIndustry {

/*
	https://tech.kakao.com/2019/10/02/kakao-blind-recruitment-2020-round1/
	
	빙하가 깨지면서 스노우타운에 떠내려 온 “죠르디”는 인생 2막을 위해 주택 건축사업에 뛰어들기로 결심하였습니다. 
	“죠르디”는 기둥과 보를 이용하여 벽면 구조물을 자동으로 세우는 로봇을 개발할 계획인데, 그에 앞서 로봇의 동작을 시뮬레이션 할 수 있는 프로그램을 만들고 있습니다.
	프로그램은 2차원 가상 벽면에 기둥과 보를 이용한 구조물을 설치할 수 있는데, 기둥과 보는 길이가 1인 선분으로 표현되며 다음과 같은 규칙을 가지고 있습니다.
	
	>> (기둥)은 바닥 위에 있거나 보의 한쪽 끝 부분 위에 있거나, 또는 다른 (기둥) 위에 있어야 합니다.
	>> (보)는 한쪽 끝 부분이 기둥 위에 있거나, 또는 양쪽 끝 부분이 다른 (보)와 동시에 연결되어 있어야 합니다.
	단, 바닥은 벽면의 맨 아래 지면을 말합니다.
	
	2차원 벽면은 n x n 크기 정사각 격자 형태이며, 각 격자는 1 x 1 크기입니다. 맨 처음 벽면은 비어있는 상태입니다.
	기둥과 보는 격자선의 교차점에 걸치지 않고, 격자 칸의 각 변에 정확히 일치하도록 설치할 수 있습니다.
	다음은 기둥과 보를 설치해 구조물을 만든 예시입니다.
	
  y축                                                 _ : 보
	5                     | : 기둥
	4
	3
	2       __ __ __
	1    __|        |
	    |           |
	0   1  2  3  4  5
	                      x축
	예를 들어, 위 그림은 다음 순서에 따라 구조물을 만들었습니다.
	>> (1, 0)에서 위쪽으로 기둥을 하나 설치 후, (1, 1)에서 오른쪽으로 보를 하나 만듭니다.
	>> (2, 1)에서 위쪽으로 기둥을 하나 설치 후, (2, 2)에서 오른쪽으로 보를 하나 만듭니다.
	>> (5, 0)에서 위쪽으로 기둥을 하나 설치 후, (5, 1)에서 위쪽으로 기둥을 하나 더 설치합니다.
	>> (4, 2)에서 오른쪽으로 보를 설치 후, (3, 2)에서 오른쪽으로 보를 설치합니다.
	
	만약 (4, 2)에서 오른쪽으로 보를 먼저 설치하지 않고, (3, 2)에서 오른쪽으로 보를 설치하려 한다면 2번 규칙에 맞지 않으므로 설치가 되지 않습니다.
	기둥과 보를 삭제하는 기능도 있는데 기둥과 보를 삭제한 후에 남은 기둥과 보들 또한 위 규칙을 만족해야 합니다.
	만약, 작업을 수행한 결과가 조건을 만족하지 않는다면 해당 작업은 무시됩니다.
	
	벽면의 크기 n, 기둥과 보를 설치하거나 삭제하는 작업이 순서대로 담긴 2차원 배열 build_frame이 매개변수로 주어질 때,
	모든 명령어를 수행한 후 구조물의 상태를 return 하도록 solution 함수를 완성해주세요.
	
	>> n은 5 이상 100 이하인 자연수입니다.
	>> build_frame의 세로(행) 길이는 1 이상 1,000 이하입니다.
	>> build_frame의 가로(열) 길이는 4입니다.
	>> build_frame의 원소는 [x, y, a, b]형태입니다.
	      - x, y는 기둥, 보를 설치 또는 삭제할 교차점의 좌표이며, [가로 좌표, 세로 좌표] 형태입니다.
	      - a는 설치 또는 삭제할 구조물의 종류를 나타내며, 0은 기둥, 1은 보를 나타냅니다.
	      - b는 구조물을 설치할 지, 혹은 삭제할 지를 나타내며 0은 삭제, 1은 설치를 나타냅니다.
	      - 벽면을 벗어나게 기둥, 보를 설치하는 경우는 없습니다.
	      - 바닥에 보를 설치 하는 경우는 없습니다.
	>> 구조물은 교차점 좌표를 기준으로 보는 오른쪽, 기둥은 위쪽 방향으로 설치 또는 삭제합니다.
	>> 구조물이 겹치도록 설치하는 경우와, 없는 구조물을 삭제하는 경우는 입력으로 주어지지 않습니다.
	>> 최종 구조물의 상태는 아래 규칙에 맞춰 return 해주세요.
	      - return 하는 배열은 가로(열) 길이가 3인 2차원 배열로, 각 구조물의 좌표를 담고있어야 합니다.
	      - return 하는 배열의 원소는 [x, y, a] 형식입니다.
	      - x, y는 기둥, 보의 교차점 좌표이며, [가로 좌표, 세로 좌표] 형태입니다.
	      - 기둥, 보는 교차점 좌표를 기준으로 오른쪽, 또는 위쪽 방향으로 설치되어 있음을 나타냅니다.
	      - a는 구조물의 종류를 나타내며, 0은 기둥, 1은 보를 나타냅니다.
	      - return 하는 배열은 x좌표 기준으로 오름차순 정렬하며, x좌표가 같을 경우 y좌표 기준으로 오름차순 정렬해주세요.
	      - x, y좌표가 모두 같은 경우 기둥이 보보다 앞에 오면 됩니다.
	
	n  build_frame                                                                                            result
	5  [[1,0,0,1],[1,1,1,1],[2,1,0,1],[2,2,1,1],[5,0,0,1],[5,1,0,1],[4,2,1,1],[3,2,1,1]]                      [[1,0,0],[1,1,1],[2,1,0],[2,2,1],[3,2,1],[4,2,1],[5,0,0],[5,1,0]]
	5  [[0,0,0,1],[2,0,0,1],[4,0,0,1],[0,1,1,1],[1,1,1,1],[2,1,1,1],[3,1,1,1],[2,0,0,0],[1,1,1,0],[2,2,0,1]]  [[0,0,0],[0,1,1],[1,1,1],[2,1,1],[3,1,1],[4,0,0]]
	                      
*/
	
	
	public static int[][] solution(int n, int[][] build_frame) {
		
		int frameCnt = build_frame.length;
		boolean[][] colPoint = new boolean[n][n];
		boolean[][] boPoint = new boolean[n][n];
		
		for (int i = 0; i < frameCnt; i++) {
			int x = build_frame[i][0];             // x좌표
			int y = build_frame[i][1];             // y좌표
			short buildType = (short)build_frame[i][2]; // 기둥 or 보
			int isBuild = build_frame[i][3];        // 생성-삭제 여부
			
			if (isBuild == 1) { // 건설 진행
				if (buildType==0) colPoint[x][y] = true;
				else boPoint[x][y] = true;
			} else {            // 건설 취소
				if (colPoint[x][y]) colPoint[x][y] = false;
				else boPoint[x][y] = false;
			}
		}
		
		int[][] result = new int[frameCnt][3];
		int reCnt = 0;
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				if (!colPoint[x][y] && !boPoint[x][y]) continue;
				if (y==0) {
					boPoint[x][0] = false;
					continue;
				}
				int isCol = -1, isBo = -1;
				int my = (y-1 > 0) ? y-1 : y;
				int mx = (x-1 > 0) ? x-1 : x;
				int px = (x+1 < n) ? x+1 : x;
				
				if (colPoint[x][y]) 
					isCol = (boPoint[x][y] || boPoint[mx][y] || colPoint[x][my])?0:-1;
				if (boPoint[x][y]) {
					isBo = (colPoint[x][my] || colPoint[px][my] || boPoint[mx][y] || boPoint[px][y])?1:-1;
				}
				if (isCol==0) {
					result[reCnt][0] = x;
					result[reCnt][1] = y;
					result[reCnt++][2] = isCol;
				} 
				if (isBo==1) {
					result[reCnt][0] = x;
					result[reCnt][1] = y;
					result[reCnt++][2] = isBo;
				}
				if (isBo==-1 && isCol ==-1) {
					result[reCnt][0] = -1;
					result[reCnt][1] = -1;
					result[reCnt++][2] = -1;
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		int[][] build_frame = {{1,0,0,1},{1,1,1,1},{2,1,0,1},{2,2,1,1},{5,0,0,1},{5,1,0,1},{4,2,1,1},{3,2,1,1}};
		int[][] build_frame2 =
			{{0,0,0,1},{2,0,0,1},{4,0,0,1},{0,1,1,1},{1,1,1,1},{2,1,1,1},{3,1,1,1},{2,0,0,0},{1,1,1,0},{2,2,0,1}};
		int[][] re = solution(5, build_frame2);
		
		for (int i = 0; i < build_frame2.length; i++) {
			System.out.print(re[i][0]);
			System.out.print(re[i][1]);
			System.out.print(re[i][2]);
			System.out.println();
		}
	}
	
} // class













