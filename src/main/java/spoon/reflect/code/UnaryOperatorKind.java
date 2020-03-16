/**
 * Copyright (C) 2006-2019 INRIA and contributors
 *
 * Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) of the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 *
 * SPDX-License-Identifier: MIT OR CECILL-C
 */
package spoon.reflect.code;

/**
 * This enumeration defines all the kinds of unary operators.
 */
public enum UnaryOperatorKind {
	/**
	 * Positivation.
	 */
	POS, // +
	/**
	 * Negation.
	 */
	NEG, // -
	/**
	 * Logical inversion.
	 */
	NOT, // !
	/**
	 * Binary complement.
	 */
	COMPL, // ~
	/**
	 * Incrementation pre assignment.
	 */
	PREINC, // ++ _
	/**
	 * Decrementation pre assignment.
	 */
	PREDEC, // -- _
	/**
	 * Incrementation post assignment.
	 */
	POSTINC, // _ ++
	/**
	 * Decrementation post assignment.
	 */
	POSTDEC
	// _ --
}
