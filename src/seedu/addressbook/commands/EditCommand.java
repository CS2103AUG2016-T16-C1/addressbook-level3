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
            + "Parameters: INDEX NAME <NEW_NAME> / INDEX EMAIL <NEW_EMAIL> \n\t"
            + "/ INDEX ADDRESS <NEW_ADDRESS> / INDEX PHONE <NEW_PHONE> \n"
            + "Example: " + COMMAND_WORD  
            + "1 NAME  johncena or " + COMMAND_WORD + "1 EMAIL johncena@wwemail.com";

    public static final String MESSAGE_SUCCESS = "Person: %1$s, details updated ";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final ReadOnlyPerson toEdit;
    private Person edited;


    /**
     *  Convenience 
     * 
     * @param
     */
    public EditCommand(int targetVisibleIndex, String detail, String updatedDetail ){
    	super(targetVisibleIndex);
    	this.toEdit = getTargetPerson();
    	
    	if(detail == "NAME"){
    		try {
				this.edited = new Person(
				        new Name(updatedDetail),
				        this.toEdit.getPhone(),
				        this.toEdit.getEmail(),
				        this.toEdit.getAddress(),
				        this.toEdit.getTags()
				        );
			} catch (IllegalValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}else if(detail == "ADDRESS"){
    		try {
				this.edited = new Person(
				        this.toEdit.getName(),
				        this.toEdit.getPhone(),
				        this.toEdit.getEmail(),
				        new Address(updatedDetail, true),
				        this.toEdit.getTags()
				        );
			} catch (IllegalValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}else if(detail == "EMAIL"){
    		try {
				this.edited = new Person(
				        this.toEdit.getName(),
				        this.toEdit.getPhone(),
				        new Email(updatedDetail, true),
				        this.toEdit.getAddress(),
				        this.toEdit.getTags()
				        );
			} catch (IllegalValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}else if(detail == "PHONE"){
    		try {
				this.edited = new Person(
				        this.toEdit.getName(),
				        new Phone(updatedDetail, true),
				        this.toEdit.getEmail(),
				        this.toEdit.getAddress(),
				        this.toEdit.getTags()
				        );
			} catch (IllegalValueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
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
        	addressBook.removePerson(toEdit);
            addressBook.addPerson(edited);
            return new CommandResult(String.format(MESSAGE_SUCCESS, edited));
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
