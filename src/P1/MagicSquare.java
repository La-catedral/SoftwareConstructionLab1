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

	static int changeToNum(int[] a, int n, String[] s)// ��һ���ַ���������ÿ���ַ���תΪ���ִ���һ��int����
	{
		for (int i = 0; i < n; i++) {
			try {
				if ((a[i] = Integer.valueOf(s[i])) <= 0) {
					System.out.println("��������");
					return -1;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
	}

	static int sumArr(int[] a)// ����һ�������Ԫ�غ�
	{
		int sum = 0;
		for (int i : a) {
			sum += i;
		}
		return sum;
	}

	static int sumDia(int[][] a, int n)// ����Խ���
	{
		int newsum = 0;
		for (int newrow = 0; newrow < n; newrow++) {
			newsum += a[newrow][newrow];
		}
		return newsum;
	}

	static int sumOpDia(int[][] a, int n)// ���㷴�Խ���
	{
		int newsum = 0;
		for (int newrow = 0; newrow < n; newrow++) {
			newsum += a[newrow][n - newrow - 1];
		}
		return newsum;
	}

	public static boolean generateMagicSquare(int n) {

		try {
			int magic[][] = new int[n][n]; // ����ά��Ϊn�Ķ�ά����
			int row = 0, col = n / 2, i, j, square = n * n; // ������ʾ�к��еı������Լ�Ԫ������
															// ���ӵ�0�п�ʼ����n/2�п�ʼ
			for (i = 1; i <= square; i++) { // �ظ�ѭ��square��Ϊÿ��Ԫ�ظ���
				magic[row][col] = i; // Ϊrow��col�и�ֵΪi
				if (i % n == 0) // ��iΪn�ı�����������һ��
					row++;
				else { // ����
					if (row == 0) // ��rowΪ������ row��Ϊ���һ��
						row = n - 1;
					else // ����row������һ��
						row--;
					if (col == (n - 1))// ��colΪ���һ�� ��Ϊ��0��
						col = 0;
					else // ������һ��
						col++;
				}
			}
			// ��ӡ���magic matrix
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
			System.out.println("��ֹ���븺��");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("��ֹ����ż��");
		}
		return true;
	}

	// ���룺�ļ������ַ����� ��� true or false (�ļ��о����Ƿ�Ϊn*n magic����)
	// �� ÿ�� �� �Խ���Ԫ�غ���ͬ
	// Ԫ��ֵΪ������ ��������ͬ ����֮����\t�ָ�� ���������ش�����Ϣ ������false
	public static boolean isLegalMagicSquare(String filename) {
		int row = 0, column = 0;
		// ����һ�����־���
		// ���ļ��� ���ж��� ÿһ�б�����һ���ַ����� ���ַ�������t�ָ� ��¼�ָ�����ֳ��ȣ�����������������ּ���
		// ��¼����������������
		// �Ƚ��Ƿ���� �ж������Ƿ�Ϊ������
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			try {
				// ͨ����ȡ��һ���ж����� ���д�һ�����ʴ�С������
				line = in.readLine();
				String[] thisLine = line.split("\t");
				column = thisLine.length;// ����
				int[][] value = new int[column][column];

				// ת��Ϊ�� ������Ƿ�Ϊ����
				if (changeToNum(value[row], column, thisLine) == -1)
					return false;
//					
				// ��һ�ж���
				row++;

				while ((line = in.readLine()) != null) {
					thisLine = line.split("\t");
					if (column != thisLine.length) {
						System.out.println("����һ�������δ��tab�ָ�!");
						return false;
					}
					if (changeToNum(value[row], column, thisLine) == -1)
						return false;
					row++;
				}
				in.close();

				if (column == row) {
					int newrow = 0, newcol = 0;

					// ÿ�к���Ȳ���
					final Integer sum = sumArr(value[newrow]);
					for (newrow = 1; newrow < row; newrow++) {
						if (sumArr(value[newrow]) != sum)
							return false;
					}

					// ����к����
					int newsum = 0;
					for (newcol = 0; newcol < column; newcol++) {
						for (newrow = 0, newsum = 0; newrow < row; newrow++) {
							newsum += value[newrow][newcol];
						}
						if (!sum.equals(newsum))
							return false;
					}

					// ��������Խ��ߺ͵���sum �򷵻�true
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

		// ��������������жԽ��ߺ��Ƿ����

		return false;// ���޸�
	}

	public static void main(String[] args) {

		System.out.print("1.txt��");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\1.txt"));
		System.out.print("2.txt��");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\2.txt"));
		System.out.print("3.txt��");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\3.txt"));
		System.out.print("4.txt��");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\4.txt"));
		System.out.print("5.txt��");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\5.txt"));
		generateMagicSquare(11);
		System.out.print("6.txt��");
		System.out.println(isLegalMagicSquare("src\\P1\\txt\\6.txt"));
	}

}
