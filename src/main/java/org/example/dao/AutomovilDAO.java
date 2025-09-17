package org.example.dao;

// Importamos la clase modelo Automovil
import org.example.model.Automovil;

// Importaciones de JPA / Jakarta Persistence
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List; // Para manejar listas de automóviles

// Clase DAO: maneja todas las operaciones de la base de datos para Automovil
public class AutomovilDAO {

    // Creamos una fábrica de EntityManager a partir del persistence unit definido en persistence.xml
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ventaautosPU");

    // --- INSERTAR un automóvil ---
    public void insertar(Automovil a) {
        EntityManager em = emf.createEntityManager(); // Creamos un EntityManager para esta operación
        try {
            em.getTransaction().begin();   // Iniciamos la transacción
            em.persist(a);                 // Guardamos el objeto Automovil en la base de datos
            em.getTransaction().commit();  // Confirmamos los cambios (commit)
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback(); // Si algo falla, deshacer cambios
            em.close();                    // Cerramos el EntityManager
        }
    }

    // --- LISTAR todos los automóviles ---
    public List<Automovil> listar() {
        EntityManager em = emf.createEntityManager();
        try {
            // JPQL: consulta los objetos Automovil en la base de datos
            return em.createQuery("SELECT a FROM Automovil a", Automovil.class)
                    .getResultList(); // Devuelve una lista de Automovil
        } finally {
            em.close(); // Siempre cerrar el EntityManager
        }
    }

    // --- BUSCAR un automóvil por matrícula ---
    public Automovil buscarPorMatricula(String matricula) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Automovil.class, matricula); // Busca usando la clave primaria
        } finally {
            em.close();
        }
    }

    // --- ACTUALIZAR un automóvil ---
    public void actualizar(Automovil a) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(a); // Merge actualiza el objeto en la base de datos
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback(); // deshacer cambios si falla
            em.close();
        }
    }

    // --- ELIMINAR un automóvil por matrícula ---
    public void eliminar(String matricula) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Automovil a = em.find(Automovil.class, matricula); // Buscar objeto a eliminar
            if (a != null) {
                em.remove(a); // Eliminar solo si existe
            }
            em.getTransaction().commit(); // Confirmar eliminación
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback(); // deshacer si falla
            em.close();
        }
    }
}

