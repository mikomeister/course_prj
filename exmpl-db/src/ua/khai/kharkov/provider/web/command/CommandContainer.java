package ua.khai.kharkov.provider.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CommandContainer {
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("updateSettings", new UpdateSettingsCommand());
		commands.put("updateCash", new UpdateCashCommand());
		commands.put("setCash", new SetCashCommand());
		
//		
//		 users commands
		commands.put("listServices", new ListServicesCommand());
		commands.put("makeOrder", new MakeOrderCommand());
		commands.put("showUserOrders", new ShowUserOrdersCommand());
		commands.put("payAnOrder", new PayAnOrderCommand());
		commands.put("writeToTxt", new WriteToTxtCommand());
//		
		// admin commands
		commands.put("listOrders", new ListOrdersCommand());
		commands.put("editTariff", new EditTariffCommand());
		commands.put("updateTariff", new UpdateTariffCommand());
		commands.put("deleteTariff", new DeleteTariffCommand());
		commands.put("privateOffice", new PrivateOfficeCommand());
		commands.put("newTariff", new NewTariffCommand());
		commands.put("createNewTariff", new CreateNewTariffCommand());
		commands.put("usersCommand", new UsersCommand());
		commands.put("registrationUserCommand", new RegistrationUserCommand());
		commands.put("findUserByLogin", new FindUserByLoginCommand());
		commands.put("editUserStatus", new EditUserStatusCommand());
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}