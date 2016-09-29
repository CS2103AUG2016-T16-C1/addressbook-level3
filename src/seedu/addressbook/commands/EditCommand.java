package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.HashSet;
import java.util.Set;

/**
 * Edits a person's details to the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edits a person's details in the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: INDEX DETAIL_TO_CHANGE NEW_DETAIL\n\t"
            + "Example: " + COMMAND_WORD  
            + " 1 NAME  johncena or " + COMMAND_WORD + " 1 EMAIL johncena@wwemail.com";

    public static final String MESSAGE_SUCCESS = "Person: %1$s, details updated ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final ReadOnlyPerson toEdit;
    private final Person edited;


    /**
     *  Convenience constructor to delete old person and create new person
     * 
     * @param
     */
    public EditCommand(int targetVisibleIndex, String detail, String updatedDetail ) 
    		throws IllegalValueException{
    	
    	super(targetVisibleIndex);
    	this.toEdit = getTargetPerson();
    	
    	if(detail.equalsIgnoreCase("NAME")){
			this.edited = new Person(
			        new Name(updatedDetail),
			        this.toEdit.getPhone(),
			        this.toEdit.getEmail(),
			        this.toEdit.getAddress(),
			        this.toEdit.getTags()
			        );
			
    
    	}else if(detail.equalsIgnoreCase("ADDRESS")){
			this.edited = new Person(
			        this.toEdit.getName(),
			        this.toEdit.getPhone(),
			        this.toEdit.getEmail(),
			        new Address(updatedDetail, true),
			        this.toEdit.getTags()
			        );
    		
    	}else if(detail.equalsIgnoreCase("EMAIL")){
			this.edited = new Person(
			        this.toEdit.getName(),
			        this.toEdit.getPhone(),
			        new Email(updatedDetail, true),
			        this.toEdit.getAddress(),
			        this.toEdit.getTags()
			        );
    		
    	}else if(detail.equalsIgnoreCase( "PHONE")){
			this.edited = new Person(
			        this.toEdit.getName(),
			        new Phone(updatedDetail, true),
			        this.toEdit.getEmail(),
			        this.toEdit.getAddress(),
			        this.toEdit.getTags()
			        );
    		
    	}else{
    		this.edited = new Person(this.toEdit);
    	}
    	
    }
    public EditCommand(ReadOnlyPerson toEdit, Person edited){
    	this.toEdit = toEdit;
    	this.edited = edited;
    }

    public ReadOnlyPerson getPerson() {
        return toEdit;
    }

    @Override
    public CommandResult execute() {
        try {
        	addressBook.addPerson(getEdited());
        	addressBook.removePerson(toEdit);
            return new CommandResult(String.format(MESSAGE_SUCCESS, getEdited()));
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
			return  new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
		}
    }


	public Person getEdited() {
		return edited;
	}

}
