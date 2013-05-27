package ro.utcluj.utils.validators;

public class BracketBalancingStringValidator {

	/**
	 * Open bracket types.
	 */
	private final static String	open	= "([<{";

	/**
	 * Closed bracket types.
	 */
	private final static String	close	= ")]>}";

	/**
	 * This method checks for opened brackets.
	 * 
	 * @param ch
	 *            the type of bracket.
	 * @return boolean result of the check
	 */
	private static boolean isOpen(final char ch) {
		return open.indexOf(ch) != -1;
	}

	/**
	 * This method checks for closed brackets.
	 * 
	 * @param ch
	 *            the type of the bracket.
	 * @return boolean result of the check.
	 */
	private static boolean isClose(final char ch) {
		return close.indexOf(ch) != -1;
	}

	/**
	 * This method checks whether or not a bracket is balanced.
	 * 
	 * @param chOpen
	 *            the type of the bracket.
	 * @param chClose
	 *            the type of the bracket.
	 * @return boolean result of the check.
	 */
	static boolean isMatching(char chOpen, char chClose) {
		return open.indexOf(chOpen) == close.indexOf(chClose);
	}

	/**
	 * Recursively checks if a given String has all the brackets in it balanced.
	 * 
	 * @param input
	 *            the input String
	 * @param stack
	 *            the stack of brackets
	 * @return boolean result of the check.
	 */
	public static boolean isBalanced(String input, String stack) {
		return input.isEmpty() ? stack.isEmpty() : isOpen(input.charAt(0)) ? isBalanced(input.substring(1),
				input.charAt(0) + stack) : isClose(input.charAt(0)) ? !stack.isEmpty()
				&& isMatching(stack.charAt(0), input.charAt(0)) && isBalanced(input.substring(1), stack.substring(1))
				: isBalanced(input.substring(1), stack);
	}

}
