/*
 * MCreator (https://mcreator.net/)
 * Copyright (C) 2020 Pylo and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.mcreator.blockly.java;

import java.util.HashMap;

public final class JavaKeywordsMap {

	public static final HashMap<String, String> BINARY_LOGIC_OPERATORS = new HashMap<>() {{
		// logic binary operations
		put("EQ", "==");
		put("NEQ", "!=");
		put("AND", "&&");
		put("OR", "||");
		put("XOR", "^");

		// math logic binary operations
		put("LT", "<");
		put("LTE", "<=");
		put("GT", ">");
		put("GTE", ">=");
	}};

	public static final HashMap<String, String> BINARY_MATH_OPERATORS = new HashMap<>() {{
		// math calc binary operations
		put("ADD", "+");
		put("MINUS", "-");
		put("MULTIPLY", "*");
		put("DIVIDE", "/ "); // The space prevents accidental inline comments from int/float markers
		put("MOD", "%");
		put("BAND", "&");
		put("BOR", "|");
		put("BXOR", "^");
	}};

	public static final HashMap<String, String> MATH_METHODS = new HashMap<>() {{
		// single input math operations
		put("ROOT", "sqrt");
		put("CUBEROOT", "cbrt");
		put("ABS", "abs");
		put("LN", "log");
		put("LOG10", "log10");
		put("SIN", "sin");
		put("COS", "cos");
		put("TAN", "tan");
		put("ASIN", "asin");
		put("ACOS", "acos");
		put("ATAN", "atan");
		put("ROUND", "round");
		put("ROUNDUP", "ceil");
		put("ROUNDDOWN", "floor");
		put("RAD2DEG", "toDegrees");
		put("DEG2RAD", "toRadians");
		put("SIGNUM", "signum");

		// dual input math operations
		put("POWER", "pow");
		put("MIN", "min");
		put("MAX", "max");
		put("ATAN2", "atan2");
		put("HYPOT", "hypot");
	}};

	public static final HashMap<String, String> MATH_CONSTANTS = new HashMap<>() {{
		put("PI", "Math.PI");
		put("E", "Math.E");
		put("RANDOM", "Math.random()");
		put("NORMAL", "(new Random().nextGaussian())");
		put("INFINITY", "Double.POSITIVE_INFINITY");
		put("NINFINITY", "Double.NEGATIVE_INFINITY");
		put("NAN", "Double.NaN");
	}};

}
