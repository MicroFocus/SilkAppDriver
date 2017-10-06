import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.borland.silktest.jtf.BrowserBaseState;
import com.borland.silktest.jtf.Desktop;
import com.borland.silktest.jtf.common.BrowserType;
import com.borland.silktest.jtf.common.types.FindOptions;
import com.borland.silktest.jtf.xbrowser.DomLink;
import com.borland.silktest.jtf.xbrowser.DomTable;
import com.borland.silktest.jtf.xbrowser.DomTableRow;

public class Generate {

	private Desktop desktop = new Desktop();

	@Before
	public void baseState() {
		// Go to web page 'http://www.w3.org/TR/webdriver/'
		BrowserBaseState baseState = new BrowserBaseState();
		baseState.setBrowserType(BrowserType.PhantomJS);
		baseState.execute(desktop);
	}

	@Test
	public void generateEndpoints() {
		DomTable table = desktop.<DomTable>find("WebBrowser.BrowserWindow.simple");

		for (int idx = 1; idx < table.getRowCount(); idx++) {
			DomTableRow row = table.getRow(idx);

			String httpMethod = row.getCell(0).getText();
			String uriTemplate = row.getCell(1).getText();
			String commandName = row.getCell(2).getText();
			String commandHref = null;
			DomLink link = row.getCell(2).find("//a", new FindOptions(false, 0));

			if (link != null) {
				commandHref = link.getDomAttribute("href").toString();
			}

			generateMethod(httpMethod, uriTemplate, commandName, commandHref);
		}
	}

	@Test
	public void generateReadme() {
		DomTable table = desktop.<DomTable>find("WebBrowser.BrowserWindow.simple");

		System.out.println("| Command  |  Status | Comment |");
		System.out.println("|----------|---------|---------|");

		for (int idx = 1; idx < table.getRowCount(); idx++) {
			DomTableRow row = table.getRow(idx);

			String commandName = row.getCell(2).getText();
			String commandHref = null;
			DomLink link = row.getCell(2).find("//a", new FindOptions(false, 0));

			if (link != null) {
				commandHref = link.getDomAttribute("href").toString();
			}

			System.out.println("| [" + commandName + "](" + commandHref + ") | TODO |  |");
		}
	}

	@Test
	public void generateErrorEnum() {
		DomTable errorTable = desktop.<DomTable>find("//th[@textContents='Error Code']/../../..");

		for (int idx = 1; idx < errorTable.getRowCount(); idx++) {
			DomTableRow row = errorTable.getRow(idx);

			String errorCode = row.getCell(0).getText();
			String httpStatus = row.getCell(1).getText();
			String description = row.getCell(3).getText();

			String enumName = errorCode.replaceAll("\\ ", "\\_");
			enumName = enumName.toUpperCase();

			System.out.println(enumName + "(\"" + errorCode + "\", " + httpStatus + ", \"" + description + "\"),");
		}

	}

	public void generateMethod(String httpMethod, String uriTemplate, String commandName, String commandHref) {
		StringBuilder sb = new StringBuilder();

		Pattern pattern = Pattern.compile("\\{[^\\}]*\\}");
		Matcher m = pattern.matcher(uriTemplate);

		String methodName = commandName.replaceAll(" ", "");
		methodName = Character.toLowerCase(methodName.charAt(0)) + methodName.substring(1);

		StringBuilder params = new StringBuilder();

		Map<String, String> uriTemplateVariableRenamings = new HashMap<String, String>();

		while (m.find()) {
			String template = m.group(0);

			String name = template.substring(1, template.length() - 1);
			name = name.replace(" ", "");

			if (name.endsWith("id")) {
				String newName = name.substring(0, name.length() - 2);
				newName += "Id";

				uriTemplateVariableRenamings.put(name, newName);

				name = newName;
			}

			params.append("@PathVariable String " + name + ", ");
		}

		uriTemplate = uriTemplate.replaceAll(" ", "");

		for (String key : uriTemplateVariableRenamings.keySet()) {
			uriTemplate = uriTemplate.replace(key, uriTemplateVariableRenamings.get(key));
		}

		String requestParams = uriTemplate.replaceAll("\\{", "\" + ");
		requestParams = requestParams.replaceAll("\\}", "+ \"");

		params.append("@RequestBody(required = false) String body");

		sb.append("/**\n");
		sb.append(" * Implements the \"" + commandName + "\" command\n");
		sb.append(" * See " + commandHref + "\n");
		sb.append(" */\n");
		sb.append("@RequestMapping(value = \"" + uriTemplate + "\", method = RequestMethod." + httpMethod
				+ ", produces = \"application/json; charset=utf-8\")\n");
		sb.append("public @ResponseBody ResponseEntity<Response> " + methodName + "(" + params.toString()
				+ ") throws Throwable {\n");

		sb.append("  LOGGER.info(\"" + methodName + " -->\");\n");
		sb.append("  LOGGER.info(\"  -> \" + body);\n\n");
		sb.append("  // TODO: Implement me!\n\n");
		sb.append("  LOGGER.info(\"" + methodName + " <--\");\n\n");

		if (params.toString().contains("sessionId")) {
			sb.append("  return responseFactory.success(sessionId);\n");
		} else {
			sb.append("  return responseFactory.success();\n");
		}

		sb.append("}\n\n");

		System.out.println(sb.toString());
	}

}