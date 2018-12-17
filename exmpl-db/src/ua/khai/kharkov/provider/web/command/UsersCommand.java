package ua.khai.kharkov.provider.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.khai.kharkov.provider.Path;
import ua.khai.kharkov.provider.exception.AppException;

public class UsersCommand extends Command {


	private static final long serialVersionUID = 6063487824366681583L;


	private static final Logger LOG = Logger.getLogger(ListServicesCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		
		return Path.PAGE_USERS;
	}

}
