package com.mar.guielements.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * An extension of the JTextField. This text field is designed to contain bounded
 * integer values. The minimum and maximum accepted values can be specified. After a
 * specified number of milliseconds since the last modification of the text field,
 * the text will automatically be updated in order to match the bounds: the value
 * is changed to the minimum accepted value if it is below it, or to the maximum
 * accepted value if it is above it.
 * 
 * @author renauld
 *
 */
public class NumericTextField extends JTextField implements DocumentListener,
															ActionListener {
	/** Minimum accepted value in the text field. */
	private int min = Integer.MIN_VALUE;
	/** Maximum accepted value in the text field. */
	private int max = Integer.MAX_VALUE;
	
	public static final int DEFAULT_TIME = 50000;
	
	/** Number of milliseconds before updating the text field. */
	private int timer_step = DEFAULT_TIME;
	/** The timer. */
	private Timer timer = new Timer(timer_step, this);
	
	/** Indicates that the text field is updating itself. Prevents the timer to
	 start when this happens. */
	private boolean stopListeners = false;
	
	/**
	 * Builds a new NumericTextField.
	 */
	public NumericTextField() {
		this.getDocument().addDocumentListener(this);
	}
	
	/**
	 * Constructs a new NumericTextField initialized with the specified string.
	 * 
	 * @param number the initial text.
	 */
	public NumericTextField( String number ) {
		this.getDocument().addDocumentListener(this);
		refresh();
	}
	
	/**
	 * Sets the minimum accepted value in the text field.
	 * 
	 * @param min the minimum accepted value in the text field.
	 */
	public void setMin( int min ) {
		this.min = min;
		if( this.min > max )
			this.min = max;
		refresh();
	}
	
	/**
	 * Sets the maximum accepted value in the text field.
	 * 
	 * @param max the maximum accepted value in the text field.
	 */
	public void setMax( int max ) {
		this.max = max;
		if( this.max < min )
			this.max = min;
		refresh();
	}
	
	/**
	 * Sets the number of milliseconds between the last modification of the text
	 * and the update of the text according to the minimum and maximum values.
	 * 
	 * @param timer_step the number of milliseconds.
	 */
	public void setTimerStep( int timer_step ) {
		if( timer_step <= 0 ) {
			System.out.println("NumericTextField.setTimerStep - error:"
					+ " the time step must be positive." );
			return;
		}
		this.timer_step = timer_step;
		if( timer.isRunning() )
			timer.stop();
		timer = new Timer(timer_step, this);
	}
	
	/**
	 * Returns the current value in the text field.
	 * 
	 * @return the current value in the text field.
	 */
	public int getValue() {
		refresh();
		return checkString(getText());
	}
	
	/**
	 * Checks if the text matches the minimum and maximum accepted values.
	 */
	private void refresh() {
		stopListeners = true;           // Stops recording the text modifications.
		int v = checkString(getText());
		setText(Integer.toString(v));
		stopListeners = false;          // Starts recording the text modifications.
	}
	
	/**
	 * Returns the integer value corresponding to the specified text.
	 * 
	 * If the text is not a number, the value returned is 0 (if 0 is an accepted
	 * value) or the minimum accepted value (if 0 is not an accepted value).
	 * 
	 * If the text value is below the minimum accepted value, the minimum accepted
	 * value is returned.
	 * 
	 * If the text value is above the maximum accepted value, the maximum accepted
	 * value is returned.
	 * 
	 * If the text value is inside the bounds, this value is returned.
	 * 
	 * @param s the text.
	 * @return the corresponding integer value.
	 */
	private int checkString( String s ) {
		int v;
		try {
			v = Integer.parseInt(s);
			if( v < min )
				v = min;
			if( v > max )
				v = max;
		}
		catch( NumberFormatException e ) {
			if( min < 0 && max > 0 )
				v = 0;
			else
				v = min;
		}
		
		return v;
	}
	
	/**
	 * Starts the timer.
	 */
	private void startTimer() {
		if( timer.isRunning() )
			timer.stop();
		timer.start();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if( stopListeners == false )
			startTimer();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if( stopListeners == false )
			startTimer();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if( stopListeners == false )
			startTimer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if( e.getSource().equals(timer) ) {
			refresh();
		}
	}
}