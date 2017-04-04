package com.scorptech.turtleremote.socket;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by talhahavadar on 04/04/2017.
 */

public class CommandBuilder {

    public enum CommandType {
        JOYSTICK,
        MOVEMENT_CONTROL,
        ULTRASONIC_SENSOR
    }
    private ArrayList<String> newArguments;
    private CommandType newType;
    private boolean timestamp = true;

    public CommandBuilder() {
        newArguments = new ArrayList<>();
    }

    public class Command {

        private final String COMMAND_START = "#CMD#";
        private final String COMMAND_END = "#END#";
        private ArrayList<String> commandArguments;
        private CommandType type;
        private boolean timestamp;

        public Command(ArrayList<String> arguments, CommandType type, boolean timestamp) {
            this.commandArguments = arguments;
            this.type = type;
            this.timestamp = timestamp;
        }

        public String getCommandString() {
            StringBuilder sb = new StringBuilder();
            sb.append(COMMAND_START).append(getCommandTypeString());
            for(int i = 0; i < commandArguments.size(); i++) {
                sb.append(commandArguments.get(i));
                if(i != commandArguments.size() - 1) {
                    sb.append(";");
                }
            }
            if (this.timestamp) {
                sb.append("@").append(new Date().getTime());
            }
            sb.append(COMMAND_END);
            return sb.toString();
        }

        public ArrayList<String> getArguments() {
            return commandArguments;
        }

        public CommandType getType() {
            return this.type;
        }

        private String getCommandTypeString() {
            switch (type) {
                case JOYSTICK:
                case MOVEMENT_CONTROL:
                    return "MC|";
                case ULTRASONIC_SENSOR:
                    return "US|";
            }
            return "";
        }

    }

    public CommandBuilder addArgument(String arg) {
        newArguments.add(arg);
        return this;
    }

    public CommandBuilder setType(CommandType type) {
        newType = type;
        return this;
    }

    public CommandBuilder setTimestamp(boolean timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public CommandBuilder parse(String commandString) {

        /**
         * TODO: Parse command string according to standards.
         */

        return this;
    }


    public Command build() {
        if (newArguments.size() == 0)
            throw new RuntimeException("Command must has at least 1 argument.");
        if (newType == null)
            throw new RuntimeException("Command must has a type.");
        return new Command(newArguments, newType, timestamp);
    }


}
