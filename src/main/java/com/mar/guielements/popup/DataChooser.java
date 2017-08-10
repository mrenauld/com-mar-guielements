package com.mar.guielements.popup;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.EventListenerList;

import com.mar.guielements.misc.SpringUtilities;

/**
 * A popup frame to ask the user to enter some simple data.
 * 
 * @author renauld
 *
 */

public class DataChooser extends JFrame implements ActionListener {
	
	public static final int TYPE_STRING = 0;
	public static final int TYPE_INT = 1;
	public static final int TYPE_DOUBLE = 2;
	
	private String[] label_names;
	private int[] types;
	private String[] values;
	
	private JLabel[] labels;
	private JTextField[] fields;
	
	private JButton button_ok;
	private JButton button_cancel;
	
	protected final EventListenerList listeners = new EventListenerList();
	private DataChooserEvent event = new DataChooserEvent(this);
	
	/**
	 * Constructs a new DataChooser frame asking to enter the specified
	 * data.
	 * 
	 * @param label_names
	 */
	public DataChooser( String[] label_names ) {
		this.label_names = label_names;
		initFrame();
	}
	
	/**
	 * Initializes the frame.
	 */
	private void initFrame() {
		labels = new JLabel[label_names.length];
		fields = new JTextField[label_names.length];
		for( int i = 0; i < label_names.length; ++i ) {
			labels[i] = new JLabel(label_names[i] + " ");
			fields[i] = new JTextField();
		}
		
		button_ok = new JButton("Ok");
		button_ok.addActionListener(this);
		button_cancel = new JButton("Cancel");
		button_cancel.addActionListener(this);
		
		Container contentpane = this.getContentPane();
		
		SpringLayout layout = new SpringLayout();
		contentpane.setLayout(layout);
		
		for( int i = 0; i < label_names.length; ++i ) {
			contentpane.add(labels[i]);
			contentpane.add(fields[i]);
		}
		contentpane.add(button_ok);
		contentpane.add(button_cancel);
		
		SpringUtilities.makeCompactGrid(contentpane,
				label_names.length+1, 2,
				5, 5, 5, 5);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocation(300, 300);
		this.setSize(300, 50*(label_names.length+1));
		this.setVisible(true);
	}
	
	/**
	 * Sets the values.
	 * 
	 * @param val
	 */
	public void setValues( String[] val ) {
		if( val.length != label_names.length ) {
			System.out.println("DataChooser.setValues - error: "
					+ "the value vector has not the correct length.");
			return;
		}
		for( int i = 0; i < label_names.length; ++i )
			fields[i].setText(val[i]);
	}
	
	/**
	 * Returns the values taken from the text fields.
	 * 
	 * @return
	 */
	public String[] getData() {
		String[] data = new String[label_names.length];
		for( int i = 0; i < label_names.length; ++i )
			data[i] = fields[i].getText();
		return data;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals(button_ok) ) {
			fireOk(event);
			return;
		}
		if( e.getSource().equals(button_cancel) ) {
			fireCancel(event);
			return;
		}
	}
	
	/*
	 * Listener.
	 */
	
	public void addDataChooserListener(DataChooserListener listener) {
		listeners.add(DataChooserListener.class, listener);
	}

	public void removeDataChooserListener(DataChooserListener listener) {
		 listeners.remove(DataChooserListener.class, listener);
	}

	public DataChooserListener[] getDataChooserListeners() {
		return listeners.getListeners(DataChooserListener.class);
	}
	
	/*
	 * Events.
	 */
	
	public void fireOk( DataChooserEvent e ) {
		for(DataChooserListener listener : getDataChooserListeners()) {
            listener.ok( e );
        }
	}
	
	public void fireCancel( DataChooserEvent e ) {
		for(DataChooserListener listener : getDataChooserListeners()) {
            listener.cancel( e );
        }
	}
}