/**
 * Copyright (C) 2006-2015 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.reflect.declaration;

import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtFormalTypeDeclarer;
import spoon.reflect.declaration.CtModifiable;
import spoon.reflect.declaration.CtNamedElement;
import spoon.reflect.declaration.CtShadowable;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtTypedElement;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeParameterReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtVisitor;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static spoon.reflect.ModelElementContainerDefaultCapacities.CONSTRUCTOR_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY;

public class CtConstructorImpl<T> extends CtExecutableImpl<T> implements CtConstructor<T> {
	private static final long serialVersionUID = 1L;

	List<CtTypeParameterReference> formalTypeParameters = emptyList();

	Set<ModifierKind> modifiers = CtElementImpl.emptySet();

	@Override
	public void accept(CtVisitor visitor) {
		visitor.visitCtConstructor(this);
	}

	@Override
	public <C extends CtNamedElement> C setSimpleName(String simpleName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSimpleName() {
		return "<init>";
	}

	@Override
	@SuppressWarnings("unchecked")
	public CtType<T> getDeclaringType() {
		return (CtType<T>) parent;
	}

	@Override
	public CtTypeReference<T> getType() {
		if (getDeclaringType() == null) {
			return null;
		}
		return getDeclaringType().getReference();
	}

	@Override
	public <C extends CtTypedElement> C setType(CtTypeReference<T> type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<CtTypeParameterReference> getFormalTypeParameters() {
		return formalTypeParameters;
	}

	@Override
	public <T extends CtFormalTypeDeclarer> T addFormalTypeParameter(CtTypeParameterReference formalTypeParameter) {
		if (formalTypeParameter == null) {
			return (T) this;
		}
		if (formalTypeParameters == CtElementImpl.<CtTypeParameterReference>emptyList()) {
			formalTypeParameters = new ArrayList<>(CONSTRUCTOR_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
		}
		formalTypeParameter.setParent(this);
		formalTypeParameters.add(formalTypeParameter);
		return (T) this;
	}

	@Override
	public <T extends CtFormalTypeDeclarer> T setFormalTypeParameters(List<CtTypeParameterReference> formalTypeParameters) {
		if (formalTypeParameters == null || formalTypeParameters.isEmpty()) {
			this.formalTypeParameters = CtElementImpl.emptyList();
			return (T) this;
		}
		if (this.formalTypeParameters == CtElementImpl.<CtTypeParameterReference>emptyList()) {
			this.formalTypeParameters = new ArrayList<>(CONSTRUCTOR_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
		}
		this.formalTypeParameters.clear();
		for (CtTypeParameterReference formalTypeParameter : formalTypeParameters) {
			addFormalTypeParameter(formalTypeParameter);
		}
		return (T) this;
	}

	@Override
	public boolean removeFormalTypeParameter(CtTypeParameterReference formalTypeParameter) {
		return formalTypeParameter != null && formalTypeParameters != CtElementImpl.<CtTypeParameterReference>emptyList() && formalTypeParameters.remove(formalTypeParameter);
	}

	@Override
	public Set<ModifierKind> getModifiers() {
		return modifiers;
	}

	@Override
	public boolean hasModifier(ModifierKind modifier) {
		return getModifiers().contains(modifier);
	}

	@Override
	public <C extends CtModifiable> C setModifiers(Set<ModifierKind> modifiers) {
		this.modifiers = modifiers;
		return (C) this;
	}

	@Override
	public <C extends CtModifiable> C addModifier(ModifierKind modifier) {
		if (modifiers == CtElementImpl.<ModifierKind>emptySet()) {
			this.modifiers = EnumSet.noneOf(ModifierKind.class);
		}
		modifiers.add(modifier);
		return (C) this;
	}

	@Override
	public boolean removeModifier(ModifierKind modifier) {
		return !modifiers.isEmpty() && modifiers.remove(modifier);
	}

	@Override
	public <C extends CtModifiable> C setVisibility(ModifierKind visibility) {
		if (modifiers == CtElementImpl.<ModifierKind>emptySet()) {
			this.modifiers = EnumSet.noneOf(ModifierKind.class);
		}
		getModifiers().remove(ModifierKind.PUBLIC);
		getModifiers().remove(ModifierKind.PROTECTED);
		getModifiers().remove(ModifierKind.PRIVATE);
		getModifiers().add(visibility);
		return (C) this;
	}

	@Override
	public ModifierKind getVisibility() {
		if (getModifiers().contains(ModifierKind.PUBLIC)) {
			return ModifierKind.PUBLIC;
		}
		if (getModifiers().contains(ModifierKind.PROTECTED)) {
			return ModifierKind.PROTECTED;
		}
		if (getModifiers().contains(ModifierKind.PRIVATE)) {
			return ModifierKind.PRIVATE;
		}
		return null;
	}

	boolean isShadow;

	@Override
	public boolean isShadow() {
		return isShadow;
	}

	@Override
	public <E extends CtShadowable> E setShadow(boolean isShadow) {
		this.isShadow = isShadow;
		return (E) this;
	}

	@Override
	public CtConstructor<T> clone() {
		return (CtConstructor<T>) super.clone();
	}
}
