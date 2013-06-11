/**
 * 
 */
package org.richfaces.validator;

import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.validation.BeanDescriptor;
import javax.validation.Configuration;
import javax.validation.ConstraintDescriptor;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

/**
 * @author asmirnov
 *
 */
public class MockValidationProvider implements ValidationProvider {
	
	public <T> ConstraintViolation<T> getDefaultConstraint(){
		return new ConstraintViolation<T>(){

		public ConstraintDescriptor<?> getConstraintDescriptor() {
			// TODO Auto-generated method stub
			return null;
		}

		public Object getInvalidValue() {
			// TODO Auto-generated method stub
			return null;
		}

		public Object getLeafBean() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getMessage() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getMessageTemplate() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getPropertyPath() {
			// TODO Auto-generated method stub
			return null;
		}

		public T getRootBean() {
			// TODO Auto-generated method stub
			return null;
		}

		public Class<T> getRootBeanClass() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	}

	/* (non-Javadoc)
	 * @see javax.validation.spi.ValidationProvider#buildValidatorFactory(javax.validation.spi.ConfigurationState)
	 */
	public ValidatorFactory buildValidatorFactory(
			ConfigurationState configurationState) {
		// TODO Auto-generated method stub
		return new ValidatorFactory(){

			public MessageInterpolator getMessageInterpolator() {
				// TODO Auto-generated method stub
				return null;
			}

			public Validator getValidator() {
				// TODO Auto-generated method stub
				return null;
			}

			public ValidatorContext usingContext() {
				// TODO Auto-generated method stub
				return new ValidatorContext(){

					public Validator getValidator() {
						// TODO Auto-generated method stub
						return new Validator(){

							public BeanDescriptor getConstraintsForClass(
									Class<?> clazz) {
								// TODO Auto-generated method stub
								return null;
							}

							public <T> Set<ConstraintViolation<T>> validate(
									T object, Class<?>... groups) {
								// TODO Auto-generated method stub
								ConstraintViolation<T> constrain = getDefaultConstraint();
								return Collections.singleton(constrain);
							}

							public <T> Set<ConstraintViolation<T>> validateProperty(
									T object, String propertyName,
									Class<?>... groups) {
								ConstraintViolation<T> constrain = getDefaultConstraint();
								return Collections.singleton(constrain);
							}

							public <T> Set<ConstraintViolation<T>> validateValue(
									Class<T> beanType, String propertyName,
									Object value, Class<?>... groups) {
								ConstraintViolation<T> constrain = getDefaultConstraint();
								return Collections.singleton(constrain);
							}
							
						};
					}

					public ValidatorContext messageInterpolator(
							MessageInterpolator messageInterpolator) {
						// TODO Auto-generated method stub
						return this;
					}

					public ValidatorContext traversableResolver(
							TraversableResolver traversableResolver) {
						// TODO Auto-generated method stub
						return this;
					}
					
				};
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see javax.validation.spi.ValidationProvider#createGenericConfiguration(javax.validation.spi.BootstrapState)
	 */
	public Configuration<?> createGenericConfiguration(BootstrapState state) {
		// TODO Auto-generated method stub
		return new Configuration(){

			public Configuration<?> addMapping(
					InputStream stream) {
				// TODO Auto-generated method stub
				return this;
			}

			public Configuration<?> addProperty(
					String name, String value) {
				// TODO Auto-generated method stub
				return this;
			}

			public ValidatorFactory buildValidatorFactory() {
				// TODO Auto-generated method stub
				return MockValidationProvider.this.buildValidatorFactory(null);
			}

			public Configuration<?> constraintValidatorFactory(
					ConstraintValidatorFactory constraintValidatorFactory) {
				// TODO Auto-generated method stub
				return this;
			}

			public MessageInterpolator getDefaultMessageInterpolator() {
				// TODO Auto-generated method stub
				return null;
			}

			public Configuration<?> ignoreXmlConfiguration() {
				// TODO Auto-generated method stub
				return this;
			}

			public Configuration<?> messageInterpolator(
					MessageInterpolator interpolator) {
				// TODO Auto-generated method stub
				return this;
			}

			public Configuration<?> traversableResolver(
					TraversableResolver resolver) {
				// TODO Auto-generated method stub
				return this;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see javax.validation.spi.ValidationProvider#createSpecializedConfiguration(javax.validation.spi.BootstrapState, java.lang.Class)
	 */
	public <T extends Configuration<T>> T createSpecializedConfiguration(
			BootstrapState state, Class<T> configurationClass) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.validation.spi.ValidationProvider#isSuitable(java.lang.Class)
	 */
	public boolean isSuitable(
			Class<? extends Configuration<?>> configurationClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
