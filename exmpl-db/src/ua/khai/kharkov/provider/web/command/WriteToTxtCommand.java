package ua.khai.kharkov.provider.web.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.db.DBManager;
import ua.khai.kharkov.provider.db.entity.Category;
import ua.khai.kharkov.provider.db.entity.Services;
import ua.khai.kharkov.provider.exception.AppException;

public class WriteToTxtCommand extends Command {

	private static final long serialVersionUID = -230824596336805240L;
	private static final Logger LOG = Logger.getLogger(ListServicesCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {
		LOG.debug("Command starts");
		int file = Integer.parseInt(request.getParameter("file"));
		LOG.trace("Request parameter: file --> " + file);

		List<Services> services = DBManager.getInstance().findServices();
		LOG.trace("Found in DB: servicesList --> " + services);
		String forward = Path.PAGE_ERROR_PAGE;
		// sort menu by category
		Collections.sort(services, new Comparator<Services>() {
			public int compare(Services o1, Services o2) {
				return (int) (o1.getCategoryId() - o2.getCategoryId());
			}
		});

		// get categories list
		List<Category> categories = DBManager.getInstance().findCategories();
		LOG.trace("Found in DB: categories --> " + categories);
		StringBuilder newstr = new StringBuilder();
		for (Category cat : categories) {
			newstr.append(cat.getName());
			newstr.append(System.lineSeparator());
			int number = 0;
			for (Services ser : services) {
				if (ser.getCategoryId() == cat.getId()) {
					newstr.append(++number);
					newstr.append(" ");
					newstr.append(ser.getName());
					newstr.append(" ");
					newstr.append(ser.getPrice());
					newstr.append(System.lineSeparator());
				}
			}
			newstr.append(System.lineSeparator());
		}
		System.out.println(newstr.toString());
		if (file == 0) {
			// File folder = new File(request.getServletContext().getRealPath("/"));
			File folder = new File("C:/eclipse-projects/java-training-2018-q2_artem.kharkov/");
			File filetxt = new File(folder, "services.txt");
			filetxt.createNewFile();
			FileWriter writer = new FileWriter(filetxt);
			writer.write(newstr.toString());
			writer.flush();
			writer.close();
			forward = "DownloadFileServlet?path=C:/eclipse-projects/java-training-2018-q2_artem.kharkov/services.txt";
		}
		if (file == 1) {
			String FILE_NAME = "C:\\eclipse-projects\\java-training-2018-q2_artem.kharkov\\services.pdf";
			Document document = new Document();
			try {
				PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
				document.open();
				Paragraph p = new Paragraph();
				p.add("Services list");
				p.setAlignment(Element.ALIGN_CENTER);
				document.add(p);
				Paragraph p2 = new Paragraph();
				p2.add(newstr.toString()); // no alignment
				document.add(p2);
				document.close();
				System.out.println("Done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			forward = "DownloadFileServlet?path=C:/eclipse-projects/java-training-2018-q2_artem.kharkov/services.pdf";
		}
		return forward;
	}
}
