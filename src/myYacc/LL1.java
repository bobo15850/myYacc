package myYacc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * 0.  A->nB
 * 1.  B->+nB
 * 2.  B->ε
 * 
 * 分析表为：
 *      n   +   $
 * A    0
 * 
 * B        1	2
 */
public class LL1 {
	private int[][] table = new int[128][128];// 分析表
	private String[][] G = new String[3][2];// 文法

	public LL1() {
		G[0][0] = "A";
		G[0][1] = "nB";
		G[1][0] = "B";
		G[1][1] = "+nB";
		G[2][0] = "B";
		G[2][1] = "ε";
		// 初始化文法
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				table[i][j] = -1;
			}
		}// 将该表置零
		table['A']['n'] = 0;
		table['B']['+'] = 1;
		table['B']['$'] = 2;
		// 初始化LL1分析表
	}

	public List<String> LL1Parse(String s) {
		List<String> resList = new ArrayList<String>();
		Stack<Character> stack = new Stack<Character>();
		stack.push('$');
		stack.push('A');
		int ip = 0;
		char top;
		while ((top = stack.peek()) != '$' && ip < s.length()) {
			char cur = s.charAt(ip);
			if (top == cur) {
				stack.pop();
				ip++;
			}
			else if (top == '+' || top == 'n') {
				System.out.println("第" + ip + "字符除有误");
				return null;
			}
			else if (table[top][cur] == -1) {
				System.out.println("第" + ip + "字符除有误");
				return null;
			}
			else if (table[top][cur] != -1) {
				resList.add(G[table[top][cur]][0] + "->" + G[table[top][cur]][1]);
				stack.pop();
				for (int i = G[table[top][cur]][1].length() - 1; i >= 0; i--) {
					stack.push(G[table[top][cur]][1].charAt(i));
				}
			}

		}
		return resList;
	}

	public static void main(String[] args) {
		LL1 ll1 = new LL1();
		List<String> resList = ll1.LL1Parse("n+n+n+");
		if (resList != null) {
			for (String s : resList) {
				System.out.println(s);
			}
		}
	}
}
