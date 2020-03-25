package cn.itcast.travel.service.impl;

import java.util.Map;
import cn.itcast.travel.util.ArithmeticUtil;

public class ArithemeticServiceImpl implements ArithemeticService {

	@Override
	public Map<String, Double> generateExercise(String[] symbolStringArray, int figure, int numberMounts, boolean isBrackets, int itemNumber) {
		return ArithmeticUtil.getItems(symbolStringArray, figure, numberMounts, isBrackets, itemNumber);
	}
}
