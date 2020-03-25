package cn.itcast.travel.service.impl;

import java.util.Map;

public interface ArithemeticService {
	
	/**
	 * 生成四则运算题目
	 * @param symbolStringArray 参与运算的符号
	 * @param figure 位数
	 * @param numberMounts 数字的数目
	 * @param isBrackets 是否包含括号
	 * @param itemNumber 生成多少题目
	 * @return
	 */
	public Map<String, Double> generateExercise(String[] symbolStringArray, int figure, int numberMounts, boolean isBrackets, int itemNumber);
}
