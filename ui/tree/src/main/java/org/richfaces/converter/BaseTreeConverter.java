package org.richfaces.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.convert.Converter;

import org.richfaces.model.AbstractTreeDataModel;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public abstract class BaseTreeConverter implements Converter {

	private static final Pattern unescapePattern = Pattern.compile("_(:|_)|_(x[0-9A-Fa-f]{2}|u[0-9A-Fa-f]{4})?|(:)");
	
	//private static final Pattern htmlEscapePattern = Pattern.compile(":|_|[^0-9a-zA-Z]");
	
	private static final Pattern xhtmlEscapePattern = Pattern.compile(":|_|[^A-Za-z\\-\\.0-9\\xC0-\\xD6\\xD8-\\xF6\\u00F8-\\u02FF\\u0370-\\u037D\\u037F-\\u1FFF\\u200C-\\u200D\\u2070-\\u218F\\u2C00-\\u2FEF\\u3001-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFFD\\xB7\\u0300-\\u036F\\u203F-\\u2040]");

	private String escape(String s) {
		Matcher matcher = xhtmlEscapePattern.matcher(s);
		StringBuilder sb = new StringBuilder(s.length());
		
		int start = 0;
		
		while (matcher.find()) {
			int idx = matcher.start();
			sb.append(s, start, idx);
			sb.append('_');

			char c = s.charAt(idx);
			if (c == ':' || c == '_') {
				sb.append(c); // _: or __
			} else {
				String asHex = Integer.toHexString(c);
				switch (asHex.length()) {
				
				case 1:
					sb.append("x0"); // _x05
					break;
				case 2:
					sb.append("x"); // _xef
					break;
				case 3:
					sb.append("u0"); // _u0fed
					break;
				case 4:
					sb.append("u"); // _ufcda
					break;
					
				default:
					throw new IllegalArgumentException();
				}
				
				sb.append(asHex);
			}
			
			start = idx + 1;
		}

		if (start < s.length()) {
			sb.append(s, start, s.length());
		}
		
		return sb.toString();
	}
	
	protected void appendToKeyString(StringBuilder builder, String segment) {
		builder.append(escape(segment));
		builder.append(AbstractTreeDataModel.SEPARATOR);
	}
	
	protected List<String> splitKeyString(String string) {
		Matcher matcher = unescapePattern.matcher(string);
		
		List<String> result = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		
		while (matcher.find()) {
			if (matcher.group(3) == null) {
				String oneChar = matcher.group(1);
				if (oneChar != null) {
					matcher.appendReplacement(sb, "$1");
				} else {
					matcher.appendReplacement(sb, "");
					String hex = matcher.group(2);
					if (hex != null) {
						hex = hex.substring(1);
						
						int h = Integer.parseInt(hex, 16);
						sb.append((char) h);
					} else {
						throw new IllegalArgumentException();
					}
				}
			} else {
				matcher.appendReplacement(sb, "");
				result.add(sb.toString());
				sb.setLength(0);
			}
		}
		
		matcher.appendTail(sb);

		if (sb.length() != 0) {
			result.add(sb.toString());
			sb.setLength(0);
		}
		
		return result;
	}
	
}
