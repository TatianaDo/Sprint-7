package ru.sber.orm.dao

import org.hibernate.SessionFactory
import ru.sber.orm.entities.Appointment
import ru.sber.orm.entities.Employee

class AppointmentDAO(private val sessionFactory: SessionFactory
) {
    fun save(appointment: Appointment) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(appointment)
            session.transaction.commit()
        }
    }

    fun update(appointment: Appointment) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.update(appointment)
            session.transaction.commit()
        }
    }

    fun delete(appointment: Appointment) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.delete(appointment)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Appointment? {
        val result: Appointment?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Appointment::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun findByEmployee(employee: Employee): Appointment? {
        val result: Appointment?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            var query = session.createQuery("from Appointment where employee = :employee and dateEnd is null")
            query.setParameter("employee", employee)
            result = query.uniqueResult() as Appointment
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Appointment> {
        val result: List<Appointment>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Appointment").list() as List<Appointment>
            session.transaction.commit()
        }
        return result
    }
}
