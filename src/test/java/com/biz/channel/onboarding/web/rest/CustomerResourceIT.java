package com.biz.channel.onboarding.web.rest;

import com.biz.channel.onboarding.BbxCustomeronboardingApp;
import com.biz.channel.onboarding.domain.Customer;
import com.biz.channel.onboarding.repository.CustomerRepository;
import com.biz.channel.onboarding.service.CustomerService;
import com.biz.channel.onboarding.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.biz.channel.onboarding.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CustomerResource} REST controller.
 */
@SpringBootTest(classes = BbxCustomeronboardingApp.class)
public class CustomerResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AGREE_TERMS = false;
    private static final Boolean UPDATED_IS_AGREE_TERMS = true;

    private static final Boolean DEFAULT_IS_AGREE_POLICY = false;
    private static final Boolean UPDATED_IS_AGREE_POLICY = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ACTIVATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerResource customerResource = new CustomerResource(customerService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .password(DEFAULT_PASSWORD)
            .isAgreeTerms(DEFAULT_IS_AGREE_TERMS)
            .isAgreePolicy(DEFAULT_IS_AGREE_POLICY)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .activatedDate(DEFAULT_ACTIVATED_DATE)
            .status(DEFAULT_STATUS);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .password(UPDATED_PASSWORD)
            .isAgreeTerms(UPDATED_IS_AGREE_TERMS)
            .isAgreePolicy(UPDATED_IS_AGREE_POLICY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .status(UPDATED_STATUS);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomer.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testCustomer.isIsAgreeTerms()).isEqualTo(DEFAULT_IS_AGREE_TERMS);
        assertThat(testCustomer.isIsAgreePolicy()).isEqualTo(DEFAULT_IS_AGREE_POLICY);
        assertThat(testCustomer.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCustomer.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCustomer.getActivatedDate()).isEqualTo(DEFAULT_ACTIVATED_DATE);
        assertThat(testCustomer.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].isAgreeTerms").value(hasItem(DEFAULT_IS_AGREE_TERMS.booleanValue())))
            .andExpect(jsonPath("$.[*].isAgreePolicy").value(hasItem(DEFAULT_IS_AGREE_POLICY.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].activatedDate").value(hasItem(DEFAULT_ACTIVATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.isAgreeTerms").value(DEFAULT_IS_AGREE_TERMS.booleanValue()))
            .andExpect(jsonPath("$.isAgreePolicy").value(DEFAULT_IS_AGREE_POLICY.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.activatedDate").value(DEFAULT_ACTIVATED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .password(UPDATED_PASSWORD)
            .isAgreeTerms(UPDATED_IS_AGREE_TERMS)
            .isAgreePolicy(UPDATED_IS_AGREE_POLICY)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .activatedDate(UPDATED_ACTIVATED_DATE)
            .status(UPDATED_STATUS);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomer.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testCustomer.isIsAgreeTerms()).isEqualTo(UPDATED_IS_AGREE_TERMS);
        assertThat(testCustomer.isIsAgreePolicy()).isEqualTo(UPDATED_IS_AGREE_POLICY);
        assertThat(testCustomer.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCustomer.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCustomer.getActivatedDate()).isEqualTo(UPDATED_ACTIVATED_DATE);
        assertThat(testCustomer.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }
}
