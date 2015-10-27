package org.corpus_tools.pepper.cli;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;

import org.corpus_tools.pepper.exceptions.PepperException;

public class Greet {

	/**
	 * Fills up a string with blanks until length.
	 * 
	 * @param text
	 * @param length
	 * @return
	 */
	public static String fillUpBlanks(String text, int length) {
		StringBuilder str = new StringBuilder();
		str.append(text);
		int numOfBlanks = length - text.length();
		for (int i = 0; i < numOfBlanks; i++) {
			str.append(" ");
		}
		return (str.toString());
	}

	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * 
	 * @return welcome screen
	 * @throws ParseException
	 */
	public static String getHello() throws ParseException {
		return (getHello(PepperStarter.CONSOLE_WIDTH, "saltnpepper@lists.hu-berlin.de", "http://u.hu-berlin.de/saltnpepper"));
	}

	/**
	 * Returns a formatted String, a kind of a welcome screen of Pepper.
	 * 
	 * @return welcome screen
	 * @throws ParseException
	 */
	public static String getHello(int width, String eMail, String hp) throws ParseException {
		StringBuilder retVal = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		if ((cal.get(Calendar.DAY_OF_MONTH) == 31 && cal.get(Calendar.MONTH) + 1 == 10) && PepperStarter.CONSOLE_WIDTH_120 == width) {
			// return Helloween header
			retVal.append("************************************************************************************************************************\n");
			retVal.append("*             ,_   .-.                      _   _                                                                      *\n");
			retVal.append("*             \\ `\\/ '`                     | | | | __ _ _ __  _ __  _   _                                              *\n");
			retVal.append("*        _.--\"|  |\"--._                    | |_| |/ _` | '_ \\| '_ \\| | | |                 /\\                 /\\       *\n");
			retVal.append("*      .' '  '`--`'  ' '.                  |  _  | (_| | |_) | |_) | |_| |                / \\'._   (\\_/)   _.'/ \\      *\n");
			retVal.append("*     /  '  /\\    /\\  '  \\                 |_| |_|\\__,_| .__/| .__/ \\__, |               /_.''._'--('.')--'_.''._\\     *\n");
			retVal.append("*    ;  '  /o_\\  /o_\\  '  ;                        ____ |_|   |_|    |___/               | \\_ / `;=/ \" \\=;` \\ _/ |     *\n");
			retVal.append("*    |  .    . /\\ :    .  |                       |  _ \\ ___ _ __  _ __   ___ _ __        \\/ `\\__|`\\___/`|__/`  \\/     *\n");
			retVal.append("*    ;  . /\\ .'--'. /\\ .  ;                       | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|     jgs`      \\(/|\\)/       `      *\n");
			retVal.append("*     \\  .\\ \\/\\/\\/\\/ /.  /                        |  __/  __/ |_) | |_) |  __/ |                   \" ` \"               *\n");
			retVal.append("*      '._:\\_/\\__/\\_/._.'                         |_|   \\___| .__/| .__/ \\___|_|                                       *\n");
			retVal.append("* jgs     `'--'--'--'`                                      |_|   |_|                                                  *\n");
			retVal.append("************************************************************************************************************************\n");
			retVal.append("* Pepper is a Salt model based converter for a variety of linguistic formats.                                          *\n");
			retVal.append("* For further information, visit: " + fillUpBlanks(hp, 85) + "*\n");
			retVal.append("* For contact write an eMail to:  " + fillUpBlanks(eMail, 85) + "*\n");
			retVal.append("* Version of Pepper:              " + fillUpBlanks(PepperStarter.getVersion(), 85) + "*\n");
			retVal.append("************************************************************************************************************************\n");
		} else if ((cal.get(Calendar.MONTH) + 1 == 12 && (cal.get(Calendar.DAY_OF_MONTH) == 24 || cal.get(Calendar.DAY_OF_MONTH) == 25 || cal.get(Calendar.DAY_OF_MONTH) == 26)) && PepperStarter.CONSOLE_WIDTH_120 == width) {
			retVal.append("                                                         .-\"``\"-.\n");
			retVal.append("                                                        /______; \\\n");
			retVal.append("                                                       {_______}\\|\n");
			retVal.append("                                                       (/ a a \\)(_)\n");
			retVal.append("                                                       (.-.).-.)\n");
			retVal.append("*************************************************ooo**(    ^    )**ooo**************************************************\n");
			retVal.append("*                                                      '-.___.-'                                                       *\n");
			retVal.append("*                            __  __                        ____                                                        *\n");
			retVal.append("*                           |  \\/  | ___ _ __ _ __ _   _  |  _ \\ ___ _ __  _ __   ___ _ __                             *\n");
			retVal.append("*                           | |\\/| |/ _ \\ '__| '__| | | | | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|                            *\n");
			retVal.append("*                           | |  | |  __/ |  | |  | |_| | |  __/  __/ |_) | |_) |  __/ |                               *\n");
			retVal.append("*                           |_|  |_|\\___|_|  |_|   \\__, | |_|   \\___| .__/| .__/ \\___|_|                               *\n");
			retVal.append("*                                                  |___/            |_|   |_|                                          *\n");
			retVal.append("************************************************************************************************************************\n");
			retVal.append("* Pepper is a Salt model based converter for a variety of linguistic formats.                                          *\n");
			retVal.append("* For further information, visit: " + fillUpBlanks(hp, 85) + "*\n");
			retVal.append("* For contact write an eMail to:  " + fillUpBlanks(eMail, 85) + "*\n");
			retVal.append("* Version of Pepper:              " + fillUpBlanks(PepperStarter.getVersion(), 85) + "*\n");
			retVal.append("************************************************************************************************************************\n");
			retVal.append("                                                       |_ | _|\n");
			retVal.append("                                                       /-'Y'-\\\n");
			retVal.append("                                                   jgs(__/ \\__)\n");
		} else {
			// return default Pepper header
			if (PepperStarter.CONSOLE_WIDTH_80 == width) {
				retVal.append("********************************************************************************\n");
				retVal.append("*                    ____                                                      *\n");
				retVal.append("*                   |  _ \\ ___ _ __  _ __   ___ _ __                           *\n");
				retVal.append("*                   | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|                          *\n");
				retVal.append("*                   |  __/  __/ |_) | |_) |  __/ |                             *\n");
				retVal.append("*                   |_|   \\___| .__/| .__/ \\___|_|                             *\n");
				retVal.append("*                             |_|   |_|                                        *\n");
				retVal.append("*                                                                              *\n");
				retVal.append("********************************************************************************\n");
				retVal.append("* Pepper is a Salt model based converter for a variety of linguistic formats.  *\n");
				retVal.append("* For further information, visit: " + fillUpBlanks(hp, 45) + "*\n");
				retVal.append("* For contact write an eMail to:  " + fillUpBlanks(eMail, 45) + "*\n");
				retVal.append("* Version of Pepper:              " + fillUpBlanks(PepperStarter.getVersion(), 45) + "*\n");
				retVal.append("********************************************************************************\n");
				retVal.append("\n");
			} else {
				retVal.append("************************************************************************************************************************\n");
				retVal.append("*                                         ____                                                                         *\n");
				retVal.append("*                                        |  _ \\ ___ _ __  _ __   ___ _ __                                              *\n");
				retVal.append("*                                        | |_) / _ \\ '_ \\| '_ \\ / _ \\ '__|                                             *\n");
				retVal.append("*                                        |  __/  __/ |_) | |_) |  __/ |                                                *\n");
				retVal.append("*                                        |_|   \\___| .__/| .__/ \\___|_|                                                *\n");
				retVal.append("*                                                  |_|   |_|                                                           *\n");
				retVal.append("*                                                                                                                      *\n");
				retVal.append("************************************************************************************************************************\n");
				retVal.append("* Pepper is a Salt model based converter for a variety of linguistic formats.                                          *\n");
				retVal.append("* For further information, visit: " + fillUpBlanks(hp, 85) + "*\n");
				retVal.append("* For contact write an eMail to:  " + fillUpBlanks(eMail, 85) + "*\n");
				retVal.append("* Version of Pepper:              " + fillUpBlanks(PepperStarter.getVersion(), 85) + "*\n");
				retVal.append("************************************************************************************************************************\n");
				retVal.append("\n");
			}
		}
		return (retVal.toString());
	}

	public static String getGoodBye(int width) {
		StringBuilder retVal = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		if ((cal.get(Calendar.DAY_OF_MONTH) == 31 && cal.get(Calendar.MONTH) + 1 == 10) && PepperStarter.CONSOLE_WIDTH_120 == width) {
			// return helloween footer
			retVal.append("************************************************************************************************************************\n");
			retVal.append("*        _))._                             _____                             _ _                             .-.       *\n");
			retVal.append("*      /`'---'`\\                          |  ___|_ _ _ __ ___  __      _____| | |                          _/oo \\      *\n");
			retVal.append("*     |  <\\ />  |                         | |_ / _` | '__/ _ \\ \\ \\ /\\ / / _ \\ | |                         ( \\v  /__    *\n");
			retVal.append("*     | \\  ^  / |                         |  _| (_| | | |  __/  \\ V  V /  __/ | |                          \\/   ___)   *\n");
			retVal.append("*     \\  '-'-'  /                         |_|  \\__,_|_|  \\___|   \\_/\\_/ \\___|_|_|                          /     \\_    *\n");
			retVal.append("*  jgs '--'----'                                                                                        jgs\\_.-.___)    *\n");
			retVal.append("************************************************************************************************************************\n");
		}else if ((cal.get(Calendar.MONTH) + 1 == 12 && (cal.get(Calendar.DAY_OF_MONTH) == 24 || cal.get(Calendar.DAY_OF_MONTH) == 25 || cal.get(Calendar.DAY_OF_MONTH) == 26)) && PepperStarter.CONSOLE_WIDTH_120 == width) {
			retVal.append("************************************************************************************************************************\n");
			retVal.append("*         ____                               _                                                   __.  ,--,             *\n");
			retVal.append("*        | __ )  ___    __ _  ___   ___   __| |       .-/___,-/___,-/___,-/___,           _.-.=,{\\/ _/  /`)            *\n");
			retVal.append("*        |  _ \\ / _ \\  / _` |/ _ \\ / _ \\ / _` |     .-/___,-/___,-/___,-/___, )     _..-'`-(`._(_.;`   /               *\n");
			retVal.append("*        | |_) |  __/ | (_| | (_) | (_) | (_| |      `\\ _ )`\\ _ )`\\ _ )`\\ _ )<`--''`     (__\\_________/___             *\n");
			retVal.append("*        |____/ \\___|  \\__, |\\___/ \\___/ \\__,_|       /< <\\ </ /< /< /< </ /<           (_____Y_____Y___,  jgs         *\n");
			retVal.append("*                      |___/                                                                                           *\n");
			retVal.append("************************************************************************************************************************\n");
		} else {
			// return default Pepper footer
			if (PepperStarter.CONSOLE_WIDTH_80 == width) {
				retVal.append("********************************************************************************\n");
				retVal.append("*   __o__                                                                      *\n");
				retVal.append("*  (_____)               ____                 _   _                            *\n");
				retVal.append("*   \\   /               / ___| ___   ___   __| | | |__  _   _  ___             *\n");
				retVal.append("*   |   |              | |  _ / _ \\ / _ \\ / _` | | '_ \\| | | |/ _ \\            *\n");
				retVal.append("*   /___\\              | |_| | (_) | (_) | (_| | | |_) | |_| |  __/            *\n");
				retVal.append("*  (_____)              \\____|\\___/ \\___/ \\__,_| |_.__/ \\__, |\\___|            *\n");
				retVal.append("*  (_____)                                              |___/                  *\n");
				retVal.append("********************************************************************************\n");
			} else {
				retVal.append("************************************************************************************************************************\n");
				retVal.append("*        __o__                                                                                             __o__       *\n");
				retVal.append("*       (_____)                         ____                 _   _                                        (_____)      *\n");
				retVal.append("*        \\   /                         / ___| ___   ___   __| | | |__  _   _  ___                          \\   /       *\n");
				retVal.append("*        |   |                        | |  _ / _ \\ / _ \\ / _` | | '_ \\| | | |/ _ \\                         |   |       *\n");
				retVal.append("*        /___\\                        | |_| | (_) | (_) | (_| | | |_) | |_| |  __/                         /___\\       *\n");
				retVal.append("*       (_____)                        \\____|\\___/ \\___/ \\__,_| |_.__/ \\__, |\\___|                        (_____)      *\n");
				retVal.append("*       (_____)                                                        |___/                              (_____)      *\n");
				retVal.append("************************************************************************************************************************\n");
			}
		}
		return (retVal.toString());
	}

}
