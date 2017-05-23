package io.ginkgo.test.mycard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.ginkgo.test.mycard.entity.Card;

/**
 * 
 * 
 * @since 1.0.0
 * @author Barry
 */
public class Zhandou {

	// private final static Integer CARD_COUNT = 3;
	// private final static Integer HH_COUNT = 15;

	// 顺序
	public static void step1(List<Card> playA, List<Card> playB) {
		Integer sumA = 0;
		Integer sumB = 0;
		for (int i = 0; i < 3; i++) {
			sumA += playA.get(i).getTl() + playA.get(i).getFl() + playA.get(i).getGj() + playA.get(i).getFy();
			sumB += playB.get(i).getTl() + playB.get(i).getFl() + playB.get(i).getGj() + playB.get(i).getFy();
		}
		if (sumA < sumB) {
			step2(playA, playB);
		} else if (sumA > sumB) {
			step2(playB, playA);
		} else if (sumA == sumB) {
			Random random = new Random();
			if (random.nextInt(2) == 0) {
				step2(playA, playB);
			} else if (random.nextInt(2) == 1) {
				step2(playB, playA);
			}
		}
	}

	// 递归
	public static void step2(List<Card> gjList, List<Card> bgjList) {
		// 2-1
		Card gj = null;
		for (int i = 0; i < 3; i++) {
			if (gjList.get(i).getTl() > 0) {
				gj = gjList.get(i);
				break;
			}
		}
		if (gj == null) {
			System.out.println("over");
			return;
		}
		Card bgj = null;
		for (int i = 0; i < 3; i++) {
			if (bgjList.get(i).getTl() > 0) {
				bgj = bgjList.get(i);
				break;
			}
		}
		if (bgj == null) {
			System.out.println("over");
			return;
		}

		// 2-2
		step3(gj, bgj);
		System.out.println("----------------");

		// 2-3
		step2(bgjList, gjList);
	}

	// 递归2
	public static void step3(Card gj, Card bgj) {
		System.out.println(
				gj.getName() + "=tl:" + gj.getTl() + ",fl:" + gj.getFl() + ",gj:" + gj.getGj() + ",fy:" + gj.getFy());
		System.out.println(bgj.getName() + "=tl:" + bgj.getTl() + ",fl:" + bgj.getFl() + ",gj:" + bgj.getGj() + ",fy:"
				+ bgj.getFy());
		System.out.print(bgj.getTl() + " -> ");
		Integer sh = gj.getGj() - bgj.getFy();
		if (sh <= 0) {
			sh = 1;
		}
		bgj.setTl(bgj.getTl() - sh);
		System.out.println(bgj.getTl());
		if (bgj.getTl() <= 0) {
			System.out.println(bgj.getName() + " died!");
		} else {
			step3(bgj, gj);
		}

	}

	public static void main(String[] args) {
		List<Card> playA = new ArrayList<>();
		playA.add(new Card(11, "11", 3, 3, 2, 2, 0));
		playA.add(new Card(12, "12", 3, 3, 2, 2, 0));
		playA.add(new Card(13, "13", 3, 3, 2, 2, 0));
		List<Card> playB = new ArrayList<>();
		playB.add(new Card(21, "21", 3, 3, 2, 2, 0));
		playB.add(new Card(22, "22", 3, 3, 2, 2, 0));
		playB.add(new Card(23, "23", 3, 3, 2, 2, 0));
		step1(playA, playB);
	}
}