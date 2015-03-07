package grails.plugin.springsecurity.ui

import grails.plugin.springsecurity.SpringSecurityUtils
import junit.framework.Assert

class RegisterControllerTests extends GroovyTestCase {

	void testPasswordValidator_SameAsUsername() {
		GroovyTestCase.assertEquals 'command.password.error.username',
			RegisterController.passwordValidator('username', [username: 'username'])
	}

	void testPasswordValidator_MinLength() {

		SpringSecurityUtils.setSecurityConfig [:] as ConfigObject

		def command = [username: 'username']
		String password = 'h!Z7'

		Assert.assertFalse RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordRegex(password, command)

		GroovyTestCase.assertEquals 'command.password.error.strength',
			RegisterController.passwordValidator(password, command)

		SpringSecurityUtils.securityConfig.ui.password.minLength = 3

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordRegex(password, command)

		assertNull RegisterController.passwordValidator(password, command)
	}

	void testPasswordValidator_MaxLength() {

		SpringSecurityUtils.setSecurityConfig [:] as ConfigObject

		def command = [username: 'username']
		String password = 'h!Z7aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1'

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertFalse RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordRegex(password, command)

		GroovyTestCase.assertEquals 'command.password.error.strength',
			RegisterController.passwordValidator(password, command)

		SpringSecurityUtils.securityConfig.ui.password.maxLength = 100

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordRegex(password, command)

		assertNull RegisterController.passwordValidator(password, command)
	}

	void testPasswordValidator_Regex() {

		SpringSecurityUtils.setSecurityConfig [:] as ConfigObject

		def command = [username: 'username']
		String password = 'password'

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertFalse RegisterController.checkPasswordRegex(password, command)

		GroovyTestCase.assertEquals 'command.password.error.strength',
			RegisterController.passwordValidator(password, command)

		password = 'h!Z7abcd'

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordRegex(password, command)

		assertNull RegisterController.passwordValidator(password, command)

		SpringSecurityUtils.securityConfig.ui.password.validationRegex = '^.*s3cr3t.*$'

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertFalse RegisterController.checkPasswordRegex(password, command)

		GroovyTestCase.assertEquals 'command.password.error.strength',
			RegisterController.passwordValidator(password, command)

		password = '123_s3cr3t_asd'

		Assert.assertTrue RegisterController.checkPasswordMinLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordMaxLength(password, command)
		Assert.assertTrue RegisterController.checkPasswordRegex(password, command)

		assertNull RegisterController.passwordValidator(password, command)
	}

	@Override
	protected void tearDown() {
		super.tearDown()
		SpringSecurityUtils.resetSecurityConfig()
	}
}
