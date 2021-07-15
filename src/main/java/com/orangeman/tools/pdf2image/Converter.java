package com.orangeman.tools.pdf2image;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class Converter {

	private final String pdfPath;
	private final String imagePath;

	Converter(String pdfPath, String imagePath) {
		this.pdfPath = pdfPath;
		this.imagePath = imagePath;
	}

	public void doConvert() throws IOException {
		String failedFile = String.format("%s/failed.txt", imagePath);
		FileWriter output = new FileWriter(failedFile);

		File[] pdfFiles = new File(pdfPath).listFiles();
		Arrays.stream(pdfFiles).forEach((pdfFile) -> {
			String pdfFileName = pdfFile.getName();
			if (!pdfFileName.contains(".pdf")) return;

			String imageFileName = pdfFileName.replace(".pdf", ".jpeg");
			try {
				convertFile(pdfFile, new File(String.format("%s/%s", imagePath, imageFileName)));
			} catch (Exception e) {
				System.err.println(String.format("%s Failed", pdfFileName));
				try {
					output.write(pdfFileName);
					output.write('\n');
				} catch (IOException ioException) {
				}
			}
		});
		output.flush();
		output.close();
	}

	private void convertFile(File in, File out) throws IOException {
		if (!out.exists()) {
			PDDocument pd = PDDocument.load(in);
			PDFRenderer pr = new PDFRenderer (pd);
			BufferedImage bi = pr.renderImageWithDPI (0, 300);
			ImageIO.write (bi, "JPEG", out);
			pd.close();
		}
	}

}
