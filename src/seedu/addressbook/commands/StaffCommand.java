package seedu.addressbook.commands;


/**
 * Shows staff of this program.
 */
public class StaffCommand extends Command{
    
    public static final String COMMAND_WORD = "staff";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" +"Shows staff of this program.\n\t"
            + "Example: " + COMMAND_WORD;
    
    public static final String MESSAGE_STAFF = "Nicolas, Quang, Tianze and Weijie";
    
    public StaffCommand() {}
    
    @Override
    public CommandResult execute() {
        return new CommandResult(MESSAGE_STAFF);
    }
}
