package library.service;

import org.springframework.data.domain.Example;

public interface FindOrCreate<T,ID> {
	/*
	 * Finds an existing entity or creates a new instance of one if no existing entity is found.
	 */
	<S extends T> S findOrCreate(Example<S> example, ID id);
	
}
