package com.team1ofus.apollo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Loader extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public Loader(ArrayList<Cell> allCells) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel pane = new JPanel();
			pane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pane, BorderLayout.SOUTH);
			{
				JComboBox mapChooser = new JComboBox();
				mapChooser.setModel(new DefaultComboBoxModel(getNames(allCells)));
				pane.add(mapChooser);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				pane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			}
		}
	private String[] getNames(ArrayList<Cell> allCells) {
		String[] result = new String[allCells.size()];
		for(int c = 0; c < allCells.size(); c++)
			result[c] = allCells.get(c).getID();
		return result;
	}
	

}
