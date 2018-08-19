package org.d11.admin.command;

import java.util.Arrays;
import java.util.List;

import com.beust.jcommander.converters.IParameterSplitter;

public class SpaceSplitter implements IParameterSplitter {

    @Override
    public List<String> split(String value) {
        return Arrays.asList(value.split(" "));
    }
}
