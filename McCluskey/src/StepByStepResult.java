import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTable;

public class StepByStepResult extends JDialog {
	private JTable table;

	public static void ClearResultInInputRecordlButton(){
		McCluskeyProgram.resultFromFirstCombine.clear();
		McCluskeyProgram.resultFromSecondCombine.clear();
		McCluskeyProgram.coverTable=null;
		McCluskeyProgram.secondCoverTable=null;
		McCluskeyProgram.finalResult.clear();
		McCluskeyProgram.ResultPrimesAlphabet.clear();
		McCluskeyProgram.primeImplicant.clear();
		McCluskeyProgram.terms.clear();
		McCluskeyProgram.arrCombine=null;
		McCluskeyProgram.tempPrimes.clear();
		McCluskeyProgram.term0.clear();
		McCluskeyProgram.term1.clear();
		McCluskeyProgram.term2.clear();
		McCluskeyProgram.term3.clear();
		McCluskeyProgram.term4.clear();
		McCluskeyProgram.subTerm.clear();
	
		McCluskey.InputListFromJTP.clear();
		

	}
	

	public StepByStepResult() {
		
	
		setBounds(100, 100, 550, 370);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 11, 500, 200);
		getContentPane().add(scrollPane);

		table = new JTable();

		scrollPane.setColumnHeaderView(table);

		JButton FinalResultButton = new JButton("Final Result");
		FinalResultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				McCluskeyProgram.FinalResult();
				McCluskeyProgram.arrCombine = new String[McCluskeyProgram.ResultPrimesAlphabet.size() - 1];
				McCluskeyProgram.GenerateCombinationsNoRepetitions(0, 0);
				McCluskeyProgram.checkForDublicate(3);
				Object[][] result = new Object[McCluskeyProgram.finalResult.size()][1];
				Object[] ColumName = { "Final Result" };

				for (int row = 0; row < result.length; row++) {
					result[row][0] = McCluskeyProgram.finalResult.get(row);
				}

				TableModel model = new DefaultTableModel(result, ColumName) {
					public boolean isCellEditable(int rowIndex, int mColIndex) {
						return false;
					}
				};
				table = new JTable(model);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.setDefaultRenderer(String.class, centerRenderer);
				table.setRowHeight(table.getRowHeight());
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				scrollPane.setColumnHeaderView(table);
				scrollPane.setViewportView(table);
				
				FinalResultButton.setEnabled(false);
				
			}
		});
		FinalResultButton.setBounds(209, 256, 116, 23);
		getContentPane().add(FinalResultButton);

		JButton FourthStepButton = new JButton("Fourth Step");
		FourthStepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				McCluskeyProgram.SubMunimisation();
				
				Object[][] SecondcoverTable = new Object[McCluskeyProgram.tempPrimes.size() + 1][McCluskeyProgram.subTerm.size() + 1];
				Object[] columName = new Object[McCluskeyProgram.subTerm.size() + 1];

				for (int row = 0; row < SecondcoverTable.length; row++) {
					for (int colum = 0; colum < SecondcoverTable[0].length; colum++) {
						SecondcoverTable[row][colum] = McCluskeyProgram.secondCoverTable[row][colum];

					}
				}

				TableModel model = new DefaultTableModel(SecondcoverTable, columName) {
					public boolean isCellEditable(int rowIndex, int mColIndex) {
						return false;
					}
				};
				table = new JTable(model);
				scrollPane.setColumnHeaderView(table);
				scrollPane.setViewportView(table);
				
				FourthStepButton.setEnabled(false);
				FinalResultButton.setEnabled(true);

			}
		});
		FourthStepButton.setBounds(398, 221, 116, 23);
		getContentPane().add(FourthStepButton);

		JButton ThirdStepButton = new JButton("Third Step");
		ThirdStepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				McCluskeyProgram.coveregeTable();

				Object[][] coverTable = new Object[McCluskeyProgram.resultFromSecondCombine.size() + 1][McCluskeyProgram.inputList.size()
						+ 1];
				Object[] columName = new Object[McCluskeyProgram.inputList.size()];

				for (int row = 0; row < coverTable.length; row++) {
					for (int colum = 0; colum < coverTable[0].length; colum++) {
						coverTable[row][colum] = McCluskeyProgram.coverTable[row][colum];

					}
			
				}

				TableModel model = new DefaultTableModel(coverTable, columName) {
					public boolean isCellEditable(int rowIndex, int mColIndex) {
						return false;
					}
				};
				 DefaultTableModel MarkTable=(DefaultTableModel) table.getModel();
				 
				McCluskeyProgram.Minimisation();
				McCluskeyProgram.markRemovableImplicant();
				
				table = new JTable(model){
					@Override
					
					 public Component prepareRenderer(TableCellRenderer renderer, int row, int col){
						 Component comp = super.prepareRenderer(renderer, row, col);
						
						String[] arr=McCluskeyProgram.markImplicatnt;
						for (int i = 1; i < McCluskeyProgram.markImplicatnt.length; i++) {
							if (McCluskeyProgram.markImplicatnt[i].equals("   |")) {
								if (col == i) {
									comp.setBackground(Color.GREEN);

									return comp;
								} else {
									comp.setBackground(Color.white);
								}
							}
							
						}	
						return comp;
				};
			
				};
				
				scrollPane.setColumnHeaderView(table);
				scrollPane.setViewportView(table);
			

				ThirdStepButton.setEnabled(false);
				FourthStepButton.setEnabled(true);
			}
		});
		ThirdStepButton.setBounds(266, 221, 115, 23);
		getContentPane().add(ThirdStepButton);

		JButton SecondStepButton = new JButton("Second Step");

		SecondStepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				McCluskeyProgram.secondStep();

				Object[][] SecondComparison = new Object[McCluskeyProgram.resultFromSecondCombine.size()][1];
				Object[] ColumnName = { "SecondComparison" };

				for (int row = 0; row < SecondComparison.length; row++) {
					SecondComparison[row][0] = McCluskeyProgram.resultFromSecondCombine.get(row);
				}
				TableModel model = new DefaultTableModel(SecondComparison, ColumnName) {
					public boolean isCellEditable(int rowIndex, int mColIndex) {
						return false;
					}
				};

				table = new JTable(model);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.setDefaultRenderer(String.class, centerRenderer);
				table.setRowHeight(table.getRowHeight());
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

				scrollPane.setColumnHeaderView(table);
				scrollPane.setViewportView(table);
				
				SecondStepButton.setEnabled(false);
				ThirdStepButton.setEnabled(true);

			}
		});
		SecondStepButton.setBounds(140, 222, 116, 23);
		getContentPane().add(SecondStepButton);

		JButton FirststepButton = new JButton("First Step");
		FirststepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				McCluskeyProgram.inputList=McCluskey.InputListFromJTP;
				McCluskeyProgram.FirstStep();
				Object[][] FirstComparison = new Object[McCluskeyProgram.resultFromFirstCombine.size()][1];
				Object[] ColumnName = { "FirstComparison" };

				for (int row = 0; row < FirstComparison.length; row++) {
					FirstComparison[row][0] = McCluskeyProgram.resultFromFirstCombine.get(row);
				}
				TableModel model = new DefaultTableModel(FirstComparison, ColumnName) {
					public boolean isCellEditable(int rowIndex, int mColIndex) {
						return false;
					}
				};

				table = new JTable(model);
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(JLabel.CENTER);
				table.setDefaultRenderer(String.class, centerRenderer);
				table.setRowHeight(table.getRowHeight());
				table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
				

				scrollPane.setColumnHeaderView(table);
				scrollPane.setViewportView(table);

				FirststepButton.setEnabled(false);
				SecondStepButton.setEnabled(true);

			}
		});

		FirststepButton.setBounds(14, 222, 116, 23);
		getContentPane().add(FirststepButton);

		SecondStepButton.setEnabled(false);
		ThirdStepButton.setEnabled(false);
		FourthStepButton.setEnabled(false);
		FinalResultButton.setEnabled(false);
		
		JButton InputNewTerms = new JButton("INPUT NEW TERMS");
		InputNewTerms.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ClearResultInInputRecordlButton();
				setVisible(false);
				
				
			}
		});
		InputNewTerms.setBounds(25, 290, 478, 23);
		getContentPane().add(InputNewTerms);
	
		
	
	}
}
