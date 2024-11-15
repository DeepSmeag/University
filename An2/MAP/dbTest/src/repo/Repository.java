package repo;

import ent.Entity;

public interface Repository<ID, E extends Entity<ID>> {
    public E findOne(ID id);
    public Iterable<E> findAll();
    public E save(E entity);
    public E delete(ID id);
    public E update(E entity);

}
