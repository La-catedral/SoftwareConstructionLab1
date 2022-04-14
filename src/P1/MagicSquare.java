package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MagicSquare {

	static int changeToNum(int[] a, int n, String[] s)// 将一个字符串数组中每个字符串转为数字存于一个int数组
	{
		for (int i = 0; i < n; i++) {
			try {
				if ((a[i] = Integer.valueOf(s[i])) <= 0) {
					System.out.println("非正整数");
					return -1;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
	}

	static int sumArr(int[] a)// 计算一个数组各元素和
	{
		int sum = 0;
		for (int i : a) {
			sum += i;
		}
		return sum;
	}

	static int sumDia(int[][] a, int n)// 计算对角线
	{
		int newsum = 0;
		for (int newrow = 0; newrow < n; newrow++) {
			newsum += a[newrow][newrow];
		}
		return newsum;
	}

	static int sumOpDia(int[][] a, int n)// 计算反对角线
	{
		int newsum = 0;
		for (int newrow = 0; newrow < n; newrow++) {
			newsum += a[newrow][n - newrow - 1];
		}
		return newsum;
	}

	public static boolean generateMagicSquare(int n) {

		try {
			int magic[][] = new int[n][n]; // 声明维度为n的二维数组
			int row = 0, col = n / 2, i, j, square = n * n; // 声明表示行和列的变量，以及元素数量
															// 并从第0行开始，从n/2列开始
			for (i = 1; i <= square; i++) { // 重复循环square遍为每个元素复制
				magic[row][col] = i; // 为row行col列赋值为i
				if (i % n == 0) // 若i为n的倍数，换到下一行
					row++;
				else { // 否则
					if (row == 0) // 若row为第零行 row变为最后一行
						row = n - 1;
					else // 否则row换到上一行
						row--;
					if (col == (n - 1))// 若col为最后一列 变为第0冽
						col = 0;
					else // 否则到下一列
						col++;
				}
			}
			// 打印输出magic matrix
			try {
				PrintWriter out = new PrintWriter(
						new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src\\\\P1\\\\txt\\\\6.txt"))));

				for (i = 0; i < n; i++) {
					for (j = 0; j < n; j++)
						out.print(magic[i][j] + "\t");
					out.println();
				}
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (NegativeArraySizeException e) {
			System.out.println("禁止输入负数");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("禁止输入偶数");
		}
		return true;
	}

	// 输入：文件名（字符串） 输出 true or false (文件中矩阵是否为n*n magic矩阵)
	// 即 每行 列 对角线元素和相同
	// 元素值为正整数 行列数相同 数字之间用\t分割等 否则输出相关错误信息 并返回false
	public static boolean isLegalMagicSquare(String filename) {
		int row = 0, column = 0;
		// 生成一个数字矩阵
		// 从文件中 逐行读入 每一行保存在一个字符串中 将字符串按、t分割 记录分割后数字长度（列数）将其放入数字急诊
		// 记录读入行数（行数）
		// 比较是否相等 判断数字是否为正整数
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			try {
				// 通过读取第一行判定行数 并有此一个合适大小的数组
				line = in.readLine();
				String[] thisLine = line.split("\t");
				column = thisLine.length;// 列数
				int[][] value = new int[column][column];

				// 转化为数 并检查是否为整数
				if (changeToNum(value[row], column, thisLine) == -1)
					return false;
//					
				// 第一行读完
				row++;

				while ((line = in.readLine()) != null) {
					thisLine = line.split("\t");
					if (column != thisLine.length) {
						System.out.println("不是一个矩阵或未按tab分割!");
						return false;
					}
					if (changeToNum(value[row], column, thisLine) == -1)
						return false;
					row++;
				}
				in.close();

				if (column == row) {
					int newrow = 0, newcol = 0;

					// 每行和相等测试
					final Integer sum = sumArr(value[newrow]);
					for (newrow = 1; newrow < row; newrow++) {
						if (sumArr(value[newrow]) != sum)
							return false;
					}

					// 如果列和相等
					int newsum = 0;
					for (newcol = 0; newcol < column; newcol++) {
						for (newrow = 0, newsum = 0; newrow < row; newrow++) {
							newsum += value[newrow][newcol];
						}
						if (!sum.equals(newsum))
							return false;
					}

					// 如果正反对角线和等于sum 则返回true
					if (sum == sumDia(value, row) && sum == sumOpDia(value, row))
						return true;
					else
						return false;

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// 若都满足计算行列对角线和是否相等

		return false;// 待修改
	}

	public static void main(String[] args) {

		System.out.print("1.txt：");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\1.txt"));
		System.out.print("2.txt：");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\2.txt"));
		System.out.print("3.txt：");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\3.txt"));
		System.out.print("4.txt：");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\4.txt"));
		System.out.print("5.txt：");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\5.txt"));
		generateMagicSquare(11);
		System.out.print("6.txt：");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\6.txt"));
	}

}
