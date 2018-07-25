package org.d11.admin.command;

import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Downloads and parses all matches for a match day.")
public class LineupsCommand extends D11Command {

    public LineupsCommand() {
        setName("lineups");
    }

    @Override
    public void execute() {
        System.out.println("jey jey");
    }
}
