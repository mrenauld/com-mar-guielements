package com.mar.guielements.file;

import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {
	
	/**
	 * Opens a JFileChooser and returns the path to the user's chosen directory.
	 * 
	 * @param default_dir the default directory.
	 * @return the path to the chosen directory.
	 */
	public static String chooseDirectory( String default_dir ) {
		JFileChooser fchooser = new JFileChooser(default_dir);
		fchooser.setCurrentDirectory(new File(""));
		
		String chosen_dir = null;
		
		fchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int status = fchooser.showOpenDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	    	File selectedFile = fchooser.getSelectedFile();
	    	chosen_dir = selectedFile.getPath();
	    } else if (status == JFileChooser.CANCEL_OPTION) {
	    	//System.out.println(JFileChooser.CANCEL_OPTION);
	    }
	    
	    return chosen_dir;
	}
	
	/**
	 * Opens a JFileChooser and returns the path to the user's chosen file. A boolean
	 * parameter 'open' determines if the button is labeled 'open' or 'save'.
	 * 
	 * @param open determines if the button is labeled 'open' or 'save'.
	 * @param default_dir the default directory.
	 * @return the path to chosen file.
	 */
	public static String chooseFile( boolean open, String default_dir ) {
		JFileChooser fchooser = new JFileChooser(default_dir);
		fchooser.setCurrentDirectory(new File(""));
		
		String chosen_file = null;
		
		fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int status = -20;
		if( open )
			status = fchooser.showOpenDialog(null);
		else
			status = fchooser.showSaveDialog(null);
	    if (status == JFileChooser.APPROVE_OPTION) {
	    	File selectedFile = fchooser.getSelectedFile();
	    	chosen_file = selectedFile.getPath();
	    } else if (status == JFileChooser.CANCEL_OPTION) {
	    	//System.out.println(JFileChooser.CANCEL_OPTION);
	    }
	    
	    return chosen_file;
	}
}