package clases;


import java.awt.Component;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


public class ToPDF{
	
	
	private JFileChooser chooser = new JFileChooser();
	private File file;
	private PdfWriter writer;
	private float height;
	private float width;
	private Document document;
	private PdfPTable table;
	private Presupuesto presu;
	
	public ToPDF(Presupuesto p){
		presu = p;
	}

	
	public void createPDF() throws IOException, DocumentException {
		
		document = new Document(PageSize.A4 , 30, 30, 185, 75);
		
		chooser.setSelectedFile(new File("PR"+presu.getnOferta().trim()+".pdf"));
		
		int value = chooser.showSaveDialog(null);
		
		file = chooser.getSelectedFile();	
		writer = PdfWriter.getInstance(document, new FileOutputStream(file));
	
		Rectangle pageSize = document.getPageSize();
		
		height = pageSize.getHeight();
		width = pageSize.getWidth();
		
		
		TableHeader event = new TableHeader();
		writer.setPageEvent(event);
		
		
		
		document.open();
	
	
		
		createTable();
		
		document.close();
		
	}
	
	class TableHeader extends PdfPageEventHelper{
		
		PdfTemplate totalPages;
		float textBase = document.bottom() - 20;

		
		public void onOpenDocument(PdfWriter writer, Document document) {
            totalPages = writer.getDirectContent().createTemplate(100, 100);
            totalPages.setBoundingBox(new Rectangle(-20,-20,100,100));
        }
		
		public void onEndPage(PdfWriter writer, Document document) {
          		
			
            try {
            	header();
                client();   	
          		fecha_num();
          		createTitleTable();
          		
          		if(writer.getPageNumber()==totalPages.getPdfWriter().getPageNumber()){
          			footer();       		
          		}
	
          		PdfContentByte canvas = writer.getDirectContent();
          		Font helvetica = new Font(FontFamily.HELVETICA, 12);
        	    BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
        	    canvas.saveState();
        	    
        	    
        	    String text = String.format("Página %s de ",writer.getPageNumber());
     	    
        	    canvas.beginText();
        	    canvas.setFontAndSize(bf_helv, 10);
        	    canvas.setTextMatrix(30,textBase);
        	    canvas.showText(text);
        	    canvas.endText();
        	    canvas.addTemplate(totalPages, 30+10,textBase);
        	    
        	    canvas.restoreState();

            }
            catch(DocumentException | IOException de) {
                throw new ExceptionConverter(de);
            }
        }
		
		public void onCloseDocument(PdfWriter writer, Document document) {

			Font helvetica = new Font(FontFamily.HELVETICA, 12);
    	    BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
			totalPages.beginText();
			totalPages.setFontAndSize(bf_helv, 10);
			totalPages.showTextAligned(Element.ALIGN_LEFT, "" + (writer.getPageNumber()-1), 50,0, 0);
			totalPages.endText();
		
        }
		
	}
	
	private void header() throws MalformedURLException, IOException, DocumentException{
		
		Image logo = Image.getInstance(getClass().getResource("/imagenes/LogoTASC.png"));
		PdfContentByte canvas = writer.getDirectContent();
		Font helvetica = new Font(FontFamily.HELVETICA, 12);
	    BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
	    canvas.beginText();
	    canvas.setFontAndSize(bf_helv, 8);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "Tecnología Avanzada de Seguridad y Control, S.L.", 30, height-20, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "c/Imprenta, 8, P-1 Of. 15-16", 30, height-30, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "28760 Tres Cantos, Madrid (Spain)", 30, height - 40, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "Tel. +34 9180792 / Fax: +34 918035875", 30, height - 50, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "email: logistica@tasc.es", 30, height - 60, 0);
	    canvas.endText();
	    
	    
	    logo.scaleToFit(150, 40);
	    logo.setAbsolutePosition(400, height-logo.getHeight()-25);
	    canvas.addImage(logo);
	    canvas.setFontAndSize(bf_helv, 12);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "www.tasc.es", 425, height - 62, 0);

	}
	
	private void client() throws DocumentException{
		String name = ""+ presu.getCliente().getNombre();
		String addr = ""+presu.getCliente().getDireccs() ;
		String CP = ""+presu.getCliente().getCp() ;
		String pobl = ""+presu.getCliente().getPoblacion() ;
		String prov =""+ presu.getCliente().getProvincia() ;
		String tlf =""+ presu.getCliente().getTlf() ;
		
		
		PdfContentByte canvas = writer.getDirectContent();
		Font helvetica = new Font(FontFamily.HELVETICA, 12);
	    BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
	    canvas.beginText();
	    canvas.setFontAndSize(bf_helv, 10);
	    canvas.showTextAligned(Element.ALIGN_LEFT,name, 70, height-100, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, addr,70, height-111, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, CP, 70, height - 122, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, pobl.trim(), 100, height - 122, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, prov.trim()+" ESPAÑA", 70, height - 133, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, tlf, 70, height - 144, 0);

	    Paragraph p = new Paragraph();
	    DottedLineSeparator separator = new DottedLineSeparator();
	    separator.setPercentage(59500f / 523f);
	    p.add(separator);
	    canvas.showTextAligned(Element.ALIGN_LEFT,p.getContent(), 70, height - 150, 0);
	    canvas.endText();
	}
	
	private void fecha_num(){
		
		String fecha = presu.getFecha();
		String oferta = presu.getnOferta();
		
		Font helvetica = new Font(FontFamily.HELVETICA, 10);
	    BaseFont bf_helv = helvetica.getCalculatedBaseFont(false);
	    PdfContentByte canvas = writer.getDirectContent();
	    canvas.beginText();
	    canvas.setFontAndSize(bf_helv, 10);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "FECHA: "+fecha, 415, height - 100, 0);
	    canvas.showTextAligned(Element.ALIGN_LEFT, "OFERTA: "+oferta, 415, height - 120, 0);
	    
	    canvas.endText();
			
	}
	
	
	
	private void createTitleTable() throws DocumentException{
		
		PdfContentByte canvas = writer.getDirectContent();
		
		table = new PdfPTable(6);
		table.setTotalWidth(width-document.leftMargin()-document.rightMargin());
		table.setWidthPercentage(100);
		table.setWidths(new int[]{2, 3, 2, 17, 3, 3});
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("It",new Font(FontFamily.HELVETICA, 9)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Código",new Font(FontFamily.HELVETICA, 9)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Cant.",new Font(FontFamily.HELVETICA, 9)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Descripción",new Font(FontFamily.HELVETICA, 9)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Precio/U",new Font(FontFamily.HELVETICA, 9)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("Importe",new Font(FontFamily.HELVETICA, 9)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		//cell.setColspan(3);
		//cell.setRowspan(2);
	    //table.addCell(cell);
		
		table.writeSelectedRows(0, -1, 0, -1,document.leftMargin(), height-170, canvas);
		
		
	}
	
	private void createTable() throws DocumentException{
		
		JTable tableOri = presu.getTable();
		
		table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{2, 3, 2, 17, 3, 3});
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

	    
		PdfPCell cell = new PdfPCell(new Phrase("",new Font(FontFamily.HELVETICA, 9)));
		cell.setPadding(-2);
	    cell.setPaddingTop(-1);
	    cell.setPaddingBottom(-1);
	    cell.setExtraParagraphSpace(-4);
	    int itemNumber = 1;//
	    
	    int rows = tableOri.getModel().getRowCount();
	    
	    for(int i=0; i<rows; i++){
	    	
    		cell.setBorder(0);
		    String it = ""+tableOri.getValueAt(i, 0);
		    if(!it.contains("null") && !it.trim().equals("")){
		    	it = ""+itemNumber;
		    	itemNumber++;
		    }
		    else
		    	it = "";

    		Phrase item = new Phrase(it, new Font(FontFamily.HELVETICA, 9));
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setPhrase(item);   
		    table.addCell(cell);
	    		
		    Phrase codigo = new Phrase(getString( ""+tableOri.getValueAt(i, 0) ), new Font(FontFamily.HELVETICA, 8));
		    cell.setPhrase(codigo);
		    table.addCell(cell);
	    	
		    Phrase cantidad = new Phrase(getString(""+tableOri.getValueAt(i, 1)), new Font(FontFamily.HELVETICA, 9));
		    cell.setPhrase(cantidad);
		    table.addCell(cell);
		    
		    Phrase descripcion = new Phrase(getString(""+tableOri.getValueAt(i, 2)), new Font(FontFamily.HELVETICA, 8));
		    cell.setPhrase(descripcion);
		    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		    cell.setExtraParagraphSpace(4);
		    table.addCell(cell);
		    
		    Phrase precioU = new Phrase(getString(""+tableOri.getValueAt(i, 3)), new Font(FontFamily.HELVETICA, 9));
		    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell.setPhrase(precioU);
		    table.addCell(cell);
		    
		    Phrase importe = new Phrase(getString(""+tableOri.getValueAt(i, 5)), new Font(FontFamily.HELVETICA, 9));
		    cell.setPhrase(importe);
		    table.addCell(cell);
		    
		    
	    	
	    	
	    	
	    }
	    
	    document.add(table);

	}
	
	private String getString(String s){
		
		if(s.contains("null"))
			return "";
		else
			return s;
		
	}
	
	private void footer() throws DocumentException{
		
		String base = presu.getBase();
		String iva = presu.getIva();
		String totalIva = presu.getTotalIva();
		String total = presu.getTotal();
		

	
		PdfContentByte canvas = writer.getDirectContent();
	
		table = new PdfPTable(4);
		table.setTotalWidth(300);
		//table.setWidthPercentage(50);
		table.setWidths(new int[]{4, 2, 4, 4});
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);
		
		PdfPCell cell;
		
		// Cabecera
		cell = new PdfPCell(new Phrase("BASE",new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("IVA",new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("T.IVA",new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("TOTAL",new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		// Totales
		cell = new PdfPCell(new Phrase(base,new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(iva,new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(totalIva,new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(total,new Font(FontFamily.HELVETICA, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		
		
		table.writeSelectedRows(0, -1, 0, -1,270, 60, canvas);
		
		//document.add(table);
		
		
		
	}

}
