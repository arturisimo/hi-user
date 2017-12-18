package org.apz.hiuser.model.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apz.hiuser.util.Constants;

public abstract class Dao implements Constants {
	
	protected abstract String getFileName();
	
	protected String getFileContent() throws FileNotFoundException, IOException {
		
		String json = "";
		
		StringBuilder sb = new StringBuilder();
		
		try (FileInputStream fis = new FileInputStream(PATH_TMP + getFileName());
				BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
		    
		    String line;
		    while ((line = br.readLine()) != null ) {
		    	 sb.append( line );
		         sb.append( '\n' );
		    }
		    json = sb.toString();
		}
				
		return json;
	}
	
	protected void addFileContent(String content) throws IOException {
		
		File file = new File(PATH_TMP + getFileName());
		
		if (!file.exists()) {
			file.createNewFile();
		}

		// true = append file
		try(FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
				BufferedWriter bw = new BufferedWriter(fw)){
			bw.write(content);
		}
	}

}