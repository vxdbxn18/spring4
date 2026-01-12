package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findByCar(String model, int series) {
      Session session = sessionFactory.getCurrentSession();

      TypedQuery<User> query = session.createQuery(
              "select u from User u join u.car c " +
                      "where c.model = :model and c.series = :series " +
                      "order by u.id",
              User.class);

      query.setParameter("model", model);
      query.setParameter("series", series);

      query.setMaxResults(1);
      List<User> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
   }


}
