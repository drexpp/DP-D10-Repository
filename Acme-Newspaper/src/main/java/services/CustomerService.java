
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Customer;
import domain.Newspaper;
import forms.ActorForm;

@Service
@Transactional
public class CustomerService {

	// Managed Repository
	@Autowired
	private CustomerRepository	customerRepository;
	
	@Autowired
	private ActorService	actorService;
	
	@Autowired
	private Validator		validator;


	// Supporting services

	// Constructors

	public CustomerService() {
		super();
	}

	// Simple CRUD methods
	public Customer create() {
		Customer result;

		result = new Customer();
		result.setNewspapers(new ArrayList<Newspaper>());
		
		
		return result;
	}

	public Customer save(final Customer customer) {
		Customer saved;
		Assert.notNull(customer);
		Actor principal = null;
		
		try{
			principal =  this.actorService.findByPrincipal();
		}catch(Throwable oops){
		}
		
		//TEST ASSERT - Testing if someone is trying to register while he/she is already being registered to the system at the moment
		Assert.isTrue(principal == null);
		//TEST ASSERT ======================================
		

		if (customer.getId() == 0) {
			final Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
			customer.getUserAccount().setPassword(passwordEncoder.encodePassword(customer.getUserAccount().getPassword(), null));
		}
		

		saved = this.customerRepository.save(customer);
		
		//TEST ASSERT - Testing if the customer is in the system after saving him/her
		Assert.isTrue(this.customerRepository.findAll().contains(saved));
		//TEST ASSERT =========================================
		return saved;
	}

	public Customer findOne(final int customerId) {
		Customer result;
		result = this.customerRepository.findOne(customerId);
		return result;
	}

	public Collection<Customer> findAll() {
		Collection<Customer> result;
		result = this.customerRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	//Other business methods
	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;

	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Customer result;
		result = this.customerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	public Customer reconstruct(final ActorForm actorForm, final BindingResult binding) {
		final Customer customer = this.create();
		customer.setName(actorForm.getName());
		customer.setSurname(actorForm.getSurname());
		customer.setEmail(actorForm.getEmail());
		customer.setId(actorForm.getId());
		customer.setVersion(actorForm.getVersion());
		customer.setPhone(actorForm.getPhone());
		customer.setUserAccount(actorForm.getUserAccount());
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority auth = new Authority();
		auth.setAuthority("CUSTOMER");
		authorities.add(auth);
		customer.getUserAccount().setAuthorities(authorities);

		this.validator.validate(actorForm, binding);
		if (!(actorForm.getConfirmPassword().equals((actorForm.getUserAccount().getPassword()))) || actorForm.getConfirmPassword() == null)
			binding.rejectValue("confirmPassword", "user.passwordMiss");
		if ((actorForm.getCheck() == false))
			binding.rejectValue("check", "user.uncheck");
		return customer;
	}
}