/*
 * @Description: PDF操作演示，使用itextpdf组件
 * 来源：https://blog.csdn.net/weixin_36380516/article/details/76984283
 * @Author: tt
 * @Date: 2019-01-30 10:09:57
 * @LastEditTime: 2019-02-12 14:11:21
 * @LastEditors: tt
 */
package com.tt.tool;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Pdf {
  public void pdfDemoCode() throws DocumentException, IOException {
    final String FILE_DIR = Config.FILEUP_SAVEPATH + "PDF/";

    // =================创建一个PDF文件开始
    Document document = new Document();
    // 关联到磁盘文件
    PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "createSamplePDF.pdf"));
    // 打开文档
    document.open();
    // 添加内容
    document.add(new Paragraph("Hello World"));
    // 关闭文档
    document.close();
    // =================创建一个PDF文件结束

    // =================PDF页面相关设置开始
    Rectangle rect = new Rectangle(PageSize.B5.rotate());
    // 页面背景色
    rect.setBackgroundColor(BaseColor.ORANGE);
    Document doc = new Document(rect);
    FileOutputStream out = new FileOutputStream(FILE_DIR + "createSamplePDF.pdf");
    PdfWriter writer = PdfWriter.getInstance(doc, out);
    // PDF版本(默认1.4)
    writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
    // 文档属性
    doc.addTitle("Title@sample");
    doc.addAuthor("Author@rensanning");
    doc.addSubject("Subject@iText sample");
    doc.addKeywords("Keywords@iText");
    doc.addCreator("Creator@iText");
    // 页边空白
    doc.setMargins(10, 20, 30, 40);
    doc.open();
    doc.add(new Paragraph("Hello World"));
    // =================PDF页面相关设置结束

    // =================PDF设置密码开始
    writer.setEncryption("Hello".getBytes(), "World".getBytes(), PdfWriter.ALLOW_SCREENREADERS,
        PdfWriter.STANDARD_ENCRYPTION_128);

    doc.open();
    doc.add(new Paragraph("Hello World"));
    // =================PDF设置密码结束

    // =================PDF添加页面开始
    document.open();
    document.add(new Paragraph("First page"));
    document.newPage();
    writer.setPageEmpty(false);
    document.newPage();
    document.add(new Paragraph("New page"));
    // =================PDF添加页面结束

    // =================添加图片背景/图片水印开始
    PdfReader reader = new PdfReader(FILE_DIR + "setWatermark.pdf");
    PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "setWatermark2.pdf"));

    Image img = Image.getInstance("resource/watermark.jpg");
    img.setAbsolutePosition(200, 400);
    PdfContentByte under = stamp.getUnderContent(1); // 内容下面加水印
    under.addImage(img);

    // 文字水印
    PdfContentByte over = stamp.getOverContent(2); // 内容上层加水印
    over.beginText();
    BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
    over.setFontAndSize(bf, 18);
    over.setTextMatrix(30, 30);
    over.showTextAligned(Element.ALIGN_LEFT, "DUPLICATE", 230, 430, 45);
    over.endText();

    // 背景图
    Image img2 = Image.getInstance("resource/test.jpg");
    img2.setAbsolutePosition(0, 0);
    PdfContentByte under2 = stamp.getUnderContent(3);
    under2.addImage(img2);

    stamp.close();
    reader.close();
    // =================添加图片背景/图片水印结束

    // =================Chunk对象: a String, a Font, and some attributes
    document.add(new Chunk("China"));
    document.add(new Chunk(" "));
    Font font = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
    Chunk id = new Chunk("chinese", font);
    id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
    id.setTextRise(6);
    document.add(id);
    document.add(Chunk.NEWLINE);

    document.add(new Chunk("Japan"));
    document.add(new Chunk(" "));
    Font font2 = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
    Chunk id2 = new Chunk("japanese", font2);
    id2.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
    id2.setTextRise(6);
    id2.setUnderline(0.2f, -2f);
    document.add(id2);
    document.add(Chunk.NEWLINE);

    // Phrase对象: a List of Chunks with leading
    document.newPage();
    document.add(new Phrase("Phrase page"));

    Phrase director = new Phrase();
    Chunk name = new Chunk("China");
    name.setUnderline(0.2f, -2f);
    director.add(name);
    director.add(new Chunk(","));
    director.add(new Chunk(" "));
    director.add(new Chunk("chinese"));
    director.setLeading(24);
    document.add(director);

    Phrase director2 = new Phrase();
    Chunk name2 = new Chunk("Japan");
    name2.setUnderline(0.2f, -2f);
    director2.add(name2);
    director2.add(new Chunk(","));
    director2.add(new Chunk(" "));
    director2.add(new Chunk("japanese"));
    director2.setLeading(24);
    document.add(director2);

    // Paragraph对象: a Phrase with extra properties and a newline
    document.newPage();
    document.add(new Paragraph("Paragraph page"));

    Paragraph info = new Paragraph();
    info.add(new Chunk("China "));
    info.add(new Chunk("chinese"));
    info.add(Chunk.NEWLINE);
    info.add(new Phrase("Japan "));
    info.add(new Phrase("japanese"));
    document.add(info);

    // List对象: a sequence of Paragraphs called ListItem
    document.newPage();
    List list = new List(List.ORDERED);
    for (int i = 0; i < 10; i++) {
      ListItem item = new ListItem(String.format("%s: %d movies", "country" + (i + 1), (i + 1) * 100),
          new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE));
      List movielist = new List(List.ORDERED, List.ALPHABETICAL);
      movielist.setLowercase(List.LOWERCASE);
      for (int j = 0; j < 5; j++) {
        ListItem movieitem = new ListItem("Title" + (j + 1));
        List directorlist = new List(List.UNORDERED);
        for (int k = 0; k < 3; k++) {
          directorlist.add(String.format("%s, %s", "Name1" + (k + 1), "Name2" + (k + 1)));
        }
        movieitem.add(directorlist);
        movielist.add(movieitem);
      }
      item.add(movielist);
      list.add(item);
    }
    document.add(list);
    // =================Chunk对象结束

    // =================其他对象，Anchor对象: internal and external links
    Paragraph country = new Paragraph();
    Anchor dest = new Anchor("china", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
    dest.setName("CN");
    dest.setReference("http://www.china.com");// external
    country.add(dest);
    country.add(String.format(": %d sites", 10000));
    document.add(country);

    document.newPage();
    Anchor toUS = new Anchor("Go to first page.", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
    toUS.setReference("#CN");// internal
    document.add(toUS);

    // Image对象
    document.newPage();
    Image imgs = Image.getInstance("resource/test.jpg");
    imgs.setAlignment(Image.LEFT | Image.TEXTWRAP);
    imgs.setBorder(Image.BOX);
    imgs.setBorderWidth(10);
    imgs.setBorderColor(BaseColor.WHITE);
    imgs.scaleToFit(1000, 72);// 大小
    imgs.setRotationDegrees(-30);// 旋转
    document.add(imgs);

    // Chapter, Section对象（目录）
    document.newPage();
    Paragraph title = new Paragraph("Title");
    Chapter chapter = new Chapter(title, 1);

    title = new Paragraph("Section A");
    Section section = chapter.addSection(title);
    section.setBookmarkTitle("bmk");
    section.setIndentation(30);
    section.setBookmarkOpen(false);
    section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);

    Section subsection = section.addSection(new Paragraph("Sub Section A"));
    subsection.setIndentationLeft(20);
    subsection.setNumberDepth(1);

    document.add(chapter);
    // =================其他对象结束

    // =================画图相关：左右箭头
    document.add(new VerticalPositionMark() {

      public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
        canvas.beginText();
        BaseFont bf = null;
        try {
          bf = BaseFont.createFont(BaseFont.ZAPFDINGBATS, "", BaseFont.EMBEDDED);
        } catch (Exception e) {
          e.printStackTrace();
        }
        canvas.setFontAndSize(bf, 12);

        // LEFT
        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), llx - 10, y, 0);
        // RIGHT
        canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), urx + 10, y + 8, 180);

        canvas.endText();
      }
    });

    // 直线
    Paragraph p1 = new Paragraph("LEFT");
    p1.add(new Chunk(new LineSeparator()));
    p1.add("R");
    document.add(p1);
    // 点线
    Paragraph p2 = new Paragraph("LEFT");
    p2.add(new Chunk(new DottedLineSeparator()));
    p2.add("R");
    document.add(p2);
    // 下滑线
    LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
    Paragraph p3 = new Paragraph("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
    p3.add(UNDERLINE);
    document.add(p3);
    // =================画图相关结束

    // =================设置段落
    Paragraph p = new Paragraph(
        "In the previous example, you added a header and footer with the showTextAligned() method. This example demonstrates that it’s sometimes more interesting to use PdfPTable and writeSelectedRows(). You can define a bottom border for each cell so that the header is underlined. This is the most elegant way to add headers and footers, because the table mechanism allows you to position and align lines, images, and text.");

    // 默认
    p.setAlignment(Element.ALIGN_JUSTIFIED);
    document.add(p);

    document.newPage();
    p.setAlignment(Element.ALIGN_JUSTIFIED);
    p.setIndentationLeft(1 * 15f);
    p.setIndentationRight((5 - 1) * 15f);
    document.add(p);

    // 居右
    document.newPage();
    p.setAlignment(Element.ALIGN_RIGHT);
    p.setSpacingAfter(15f);
    document.add(p);

    // 居左
    document.newPage();
    p.setAlignment(Element.ALIGN_LEFT);
    p.setSpacingBefore(15f);
    document.add(p);

    // 居中
    document.newPage();
    p.setAlignment(Element.ALIGN_CENTER);
    p.setSpacingAfter(15f);
    p.setSpacingBefore(15f);
    document.add(p);

    // page排序居中
    writer.setLinearPageMode();
    doc.open();
    doc.add(new Paragraph("1 page"));
    doc.newPage();
    doc.add(new Paragraph("2 page"));
    doc.newPage();
    doc.add(new Paragraph("3 page"));
    doc.newPage();
    doc.add(new Paragraph("4 page"));
    doc.newPage();
    doc.add(new Paragraph("5 page"));

    int[] order = { 4, 3, 2, 1 };
    writer.reorderPages(order);

    // 目录
    document.add(new Chunk("Chapter 1").setLocalDestination("1"));

    document.newPage();
    document.add(new Chunk("Chapter 2").setLocalDestination("2"));
    document.add(new Paragraph(new Chunk("Sub 2.1").setLocalDestination("2.1")));
    document.add(new Paragraph(new Chunk("Sub 2.2").setLocalDestination("2.2")));

    document.newPage();
    document.add(new Chunk("Chapter 3").setLocalDestination("3"));

    // Code 2
    PdfContentByte cb = writer.getDirectContent();
    PdfOutline root = cb.getRootOutline();

    // Code 3
    @SuppressWarnings("unused")
    PdfOutline oline1 = new PdfOutline(root, PdfAction.gotoLocalPage("1", false), "Chapter 1");

    PdfOutline oline2 = new PdfOutline(root, PdfAction.gotoLocalPage("2", false), "Chapter 2");
    oline2.setOpen(false);

    @SuppressWarnings("unused")
    PdfOutline oline2_1 = new PdfOutline(oline2, PdfAction.gotoLocalPage("2.1", false), "Sub 2.1");
    @SuppressWarnings("unused")
    PdfOutline oline2_2 = new PdfOutline(oline2, PdfAction.gotoLocalPage("2.2", false), "Sub 2.2");

    @SuppressWarnings("unused")
    PdfOutline oline3 = new PdfOutline(root, PdfAction.gotoLocalPage("3", false), "Chapter 3");

    // Header, Footer
    writer.setPageEvent(new PdfPageEventHelper() {

      public void onEndPage(PdfWriter writer, Document document) {

        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();

        cb.beginText();
        BaseFont bf = null;
        try {
          bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        } catch (Exception e) {
          e.printStackTrace();
        }
        cb.setFontAndSize(bf, 10);

        // Header
        float x = document.top(-20);

        // 左
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "H-Left", document.left(), x, 0);
        // 中
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, writer.getPageNumber() + " page",
            (document.right() + document.left()) / 2, x, 0);
        // 右
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "H-Right", document.right(), x, 0);

        // Footer
        float y = document.bottom(-20);

        // 左
        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "F-Left", document.left(), y, 0);
        // 中
        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, writer.getPageNumber() + " page",
            (document.right() + document.left()) / 2, y, 0);
        // 右
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "F-Right", document.right(), y, 0);

        cb.endText();

        cb.restoreState();
      }
    });

    // 左右文字
    doc.open();
    doc.add(new Paragraph("1 page"));
    doc.newPage();
    doc.add(new Paragraph("2 page"));
    doc.newPage();
    doc.add(new Paragraph("3 page"));
    doc.newPage();
    doc.add(new Paragraph("4 page"));

    document.open();

    PdfContentByte canvas = writer.getDirectContent();

    Phrase phrase1 = new Phrase("This is a test!left");
    Phrase phrase2 = new Phrase("This is a test!right");
    Phrase phrase3 = new Phrase("This is a test!center");
    ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 10, 500, 0);
    ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, phrase2, 10, 536, 0);
    ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase3, 10, 572, 0);

    // 幻灯片放映
    writer.setPdfVersion(PdfWriter.VERSION_1_5);

    writer.setViewerPreferences(PdfWriter.PageModeFullScreen);// 全屏
    writer.setPageEvent(new PdfPageEventHelper() {
      public void onStartPage(PdfWriter writer, Document document) {
        writer.setTransition(new PdfTransition(PdfTransition.DISSOLVE, 3));
        writer.setDuration(5);// 间隔时间
      }
    });

    doc.open();
    doc.add(new Paragraph("1 page"));
    doc.newPage();
    doc.add(new Paragraph("2 page"));
    doc.newPage();
    doc.add(new Paragraph("3 page"));
    doc.newPage();
    doc.add(new Paragraph("4 page"));
    doc.newPage();
    doc.add(new Paragraph("5 page"));

    // 压缩到ZIP
    ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(FILE_DIR + "zipPDF.zip"));  
    for (int i = 1; i <= 3; i++) {  
        ZipEntry entry = new ZipEntry("hello_" + i + ".pdf");  
        zip.putNextEntry(entry);  
        writer.setCloseStream(false);  
        document.open();  
        document.add(new Paragraph("Hello " + i));  
        document.close();  
        zip.closeEntry();  
    }  
    zip.close();  

  }

}