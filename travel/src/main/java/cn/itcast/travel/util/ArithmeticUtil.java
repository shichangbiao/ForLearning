package cn.itcast.travel.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ArithmeticUtil {

	/**
	 * 根据传入的符号，随机返回一个符号
	 * 
	 * @param symbolStringArray 数学符号
	 * @return 随机的一个数学符号
	 */
	public static String getSymbolRandom(String symbolStringArray[]) {
		Random random = new Random();
		// 随机产生索引
		int randomIndex = random.nextInt(symbolStringArray.length);
		// 返回索引对应的数学符号
		return symbolStringArray[randomIndex];
	}

	/**
	 * 随机产生一个figure位的整数
	 * 
	 * @param figure 位数
	 * @return
	 */
	public static String randomNumberProducer(int figure) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < figure; i++) {
			sb.append(9);
		}
		int maxNumber = Integer.valueOf(sb.toString());
		Random random = new Random();
		int randomNumber = random.nextInt(maxNumber);
		if (randomNumber == 0) {
			randomNumber = randomNumber + 1;
		}
		return String.valueOf(randomNumber);
	}

	/**
	 * 获取四则运算
	 * @param symbolStringArray	运算字符
	 * @param figure 数字位数
	 * @param numberMounts	运算数的数目
	 * @param isBrackets	是否带括号
	 * @param itemNumber	题目数目
	 * @return
	 */
	public static Map<String, Double> getItems(String symbolStringArray[], int figure, int numberMounts, boolean isBrackets, int itemNumber) {
		ArithmeticUtil arithmeticUtil = new ArithmeticUtil();
		Map<String, Double> retMap = new HashMap<String, Double>();
		String item = null;
		for(int y = 0; y<itemNumber; y++) {
			item = IntegerItemProducer(symbolStringArray, figure, numberMounts, isBrackets);
			if(Double.valueOf(arithmeticUtil.CalculationForInteger(item.replace("[","(").replace("]", ")")))<0) {
				y--;
			} else {
				System.out.println(item.replace("*", "x").replace("/", "÷") + "=" + Double.valueOf(arithmeticUtil.CalculationForInteger(item.replace("[","(").replace("]", ")"))));
				retMap.put(item.replace("*", "x").replace("/", "÷"), Double.valueOf(arithmeticUtil.CalculationForInteger(item.replace("[","(").replace("]", ")"))));
			}
		}
		// 返回map
		return retMap;
	}
	
	/**
	 * 返回一个整式
	 * 
	 * @param symbolStringArray
	 * @param figure
	 * @param numberMounts
	 * @return
	 */
	public static String IntegerItemProducer(String symbolStringArray[], int figure, int numberMounts, boolean isBrackets) {

		// 判断传递过来的数组是否包含/号
		boolean isContentDivide = false;
		for (int i = 0; i < symbolStringArray.length; i++) {
			if (symbolStringArray[i].equals("/")) {
				isContentDivide = true;
				numberMounts = numberMounts - 2;
				break;
			}
		}

		// 排除symbolStringArray中的除号
		String[] temp_symbolStringArray = null;
		if (true == isContentDivide) {
			temp_symbolStringArray = new String[symbolStringArray.length - 1];
		}
		if (true != isContentDivide) {
			temp_symbolStringArray = new String[symbolStringArray.length];
		}
		for (int i = 0, j = 0; i < symbolStringArray.length; i++) {
			if (!"/".equals(symbolStringArray[i])) {
				temp_symbolStringArray[j] = symbolStringArray[i];
				j++;
			}
		}

		// 判断是否包含括号
		if (isBrackets) {
			numberMounts = numberMounts - 3;
		}

		// 动态生成算式
		// 1、不带括号，不带/号
		StringBuilder sb = new StringBuilder();
		if (false == isContentDivide && false == isBrackets) {
			for (int i = 1; i <= numberMounts; i++) {
				// 判断是否是最后一位
				if (i == numberMounts) {
					sb.append(randomNumberProducer(figure));
				} else {
					sb.append(randomNumberProducer(figure) + getSymbolRandom(symbolStringArray));
				}
			}
		}

		// 2、带括号和带除号
//		if (true == isBrackets) {
			// 1、判断numberMounts==0
			if (0 == numberMounts) {
				sb.append(itemWidthDivide(figure) + getSymbolRandom(temp_symbolStringArray) + itemWithBrackets(figure));
			}

			// 2、如果numberMounts!=0
			if (0 != numberMounts) {
				for (int i = 1; i <= numberMounts; i++) {
					sb.append(randomNumberProducer(figure) + getSymbolRandom(temp_symbolStringArray));
				}

				// 判断是否包含除
				if (true == isContentDivide) {
					sb.append(itemWidthDivide(figure));
				}

				// 包含除，包含括号
				if (true == isBrackets && true == isContentDivide) {
					sb.append(getSymbolRandom(temp_symbolStringArray) + itemWithBrackets(figure));
				}

				// 不含除，包括括号
				if (true == isBrackets && true != isContentDivide) {
					sb.append(itemWithBrackets(figure));
				}
			}
//		}
		return sb.toString();
	}

	/**
	 * 返回一个带括号的单元，例如（12-34），占用两个计算位
	 * 
	 * @param figure
	 * @return
	 */
	public static String itemWithBrackets(int figure) {
		String[] symbolStringArray = new String[] { "+", "-" };
		StringBuilder sb = new StringBuilder();
		sb.append("[" + randomNumberProducer(figure) + getSymbolRandom(symbolStringArray));
		sb.append("(" + randomNumberProducer(figure));
		sb.append(getSymbolRandom(symbolStringArray) + randomNumberProducer(figure) + ")]");
		return sb.toString();
	}

	/**
	 * 返回一个除的单元，例如3/4 ，占用两个计算位
	 * 
	 * @param figure 数字的位数
	 * @return 返回一个除的单元
	 */
	public static String itemWidthDivide(int figure) {
		// 生成一个1-9的随机倍数
		Random random = new Random();
		// 生成被除数
		int a = Integer.valueOf(randomNumberProducer(figure));
		// 除数
		int b = 0;
		// 寻找除数
		boolean ifFined = false;
		while (!ifFined) {
			b = random.nextInt(a) + 1;
			if (0 == a % b) {
				ifFined = true;
			}
		}
		// 声明一个StringBulider对象
		StringBuilder sb = new StringBuilder();
		sb.append(a + "/" + b);

		// 返回生成的除法
		return sb.toString();
	}

	/**
	 * 计算整数
	 * 
	 * @param formula 有待计算的字符串
	 * @return 计算结果
	 */
	public static double CalculationForInteger(String formula) {
		int result = 0;
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			result = (int) engine.eval(formula);
		} catch (ScriptException e) {
		}
		return (double) result;
	}

	/**
	 * 主测试方法
	 * @param args
	 */
	public static void main(String[] args) {
		ArithmeticUtil arithmeticUtil = new ArithmeticUtil();
		System.out.println(
				new ArithmeticUtil().getItems(new String[] { "+", "-", "*", "/" }, 2, 6, true, 100));
	}
}
