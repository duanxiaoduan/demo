package com.example.demo.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFToHTML;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author duanxiaoduan
 * @version 2019-07-31
 */
public class Pdf2HtmlUtil {

    public static void generateHTMLFromPDF(String filename) throws IOException, ParserConfigurationException {
        PDDocument pdf = PDDocument.load(new File(filename));
        Writer output = new PrintWriter("/Users/Cinderella/Desktop/pdf.html", "utf-8");
        new PDFDomTree().writeText(pdf, output);
        output.close();
    }

    public static void main(String[] args){
        final String filePath = "/Users/Cinderella/Desktop/1206445777.PDF";
        try {
            generateHTMLFromPDF(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
