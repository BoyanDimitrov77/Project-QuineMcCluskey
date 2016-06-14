import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class McCluskeyProgram {
	public static List<String> inputList = new ArrayList<String>();
	public static List<String> resultFromFirstCombine = new ArrayList<String>();
	public static List<String> resultFromSecondCombine = new ArrayList<String>();
	public static List<List<String>> terms = new ArrayList<List<String>>();
	public static List<String> term0 = new ArrayList<String>();
	public static List<String> term1 = new ArrayList<String>();
	public static List<String> term2 = new ArrayList<String>();
	public static List<String> term3 = new ArrayList<String>();
	public static List<String> term4 = new ArrayList<String>();
	public static int flagIn = 0;
	public static String[][] coverTable;
	public static String[][] secondCoverTable;
	public static String markImplicatnt[]=new String[inputList.size()+1];
	public static List<String> primeImplicant = new ArrayList<String>();
	public static List<String> tempPrimes;
	public static List<String> subTerm = new ArrayList<String>();
	public static List<String> ResultPrimesAlphabet;
	public static List<String>finalResult=new ArrayList<String>();
	public static String arrCombine[];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FirstStep();
		// OrderTerms();

		System.out.println("First Comparasion");
		for (String term : resultFromFirstCombine) {
			System.out.println(term);
		}
		System.out.println(resultFromFirstCombine.size());

		System.out.println("----------------");

		secondStep();

		System.out.println("Second Comparasion");
		for (String term2 : resultFromSecondCombine) {
			System.out.println(term2);
		}
		System.out.println(resultFromSecondCombine.size());
		System.out.println("----------------");
		// System.out.println(inputList);

		System.out.println("Coverage Table");
		System.out.println("----------------");
		coveregeTable();

		Minimisation();
		FinalResult();
		System.out.println("----------------");
		arrCombine = new String[ResultPrimesAlphabet.size()];
		GenerateCombinationsNoRepetitions(0, 0);

		
	}

	public static void FirstStep() {

		
		OrderTerms(inputList);

		/*тук се добавят в terms подредените по брой 1-ци term */
		terms.add(term0);
		terms.add(term1);
		terms.add(term2);
		terms.add(term3);
		terms.add(term4);

		int counter = 1;
		for (int i = 0; i < terms.size(); i++) {

			if (counter != terms.size()) {
				calculateMinTerms(terms.get(i), terms.get(counter), 1);
				counter++;
			}

		}

	}

	private static void calculateMinTerms(List<String> list, List<String> list2, int f) {

		/* тук се прави възможните слепвания*/
		for (int first = 0; first < list.size(); first++) {
			String temp = list.get(first);

			for (int second = 0; second < list2.size(); second++) {
				String temp2 = list2.get(second);
				String copyTerm = temp;
				
				if (checkDifferentCharacter(temp, temp2)) {
					for (int g = 0; g < temp2.length(); g++) {

						if ((temp.charAt(g) != temp2.charAt(g))) {

							copyTerm = replaceCharAt(temp, g, '-');

						}
					}

				} else {
					continue;
				}
				if (f == 1) {
					resultFromFirstCombine.add(copyTerm);
				} else if (f == 2) {
					resultFromSecondCombine.add(copyTerm);
				}

			}

		}
		if (f == 1) {
			checkForDublicate(1);
		} else if (f == 2) {
			checkForDublicate(2);
		}

	}

	public static String replaceCharAt(String s, int pos, char c) {
		return s.substring(0, pos) + c + s.substring(pos + 1);
	}

	public static void checkForDublicate(int i) {
		/*проверява се за дублиращи членове*/
		if (i == 1) {
			Set<String> set = new HashSet<String>();
			set.addAll(resultFromFirstCombine);
			resultFromFirstCombine.clear();
			resultFromFirstCombine.addAll(set);
		} else if (i == 2) {
			Set<String> set = new HashSet<String>();
			set.addAll(resultFromSecondCombine);
			resultFromSecondCombine.clear();
			resultFromSecondCombine.addAll(set);
		}else if(i==3){
			Set<String> set = new HashSet<String>();
			set.addAll(finalResult);
			finalResult.clear();
			finalResult.addAll(set);
		}

	}

	public static void OrderTerms(List<String> input) {
		
		/*продрежда се членовете по броя на единиците*/
	
		if (!(term0.isEmpty() && term1.isEmpty() && term2.isEmpty() && term3.isEmpty() && term4.isEmpty())) {
			term0.clear();
			term1.clear();
			term2.clear();
			term3.clear();
			term4.clear();

		}

		for (String term : input) {
			int counter = 0;
			for (int i = 0; i < term.length(); i++) {
				if (term.charAt(i) == '1') {
					counter++;

				}
			}
			if (counter == 0) {
				term0.add(term);
			} else if (counter == 1) {
				term1.add(term);
			} else if (counter == 2) {
				term2.add(term);
			} else if (counter == 3) {
				term3.add(term);
			} else if (counter == 4) {
				term4.add(term);
			}

		}
		

	}


	public static boolean checkDifferentCharacter(String first, String second) {

		/*проверка за съвпадение(проверака за различни characters)*/
		int counter = 0;
		for (int i = 0; i < first.length(); i++) {
			if (first.charAt(i) != second.charAt(i)) {
				counter++;
			}
		}
		if (counter == 1) {
			return true;
		}

		return false;

	}

	public static void OrderTermsByDash() {
		/*подрежда членовете по позиция на липсващите местта(-)*/
		if (!(term0.isEmpty() && term1.isEmpty() && term2.isEmpty() && term3.isEmpty())) {
			term0.clear();
			term1.clear();
			term2.clear();
			term3.clear();

		}

		for (int i = 0; i < resultFromFirstCombine.size(); i++) {
			String tempTerms = resultFromFirstCombine.get(i);
			int positionIndexDash = tempTerms.indexOf('-');

			if (positionIndexDash == 0) {
				term0.add(tempTerms);

			} else if (positionIndexDash == 1) {
				term1.add(tempTerms);
			} else if (positionIndexDash == 2) {
				term2.add(tempTerms);
			} else if (positionIndexDash == 3) {
				term3.add(tempTerms);
			}

		}
		

	}

	public static void secondStep() {

		/*втора стъпка от алгоритъма*/
		OrderTermsByDash();

		List<String> newT = new ArrayList<String>();

		newT.addAll(term0);
		newT.addAll(term1);
		newT.addAll(term2);
		newT.addAll(term3);

		for (int i = 0; i < newT.size(); i++) {
			String temp = newT.get(i);

			for (int j = 0; j < newT.size(); j++) {
				String temp2 = newT.get(j);
				String copyTerm = temp;

				/*Проверява дали има повече от един различен character */
				if (checkDifferentCharacter(temp, temp2)) { 
					for (int t = 0; t < temp2.length(); t++) {

						if (temp.charAt(t) != temp2.charAt(t)) {

							copyTerm = replaceCharAt(temp, t, '-');

						}

					}

				} else {
					continue;
				}

				resultFromSecondCombine.add(copyTerm);
				
			}
		}
		checkForDublicate(2);

	}

	public static void coveregeTable() {
		/*създаване и попълване на таблицата на покритията*/
		coverTable = new String[resultFromSecondCombine.size() + 1][inputList.size() + 1];

		for (int i = 0; i < resultFromSecondCombine.size(); i++) {
			coverTable[i + 1][0] = resultFromSecondCombine.get(i);
		}

		for (int j = 0; j < inputList.size(); j++) {
			coverTable[0][j + 1] = inputList.get(j);
		}

		int o = coverTable.length;
		for (int r = 1; r < coverTable.length; r++) {
			String temp = coverTable[r][0];

			for (int c = 1; c < coverTable[0].length; c++) {
				String temp2 = coverTable[0][c];

				if (checkMark(temp, temp2)) {
					coverTable[r][c] = "   x";

				} else {
					coverTable[r][c] = "    ";
				}

			}

		}

		for (int row = 0; row < coverTable.length; row++) {
			for (int colum = 0; colum < coverTable[0].length; colum++) {

				System.out.print(" " + coverTable[row][colum]);
			}
			System.out.println();
		}

	}

	public static boolean checkMark(String first, String second) {
		/*тук се проверява покритието на членовете от импликантите*/
		int counter = 0;
		for (int i = 0; i < first.length(); i++) {
			if (first.charAt(i) == second.charAt(i)) {
				counter++;
			}
		}
		if (counter <= 1) {
			return false;
		}

		return true;

	}

	public static void Minimisation() {

		markImplicatnt = new String[inputList.size() + 1];

		int row = 1;
		int size = coverTable[0].length;
		for (row = 1; row < coverTable[0].length; row++) {
			int counter = 0;
			int colum = 1;
			int position = 0; /* използва се за да се вземе позицията на задължителните импликанти*/
			for (colum = 1; colum < coverTable.length; colum++) {

				if (coverTable[colum][row].contains("x")) {
					counter++;
					position = colum;
				}
			}
			if (counter == 1) {
				primeImplicant.add(coverTable[position][0]);
				markImplicatnt[row] = "   ^";
			} else {
				markImplicatnt[row] = "   -";
			}

		}

		System.out.println();
		System.out.println(primeImplicant);


	}

	public static void SubMunimisation() {
		
		markRemovableImplicant();
		/*добавят се в списък импликантите,който не се покриват от задължителните импликанти*/
	
		for (int j = 1; j < markImplicatnt.length; j++) {
			String temp = markImplicatnt[j];
			if (markImplicatnt[j].contains("   -")) {
				subTerm.add(inputList.get(j - 1));
			}
		}

		for (int i = 0; i < markImplicatnt.length; i++) {
			System.out.print(" " + markImplicatnt[i]);
		}
		System.out.println();
		System.out.println("----------------");
		System.out.println(subTerm);

		SubCoverTable();

	}

	public static void markRemovableImplicant() {
		/*маркират се онези членове които се препокриват от задължителните импликанти*/
			
				for (String part : primeImplicant) {
					String prime = part;

					for (int i = 1; i < coverTable[0].length; i++) {
						String term = coverTable[0][i];

						if (checkMark(prime, term)) {
							markImplicatnt[i] = "   |";

						} else {

							continue;

						}

					}

				}
		
	}

	public static void SubCoverTable() {
		tempPrimes = resultFromSecondCombine;
		/*премахва елементите,които са задължителни импликанти ,за да може после останалите да се използват във втората таблицата на покритията*/
		
		for (String part : primeImplicant) {

			tempPrimes.remove(part);
		}

		secondCoverTable = new String[tempPrimes.size() + 1][subTerm.size() + 1];

		for (int i = 0; i < tempPrimes.size(); i++) {
			secondCoverTable[i + 1][0] = tempPrimes.get(i);
		}

		for (int j = 0; j < subTerm.size(); j++) {
			secondCoverTable[0][j + 1] = subTerm.get(j);
		}

		/*попълваме таблицата*/
		for (int r = 1; r < secondCoverTable.length; r++) {
			String temp = secondCoverTable[r][0];

			for (int c = 1; c < secondCoverTable[0].length; c++) {
				String temp2 = secondCoverTable[0][c];

				if (checkMark(temp, temp2)) {
					secondCoverTable[r][c] = "   x";

				} else {
					secondCoverTable[r][c] = "    ";
				}

			}

		}

		for (int row = 0; row < secondCoverTable.length; row++) {
			for (int colum = 0; colum < secondCoverTable[0].length; colum++) {

				System.out.print(" " + secondCoverTable[row][colum]);
			}
			System.out.println();
		}

		System.out.println("----------------");
		int maxCover = Integer.MIN_VALUE;

		/* тук се обхожда втората таблица на покритията ,за да може да се провери дали съответната импликанта покрива всички или част от terms*/
		List<String> list = new ArrayList<String>();
		for (int r = 1; r < secondCoverTable.length; r++) {
			int counter = 0;
			String temp = secondCoverTable[r][0];
			for (int c = 1; c < secondCoverTable[0].length; c++) {

				if (secondCoverTable[r][c].contains("x")) {
					counter++;
				}
				if (counter > maxCover) {
					maxCover = counter;
				} else if (counter == maxCover) {
					System.out.println(temp);
					list.add(temp);

				} else if (maxCover == secondCoverTable[0].length) {
					list.clear();
					list.add(temp);
					System.out.println(temp);
				}

			}

		}
		// добавяме останлите импликанти от минимизацията
		primeImplicant.addAll(list);

	

	}

	public static void GenerateCombinationsNoRepetitions(int index, int start) {
		
		if (index >= ResultPrimesAlphabet.size()-1) {
			PrintCombination();
		} else {
			for (int i = start; i < ResultPrimesAlphabet.size(); i++) {
				arrCombine[index] = ResultPrimesAlphabet.get(i);
				GenerateCombinationsNoRepetitions(index + 1, i + 1);
			}
		}
	}

	public static void PrintCombination() {

		System.out.print(String.join("+ ", arrCombine));
		finalResult.add(String.join("+ ", arrCombine));

		System.out.println();

	}

	public static void FinalResult() {

		/* тук означава задължителните импликанти с буквите от A-D*/
		ResultPrimesAlphabet = new ArrayList<String>();
		for (int i = 0; i < primeImplicant.size(); i++) {
			String term = primeImplicant.get(i);
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < term.length(); j++) {

				if (term.charAt(j) == '-') {
					sb.append("");
				} else if (term.charAt(j) == '1' && j == 0) {
					sb.append("A");
				} else if (term.charAt(j) == '0' && j == 0) {
					sb.append("A'");
				} else if (term.charAt(j) == '1' && j == 1) {
					sb.append("B");
				} else if (term.charAt(j) == '0' && j == 1) {
					sb.append("B'");
				} else if (term.charAt(j) == '1' && j == 2) {
					sb.append("C");
				} else if (term.charAt(j) == '0' && j == 2) {
					sb.append("C'");
				} else if (term.charAt(j) == '1' && j == 3) {
					sb.append("D");
				} else if (term.charAt(j) == '0' && j == 3) {
					sb.append("D'");
				}
			}

			ResultPrimesAlphabet.add(sb.toString());

		}

		System.out.println(ResultPrimesAlphabet);

	}


}
