package com.mar.guielements.popup;

import java.util.EventListener;

public interface DataChooserListener extends EventListener {
	
	public void ok( DataChooserEvent e );
	
	public void cancel( DataChooserEvent e );
	
}