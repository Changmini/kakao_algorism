package advertising;


/*
	https://tech.kakao.com/2021/01/25/2021-kakao-recruitment-round-1/#%EB%AC%B8%EC%A0%9C-5-%EA%B4%91%EA%B3%A0-%EC%82%BD%EC%9E%85
	
	[문제]
	"죠르디"의 동영상 재생시간 길이 play_time, 공익광고의 재생시간 길이 adv_time, 시청자들이 해당 동영상을 재생했던 구간 정보 logs가 매개변수로 주어질 때, 시청자들의 누적 재생시간이 가장 많이 나오는 곳에 공익광고를 삽입하려고 합니다.
	 이때, 공익광고가 들어갈 시작 시각을 구해서 return 하도록 solution 함수를 완성해주세요.
	 만약, 시청자들의 누적 재생시간이 가장 많은 곳이 여러 곳이라면, 그 중에서 가장 빠른 시작 시각을 return 하도록 합니다.

	[제한사항]
	play_time, adv_time은 길이 8로 고정된 문자열입니다.
		play_time, adv_time은 HH:MM:SS 형식이며, 00:00:01 이상 99:59:59 이하입니다.
		즉, 동영상 재생시간과 공익광고 재생시간은 00시간 00분 01초 이상 99시간 59분 59초 이하입니다.
		공익광고 재생시간은 동영상 재생시간보다 짧거나 같게 주어집니다.
	logs는 크기가 1 이상 300,000 이하인 문자열 배열입니다.
		logs 배열의 각 원소는 시청자의 재생 구간을 나타냅니다.
		logs 배열의 각 원소는 길이가 17로 고정된 문자열입니다.
		logs 배열의 각 원소는 H1:M1:S1-H2:M2:S2 형식입니다.
			H1:M1:S1은 동영상이 시작된 시각, H2:M2:S2는 동영상이 종료된 시각을 나타냅니다.
			H1:M1:S1는 H2:M2:S2보다 1초 이상 이전 시각으로 주어집니다.
			H1:M1:S1와 H2:M2:S2는 play_time 이내의 시각입니다.
	시간을 나타내는 HH, H1, H2의 범위는 00~99, 분을 나타내는 MM, M1, M2의 범위는 00~59,
	초를 나타내는 SS, S1, S2의 범위는 00~59까지 사용됩니다. 잘못된 시각은 입력으로 주어지지 않습니다. (예: 04:60:24, 11:12:78, 123:12:45 등)

	return 값의 형식
		공익광고를 삽입할 시각을 HH:MM:SS 형식의 8자리 문자열로 반환합니다.

	예) 01:00:00에 공익광고를 삽입하면 26:00:00까지 재생되며, 이곳이 가장 좋은 위치입니다. 이 구간의 시청자 누적 재생시간은 다음과 같습니다.
		* 01:00:00-11:00:00 : 해당 구간이 1회(2번 기록) 재생되었으므로 누적 재생시간은 10시간 00분 00초 입니다.
		* 11:00:00-21:00:00 : 해당 구간이 2회(2번, 4번 기록) 재생되었으므로 누적 재생시간은 20시간 00분 00초 입니다.
		* 21:00:00-26:00:00 : 해당 구간이 1회(4번 기록) 재생되었으므로 누적 재생시간은 05시간 00분 00초 입니다.
		* 따라서, 이 구간의 시청자 누적 재생시간은 10시간 00분 00초 + 20시간 00분 00초 + 05시간 00분 00초 = 35시간 00분 00초 입니다.
		* 초록색으로 표시된 구간(69:59:59-94:59:59)에 광고를 삽입해도 동일한 결과를 얻을 수 있으나, 01:00:00이 69:59:59 보다 빠른 시각이므로, "01:00:00"을 return 합니다.
 */
public class Advertising {
	int convert(String time) {
		String[] nums = time.split(":");
		return Integer.parseInt(nums[0]) * 60 * 60 +
			Integer.parseInt(nums[1]) * 60 +
			Integer.parseInt(nums[2]);
	}
	
	public String solution(String play_time, String adv_time, String[] logs) {
		int palySec = convert(play_time);
		int advSec = convert(adv_time);

		int[] totalSec = new int[100 * 3600];
		for(String log : logs) {
			int start = convert(log.substring(0, 8));
			int end = convert(log.substring(9, 17));
			for(int i = start; i < end; ++i) {
				totalSec[i] += 1;
			}
		}

		long currSum = 0;
		for(int i = 0; i < advSec; ++i) {
			currSum += totalSec[i];
		}

		long maxSum = currSum;
		int maxIdx = 0;
		for(int i = advSec; i < palySec; ++i) {
			currSum = currSum + totalSec[i] - totalSec[i - advSec];
			if(currSum > maxSum) {
				maxSum = currSum;
				maxIdx = i - advSec + 1;
			}
		}
		
		return String.format("%02d:%02d:%02d", maxIdx / 3600
											 , maxIdx / 60 % 60
											 , maxIdx % 60);
	}
	
	public static void main(String[] args) {
		
		String[] play_time = {"02:03:55", "99:59:59", "50:00:00"};
		String[] adv_time = {"00:14:15", "25:00:00", "50:00:00"};
		String[][] logs = {
			{"01:20:15-01:45:14", "00:40:31-01:00:00", "00:25:50-00:48:29", "01:30:59-01:53:29", "01:37:44-02:02:30"}
			,{"69:59:59-89:59:59", "01:00:00-21:00:00", "79:59:59-99:59:59", "11:00:00-31:00:00"}
			,{"15:36:51-38:21:49", "10:14:18-15:36:51", "38:21:49-42:51:45"}
		};
		String[] result = {"01:30:59", "01:00:00", "00:00:00"};
		
		System.out.println(solution(play_time[1], adv_time[1], logs[1]));
		

	} // main
}
