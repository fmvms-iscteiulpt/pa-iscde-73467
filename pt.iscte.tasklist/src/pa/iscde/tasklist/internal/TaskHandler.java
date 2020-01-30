package pa.iscde.tasklist.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Manages the creation of tasks
 * @author franc
 *
 */

public class TaskHandler {

	static class Match {
		int start;
		String text;
	}
	
	static List<String> comments = new ArrayList<String>();
	static List<Integer> commentLines = new ArrayList<Integer>();
	static List<Match> commentMatches = new ArrayList<Match>();
	private Set<Task> tasks = new HashSet<Task>();
	
	/**
	 * Finds comments with tags and creates tasks
	 * @param tags List of tags
	 * @param file File we want to find comments
	 * @param s String text
	 */
	public void createTasks(List<String> tags, File file, String s) {
		comments.clear();
		commentLines.clear();
		commentMatches.clear();
		String text = s;

		Pattern commentsPattern = Pattern.compile("(//.*?$)|(/\\*.*?\\*/)", Pattern.MULTILINE | Pattern.DOTALL);
		Pattern stringsPattern = Pattern.compile("(\".*?(?<!\\\\)\")");

		Matcher commentsMatcher = commentsPattern.matcher(text);
		while (commentsMatcher.find()) {
			Match match = new Match();
			match.start = commentsMatcher.start();
			match.text = commentsMatcher.group();

			int line = getLine(text, commentsMatcher.start());
			commentLines.add(line);
			commentMatches.add(match);
		}

		List<Match> commentsList = new ArrayList<Match>();
		Matcher stringsMatcher = stringsPattern.matcher(text);
		while (stringsMatcher.find()) {
			for (Match comment : commentMatches) {
				if (comment.start > stringsMatcher.start() && comment.start < stringsMatcher.end())
					commentsList.add(comment);
			}
		}
		
		for (Match comment : commentsList)
			commentMatches.remove(comment);

		for (Match comment : commentMatches) {
			String commentText = comment.text.replaceAll("(//)|(/*)", "");
			comments.add(commentText);

		}

		for (int i = 0; i < comments.size(); i++) {
			if (findTags(tags, comments.get(i)) != "") {
				tasks.add(new Task("", comments.get(i), file.getAbsolutePath(), file.getName(), commentLines.get(i)));
			}
		}
	}
	
	/**
	 * Returns the line number of a given string
	 * @param data 
	 * @param start
	 * @return Integer Line number of the string
	 */
	static int getLine(String data, int start) {
		int line = 1;
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(data);
		matcher.region(0, start);
		while (matcher.find()) {
			line++;
		}
		return (line);
	}

	/**
	 * Finds strings with the given tags
	 * @param tags List of tags
	 * @param text Comment String
	 * @return String Text after the tag
	 */
	public String findTags(List<String> tags, String text) {
		String value = "";
		String patternString = "\\b(" + String.join("|", tags) + ")\\b";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			value = matcher.group(1);
		}
		return value;
	}
	
	/**
	 * Returns all the tasks found
	 * 
	 * @return Set of Tasks
	 */
	public Set<Task> getTasks() {
		return tasks;
	}

}
