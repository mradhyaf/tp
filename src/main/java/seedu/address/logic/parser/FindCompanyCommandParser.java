package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCompanyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entry.predicate.CompanyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCompanyPersonCommand object
 */
public class FindCompanyCommandParser implements Parser<FindCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCompanyCommand
     * and returns a FindCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCompanyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!isValid(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCompanyCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = argMultimap.getValue(PREFIX_NAME).orElse("").split("\\s+");
        String[] tagKeywords = argMultimap.getValue(PREFIX_TAG).orElse("").split("\\s+");

        CompanyContainsKeywordsPredicate predicate = new CompanyContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                Arrays.asList(tagKeywords));
        return new FindCompanyCommand(predicate);
    }

    private boolean isValid(ArgumentMultimap argumentMultimap) throws ParseException {
        boolean namePresent = argumentMultimap.getValue(PREFIX_NAME).isPresent();
        boolean tagPresent = argumentMultimap.getValue(PREFIX_TAG).isPresent();

        if (namePresent) {
            List<String> dummy = Arrays.asList(argumentMultimap.getValue(PREFIX_NAME).get().split("\\s+"));
            for (String s : dummy) {
                ParserUtil.parseName(s);
            }
        }
        if (tagPresent) {
            List<String> dummy = Arrays.asList(argumentMultimap.getValue(PREFIX_TAG).get().split("\\s+"));
            for (String s : dummy) {
                ParserUtil.parseTag(s);
            }
        }

        return namePresent || tagPresent;
    }
}
