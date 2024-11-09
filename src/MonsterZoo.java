import java.util.Arrays;
import java.util.stream.IntStream;

public class MonsterZoo {
	double distance = 0.0; //歩いた距離
	int balls = 10; //モンスターを捕まえられるボールの数
	int fruits = 0; //ぶつけるとモンスターが捕まえやすくなるフルーツ

	// 卵は最大9個まで持てる。卵を取得するとeggにtrueが代入され，
	// 移動するたびに,eggDistanceに1.0kmずつ加算される。3km移動するとランダムでモンスターが孵る
	double eggDistance[] = new double[9];
	boolean egg[] = new boolean[9];

	// ユーザがGetしたモンスター一覧
	String userMonster[] = new String[100];

	// モンスター図鑑。モンスターの名前とレア度(0.0~9.0)がそれぞれの配列に保存されている
	// レア度が高いほうが捕まえにくい
	String monsterZukan[] = new String[22];
	double monsterRare[] = new double[22];

	// 呼び出すと1km distanceが増える
	void move() {
		this.distance++;

		// for文を使わずにStreamを使用
		IntStream.range(0, this.egg.length)
				 .filter(i -> this.egg[i] == true)
				 .forEach(i -> this.eggDistance[i]++);

		int flg1 = (int) (Math.random() * 10); // 0,1の場合はズーstation，7~9の場合はモンスター
		if (flg1 <= 1) {
			System.out.println("ズーstationを見つけた！");
			int b = (int) (Math.random() * 3); //ball,fruits,eggがランダムに出る
			int f = (int) (Math.random() * 2);
			int e = (int) (Math.random() * 2);
			System.out.println("ボールを" + b + "個，" + "フルーツを" + f + "個" + "卵を" + e + "個Getした！");
			this.balls += b;
			this.fruits += f;

			if (e >= 1) { // 卵を1つ以上Getしたら
				// for文を使わずにStreamを使用
				IntStream.range(0, this.eggDistance.length)
						.filter(i -> this.egg[i] == false)
						.findFirst()
						.ifPresent(i -> {
							this.egg[i] = true;
							this.eggDistance[i] = 0.0;
						});
			}
		} else if (flg1 >= 7) {
			int m = (int) (this.monsterZukan.length * Math.random()); //monsterZukanからランダムにモンスターを出す
			System.out.println(this.monsterZukan[m] + "が現れた！");

			// for文を使わずにStreamを使用
			IntStream.range(0, 3).filter(i -> this.balls > 0).forEach(i -> { //捕まえる or 3回ボールを投げるまで繰り返す
				int r = (int) (6 * Math.random()); // 0~5までの数字をランダムに返す
				if (this.fruits > 0) {
					System.out.println("フルーツを投げた！捕まえやすさが倍になる！");
					this.fruits--;
					r = r * 2;
				}
				System.out.println(this.monsterZukan[m] + "にボールを投げた");
				this.balls--;
				if (this.monsterRare[m] <= r) {
					System.out.println(this.monsterZukan[m] + "を捕まえた！");
					IntStream.range(0, this.userMonster.length)
						.filter(j -> this.userMonster[j] == null)
						.findFirst()
						.ifPresent(j -> {
							this.userMonster[j] = this.monsterZukan[m];
						});
				} else {
					System.out.println(this.monsterZukan[m] + "に逃げられた！");
				}
			});
		}

		// for文を使わずにStreamを使用
		IntStream.range(0, this.egg.length).filter(i -> this.egg[i]==true && this.eggDistance[i]>=3).forEach(i -> {
			System.out.println("卵が孵った！");
			int m = (int) (this.monsterZukan.length * Math.random());
			System.out.println(this.monsterZukan[m] + "が産まれた！");
			// for文を使わずにStreamを使用
			IntStream.range(0, this.userMonster.length)
				.filter(j -> this.userMonster[j] == null)
				.findFirst()
				.ifPresent(j -> {
					this.userMonster[j] = this.monsterZukan[m];
				});
			this.egg[i] = false;
			this.eggDistance[i] = 0.0;
		});
	}

	public double getDistance() {
		return distance;
	}

	public int getBalls() {
		return balls;
	}

	public int getFruits() {
		return fruits;
	}

	public String[] getUserMonster() {
		return userMonster;
	}

	public void setMonsterZukan(String[] monsterZukan) {
		this.monsterZukan = monsterZukan;
	}

	public void setMonsterRare(double[] monsterRare) {
		this.monsterRare = monsterRare;
	}
}
