package com.mar.guielements.popup;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.event.EventListenerList;

public class ProgressFrame implements WindowListener {
	
	private final int NBSTEP = 1000;
	
	private JFrame frame;
	private JProgressBar progressbar;
	
	protected final EventListenerList listeners = new EventListenerList();
	
	public ProgressFrame() {
		
		progressbar = new JProgressBar(0, NBSTEP);
		frame = new JFrame();
		frame.add(progressbar);
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Please wait...");
		frame.setLocation(400, 350);
		frame.setSize(250, 70);
		frame.setVisible(true);
		
	}
	
	public void setProgress( double r ) {
		progressbar.setValue((int)(r*NBSTEP));
		if( r == 1 )
			frame.dispose();
	}
	
	public void dispose() {
		frame.dispose();
	}
	
	/*
	 * Listener.
	 */
	
	public void addProgressFrameListener(ProgressFrameListener listener) {
		listeners.add(ProgressFrameListener.class, listener);
	}

	public void removeProgressFrameListener(ProgressFrameListener listener) {
		 listeners.remove(ProgressFrameListener.class, listener);
	}

	public ProgressFrameListener[] getProgressFrameListeners() {
		return listeners.getListeners(ProgressFrameListener.class);
	}
	
	/*
	 * Events.
	 */
	
	public void fireInterrupt() {
		for(ProgressFrameListener listener : getProgressFrameListeners()) {
            listener.interrupt();
        }
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		fireInterrupt();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}