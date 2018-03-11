package edu.vt.crest.hr.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import edu.vt.crest.hr.entity.DepartmentEntity;
import edu.vt.crest.hr.entity.EmployeeEntity;

/**
 * Implements an EmployeeService
 */
@ApplicationScoped
public class EmployeeServiceBean implements EmployeeService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity createEmployee(EmployeeEntity employee) {
	 	em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		return employee;  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity findById(Long id) {
	  	em.getTransaction().begin();
		EmployeeEntity employee = em.find(EmployeeEntity.class, id);
		em.getTransaction().commit();
		return employee;  
		}

  /**
   * {@inheritDoc}
   */
  @Override
  public List<EmployeeEntity> listAll(Integer startPosition, Integer maxResult) {
	  CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<EmployeeEntity> criteriaQuery = criteriaBuilder.createQuery(EmployeeEntity.class);
		Root<EmployeeEntity> from = criteriaQuery.from(EmployeeEntity.class);
		CriteriaQuery<EmployeeEntity> select = criteriaQuery.select(from);
		TypedQuery<EmployeeEntity> typedQuery = em.createQuery(select);
		typedQuery.setFirstResult(startPosition);
		typedQuery.setMaxResults(maxResult);
		List<EmployeeEntity> employees = typedQuery.getResultList();
		return employees;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity update(Long id, EmployeeEntity employee) throws OptimisticLockException {
	  em.getTransaction().begin();
		EmployeeEntity employeeEntity = em.merge(employee);
		em.getTransaction().commit();
		return employeeEntity;
  }
}
