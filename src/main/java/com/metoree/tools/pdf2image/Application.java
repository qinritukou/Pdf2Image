package com.metoree.tools.pdf2image;

import java.io.IOException;

public class Application {

	public static void main(String[] args) throws IOException {
		String pdfFolder = "<pdf folder path>";
		String imageFolder = "<image folder path>";

		Converter converter = new Converter(pdfFolder, imageFolder);
		converter.doConvert();
	}

}
