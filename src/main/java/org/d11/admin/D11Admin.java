package org.d11.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class D11Admin {

    @Parameter(names = { "-help", "-h" }, description = "Display this help message.", help = true)
    private boolean help;
    @Parameter(names = { "-c", "-command" }, description = "The command to execute.", required = true)
    private String command;
    @Parameter(names = { "-commands" }, description = "List of available commands.", help = true)
    private boolean commandss;
    private final static Logger logger = LoggerFactory.getLogger(D11Admin.class);

    public static void main(String[] args) {
        D11Admin d11Admin = new D11Admin();
        JCommander jCommander = JCommander.newBuilder()
            .addObject(d11Admin)
            .build();
        jCommander.setProgramName("d11admin");

        try {
            jCommander.parse(args);
            d11Admin.run();
        } catch(ParameterException e) {
            jCommander.usage();
        }
    }

    public void run() {
        System.out.printf("%s %s", command, commandss);
    }

}
