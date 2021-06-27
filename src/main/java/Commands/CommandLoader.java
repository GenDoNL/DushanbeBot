package Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLoader {
  private static CommandLoader instance;
  Map<String, Command> commandMap;
  List<Command> commandList;

  /**
   * Private constructor of the singleton CommandLoader.
   * All commands are added to the commandList, after which they are put into a HashMap to attain a fast look-up speed.
   */
  private CommandLoader() {
    commandList = new ArrayList<>();
    commandList.add(new PingCommand());
    commandList.add(new SetKeyCommand());

    init();
  }

  /**
   * Initialize the map that links all aliases to the corresponding commands.
   */
  private void init() {
    commandMap = new HashMap<>();
    for (var command: commandList) {
      for (var alias : command.getAlias()) {
        commandMap.put(alias, command);
      }
    }
  }

  public static CommandLoader getInstance() {
    if (instance == null) {
      instance = new CommandLoader();
    }
    return instance;
  }

  public Command getCommand(String commandName) {
    return commandMap.get(commandName);
  }

  public Map<String, Command> getCommands() {
    return this.commandMap;
  }

}
