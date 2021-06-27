package Util;

import Commands.Command;
import Data.GuildData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandUtilTest {

    private static GuildData guildData;

    @BeforeEach
    public void before() {
        guildData = new GuildData();
        guildData.setKey("!");
    }

    @Test
    public void testGetPingCommand() {
        Command command = CommandUtil.getCommand("!ping", guildData);
        Assertions.assertNotNull(command);
        Assertions.assertEquals("Ping", command.getName());
    }

    @Test
    public void testNoPrefixPingCommand() {
        Command command = CommandUtil.getCommand("ping", guildData);
        Assertions.assertNull(command);
    }


    @Test
    public void testGetNoCommand() {
        Command command = CommandUtil.getCommand("dummy text", guildData);
        Assertions.assertNull(command);
    }


    @Test
    public void testNoTextGetCommand() {
        Command command = CommandUtil.getCommand("", guildData);
        Assertions.assertNull(command);
    }
}
