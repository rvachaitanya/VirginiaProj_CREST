package edu.vt.crest.hr.services;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import edu.vt.crest.hr.entity.DepartmentEntity;

/**
 * Implements a DepartmentService
 */
@ApplicationScoped
public class DepartmentServiceBean implements DepartmentService {

	@PersistenceContext
	private EntityManager em;

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("cresthr");

	public DepartmentServiceBean() {
		em = emf.createEntityManager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentEntity createDepartment(DepartmentEntity department) {

		em.getTransaction().begin();
		em.persist(department);
		em.getTransaction().commit();
		return department;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentEntity findById(Long id) {
		em.getTransaction().begin();
		DepartmentEntity department = em.find(DepartmentEntity.class, id);
		em.getTransaction().commit();
		return department;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<DepartmentEntity> listAll(Integer startPosition, Integer maxResult) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<DepartmentEntity> criteriaQuery = criteriaBuilder.createQuery(DepartmentEntity.class);
		Root<DepartmentEntity> from = criteriaQuery.from(DepartmentEntity.class);
		CriteriaQuery<DepartmentEntity> select = criteriaQuery.select(from);
		TypedQuery<DepartmentEntity> typedQuery = em.createQuery(select);
		typedQuery.setFirstResult(startPosition);
		typedQuery.setMaxResults(maxResult);
		List<DepartmentEntity> departments = typedQuery.getResultList();
		return departments;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DepartmentEntity update(Long id, DepartmentEntity department) throws OptimisticLockException {
		em.getTransaction().begin();
		DepartmentEntity departmentEntity = em.merge(department);
		em.getTransaction().commit();
		return departmentEntity;
	}

}
