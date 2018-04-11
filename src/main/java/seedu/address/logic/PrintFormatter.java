//@@author cxingkai
package seedu.address.logic;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import seedu.address.logic.login.LoginManager;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Record;
import seedu.address.model.patient.RecordList;


/**
 * Prints the records of given patient to pdf format
 */
public class PrintFormatter {
    private static Document document;
    private static String filePath;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,
            Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);

    public PrintFormatter(Patient patient) {
        initDocument(patient);
        document.open();
        addHeader();
        writePatientInfo(patient);
        writeRecords(patient);
        document.close();
        openPdf();
    }

    /**
     * Creates a new pdf document
     */
    private void initDocument(Patient patient) {
        filePath = patient.getName().toString() + " records.pdf";
        document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds title of pdf document
     */
    private void addHeader() {
        Paragraph header = new Paragraph();
        header.setAlignment(Element.ALIGN_CENTER);
        header.add(new Chunk("Medical Records", catFont));
        header.add(Chunk.NEWLINE);
        header.add(Chunk.NEWLINE);

        try {
            document.add(header);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * Writes information of given patient at the top of the pdf document
     */
    private void writePatientInfo(Patient patient) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(new Chunk("Name: ", smallBold));
        paragraph.add(new Chunk(patient.getName().toString(), smallNormal));
        paragraph.add(Chunk.NEWLINE);

        paragraph.add(new Chunk("Blood Type: ", smallBold));
        paragraph.add(new Chunk(patient.getBloodType().toString(), smallNormal));
        paragraph.add(Chunk.NEWLINE);

        paragraph.add(new Chunk("Remarks: ", smallBold));
        paragraph.add(new Chunk(patient.getRemark().toString(), smallNormal));
        paragraph.add(Chunk.NEWLINE);

        DottedLineSeparator separator = new DottedLineSeparator();
        paragraph.add(new Chunk(separator));

        try {
            document.add(paragraph);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    /**
     * Writes out records of given patient on the pdf document
     */
    private void writeRecords(Patient patient) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_LEFT);

        RecordList recordList = patient.getRecordList();
        int recordListSize = recordList.getNumberOfRecords();

        for (int recordIndex = 0; recordIndex < recordListSize; recordIndex++) {
            paragraph.add(Chunk.NEWLINE);

            Record record = recordList.getRecord(recordIndex);
            Chunk recordNumberChunk = new Chunk("Record #" + (recordIndex + 1));
            recordNumberChunk.setUnderline(0.1f, -2f);
            recordNumberChunk.setFont(subFont);
            paragraph.add(recordNumberChunk);
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Recorded by: ", smallBold));
            paragraph.add(new Chunk(LoginManager.getUserName(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Date recorded: ", smallBold));
            paragraph.add(new Chunk(record.getDate(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Symptoms: ", smallBold));
            paragraph.add(new Chunk(record.getSymptom(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Illness: ", smallBold));
            paragraph.add(new Chunk(record.getIllness(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            paragraph.add(new Chunk("Treatment: ", smallBold));
            paragraph.add(new Chunk(record.getTreatment(), smallNormal));
            paragraph.add(Chunk.NEWLINE);

            DottedLineSeparator separator = new DottedLineSeparator();
            paragraph.add(new Chunk(separator));
            paragraph.add(Chunk.NEWLINE);
        }

        try {
            document.add(paragraph);
        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }

    private void openPdf() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filePath);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
                ex.printStackTrace();
            }
        }
    }
}
