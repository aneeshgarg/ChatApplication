package com.gui;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class UserPanel {
	private JPanel userPanel;
	private static JList list;
	private static DefaultListModel listModel;
	
	
	public UserPanel(){
		userPanel=new JPanel();
		setListModel(new DefaultListModel());
		list=new JList();
		userPanel.add(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisible(true);
		JFrame frame =new JFrame("List");
		frame.setSize(400,600);
		frame.add(userPanel);
		frame.setVisible(true);
	}

	public JPanel getUserPanel() {
		return userPanel;
	}

	public static void setList(JList list) {
		UserPanel.list = list;
	}

	public static JList getList() {
		return list;
	}

	public static void setListModel(DefaultListModel listModel) {
		UserPanel.listModel = listModel;
	}

	public static DefaultListModel getListModel() {
		return listModel;
	}

}
