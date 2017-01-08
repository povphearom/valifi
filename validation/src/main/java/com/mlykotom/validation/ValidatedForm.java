package com.mlykotom.validation;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;


/**
 * Bundles more fields together and provides validation for all of them + destroying
 */
public class ValidatedForm extends BaseObservable {
	private List<ValidatedBaseField> mFields = new ArrayList<>();


	public ValidatedForm(ValidatedBaseField... fields) {
		for(ValidatedBaseField field : fields) {
			addField(field);
		}
	}


	/**
	 * Checks whether all bundled fields are valid
	 *
	 * @return If any field is not valid -> false
	 */
	@Bindable
	public boolean getIsFormValid() {
		for(ValidatedBaseField field : mFields) {
			if(!field.getIsValid()) return false;
		}

		return true;
	}


	/**
	 * Adds field to this form so can be validated with others
	 *
	 * @param field to be validated through this form
	 */
	public void addField(ValidatedBaseField field) {
		field.setFormValidation(this);
		mFields.add(field);
	}


	/**
	 * Clears used resources by this form + clears validated field's resources.
	 * Should be called after done working with form and fields
	 */
	public void destroy() {
		for(ValidatedBaseField field : mFields) {
			field.destroy();
		}

		mFields.clear();
	}


	/**
	 * Field validation was changed and informs this form about it
	 *
	 * @param field which was changed
	 */
	void fieldValidationChanged(ValidatedBaseField field) {
		notifyPropertyChanged(com.mlykotom.validation.BR.isFormValid);
	}
}